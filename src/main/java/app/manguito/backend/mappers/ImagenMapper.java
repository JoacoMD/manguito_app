package app.manguito.backend.mappers;

import app.manguito.backend.dto.ImagenDTO;
import app.manguito.backend.entities.Imagen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
        FileMapper.class
})
public interface ImagenMapper {

    ImagenMapper INSTANCE = Mappers.getMapper( ImagenMapper.class );

    @Mapping(target = "width", source = "width")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "archivo", source = "archivo")
    Imagen toEntity (ImagenDTO dto);

    @Mapping(target = "width", source = "width")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "archivo", source = "archivo")
    ImagenDTO toDTO (Imagen imagen);
}
