package app.manguito.backend.controllers;

import app.manguito.backend.dto.UsuarioDTO;
import app.manguito.backend.entities.Usuario;
import app.manguito.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/users")
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping(path = "/current")
    public ResponseEntity<UsuarioDTO> getCurrent(Principal principal) {
        Usuario usuario = usuarioService.findUsuarioByMail(principal.getName());
        UsuarioDTO dto = new UsuarioDTO();
        dto.setMail(usuario.getMail());
        return ResponseEntity.ok(dto);
    }
}
