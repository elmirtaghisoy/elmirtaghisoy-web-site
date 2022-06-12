package az.et.ws.component.exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String email) {
        super(email);
    }
}
