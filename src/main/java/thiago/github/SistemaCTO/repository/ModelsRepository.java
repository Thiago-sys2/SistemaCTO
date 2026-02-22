package thiago.github.SistemaCTO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thiago.github.SistemaCTO.model.Models;

public interface ModelsRepository extends JpaRepository<Models, Long> {
    boolean existsByNameAndManufacturer(String name, String manufacturer);
}
