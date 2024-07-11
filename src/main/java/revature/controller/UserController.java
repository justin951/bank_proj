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

    public UserController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
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
        System.out.println("What would you like to do?");
        System.out.println("1. register an account");
        System.out.println("2. login");
        System.out.println("q. quit");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    controlMap.put("User", login().getUsername());
                    controlMap.put("user_id", Integer.toString(login().getUser_id()));
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
        System.out.println();
    }

    public void registerNewUser() {
        // TODO: generic runtime exception is thrown, make it more specific
        User newCredentials = getUserCredentials();
        User newUser = userService.validateNewCredentials(newCredentials);
        System.out.printf("New account created: %s", newUser);
        System.out.println();
    }

    public User login() {
        return userService.checkLoginCredentials(getUserCredentials());
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