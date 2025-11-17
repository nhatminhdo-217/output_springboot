package nhatm.project.demo.jwt.controller;

import jakarta.servlet.http.HttpServletResponse;
import nhatm.project.demo.jwt.model.RefreshToken;
import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.model.dto.LoginRequestDTO;
import nhatm.project.demo.jwt.model.dto.LoginResponse;
import nhatm.project.demo.jwt.model.dto.RegisterRequestDTO;
import nhatm.project.demo.jwt.service.AuthenticationService;
import nhatm.project.demo.jwt.service.JwtService;
import nhatm.project.demo.jwt.service.RefreshTokenService;
import nhatm.project.demo.jwt.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService, RefreshTokenService refreshTokenService, UserService userService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            User registeredUser = authenticationService.signUp(registerRequestDTO);

            return  ResponseEntity.ok(registeredUser);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        User loginUser = authenticationService.login(loginRequestDTO);

        if (!loginUser.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User with id " + loginUser.getId() + " has been banned. If there anything, please contract admin.");
        }

        String jwtToken = jwtService.generateToken(loginUser);
        String refreshToken = refreshTokenService.generateRefreshToken(loginUser);

        //Set httpOnly Cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(refreshTokenService.getRefreshExpiration())
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        LoginResponse loginResponse = LoginResponse.builder()
                .jwtToken(jwtToken)
                .expiresIn(jwtService.getJwtExpiration())
                .refreshToken(refreshToken)
                .build();

        refreshTokenService.addRefreshToken(refreshToken, loginUser);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken) {
        String username = refreshTokenService.extractUsername(refreshToken);

        if (username == null) return ResponseEntity.badRequest().build();

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!refreshTokenService.isTokenValid(refreshToken, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        RefreshToken refreshTokenModel = refreshTokenService.getRefreshTokenByToken(refreshToken);
        if (refreshTokenModel.isRevoked()) {
            return ResponseEntity.badRequest().body("Refresh Token has been revoked");
        }

        String newJwtToken = jwtService.generateToken(userDetails);

        LoginResponse refreshResponse = LoginResponse.builder()
                .jwtToken(newJwtToken)
                .expiresIn(jwtService.getJwtExpiration())
                .refreshToken(refreshToken)
                .build();

        return  ResponseEntity.ok(refreshResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {

        //Delete cookie
        ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        refreshTokenService.deleteFreshToken(refreshToken);

        return  ResponseEntity.noContent().build();
    }
}
