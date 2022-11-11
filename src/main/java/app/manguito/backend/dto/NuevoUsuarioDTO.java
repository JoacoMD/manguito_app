package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevoUsuarioDTO {

    private String mail;

    private String password;

    private String passwordConfirmation;

    private EmprendimientoDTO emprendimiento;

}
