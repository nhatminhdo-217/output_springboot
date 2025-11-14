package nhatm.project.demo.jwt.config;

import nhatm.project.demo.jwt.model.ERole;
import nhatm.project.demo.jwt.model.Role;
import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.repository.RoleRepository;
import nhatm.project.demo.jwt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role admin = roleRepository.findByRole(ERole.ROLE_ADMIN).get();
        createDefaultAdminUser(admin);
    }

    private void createDefaultAdminUser(Role role) {
        final String email = "abcd@gmail.com";
        final String password = "12345";

        if (userRepository.findByEmail(email).isEmpty()) {
            User admin = User.builder()
                    .fullName("Nguyen Van A")
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .roles(Set.of(role))
                    .build();

            userRepository.save(admin);
        }
    }
}
