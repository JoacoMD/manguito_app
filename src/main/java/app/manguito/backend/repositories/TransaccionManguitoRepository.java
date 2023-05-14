package app.manguito.backend.repositories;

import app.manguito.backend.dto.TopDonadorDTO;
import app.manguito.backend.entities.TransaccionManguito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransaccionManguitoRepository extends JpaRepository<TransaccionManguito, Long> {

    List<TransaccionManguito> findAllByDestinatario_UrlAndEstado(String url, String estado);

    @Query("select new app.manguito.backend.dto.TopDonadorDTO(t.nombre, sum(tm.cantidad)) " +
            "from TransaccionManguito tm " +
            "left join Transaccion t on t.id = tm.id " +
            "where t.estado = 'approved' and t.destinatario.url = ?1 " +
            "group by t.nombre ")
    List<TopDonadorDTO> getTopDonadores(String emprendimientoUrl);

    @Query("select sum(tm.cantidad) " +
            "from TransaccionManguito tm " +
            "left join Transaccion t on t.id = tm.id " +
            "where t.estado = 'approved' and t.destinatario.url = ?1 ")
    Optional<Long> getManguitosRecibidos(String emprendimientoUrl);
}