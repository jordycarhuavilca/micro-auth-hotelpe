package pe.edu.cibertec.micro_auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.micro_auth.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
