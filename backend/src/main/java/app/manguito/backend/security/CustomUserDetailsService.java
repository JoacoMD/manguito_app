package app.manguito.backend.security;

import app.manguito.backend.entities.Usuario;
import app.manguito.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByMail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario con email '" + email + "' no encontrado")
                );

        return UserPrincipal.create(usuario);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Usuario con id '" + id + "' no encontrado")
        );

        return UserPrincipal.create(usuario);
    }
}
