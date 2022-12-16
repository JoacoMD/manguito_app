package app.manguito.backend.controllers;

import app.manguito.backend.dto.NuevoUsuarioDTO;
import app.manguito.backend.dto.UsuarioDTO;
import app.manguito.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioDTO usuarioDTO) throws Exception {

        Authentication authObject;
        try {
            authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getMail(), usuarioDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObject);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }

        return ResponseEntity.ok(authObject.getPrincipal());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody NuevoUsuarioDTO usuarioDTO) {
        if(!usuarioDTO.getPassword().equals(usuarioDTO.getPasswordConfirmation())) {
            return ResponseEntity.badRequest().body("Contraseñas no coinciden");
        }

        if(usuarioService.existsUserOrEmprendimientoUrl(usuarioDTO)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya registrado");
        }

        usuarioService.saveUsuario(usuarioDTO);
        return ResponseEntity.ok("Registro exitoso");
    }

}