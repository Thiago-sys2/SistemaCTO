package thiago.github.SistemaCTO.dtos.sensor;

import lombok.Data;
import lombok.NoArgsConstructor;
import thiago.github.SistemaCTO.model.enums.StatusSensor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SensorCreateDTO {

    private LocalDate eventDate;
    private StatusSensor status;
    private String cause;
    private Long ctoId;
}
