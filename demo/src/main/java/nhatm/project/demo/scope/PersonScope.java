package nhatm.project.demo.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PersonScope {
    
    @Bean
    public Person personSingleton() {
        return new Person();
    }

    @Bean
    @Scope("prototype")
    public Person personPrototype() {
        return new Person();
    }
}
