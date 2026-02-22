package thiago.github.SistemaCTO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thiago.github.SistemaCTO.model.Sensor;
import thiago.github.SistemaCTO.model.enums.StatusSensor;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByCtoId(Long ctoId);

    boolean existsByCtoIdAndStatus(Long ctoId, StatusSensor status);
}
