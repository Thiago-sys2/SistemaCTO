package thiago.github.SistemaCTO.dtos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModelsResponseDTO {
    private Long id;
    private String name;
    private String manufacturer;
}
