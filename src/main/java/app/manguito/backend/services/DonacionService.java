package app.manguito.backend.services;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.dto.SuscripcionDTO;

public interface DonacionService {

    String iniciarDonacionManguitos(NuevaDonacionDTO<DonacionManguitoDTO> donacion);
    String iniciarSuscripcion(NuevaDonacionDTO<SuscripcionDTO> donacion);

    void procesarDonacion(Long paymentId, Long transaccionId);
}
