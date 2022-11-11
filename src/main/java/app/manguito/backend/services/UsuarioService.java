package app.manguito.backend.services;

import app.manguito.backend.dto.NuevoUsuarioDTO;
import app.manguito.backend.entities.Usuario;

public interface UsuarioService {

    void saveUsuario(NuevoUsuarioDTO usuario);

    Boolean existsUserOrEmprendimientoUrl(NuevoUsuarioDTO dto);

    Usuario findUsuarioByMail(String mail);
}
