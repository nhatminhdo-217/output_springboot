package nhatm.project.demo.jwt.service;

import nhatm.project.demo.jwt.model.ERole;
import nhatm.project.demo.jwt.model.Role;
import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.model.dto.LoginRequestDTO;
import nhatm.project.demo.jwt.model.dto.RegisterRequestDTO;
import nhatm.project.demo.jwt.repository.RoleRepository;
import nhatm.project.demo.jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public User signUp(RegisterRequestDTO registerRequestDTO) throws Exception {
        if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }

        Role role = roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(() -> new Exception("Role not found"));

        User user = User.builder()
                .fullName(registerRequestDTO.getFullName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .roles(Set.of(role))
                .build();

        return userRepository.save(user);
    }

    public User login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        return userRepository.findByEmail(loginRequestDTO.getUsername()).orElseThrow();
    }
}
