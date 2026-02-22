package thiago.github.SistemaCTO.dtos.cto;

import org.springframework.stereotype.Component;
import thiago.github.SistemaCTO.dtos.model.ModelsResponseDTO;
import thiago.github.SistemaCTO.model.Cto;
import thiago.github.SistemaCTO.model.Models;

@Component
public class CtoMapper {

    public CtoResponseDTO toDTO(Cto cto) {
        CtoResponseDTO dto = new CtoResponseDTO();
        dto.setId(cto.getId());
        dto.setName(cto.getName());
        dto.setLatitude(cto.getLatitude());
        dto.setLongitude(cto.getLongitude());

        if (cto.getModels() != null) {
            ModelsResponseDTO modelDTO = new ModelsResponseDTO();
            modelDTO.setId(cto.getModels().getId());
            modelDTO.setName(cto.getModels().getName());
            modelDTO.setManufacturer(cto.getModels().getManufacturer());

            dto.setModels(modelDTO);
        }

        return dto;
    }

    public Cto toEntity(CtoCreateDTO dto, Models models) {
        Cto cto = new Cto();
        cto.setName(dto.getName());
        cto.setLongitude(dto.getLongitude());
        cto.setLatitude(dto.getLatitude());
        cto.setModels(models);

        return cto;
    }
}
