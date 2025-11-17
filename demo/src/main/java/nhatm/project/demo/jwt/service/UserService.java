package nhatm.project.demo.jwt.service;

import jakarta.transaction.Transactional;
import nhatm.project.demo.jwt.model.RefreshToken;
import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.repository.RefreshTokenRepository;
import nhatm.project.demo.jwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }

        return false;
    }

    public boolean banUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User userToBan = user.get();
            userToBan.setEnabled(false);

            deleteUserRefreshTokens(userToBan);

            userRepository.save(userToBan);
            return true;
        }

        return false;
    }

    private void deleteUserRefreshTokens(User user) {
        refreshTokenRepository.deleteAll(user.getRefreshTokens());
    }
}
