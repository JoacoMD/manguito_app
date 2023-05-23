package app.manguito.backend.repositories;

import app.manguito.backend.entities.Emprendimiento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {

    Emprendimiento findByUrl(String url);

    boolean existsByUrl(String url);

    @Query("select tm.destinatario " +
            "from TransaccionManguito tm " +
            "where tm.estado = 'approved' " +
            "group by tm.destinatario " +
            "order by sum(tm.cantidad) desc "
    )
    List<Emprendimiento> getEmprendimientosMasDonados(Pageable pageable);

    @Query("select tm.destinatario " +
            "from TransaccionManguito tm " +
            "where tm.estado = 'approved' and tm.fecha > ?1 " +
            "group by tm.destinatario " +
            "order by sum(tm.cantidad) desc "
    )
    List<Emprendimiento> getEmprendimientosDestacados(LocalDateTime fecha, Pageable pageable);
}