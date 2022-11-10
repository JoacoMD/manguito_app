package app.manguito.backend.repositories;

import app.manguito.backend.entities.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
}