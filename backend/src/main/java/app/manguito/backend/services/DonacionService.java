package app.manguito.backend.services;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.dto.StatusDonacionManguito;
import app.manguito.backend.dto.SuscripcionDTO;

public interface DonacionService {

    String iniciarDonacionManguitos(NuevaDonacionDTO<DonacionManguitoDTO> donacion, boolean conMP);
    String iniciarSuscripcion(NuevaDonacionDTO<SuscripcionDTO> donacion, boolean conMP);

    void procesarDonacion(Long paymentId, Long transaccionId);

    StatusDonacionManguito getStatusByExternalReference(String externalReference);
}
