package app.manguito.backend.controllers;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.entities.Suscripcion;
import app.manguito.backend.entities.TransaccionManguito;
import app.manguito.backend.mappers.TransaccionMapper;
import app.manguito.backend.repositories.SuscripcionRepository;
import app.manguito.backend.repositories.TransaccionManguitoRepository;
import app.manguito.backend.services.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/donaciones")
public class DonacionController {

    @Autowired
    private TransaccionMapper transaccionMapper;

    @Autowired
    private TransaccionManguitoRepository manguitoRepository;

    @Autowired
    private DonacionService donacionService;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @PostMapping(path = "/manguitos")
    public ResponseEntity<String> donarManguitos(@RequestBody NuevaDonacionDTO<DonacionManguitoDTO> dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionService.iniciarDonacionManguitos(dto));
    }

    @GetMapping(path = "/manguitos/feedback")
    public ResponseEntity<HttpStatus> feedbackDonacionManguitos(
            @RequestParam("payment_id") Long paymentID,
            @RequestParam("external_reference") Long idTransaccion) {
        donacionService.procesarDonacionManguitos(paymentID, idTransaccion);
        return ResponseEntity.ok().build();
    }
}
