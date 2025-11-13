package nhatm.project.demo.session.exception;

public class UnmatchPasswordException extends RuntimeException {
    public UnmatchPasswordException(String message) {
        super(message);
    }
}
