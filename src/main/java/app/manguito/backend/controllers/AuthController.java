package app.manguito.backend.controllers;

import app.manguito.backend.dto.*;
import app.manguito.backend.exception.BadRequestException;
import app.manguito.backend.security.JwtTokenProvider;
import app.manguito.backend.services.EmprendimientoService;
import app.manguito.backend.services.R2Service;
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

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmprendimientoService emprendimientoService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    R2Service r2Service;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsuarioDTO usuarioDTO) throws Exception {

        Authentication authObject;
        try {
            authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getMail(), usuarioDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObject);
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Mail y/o contraseña incorrecta");
        }

        String jwt = tokenProvider.generateToken(authObject);
        String url = "";
        if (authObject.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_USER"))) {
            EmprendimientoDTO emprendimiento = emprendimientoService.findEmprendimientoByMail(authObject.getName());
            url = emprendimiento.getUrl();
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authObject.getName(), tokenProvider.getJwtExpirationInMs(), url));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody NuevoUsuarioDTO usuarioDTO) {
        if(!usuarioDTO.getPassword().equals(usuarioDTO.getPasswordConfirmation())) {
            throw new BadRequestException("Contraseñas no coinciden");
        }

        if(usuarioService.existsUserOrEmprendimientoUrl(usuarioDTO)){
            throw new BadRequestException("Usuario ya registrado");
        }

        usuarioService.saveUsuario(usuarioDTO);
        return ResponseEntity.ok("Registro exitoso");
    }

    @PostMapping("pre-register")
    public ResponseEntity<String> preRegister(@RequestBody NuevoUsuarioDTO usuarioDTO) {
        if(!usuarioDTO.getPassword().equals(usuarioDTO.getPasswordConfirmation())) {
            throw new BadRequestException("Contraseñas no coinciden");
        }

        if(usuarioService.existsUserOrEmprendimientoUrl(usuarioDTO)){
            throw new BadRequestException("Usuario ya registrado");
        }

        return ResponseEntity.ok("Datos validos para registro");
    }
}