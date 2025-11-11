package nhatm.project.demo.scope;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.annotation.Resource;

@Controller
public class ScopeController {
    
    @Resource(name = "requestScopedBean")
    HelloMessageGenerate requestScopedBean;

    @GetMapping("scope/request")
    public String requestMethodMessage(final Model model) {
        model.addAttribute("prevMsg", requestScopedBean.getMessage());
        requestScopedBean.setMessage("Good Morning!");
        model.addAttribute("currMsg", requestScopedBean.getMessage());

        return "scopesExample";
    }
    
}
