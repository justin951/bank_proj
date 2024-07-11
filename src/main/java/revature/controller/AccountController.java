package revature.controller;

import revature.repository.AccountDao;
import revature.service.AccountService;

import java.util.Map;
import java.util.Scanner;

public class AccountController implements AccountDao {
    private final Scanner scanner;
    private final AccountService accountService;

    public AccountController(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    public void registerNewAccount() {

    }

    public void retrieveUserId(Map<String, String> controlMap) {
        // TODO: pull string user_id from controlMap as Int
    }
}
