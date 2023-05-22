package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanDTO {

    private Long id;

    private String nombre;

    private Double precio;

    private String descripcion;
}
