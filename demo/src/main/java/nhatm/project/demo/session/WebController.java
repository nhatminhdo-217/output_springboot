package nhatm.project.demo.session;

import jakarta.validation.Valid;
import nhatm.project.demo.session.exception.UnmatchPasswordException;
import nhatm.project.demo.session.exception.UserAlreadyExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "session/index";
    }

    @GetMapping("/login")
    public String login() {
        return "session/login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "session/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                           BindingResult br, Model model) {
        try {
            userService.addUser(registerRequest);
        }catch (UserAlreadyExistException uae){
            br.addError(new FieldError("registerRequest", "username", uae.getMessage()));
            System.err.println(uae.getMessage());
            return "session/register";
        }catch (UnmatchPasswordException upe) {
            br.addError(new FieldError("registerRequest", "rePassword", upe.getMessage()));
            System.err.println(upe.getMessage());
            return "session/register";
        }

        return "redirect:/login";
    }
}
