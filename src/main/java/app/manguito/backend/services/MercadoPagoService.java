package app.manguito.backend.services;

import app.manguito.backend.entities.TransaccionManguito;

public interface MercadoPagoService {

    String crearCheckoutUrlManguitos(TransaccionManguito donacion, Double valorManguito);
}
