package app.manguito.backend.services;

import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;

public interface EmprendimientoService {

    EmprendimientoDTO findEmprendimientoByUrl(String url);

    Emprendimiento saveEmprendimiento(EmprendimientoDTO dto);

    EmprendimientoDTO updateEmprendimiento(EmprendimientoDTO dto);

    DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl);


}
