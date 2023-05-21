package app.manguito.backend.repositories;

import app.manguito.backend.dto.FilterEmprendimientosDTO;
import app.manguito.backend.entities.Emprendimiento;
import org.springframework.data.domain.Page;

public interface CustomEmprendimientoRepository {

    Page<Emprendimiento> findEmprendimientos(FilterEmprendimientosDTO filter);
}
