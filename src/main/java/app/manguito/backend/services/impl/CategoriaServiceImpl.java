package app.manguito.backend.services.impl;

import app.manguito.backend.dto.UpdateCategoriasDTO;
import app.manguito.backend.entities.Categoria;
import app.manguito.backend.exception.AppException;
import app.manguito.backend.mappers.CategoriaMapper;
import app.manguito.backend.repositories.CategoriaRepository;
import app.manguito.backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    CategoriaMapper categoriaMapper;

    public List<String> getAll() {
        try {
            return categoriaMapper.toStringList(categoriaRepository.findAll());
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al recuperar todas las categorias", pe);
        }
    }

    @Override
    public void updateCategorias(UpdateCategoriasDTO dto) {
        try {
            this.categoriaRepository.deleteAllByNombreIn(dto.getRemovedCategorias());
            dto.getAddedCategorias().forEach(categoria -> this.categoriaRepository.save(new Categoria(categoria)));
        } catch (PersistenceException pe) {
            throw new AppException("Ocurrio un error al intentar actualizar las categorias", pe);
        }
    }
}
