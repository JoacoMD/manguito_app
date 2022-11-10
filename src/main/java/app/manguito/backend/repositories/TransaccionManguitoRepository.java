package app.manguito.backend.repositories;

import app.manguito.backend.entities.TransaccionManguito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionManguitoRepository extends JpaRepository<TransaccionManguito, Long> {
}