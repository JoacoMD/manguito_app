package app.manguito.backend.repositories;

import app.manguito.backend.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}