package app.manguito.backend.security;

import app.manguito.backend.entities.Usuario;
import app.manguito.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Usuario user = userRepository.findByMail(usernameOrEmail);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getMail()
                    , user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(user.getRol().getNombre())));
        }else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}
