package app.manguito.backend.repositories;

import app.manguito.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByMail(String mail);

    boolean existsByMailOrEmprendimiento_Url(String mail, String url);
}