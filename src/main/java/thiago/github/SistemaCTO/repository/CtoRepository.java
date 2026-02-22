package thiago.github.SistemaCTO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thiago.github.SistemaCTO.model.Cto;

public interface CtoRepository extends JpaRepository<Cto, Long> {
    boolean existsByName(String name);
}
