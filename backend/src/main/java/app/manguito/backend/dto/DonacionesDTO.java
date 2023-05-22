package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DonacionesDTO {

    private List<DonacionManguitoDTO> manguitos;

    private List<SuscripcionDTO> suscripciones;
}
