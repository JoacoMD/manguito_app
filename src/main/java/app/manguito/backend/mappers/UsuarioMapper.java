package app.manguito.backend.mappers;

import app.manguito.backend.dto.NuevoUsuarioDTO;
import app.manguito.backend.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = {
        EmprendimientoMapper.class
})
public interface UsuarioMapper {

    @Mapping(target = "emprendimiento", ignore = true)
    Usuario toNewUser(NuevoUsuarioDTO dto);
}
