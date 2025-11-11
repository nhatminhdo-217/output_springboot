package nhatm.project.demo.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ScopeApplication {

    private static final String NAME = "Minh";
    private static final String NAME_OTHER = "Nhat";
    
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ScopeApplication.class, args);

        Person person1 = (Person) context.getBean("personSingleton"); //All is singleton so all of them is still 1 instance
        Person person2 = (Person) context.getBean("personSingleton");

        person1.setName(NAME);

        System.err.println(person2.getName().equals(person1.getName())); //True

        Person personPrototype1 = (Person) context.getBean("personPrototype");//Each call is each time container create new instance
        Person personPrototype2 = (Person) context.getBean("personPrototype");

        personPrototype1.setName(NAME);
        personPrototype2.setName(NAME_OTHER);

        System.err.println(personPrototype1.getName().equals(personPrototype2.getName())); //False
    }
}
