package revature;

import revature.controller.UserController;
import revature.repository.SqliteUserDao;
import revature.repository.UserDao;
import revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            UserDao userDao = new SqliteUserDao();

            // handles validating User data, follows software/business rules
            UserService userService = new UserService(userDao);

            // handles receiving and returning data to the user
            UserController userController = new UserController(scanner, userService);

            // this Map will update the loopApplication boolean and store the logged-in user data
            Map<String, String> controlMap = new HashMap<>();
            controlMap.put("Continue Loop", "true");

            while (Boolean.parseBoolean(controlMap.get("Continue Loop"))) {
                // we pass the controlMap into the promptUserForService to get the potential user data and
                // check it in the next if statement
                userController.promptUserForService(controlMap);
                if (controlMap.containsKey("User")) {
                    System.out.printf("Banking stuff for %s can happen here! Press any key to continue", controlMap.get("User"));
                    scanner.nextLine();
                    /*
                        NOTE: currently the User information has no means of being removed: when you implement a log-out
                        functionality the controlMap needs to have the User key/value pair removed:
                        - controlMap.remove("User");
                     */
                }
            }

        }
    }
}