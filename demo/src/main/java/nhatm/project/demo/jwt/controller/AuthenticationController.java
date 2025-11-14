package nhatm.project.demo.jwt.controller;

import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.model.dto.LoginRequestDTO;
import nhatm.project.demo.jwt.model.dto.LoginResponse;
import nhatm.project.demo.jwt.model.dto.RegisterRequestDTO;
import nhatm.project.demo.jwt.service.AuthenticationService;
import nhatm.project.demo.jwt.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        User loginUser = authenticationService.login(loginRequestDTO);

        String jwtToken = jwtService.generateToken(loginUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getJwtExpiration())
                .build();


        return ResponseEntity.ok(loginResponse);
    }
}
