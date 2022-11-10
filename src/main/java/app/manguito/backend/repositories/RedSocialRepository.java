package app.manguito.backend.repositories;

import app.manguito.backend.entities.RedSocial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedSocialRepository extends JpaRepository<RedSocial, Long> {
}