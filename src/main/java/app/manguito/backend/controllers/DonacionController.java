package app.manguito.backend.controllers;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.SuscripcionRepository;
import app.manguito.backend.repositories.TransaccionManguitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/donaciones")
public class DonacionController {

    @Autowired
    private TransaccionMapper transaccionMapper;

    @Autowired
    private TransaccionManguitoRepository manguitoRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @PostMapping(path = "/manguitos")
    public ResponseEntity<HttpStatus> donarManguitos(@RequestBody NuevaDonacionDTO<DonacionManguitoDTO> dto) {
        TransaccionManguito manguito = transaccionMapper.toNuevoManguito(dto.getDonacion(), dto.getEmprendimientoId());
        manguitoRepository.save(manguito);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/suscripciones")
    public ResponseEntity<HttpStatus> suscribirse(@RequestBody NuevaDonacionDTO<SuscripcionDTO> dto) {
        Suscripcion suscripcion = transaccionMapper.toNuevaSuscripcion(dto.getDonacion(), dto.getEmprendimientoId());
        suscripcionRepository.save(suscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
