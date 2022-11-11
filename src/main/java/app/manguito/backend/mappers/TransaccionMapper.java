package app.manguito.backend.mappers;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransaccionMapper {

    TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);

    DonacionManguitoDTO toManguitoDTO(TransaccionManguito transaccionManguito);

    @Mapping(target = "plan", source = "planComprado")
    SuscripcionDTO toSuscripcionDTO(Suscripcion suscripcionDTO);

    List<DonacionManguitoDTO> toManguitoDTOList(List<TransaccionManguito> transacciones);
    List<SuscripcionDTO> toSuscripcionDTOList(List<Suscripcion> transacciones);
}
