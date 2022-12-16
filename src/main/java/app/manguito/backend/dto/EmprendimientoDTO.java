package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmprendimientoDTO {

    private Long id;

    private String nombreEmprendimiento;

    private ImagenDTO imagenPerfil;

    private ImagenDTO banner;

    private String url;

    private String descripcion;

    private Double precioManguito;

    private Boolean mostrarTopDonadores;

    private Boolean ocultarManguitosRecibidos;

    private List<String> categorias = new ArrayList<>();

    private List<RedSocialEmprendimientoDTO> redesSociales;

    private List<PlanDTO> planes;
}
