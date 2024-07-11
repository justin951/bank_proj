package revature.controller;

import revature.exception.LoginFail;
import revature.exception.UsernameNotUnique;
import revature.service.UserService;
import revature.entity.User;

import java.util.Map;
import java.util.Scanner;

public class UserController {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountController accountController;


    public UserController(Scanner scanner, UserService userService, AccountController accountController) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountController = accountController;
    }

    /*
        ENTRYPOINT to bank application:
        handles REGISTERING account or EXITING app,
        TODO:
         - refactor to call helper methods / additional helper methods
         to control the flow of the application better
     */
    public void promptUserForService(Map<String, String> controlMap) {
        // user needs to prompt they want to make an account
        System.out.println("Welcome to [Bank]! What would you like to do today?");
        System.out.println("1. Register a new User Account");
        System.out.println("2. Login");
        System.out.println("q. Quit");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    User credentials = getUserCredentials();
                    User loggedInUser = userService.checkLoginCredentials(credentials);
                    controlMap.put("user", loggedInUser.getUsername());
                    controlMap.put("user_id", Integer.toString(loggedInUser.getUser_id()));
//                    System.out.println("UserController loggedInUser: " + loggedInUser);
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
        System.out.println("Welcome, %s! Please select your next course of action.");
        System.out.println("1. Create a new Checking Account");
        System.out.println("2. Log out");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    accountController.registerNewAccount(controlMap);
                case "2":

            }
        } catch (RuntimeException ex) {
            // TODO: better exception
            System.out.println(ex.getMessage());
        }
    }

    // TODO: generic runtime exception is thrown, make it more specific
    public void registerNewUser() {
        User newCredentials = getUserCredentials();
        User newUser = userService.validateNewCredentials(newCredentials);
        System.out.printf("New account created: %s", newUser);
        System.out.println();
    }

    public User login() {
        return userService.checkLoginCredentials(getUserCredentials());
    }

    public void logout(Map<String, String> controlMap) {
        controlMap.remove("user");
        controlMap.remove("user_id");
        System.out.println();
    }

    // HELPER METHODS
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