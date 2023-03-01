package app.manguito.backend.controllers;

import app.manguito.backend.dto.DonacionesDTO;
import app.manguito.backend.dto.EmprendimientoDTO;
import app.manguito.backend.dto.UpdateEmprendimientoDTO;
import app.manguito.backend.repositories.UsuarioRepository;
import app.manguito.backend.security.CurrentUser;
import app.manguito.backend.security.UserPrincipal;
import app.manguito.backend.services.EmprendimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping(path = "/emprendimientos")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class EmprendimientoController {

    @Autowired
    private EmprendimientoService emprendimientoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<EmprendimientoDTO>> getEmprendimientos() {
        return ResponseEntity.ok(emprendimientoService.findEmprendimientos());
    }

    @GetMapping(path = "/{urlEmprendimiento}")
    public ResponseEntity<EmprendimientoDTO> getEmprendimiento(
            @PathVariable String urlEmprendimiento,
            @CurrentUser UserPrincipal userPrincipal) {
        EmprendimientoDTO emprendimientoDTO;
        if (Objects.equals(urlEmprendimiento, "actual")) {
            emprendimientoDTO = emprendimientoService.findEmprendimientoByMail(userPrincipal.getEmail());
        } else {
            emprendimientoDTO = emprendimientoService.findEmprendimientoByUrl(urlEmprendimiento);
        }

        if (emprendimientoDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(emprendimientoDTO);
    }

    @PutMapping(path = "/{urlEmprendimiento}")
    public ResponseEntity<EmprendimientoDTO> updateEmprendimiento(
            @PathVariable String urlEmprendimiento,
            @RequestBody UpdateEmprendimientoDTO emprendimientoDTO,
            Principal principal) {
        emprendimientoDTO.setUrl(urlEmprendimiento);
        if (principal == null || !usuarioRepository.existsByMailOrEmprendimiento_Url(principal.getName(), urlEmprendimiento)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        emprendimientoService.updateEmprendimiento(emprendimientoDTO);
        return ResponseEntity.ok(emprendimientoDTO);
    }

    @GetMapping(path = "/{urlEmprendimiento}/donaciones")
    public ResponseEntity<DonacionesDTO> getDonacionesEmprendimiento(@PathVariable String urlEmprendimiento) {
        DonacionesDTO dto = emprendimientoService.getDonacionesByEmprendimientoUrl(urlEmprendimiento);

        if (dto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(dto);
    }
}
