package app.manguito.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopDonadorDTO {

    private String nombre;

    private Long cantidadTotalManguitos;
}
