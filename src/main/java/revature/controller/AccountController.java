package revature.controller;

import revature.entity.Account;
import revature.service.AccountService;

import java.util.Map;
import java.util.Scanner;

public class AccountController {
    private final Scanner scanner;
    private final AccountService accountService;

    public AccountController(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    // CORE METHODS
    public void registerNewAccount(Map<String, String> controlMap) {
        int userId = retrieveUserId(controlMap);
        String accountName = getNewAccountName();
        Account newAccount = accountService.createAccount(userId, accountName);
        System.out.println(">>>>>>>>: " + newAccount);
    }

    // TODO: RESUME HERE
    public void promptUserForAccountService(Map<String, String> controlMap) {
        System.out.println("Please choose from the listed accounts for action: ");
        System.out.println("placeholder ... incomplete");
    }

    // HELPER METHODS
    public int retrieveUserId(Map<String, String> controlMap) {
        String userIdString = controlMap.get("user_id");
        return Integer.parseInt(userIdString);
    }

    public String getNewAccountName() {
        String newAccountName;
        System.out.println("Please provide a name for the account: ");
        newAccountName = scanner.nextLine();
        return newAccountName;
    }
}
