package app.manguito.backend.services;

import app.manguito.backend.entities.Plan;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import com.mercadopago.resources.payment.Payment;

public interface MercadoPagoService {

    String crearCheckoutUrlManguitos(TransaccionManguito donacion, Double valorManguito);

    String crearCheckoutUrlSuscripcion(Suscripcion suscripcion, Plan plan);

    Payment getPaymentById(Long paymentId);
}
