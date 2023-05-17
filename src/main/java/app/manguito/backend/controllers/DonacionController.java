package app.manguito.backend.controllers;

import app.manguito.backend.dto.DonacionManguitoDTO;
import app.manguito.backend.dto.NuevaDonacionDTO;
import app.manguito.backend.dto.StatusDonacionManguito;
import app.manguito.backend.dto.SuscripcionDTO;
import app.manguito.backend.services.DonacionService;
import com.amazonaws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/donaciones")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class DonacionController {

    @Autowired
    private DonacionService donacionService;

    @PostMapping(path = "/manguitos")
    public ResponseEntity<String> donarManguitos(@RequestBody NuevaDonacionDTO<DonacionManguitoDTO> dto,
                                                 @RequestParam("mp") boolean conMP) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(donacionService.iniciarDonacionManguitos(dto, conMP));
    }

    @GetMapping(path = "/feedback")
    public ResponseEntity<HttpStatus> feedbackDonacionManguitos(
            @RequestParam("payment_id") Long paymentID,
            @RequestParam("external_reference") Long idTransaccion) {
        donacionService.procesarDonacion(paymentID, idTransaccion);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping(path = "/status/{externalReference}")
    public ResponseEntity<StatusDonacionManguito> getStatusDonacion(@PathVariable String externalReference) {
        return ResponseEntity.ok(donacionService.getStatusByExternalReference(externalReference));
    }

    @PostMapping(path = "/suscripciones")
    public ResponseEntity<String> suscribirse(@RequestBody NuevaDonacionDTO<SuscripcionDTO> dto,
                                              @RequestParam("mp") boolean conMP) {
        String url = donacionService.iniciarSuscripcion(dto, conMP);
        if (url == null) return ResponseEntity.badRequest().body("Plan no pertenece a emprendimiento elegido");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(url);
    }
}
