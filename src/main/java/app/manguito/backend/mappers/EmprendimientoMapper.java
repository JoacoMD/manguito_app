package app.manguito.backend.mappers;

import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
        FileMapper.class,
        CategoriaMapper.class
})
public interface EmprendimientoMapper {

    public static EmprendimientoMapper INSTANCE = Mappers.getMapper(EmprendimientoMapper.class);

    EmprendimientoDTO toDTO(Emprendimiento emprendimiento);
}
