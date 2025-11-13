package nhatm.project.demo.session;

import nhatm.project.demo.session.exception.RoleNotFoundException;
import nhatm.project.demo.session.exception.UnmatchPasswordException;
import nhatm.project.demo.session.exception.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        return user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void addUser(RegisterRequest registerRequest) throws UserAlreadyExistException, UnmatchPasswordException, RoleNotFoundException {

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User already exist");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRePassword())) {
            throw new UnmatchPasswordException("Passwords do not match");
        }

        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User user = User.builder()
                .role(role)
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .enabled(true)
                .build();

        userRepository.save(user);
    }
}
