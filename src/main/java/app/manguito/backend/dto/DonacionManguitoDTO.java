package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DonacionManguitoDTO extends DonacionDTO {

    private Integer cantidad;
}
