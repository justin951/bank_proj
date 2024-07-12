package revature;

import revature.controller.AccountController;
import revature.controller.UserController;
import revature.repository.AccountDao;
import revature.repository.SqliteAccountDao;
import revature.repository.SqliteUserDao;
import revature.repository.UserDao;
import revature.service.AccountService;
import revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            UserDao userDao = new SqliteUserDao();
            AccountDao accountDao = new SqliteAccountDao();

            // handles validating User and Account data, follows software/business rules
            UserService userService = new UserService(userDao);
            AccountService accountService = new AccountService(accountDao);

            // handles receiving and returning data to the user
            AccountController accountController = new AccountController(scanner, accountService);
            UserController userController = new UserController(scanner, userService, accountController);

            // this Map will update the loopApplication boolean and store the logged-in user data
            Map<String, String> controlMap = new HashMap<>();
            controlMap.put("Continue Loop", "true");

            while (Boolean.parseBoolean(controlMap.get("Continue Loop"))) {
                if (Boolean.parseBoolean(controlMap.get("viewAccounts"))) {
                    accountController.promptForService(controlMap);
                }
                userController.promptForService(controlMap);
            }
        }
    }
}