package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuscripcionDTO extends DonacionDTO {

    private PlanDTO plan;
}
