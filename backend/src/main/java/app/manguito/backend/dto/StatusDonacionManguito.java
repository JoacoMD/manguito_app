package app.manguito.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusDonacionManguito {

    private String externalReference;
    private String estado;
    private String nombreEmprendimiento;
    private String urlEmprendimiento;
    private Integer cantidadManguitos;
    private Double montoTotal;
}
