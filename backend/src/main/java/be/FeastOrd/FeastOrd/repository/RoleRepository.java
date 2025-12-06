package be.FeastOrd.FeastOrd.repository;
import be.FeastOrd.FeastOrd.model.Role;
import be.FeastOrd.FeastOrd.model.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(TypeRole role);
}
