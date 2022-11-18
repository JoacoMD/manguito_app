package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevaDonacionDTO<T extends DonacionDTO> {

    String emprendimiento;

    T donacion;
}
