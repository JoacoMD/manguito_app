package app.manguito.backend.services.impl;

import app.manguito.backend.EstadoPago;
import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.entities.*;
import app.manguito.backend.exception.BadRequestException;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.*;
import app.manguito.backend.services.DonacionService;
import app.manguito.backend.services.MercadoPagoService;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DonacionServiceImpl implements DonacionService {

    @Autowired
    private TransaccionManguitoRepository manguitoRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @Autowired
    private TransaccionMapper transaccionMapper;

    public String iniciarDonacionManguitos(NuevaDonacionDTO<DonacionManguitoDTO> donacion, boolean conMP) {
        Emprendimiento emprendimiento = emprendimientoRepository.findByUrl(donacion.getEmprendimiento());
        TransaccionManguito transaccionManguito = transaccionMapper.toNuevoManguito(donacion.getDonacion(), emprendimiento);
        if (!conMP) {
            transaccionManguito.setEstado(EstadoPago.APROBADO.getCodigo());
        }
        transaccionManguito = manguitoRepository.save(transaccionManguito);
        return conMP ? this.mercadoPagoService.crearCheckoutUrlManguitos(transaccionManguito, emprendimiento.getPrecioManguito()) : "";
    }

    @Override
    public String iniciarSuscripcion(NuevaDonacionDTO<SuscripcionDTO> donacion, boolean conMP) {
        Emprendimiento emprendimiento = emprendimientoRepository.findByUrl(donacion.getEmprendimiento());
        Plan plan = planRepository.findById(donacion.getDonacion().getPlan().getId()).orElseThrow(() -> new BadRequestException("Plan no existe"));
        if (!Objects.equals(plan.getEmprendimiento().getId(), emprendimiento.getId())) return null;
        Suscripcion suscripcion = transaccionMapper.toNuevaSuscripcion(donacion.getDonacion(), emprendimiento.getId(), plan.getId());
        if (!conMP) {
            suscripcion.setEstado(EstadoPago.APROBADO.getCodigo());
        }
        suscripcion = suscripcionRepository.save(suscripcion);
        return conMP ? this.mercadoPagoService.crearCheckoutUrlSuscripcion(suscripcion, plan) : "";
    }

    public void procesarDonacion(Long paymentId, Long transaccionId) {
        Payment payment = mercadoPagoService.getPaymentById(paymentId);
        Transaccion transaccion = transaccionRepository.getReferenceById(transaccionId);
        if (payment.getStatus().equalsIgnoreCase(EstadoPago.PENDIENTE.getCodigo())) return;
        transaccion.setEstado(payment.getStatus());
        transaccionRepository.save(transaccion);
    }
}
