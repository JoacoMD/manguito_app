package app.manguito.backend.repositories;

import app.manguito.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByMail(String mail);

    boolean existsByMailOrEmprendimiento_Url(String mail, String url);
}