package thiago.github.SistemaCTO.dtos.cto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CtoUpdateDTO {
    private String name;
    private Double longitude;
    private Double latitude;
}
