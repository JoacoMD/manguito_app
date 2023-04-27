package app.manguito.backend.mappers;

import app.manguito.backend.dto.RedSocialEmprendimientoDTO;
import app.manguito.backend.entities.RedSocialEmprendimiento;
import app.manguito.backend.repositories.RedSocialRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RedSocialRepository.class})
public interface RedSocialEmprendimientoMapper {

    @Mapping(target = "redSocial", source = "redSocial.nombre")
    RedSocialEmprendimientoDTO toDTO(RedSocialEmprendimiento redSocial);

    RedSocialEmprendimiento toEntity(RedSocialEmprendimientoDTO redSocialDTO);

    List<RedSocialEmprendimiento> toEntityList(List<RedSocialEmprendimientoDTO> redSocialDTOList);

    List<RedSocialEmprendimientoDTO> toDTOList(List<RedSocialEmprendimiento> redSocialList);
}
