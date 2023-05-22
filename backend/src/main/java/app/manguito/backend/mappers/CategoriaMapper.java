package app.manguito.backend.mappers;

import app.manguito.backend.entities.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    default String toString(Categoria categoria) {
        if (categoria == null) return null;
        return categoria.getNombre();
    }

    List<String> toStringList(List<Categoria> categorias);
}
