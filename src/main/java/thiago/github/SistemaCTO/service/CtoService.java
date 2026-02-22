package thiago.github.SistemaCTO.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import thiago.github.SistemaCTO.dtos.cto.CtoMapper;
import thiago.github.SistemaCTO.dtos.cto.CtoResponseDTO;
import thiago.github.SistemaCTO.dtos.cto.CtoCreateDTO;
import thiago.github.SistemaCTO.dtos.cto.CtoUpdateDTO;
import thiago.github.SistemaCTO.model.Cto;
import thiago.github.SistemaCTO.model.Models;
import thiago.github.SistemaCTO.model.enums.StatusSensor;
import thiago.github.SistemaCTO.repository.CtoRepository;
import thiago.github.SistemaCTO.repository.ModelsRepository;
import thiago.github.SistemaCTO.repository.SensorRepository;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CtoService {

    private final CtoRepository ctoRepository;
    private final SensorRepository sensorRepository;
    private final ModelsRepository modelsRepository;
    private final SnsClient snsClient;
    private final String topicArn = "arn:aws:sns:us-east-1:000000000000:events-sensor";
    private final CtoMapper ctoMapper;

    public CtoResponseDTO createCto(CtoCreateDTO dto) {
        if (ctoRepository.existsByName(dto.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "There is already a CTO with that name."
            );
        }

        Models models = modelsRepository.findById(dto.getModelId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Model not found."
                ));

        Cto cto = ctoMapper.toEntity(dto, models);
        cto = ctoRepository.save(cto);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> payload = new HashMap<>();
        payload.put("ctoId", cto.getId());
        payload.put("status", "ALARMED");

        String message = mapper.writeValueAsString(payload);

        snsClient.publish(PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build());

        return ctoMapper.toDTO(cto);
    }

    public CtoResponseDTO updateCto(Long ctoId, CtoUpdateDTO dto) {
        Cto cto = ctoRepository.findById(ctoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "There is no CTO with that ID:" + ctoId
                ));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            cto.setName(dto.getName());
        }
        if (dto.getLatitude() != null) {
            cto.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != null) {
            cto.setLongitude(dto.getLongitude());
        }

        ctoRepository.save(cto);

        return ctoMapper.toDTO(cto);
    }

    public List<CtoResponseDTO> findAll() {
        return ctoRepository.findAll()
                .stream()
                .map(ctoMapper::toDTO)
                .toList();
    }

    public CtoResponseDTO findById(Long ctoId) {
        Cto cto = ctoRepository.findById(ctoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "There is no CTO with that ID:" + ctoId
                ));

        return ctoMapper.toDTO(cto);
    }

    public void delete(Long ctoId) {
        Cto cto = ctoRepository.findById(ctoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "There is no CTO with that ID:" + ctoId
                ));

        boolean hasAlarmedSensor =
                sensorRepository.existsByCtoIdAndStatus(ctoId, StatusSensor.ALARMED);

        if (hasAlarmedSensor){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "It is not permitted to delete a CTO that has an alarmed sensor."
            );
        }

        ctoRepository.delete(cto);
    }
}
