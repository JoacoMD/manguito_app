package app.manguito.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCategoriasDTO {

    private List<String> addedCategorias;

    private List<String> removedCategorias;
}
