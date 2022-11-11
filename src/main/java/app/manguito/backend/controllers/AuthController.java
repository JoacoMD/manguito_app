package app.manguito.backend.controllers;

import app.manguito.backend.dto.UsuarioDTO;
import app.manguito.backend.entities.Usuario;
import app.manguito.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody UsuarioDTO usuarioDTO) throws Exception {

        Authentication authObject = null;
        try {
            authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getMail(), usuarioDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObject);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/register")
//    public ResponseEntity<HttpStatus> register(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
//        Usuario existingUser = usuarioService.findUsuarioByMail(usuarioDTO.getMail());
//
//        if(existingUser != null && existingUser.getMail() != null && !existingUser.getMail().isEmpty()){
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//
//        usuarioService.saveUsuario(usuarioDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}