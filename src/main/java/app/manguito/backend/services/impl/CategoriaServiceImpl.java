package app.manguito.backend.services.impl;

import app.manguito.backend.dto.UpdateCategoriasDTO;
import app.manguito.backend.entities.Categoria;
import app.manguito.backend.mappers.CategoriaMapper;
import app.manguito.backend.repositories.CategoriaRepository;
import app.manguito.backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    CategoriaMapper categoriaMapper;

    public List<String> getAll() {
        return categoriaMapper.toStringList(categoriaRepository.findAll());
    }

    @Override
    public void updateCategorias(UpdateCategoriasDTO dto) {
        this.categoriaRepository.deleteAllByNombreIn(dto.getRemovedCategorias());
        dto.getAddedCategorias().forEach(categoria -> this.categoriaRepository.save(new Categoria(categoria)));
    }
}
