package app.manguito.backend.services.impl;

import app.manguito.backend.entities.Plan;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import app.manguito.backend.services.MercadoPagoService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Value("${mercadoPago.accessToken}")
    private String accessToken;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public String crearCheckoutUrlManguitos(TransaccionManguito donacion, Double valorManguito) {
        PreferenceClient client = new PreferenceClient();
        PreferenceRequest request = createPreferenceRequest(
                "Manguito", donacion.getCantidad(), BigDecimal.valueOf(valorManguito), donacion.getId().toString());
        try {
            return client.create(request).getInitPoint();
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    public String crearCheckoutUrlSuscripcion(Suscripcion suscripcion, Plan plan) {
        PreferenceClient client = new PreferenceClient();
        PreferenceRequest request = createPreferenceRequest(
                "Suscripcion: " + plan.getNombre(), 1, BigDecimal.valueOf(plan.getPrecio()), suscripcion.getId().toString());
        try {
            return client.create(request).getInitPoint();
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Payment getPaymentById(Long paymentId) {
        PaymentClient client = new PaymentClient();
        try {
            return client.get(paymentId);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    private PreferenceRequest createPreferenceRequest(String title, Integer quantity, BigDecimal price, String externalReference) {
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title(title)
                        .quantity(quantity)
                        .unitPrice(price)
                        .build();
        items.add(item);

        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/donaciones/feedback")
                .pending("http://localhost:8080/donaciones/feedback")
                .failure("http://localhost:8080/donaciones/feedback")
                .build();

        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .externalReference(externalReference)
                .build();
    }
}
