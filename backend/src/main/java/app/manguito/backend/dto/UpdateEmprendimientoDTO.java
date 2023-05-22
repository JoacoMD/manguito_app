package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmprendimientoDTO extends EmprendimientoDTO {

    private SaveImagenDTO newImagenPerfil;

    private SaveImagenDTO newBanner;
}
