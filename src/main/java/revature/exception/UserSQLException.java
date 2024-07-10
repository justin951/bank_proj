package revature.exception;

import revature.entity.User;

public class UserSQLException extends RuntimeException {
    public UserSQLException(String message){
        super(message);
    }
}
