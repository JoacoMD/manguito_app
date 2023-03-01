package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedSocialDTO {

    private Long id;

    private String nombre;

    private SaveImagenDTO icono;
}
