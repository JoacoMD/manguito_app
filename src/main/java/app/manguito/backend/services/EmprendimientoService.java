package app.manguito.backend.services;

import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.dto.UpdateEmprendimientoDTO;
import app.manguito.backend.entities.Emprendimiento;

import java.util.List;

public interface EmprendimientoService {

    List<EmprendimientoDTO> findEmprendimientos();

    EmprendimientoDTO findEmprendimientoByUrl(String url);

    EmprendimientoDTO findEmprendimientoByMail(String mail);

    Emprendimiento saveEmprendimiento(EmprendimientoDTO dto);

    EmprendimientoDTO updateEmprendimiento(UpdateEmprendimientoDTO dto);

    DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl);


}
