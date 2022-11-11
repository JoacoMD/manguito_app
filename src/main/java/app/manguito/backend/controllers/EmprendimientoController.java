package app.manguito.backend.controllers;

import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.services.EmprendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/emprendimientos")
public class EmprendimientoController {

    @Autowired
    private EmprendimientoService emprendimientoService;

    @GetMapping(path = "/{urlEmprendimiento}")
    public ResponseEntity<EmprendimientoDTO> getEmprendimiento(@PathVariable String urlEmprendimiento) {
        EmprendimientoDTO emprendimientoDTO = emprendimientoService.findEmprendimientoByUrl(urlEmprendimiento);

        if (emprendimientoDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(emprendimientoDTO);
    }
}
