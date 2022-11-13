package app.manguito.backend.mappers;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface TransaccionMapper {

    DonacionManguitoDTO toManguitoDTO(TransaccionManguito transaccionManguito);

    @Mapping(target = "destinatario.id", source = "emprendimientoId")
    @Mapping(target = "fecha", expression = "java(LocalDateTime.now())")
    TransaccionManguito toNuevoManguito(DonacionManguitoDTO donacionManguitoDTO, Long emprendimientoId);

    @Mapping(target = "destinatario.id", source = "emprendimientoId")
    @Mapping(target = "fecha", expression = "java(LocalDateTime.now())")
    @Mapping(target = "planComprado.id", source = "suscripcionDTO.plan.id")
    Suscripcion toNuevaSuscripcion(SuscripcionDTO suscripcionDTO, Long emprendimientoId);

    @Mapping(target = "plan", source = "planComprado")
    SuscripcionDTO toSuscripcionDTO(Suscripcion suscripcionDTO);

    List<DonacionManguitoDTO> toManguitoDTOList(List<TransaccionManguito> transacciones);
    List<SuscripcionDTO> toSuscripcionDTOList(List<Suscripcion> transacciones);
}
