package app.manguito.backend.mappers;

import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.entities.Imagen;
import app.manguito.backend.repositories.CategoriaRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                ImagenMapper.class,
                CategoriaMapper.class,
                CategoriaRepository.class
        })
public interface EmprendimientoMapper {

    EmprendimientoDTO toDTO(Emprendimiento emprendimiento);

    @Mapping(target = "imagenPerfil", ignore = true)
    @Mapping(target = "banner", ignore = true)
    Emprendimiento toEntity(EmprendimientoDTO emprendimientoDTO);

    @Mapping(target = "actual.nombreEmprendimiento", source = "changes.nombreEmprendimiento")
    @Mapping(target = "actual.descripcion", source = "changes.descripcion")
    @Mapping(target = "actual.precioManguito", source = "changes.precioManguito")
    @Mapping(target = "actual.mostrarTopDonadores", source = "changes.mostrarTopDonadores")
    @Mapping(target = "actual.ocultarManguitosRecibidos", source = "changes.ocultarManguitosRecibidos")
    @Mapping(target = "actual.categorias", source = "changes.categorias", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "actual.redesSociales", source = "changes.redesSociales")
    @Mapping(target = "actual.planes", source = "changes.planes")
    @BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Emprendimiento update(@MappingTarget Emprendimiento actual, EmprendimientoDTO changes);

    List<EmprendimientoDTO> toDTOList(List<Emprendimiento> emprendimientos);
}
