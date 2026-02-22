package thiago.github.SistemaCTO.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import thiago.github.SistemaCTO.dtos.model.ModelsCreateDTO;
import thiago.github.SistemaCTO.dtos.model.ModelsMapper;
import thiago.github.SistemaCTO.dtos.model.ModelsResponseDTO;
import thiago.github.SistemaCTO.dtos.model.ModelsUpdateDTO;
import thiago.github.SistemaCTO.model.Models;
import thiago.github.SistemaCTO.repository.ModelsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelsService {

    private final ModelsRepository modelsRepository;
    private final ModelsMapper modelsMapper;

    public ModelsResponseDTO create(ModelsCreateDTO dto) {
        if (modelsRepository.existsByNameAndManufacturer(dto.getName(), dto.getManufacturer())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "There is already a model with that name and manufacturer."
            );
        }

        Models models = modelsMapper.toEntity(dto);

        models = modelsRepository.save(models);

        return modelsMapper.toDTO(models);
    }

    public ModelsResponseDTO update(Long id, ModelsUpdateDTO dto) {
        Models models = modelsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Model not found with this ID:" + id
                ));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            models.setName(dto.getName());
        }
        if (dto.getManufacturer() != null && !dto.getManufacturer().isBlank()) {
            models.setManufacturer(dto.getManufacturer());
        }

        modelsRepository.save(models);

        return modelsMapper.toDTO(models);
    }

    public List<ModelsResponseDTO> findAll() {
        return modelsRepository.findAll()
                .stream()
                .map(modelsMapper::toDTO)
                .toList();
    }

    public ModelsResponseDTO findById(Long id) {
        Models models = modelsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Model not found with this ID:" + id
                ));

        return modelsMapper.toDTO(models);
    }

    public void delete(Long id) {
        Models models = modelsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Model not found with this ID:" + id
                ));

        modelsRepository.delete(models);
    }
}
