package app.manguito.backend.services;

import app.manguito.backend.dto.*;
import app.manguito.backend.entities.Emprendimiento;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmprendimientoService {

    List<EmprendimientoDTO> findEmprendimientos();

    EmprendimientoDTO findEmprendimientoByUrl(String url);

    EmprendimientoDTO findEmprendimientoByMail(String mail);

    Emprendimiento saveEmprendimiento(EmprendimientoDTO dto);

    EmprendimientoDTO updateEmprendimiento(UpdateEmprendimientoDTO dto);

    DonacionesDTO getDonacionesByEmprendimientoUrl(String emprendimientoUrl);

    List<TopDonadorDTO> getTopDonadoresEmprendimiento(String emprendimientoUrl);

    Page<EmprendimientoDTO> findEmprendimientos(FilterEmprendimientosDTO filter);

    List<EmprendimientoDTO> getEmprendimientosMasDonados();

    List<EmprendimientoDTO> getEmprendimientosDestacados();
}
