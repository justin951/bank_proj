package revature.exception;

public class PasswordTooLong extends RuntimeException {
    public PasswordTooLong(String message) {
        super(message);
    }
}
