package nhatm.project.demo.scope;

public class HelloMessageGenerate {
    private String message;

    public HelloMessageGenerate() {};

    public HelloMessageGenerate(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
