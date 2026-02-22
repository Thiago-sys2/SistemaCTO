package thiago.github.SistemaCTO.dtos.model;

import org.springframework.stereotype.Component;
import thiago.github.SistemaCTO.model.Models;

@Component
public class ModelsMapper {

    public ModelsResponseDTO toDTO(Models models) {
        ModelsResponseDTO dto = new ModelsResponseDTO();
        dto.setId(models.getId());
        dto.setName(models.getName());
        dto.setManufacturer(models.getManufacturer());

        return dto;
    }

    public Models toEntity(ModelsCreateDTO dto) {
        Models models = new Models();
        models.setName(dto.getName());
        models.setManufacturer(dto.getManufacturer());


        return models;
    }
}
