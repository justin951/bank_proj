package revature.controller;

import revature.entity.Account;
import revature.service.AccountService;

import java.util.List;
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

    public void promptForService(Map<String, String> controlMap) {
        if (controlMap.containsKey("user_id")) {
            promptUserForAccountService(controlMap);
        } else {
            System.out.println("Not yet implemented");
        }
    }

    public void promptUserForAccountService(Map<String, String> controlMap) {
        int userId = Integer.parseInt(controlMap.get("user_id"));
        List<Account> userAccounts = accountService.getUserAccounts(userId);
        System.out.println("userAccounts: " + userAccounts);
        System.out.println("Please choose from the listed accounts for action: ");
//        userAccounts.forEach(account -> System.out.println(account));

        for (int i = 0; i < userAccounts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, userAccounts.get(i).getAccount_name());
        }

        try {
            int selectedOption = Integer.parseInt(scanner.nextLine()) - 1;
            if (selectedOption >= 0 && selectedOption < userAccounts.size()) {
                Account selectedAccount = userAccounts.get(selectedOption);
                handleAccountAction(selectedAccount);
            } else {
                System.out.println("Invalid selection. Please select from one of your listed accounts.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a number corresponding to the listed accounts.");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleAccountAction(Account selectedAccount) {
        System.out.println(">>>>>>>>>>>>>>>");
        System.out.printf("Selected Account: %s\n", selectedAccount.getAccount_name());
        System.out.println(">>>>>>>>>>>>>>>");
        System.out.println("Choose an action for this account:");
        System.out.println("1. View account details");
        System.out.println("2. Perform transaction");
        System.out.println("3. Add joint user to account");
        System.out.println("4. Delete account");

        try {
            String action = scanner.nextLine();
            switch (action) {
                case "1" -> viewAccountDetails(selectedAccount);
                case "2" -> performTransaction(selectedAccount);
                case "3" -> addJointUser(selectedAccount);
                case "4" -> deleteAccount(selectedAccount);
                default -> System.out.println("Invalid selection, please try again");
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // handleAccountAction HELPER METHODS
    private void viewAccountDetails(Account account) {
        System.out.println("Account details: " + account);
        System.out.println();
        System.out.println("Press any key to continue");
        scanner.nextLine();
    }

    private void performTransaction(Account account) {
        System.out.println("not yet implemented");
    }

    private void addJointUser(Account account) {
        System.out.println("not yet implemented");
    }

    private void deleteAccount(Account account) {
        System.out.println("not yet implemented");
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
