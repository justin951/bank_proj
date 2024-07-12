package revature.service;

import org.slf4j.ILoggerFactory;
import revature.entity.User;
import revature.exception.LoginFail;
import revature.exception.UsernameNotUnique;
import revature.repository.UserDao;

import java.util.List;

/*
    SERVICE verifies business and software requirements

    SERVICE facilitates data between CONTROLLER and REPOSITORY
    provide DAO to SERVICE so DATABASE operations can be performed
    after business and software requirements are verified
 */

// TODO: LOGOUT - removing any user identifying information and returning to log in/register view

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // ENTRY POINT for UserService registration functionality
    public User validateNewCredentials(User newUserCredentials) {
        if (checkUsernamePasswordLength(newUserCredentials)) {
            if (checkUsernameIsUnique(newUserCredentials)) {
                return userDao.createUser(newUserCredentials);
            }
        }
        // TODO: INFORM USER OF RESULTS
        System.out.println();
        throw new UsernameNotUnique("Username is taken, please register under a new username");
    }

    public User checkLoginCredentials(User credentials) {
        for (User user : userDao.getAllUsers()) {
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());
            if (usernameMatches && passwordMatches) {
                return user;
            }
        }
        System.out.println();
        throw new LoginFail("Credentials are invalid: please try again");
    }

    private boolean checkUsernamePasswordLength(User newUserCredentials) {
        boolean usernameIsValid = newUserCredentials.getUsername().length() <= 30;
        boolean passwordIsValid = newUserCredentials.getPassword().length() <= 30;
        return usernameIsValid && passwordIsValid;
    }

    private boolean checkUsernameIsUnique(User newUserCredentials) {
        boolean usernameIsUnique = true;
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            if (newUserCredentials.getUsername().equals(user.getUsername())) {
                usernameIsUnique = false;
                break;
            }
        }
        return usernameIsUnique;
    }

}