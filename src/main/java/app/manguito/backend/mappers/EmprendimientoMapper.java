package app.manguito.backend.mappers;

import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.repositories.CategoriaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {
                FileMapper.class,
                CategoriaMapper.class,
                CategoriaRepository.class
        })
public interface EmprendimientoMapper {

    EmprendimientoMapper INSTANCE = Mappers.getMapper(EmprendimientoMapper.class);

    EmprendimientoDTO toDTO(Emprendimiento emprendimiento);

    Emprendimiento toEntity(EmprendimientoDTO emprendimientoDTO);
}
