package revature.controller;

import revature.entity.Account;
import revature.repository.AccountDao;
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
        Account newAccount = accountService.createAccount(userId);
        System.out.println(newAccount);
    }

    // HELPER METHODS
    public int retrieveUserId(Map<String, String> controlMap) {
        String userIdString = controlMap.get("user_id");
        return Integer.parseInt(userIdString);
    }
}
