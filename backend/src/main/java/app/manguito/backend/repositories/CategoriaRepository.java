package app.manguito.backend.repositories;

import app.manguito.backend.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findAllByNombreIn(List<String> nombres);

    void deleteAllByNombreIn(List<String> nombres);
}