package nhatm.project.demo.jwt.controller;

import nhatm.project.demo.jwt.model.User;
import nhatm.project.demo.jwt.model.dto.ResponseDTO;
import nhatm.project.demo.jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(404, "User not found", null));
        }

        return ResponseEntity.ok(new ResponseDTO<User>(200, "Show user information success", user));
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> listUser = userService.getAllUsers();

        return ResponseEntity.ok(listUser);
    }
}
