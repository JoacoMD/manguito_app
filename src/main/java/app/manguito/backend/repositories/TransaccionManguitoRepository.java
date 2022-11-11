package app.manguito.backend.repositories;

import app.manguito.backend.entities.TransaccionManguito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionManguitoRepository extends JpaRepository<TransaccionManguito, Long> {

    List<TransaccionManguito> findAllByDestinatario_Url(String url);
}