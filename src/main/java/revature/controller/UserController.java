package revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import revature.exception.LoginFail;
import revature.exception.PasswordTooLong;
import revature.exception.UsernameNotUnique;
import revature.exception.UsernameTooLong;
import revature.service.UserService;
import revature.entity.User;

import java.util.Map;
import java.util.Scanner;

public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Scanner scanner;
    private final UserService userService;
    private final AccountController accountController;


    public UserController(Scanner scanner, UserService userService, AccountController accountController) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountController = accountController;
    }

    public void promptForService(Map<String, String> controlMap) {
        if (controlMap.containsKey("user_id")) {
            promptLoggedInUserForService(controlMap);
        } else {
            promptUserForService(controlMap);
        }
    }

    // CORE METHODS
    public void promptUserForService(Map<String, String> controlMap) {
        System.out.println("...");
        System.out.println("What would you like to do today?");
        System.out.println("1. Register a new User Account");
        System.out.println("2. Login");
        System.out.println("q. Quit");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser(controlMap);
                    break;
                case "2":
                    login(controlMap);
                    break;
                case "q":
                    System.out.println("Goodbye!");
                    controlMap.put("Continue Loop", "false");
            }
        } catch (LoginFail | UsernameNotUnique exception) {
            System.out.println(exception.getMessage());
        }
    }

    // TODO: interactions for logged-in user
    public void promptLoggedInUserForService(Map<String, String> controlMap) {
        System.out.println("...");
        System.out.printf("Welcome, %s! Please select your next course of action.", controlMap.get("user"));
        System.out.println();
        System.out.println("1. Create a new Checking Account");
        System.out.println("2. Review existing Account(s)");
        System.out.println("q. Log out");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    accountController.registerNewAccount(controlMap);
                    controlMap.put("account", "true");
                    break;
                case "2":
                    controlMap.put("viewAccounts", "true");
                    break;
                case "q":
                    logout(controlMap);
                    break;
            }
        } catch (RuntimeException ex) {
            // TODO: better exception
            System.out.println(ex.getMessage());
        }
    }

    // TODO: generic runtime exception is thrown, make it more specific
    public void registerNewUser(Map<String, String> controlMap) {
        User newUser = null;

        while (newUser == null) {
            try {
                User newCredentials = getUserCredentials();
                newUser = userService.validateNewCredentials(newCredentials);
            } catch (UsernameTooLong | PasswordTooLong | UsernameNotUnique e) {
                System.out.println(e.getMessage());
            }
            if (newUser == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        controlMap.put("user", newUser.getUsername());
        controlMap.put("user_id", Integer.toString(newUser.getUser_id()));
        controlMap.put("password",newUser.getPassword());
        System.out.printf("New account created: %s", newUser);
        System.out.println();
        promptLoggedInUserForService(controlMap);
    }

    // HELPER METHODS
    public void login(Map<String, String> controlMap) {
        User loggedInUser = userService.checkLoginCredentials(getUserCredentials());
        controlMap.put("user", loggedInUser.getUsername());
        controlMap.put("user_id", Integer.toString(loggedInUser.getUser_id()));
        controlMap.put("password", loggedInUser.getPassword());
        System.out.printf("Account %s log-in successful.", loggedInUser.getUsername());
        System.out.println();
        promptLoggedInUserForService(controlMap);
    }

    public void logout(Map<String, String> controlMap) {
        String temp = controlMap.get("user");
        controlMap.remove("viewAccounts");
        controlMap.remove("user");
        controlMap.remove("user_id");
        System.out.printf("User %s has logged out.", temp);
        System.out.println();
    }

    public User getUserCredentials() {
        String newUsername;
        String newPassword;
        // user needs to provide a username and password
        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        return new User(newUsername, newPassword);
    }
}