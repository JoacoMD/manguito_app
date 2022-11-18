package app.manguito.backend.services;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;

public interface DonacionService {

    String iniciarDonacionManguitos(NuevaDonacionDTO<DonacionManguitoDTO> donacion);

    void procesarDonacionManguitos(Long paymentId, Long transaccionId);
}
