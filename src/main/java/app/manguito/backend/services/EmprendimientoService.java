package app.manguito.backend.services;

import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;

public interface EmprendimientoService {

    EmprendimientoDTO findEmprendimientoByUrl(String url);

    DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl);


}
