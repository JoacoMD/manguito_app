package app.manguito.backend.repositories;

import app.manguito.backend.entities.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {

    Emprendimiento findByUrl(String url);
}