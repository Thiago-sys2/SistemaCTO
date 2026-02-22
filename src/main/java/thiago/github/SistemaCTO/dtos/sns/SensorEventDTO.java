package thiago.github.SistemaCTO.dtos.sns;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import thiago.github.SistemaCTO.model.enums.StatusSensor;

import java.time.LocalDate;

@NoArgsConstructor
public class SensorEventDTO {

    private Long ctoId;
    private StatusSensor status;
    private String cause;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    public Long getCtoId() {
        return ctoId;
    }

    public void setCtoId(Long ctoId) {
        this.ctoId = ctoId;
    }

    public StatusSensor getStatus() {
        return status;
    }

    public void setStatus(StatusSensor status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
}
