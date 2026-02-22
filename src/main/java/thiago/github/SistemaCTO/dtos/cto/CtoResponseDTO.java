package thiago.github.SistemaCTO.dtos.cto;

import lombok.Data;
import lombok.NoArgsConstructor;
import thiago.github.SistemaCTO.dtos.model.ModelsResponseDTO;

@Data
@NoArgsConstructor
public class CtoResponseDTO {

    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
    private ModelsResponseDTO models;
}
