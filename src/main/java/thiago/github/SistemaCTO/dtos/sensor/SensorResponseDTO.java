package thiago.github.SistemaCTO.dtos.sensor;

import lombok.Data;
import lombok.NoArgsConstructor;
import thiago.github.SistemaCTO.model.enums.StatusSensor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SensorResponseDTO {

    private Long id;
    private StatusSensor status;
    private LocalDate eventDate;
    private String cause;
    private Long ctoId;
}
