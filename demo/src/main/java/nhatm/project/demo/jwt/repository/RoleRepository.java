package nhatm.project.demo.jwt.repository;

import nhatm.project.demo.jwt.model.ERole;
import nhatm.project.demo.jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole roleName);
}
