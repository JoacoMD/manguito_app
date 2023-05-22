package app.manguito.backend.services;

import app.manguito.backend.dto.UpdateCategoriasDTO;

import java.util.List;

public interface CategoriaService {

    List<String> getAll();

    void updateCategorias(UpdateCategoriasDTO dto);
}
