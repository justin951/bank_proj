package revature.exception;

public class UsernameNotUnique extends RuntimeException {
    public UsernameNotUnique(String message) {
        super(message);
    }
}
