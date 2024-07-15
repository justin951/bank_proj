package revature.exception;

public class UsernameTooLong extends RuntimeException {
    public UsernameTooLong(String message) {
        super(message);
    }
}
