package app.manguito.backend.mappers;

import app.manguito.backend.EstadoPago;
import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, EstadoPago.class})
public interface TransaccionMapper {

    DonacionManguitoDTO toManguitoDTO(TransaccionManguito transaccionManguito);

    @Mapping(target = "destinatario.id", source = "emprendimiento.id")
    @Mapping(target = "fecha", expression = "java(LocalDateTime.now())")
    @Mapping(target = "estado", expression = "java(EstadoPago.PENDIENTE.getCodigo())")
    @Mapping(target = "monto", expression = "java(donacionManguitoDTO.getCantidad() * emprendimiento.getPrecioManguito())")
    TransaccionManguito toNuevoManguito(DonacionManguitoDTO donacionManguitoDTO, Emprendimiento emprendimiento);

    @Mapping(target = "destinatario.id", source = "emprendimientoId")
    @Mapping(target = "fecha", expression = "java(LocalDateTime.now())")
    @Mapping(target = "estado", expression = "java(EstadoPago.PENDIENTE.getCodigo())")
    @Mapping(target = "planComprado.id", source = "planId")
    Suscripcion toNuevaSuscripcion(SuscripcionDTO suscripcionDTO, Long emprendimientoId, Long planId);

    @Mapping(target = "plan", source = "planComprado")
    SuscripcionDTO toSuscripcionDTO(Suscripcion suscripcionDTO);

    List<DonacionManguitoDTO> toManguitoDTOList(List<TransaccionManguito> transacciones);
    List<SuscripcionDTO> toSuscripcionDTOList(List<Suscripcion> transacciones);
}
