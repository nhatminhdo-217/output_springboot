package nhatm.project.demo.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequestScope
public class HelloMessageGenerateScope {

    @Bean
    public HelloMessageGenerate requestScopedBean() {
        return new HelloMessageGenerate();
    }
}
