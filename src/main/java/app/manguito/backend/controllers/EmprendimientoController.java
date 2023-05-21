package app.manguito.backend.controllers;

import app.manguito.backend.dto.*;
import app.manguito.backend.repositories.UsuarioRepository;
import app.manguito.backend.security.CurrentUser;
import app.manguito.backend.security.UserPrincipal;
import app.manguito.backend.services.EmprendimientoService;
import org.springframework.data.domain.Page;
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

    private final EmprendimientoService emprendimientoService;
    private final UsuarioRepository usuarioRepository;

    public EmprendimientoController(EmprendimientoService emprendimientoService, UsuarioRepository usuarioRepository) {
        this.emprendimientoService = emprendimientoService;
        this.usuarioRepository = usuarioRepository;
    }

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

    @GetMapping(path = "/{urlEmprendimiento}/top-donadores")
    public ResponseEntity<List<TopDonadorDTO>> getTopDonadoresEmprendimiento(@PathVariable String urlEmprendimiento) {
        List<TopDonadorDTO> dto = emprendimientoService.getTopDonadoresEmprendimiento(urlEmprendimiento);

        if (dto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Page<EmprendimientoDTO>> searchEmprendimientos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) List<String> categorias,
            @RequestParam(defaultValue = "0") Integer page
            ) {
        return ResponseEntity.ok(emprendimientoService.findEmprendimientos(new FilterEmprendimientosDTO(nombre, categorias, page)));
    }
}
