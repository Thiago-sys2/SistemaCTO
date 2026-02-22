package thiago.github.SistemaCTO.dtos.sensor;

import org.springframework.stereotype.Component;
import thiago.github.SistemaCTO.model.Sensor;

@Component
public class SensorMapper {

    public SensorResponseDTO toDTO(Sensor sensor) {
        SensorResponseDTO dto = new SensorResponseDTO();
        dto.setId(sensor.getId());
        dto.setStatus(sensor.getStatus());
        dto.setCause(sensor.getCause());
        dto.setCtoId(sensor.getCto().getId());
        dto.setEventDate(sensor.getEventDate());

        return dto;
    }

    public Sensor toEntity(SensorCreateDTO dto) {
        Sensor sensor = new Sensor();
        sensor.setStatus(dto.getStatus());
        sensor.setCause(dto.getCause());
        sensor.setEventDate(dto.getEventDate());

        return sensor;
    }
}
