package app.manguito.backend.services.impl;

import app.manguito.backend.EstadoPago;
import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.entities.Emprendimiento;
import app.manguito.backend.entities.TransaccionManguito;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.EmprendimientoRepository;
import app.manguito.backend.repositories.TransaccionManguitoRepository;
import app.manguito.backend.services.DonacionService;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DonacionServiceImpl implements DonacionService {

    @Autowired
    private TransaccionManguitoRepository manguitoRepository;

    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Autowired
    private MercadoPagoServiceImpl mercadoPagoService;

    @Autowired
    private TransaccionMapper transaccionMapper;

    public String iniciarDonacionManguitos(NuevaDonacionDTO<DonacionManguitoDTO> donacion) {
        Emprendimiento emprendimiento = emprendimientoRepository.findByUrl(donacion.getEmprendimiento());
        TransaccionManguito transaccionManguito = transaccionMapper.toNuevoManguito(donacion.getDonacion(), emprendimiento);
        transaccionManguito = manguitoRepository.save(transaccionManguito);
        return this.mercadoPagoService.crearCheckoutUrlManguitos(transaccionManguito, emprendimiento.getPrecioManguito());
    }

    public void procesarDonacionManguitos(Long paymentId, Long transaccionId) {
        Payment payment = mercadoPagoService.getPaymentById(paymentId);
        TransaccionManguito transaccionManguito = manguitoRepository.getReferenceById(transaccionId);
        if (payment.getStatus().equalsIgnoreCase(EstadoPago.PENDIENTE.getCodigo())) return;
        transaccionManguito.setEstado(payment.getStatus());
        manguitoRepository.save(transaccionManguito);
    }
}
