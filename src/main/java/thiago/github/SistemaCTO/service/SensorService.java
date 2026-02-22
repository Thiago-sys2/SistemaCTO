package thiago.github.SistemaCTO.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import thiago.github.SistemaCTO.dtos.sensor.SensorCreateDTO;
import thiago.github.SistemaCTO.dtos.sensor.SensorMapper;
import thiago.github.SistemaCTO.dtos.sensor.SensorResponseDTO;
import thiago.github.SistemaCTO.dtos.sns.SensorEventDTO;
import thiago.github.SistemaCTO.model.Cto;
import thiago.github.SistemaCTO.model.Sensor;
import thiago.github.SistemaCTO.model.enums.StatusSensor;
import thiago.github.SistemaCTO.repository.CtoRepository;
import thiago.github.SistemaCTO.repository.SensorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final CtoRepository ctoRepository;
    private final SensorMapper sensorMapper;

    public SensorResponseDTO create(SensorCreateDTO dto) {

        Cto cto = ctoRepository.findById(dto.getCtoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "There is no CTO with that ID:" + dto.getCtoId()
                ));

        Sensor sensor = sensorMapper.toEntity(dto);
        sensor.setCto(cto);

        sensor = sensorRepository.save(sensor);

        return sensorMapper.toDTO(sensor);

    }

    public List<SensorResponseDTO> findAll(Long ctoId) {
        if (!ctoRepository.existsById(ctoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CTO not found with this ID:" + ctoId
            );
        }

        return sensorRepository.findByCtoId(ctoId)
                .stream()
                .map(sensorMapper::toDTO)
                .toList();
    }

    @Transactional
    public void processEvent(SensorEventDTO dto) {

        Cto cto = ctoRepository.findById(dto.getCtoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "CTO not found with ID: " + dto.getCtoId()
                ));

        Sensor sensor = new Sensor();
        sensor.setCto(cto);
        sensor.setStatus(dto.getStatus());
        sensor.setCause(dto.getCause());
        sensor.setEventDate(dto.getEventDate());

        sensorRepository.save(sensor);
    }

    public boolean existsAlarmedSensor(Long ctoId) {
        return sensorRepository.existsByCtoIdAndStatus(
                ctoId,
                StatusSensor.ALARMED
        );
    }
}
