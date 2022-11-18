package app.manguito.backend.services.impl;

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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    private final String accessToken = "TEST-5267572198433376-111522-81d3c9c368d38b99c2b7653070fddb6d-195418765";

    public MercadoPagoServiceImpl() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public String crearCheckoutUrlManguitos(TransaccionManguito donacion, Double valorManguito) {
        PreferenceClient client = new PreferenceClient();

        // Crea un ítem en la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title("Manguito")
                        .quantity(donacion.getCantidad())
                        .unitPrice(new BigDecimal(valorManguito))
                        .build();
        items.add(item);

        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/donaciones/manguitos/feedback")
                .pending("http://localhost:8080/donaciones/manguitos/feedback")
                .failure("http://localhost:8080/donaciones/manguitos/feedback")
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .externalReference(donacion.getId().toString())
                .build();

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
}
