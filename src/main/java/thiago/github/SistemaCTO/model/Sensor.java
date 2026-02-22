package thiago.github.SistemaCTO.model;

import jakarta.persistence.*;
import lombok.Data;
import thiago.github.SistemaCTO.model.enums.StatusSensor;

import java.time.LocalDate;

@Entity
@Table(name = "sensors")
@Data
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSensor status;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "cause")
    private String cause;

    @ManyToOne
    @JoinColumn(name = "cto_id")
    private Cto cto;
}
