package revature.controller;

import revature.entity.Account;
import revature.entity.Transaction;
import revature.service.AccountService;
import revature.service.TransactionService;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class AccountController {
    private final Scanner scanner;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(
            Scanner scanner,
            AccountService accountService,
            TransactionService transactionService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    // CORE METHODS
    public void registerNewAccount(Map<String, String> controlMap) {
        int userId = retrieveUserId(controlMap);
        String accountName = getNewAccountName();
        Account newAccount = accountService.createAccount(userId, accountName);
        System.out.println(">>>>>>>>: " + newAccount);
    }

    public void promptForService(Map<String, String> controlMap) {
        if (controlMap.containsKey("viewAccounts")) {
            promptUserForAccountService(controlMap);
        } else {
            System.out.println("Not yet implemented");
        }
    }

    public void viewAccountTransactions(int accountId) {
        List<Transaction> accountTransactions = transactionService.getTransactionsByAccountId(accountId);
        System.out.println("...");
        for (Transaction accountTransaction : accountTransactions) {
            System.out.println(accountTransaction.toShortString());
        }
        if (accountTransactions.isEmpty()) {
            System.out.println("This account has no transactions");
        }
        System.out.println();
        System.out.println("Press any key to continue");
        scanner.nextLine();
    }

    public void promptUserForAccountService(Map<String, String> controlMap) {
        int userId = Integer.parseInt(controlMap.get("user_id"));
        List<Account> userAccounts = accountService.getUserAccounts(userId);
        System.out.println("...");
        System.out.println("Please choose from the listed accounts for action: ");
//        userAccounts.forEach(account -> System.out.println(account));

        for (int i = 0; i < userAccounts.size(); i++) {
            System.out.printf("%d. %s | $%.2f\n",
                    i + 1,
                    userAccounts.get(i).getAccount_name(),
                    userAccounts.get(i).getBalance());
        }
        if (userAccounts.isEmpty()) {
            System.out.println("You have no active accounts");
        }
        System.out.println("q. quit");
        try {
            int selectedOption = Integer.parseInt(scanner.nextLine()) - 1;
            if (selectedOption == 'q') {
                controlMap.remove("viewAccounts");
            }
            if (selectedOption >= 0 && selectedOption < userAccounts.size()) {
                Account selectedAccount = userAccounts.get(selectedOption);
                handleAccountAction(selectedAccount);
            } else {
                System.out.println("Invalid selection. Please select from one of your listed accounts.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Redirecting...");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleAccountAction(Account selectedAccount) {
        System.out.println("...");
        System.out.printf("Choose an action for account {%s}:", selectedAccount.getAccount_name());
        System.out.println();
        System.out.println("1. View account details");
        System.out.println("2. Perform transaction");
        System.out.println("3. Review transaction history");
        System.out.println("4. Delete account");
        System.out.println("5. Go back");

        try {
            String action = scanner.nextLine();
            switch (action) {
                case "1" -> viewAccountDetails(selectedAccount);
                case "2" -> performTransaction(selectedAccount);
                case "3" -> viewAccountTransactions(selectedAccount.getAccount_id());
                case "4" -> deleteAccount(selectedAccount);
                case "5" -> System.out.println();
                default -> System.out.println("Invalid selection, please try again");
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // handleAccountAction HELPER METHODS
    private void viewAccountDetails(Account account) {
        System.out.println("Account details: " + account.toShortString());
        System.out.println();
        System.out.println("Press any key to continue");
        scanner.nextLine();
        handleAccountAction(account);
    }

    private void performTransaction(Account account) {
        System.out.println("...");
        System.out.printf("Select a transaction for account {%s}: ", account.getAccount_name());
        System.out.println();
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Cancel Transaction");
        try {
            String userChoice = scanner.nextLine();
            switch (userChoice) {
                case "1" -> balanceWithdraw(account);
                case "2" -> balanceDeposit(account);
                default -> handleAccountAction(account);
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void balanceWithdraw(Account account) {
        System.out.printf(
                "Account %s balance: %.2f. Please input an amount to withdraw: ",
                account.getAccount_name(),
                account.getBalance());
        try {
            BigDecimal withdrawAmount = BigDecimal.valueOf(Integer.parseInt(scanner.nextLine()));
            Account updatedAccount = accountService.checkSufficientBalanceForWithdraw(account, withdrawAmount);
            createNewTransaction(updatedAccount, withdrawAmount, "withdraw");
            System.out.printf("Account %s now has a new balance of %.2f",
                    updatedAccount.getAccount_name(),
                    updatedAccount.getBalance());
            System.out.println();
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a number value for withdraw");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void balanceDeposit(Account account) {
        System.out.printf(
                "Account %s balance: %.2f. Please input an amount to deposit: ",
                account.getAccount_name(),
                account.getBalance());
        try {
            BigDecimal depositAmount = BigDecimal.valueOf(Integer.parseInt(scanner.nextLine()));
            Account updatedAccount = accountService.addBalanceForDeposit(account, depositAmount);
            createNewTransaction(updatedAccount, depositAmount, "deposit");
            System.out.printf("Account %s now has a new balance of %.2f",
                    updatedAccount.getAccount_name(),
                    updatedAccount.getBalance());
            System.out.println();
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a number value for deposit");
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    private void addJointUser(Account account) {
//        System.out.println("not yet implemented");
//    }

    private void deleteAccount(Account account) {
        if (!accountService.checkBalanceIsZero(account)) {
            System.out.printf(
                    "Balance for account {%s} is currently %.2f. Please draw balance down to 0.00 before closing account.",
                    account.getAccount_name(),
                    account.getBalance());
            System.out.println();
            performTransaction(account);
        } else {

            System.out.printf(
                    "Are you sure you wish to close account {%s}?",
                    account.getAccount_name());
            System.out.println();
            System.out.println("1. delete");
            System.out.println("2. cancel");
            String userChoice = scanner.nextLine();
            try {
                switch (userChoice) {
                    case "1":
                        System.out.println(accountService.deleteAccount(account.getAccount_id()));
                        break;
                    case "2":
                        System.out.println("Account deletion canceled.");
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // HELPER METHODS
    public void createNewTransaction(Account account, BigDecimal amount, String transType) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAccount_id(account.getAccount_id());
        newTransaction.setTransaction_type(transType);
        if (Objects.equals(transType, "withdraw")) {
            newTransaction.setTransaction_amount(amount.negate());
        } else {
            newTransaction.setTransaction_amount(amount);
        }
        newTransaction.setBalance(account.getBalance());
        newTransaction.setTransaction_time(Timestamp.from(Instant.now()));
        Transaction transaction = transactionService.createTransaction(newTransaction);
    }

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
