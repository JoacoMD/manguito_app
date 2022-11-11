package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class DonacionDTO {

    private Double monto;

    private String nombre;

    private String contacto;

    private String mensaje;

    private LocalDateTime fecha;
}
