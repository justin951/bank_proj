package revature.service;

import revature.entity.Account;
import revature.exception.InsufficientFunds;
import revature.repository.AccountDao;

import java.math.BigDecimal;
import java.util.List;

// ENFORCE SOFTWARE REQUIREMENTS and BUSINESS LOGIC
public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account createAccount(int userId, String accountName) {
        System.out.println("AccountService createAccount userId: " + userId);
        Account newAccount = new Account();
        newAccount.setPrimary_user(userId);
        newAccount.setAccount_name(accountName);
        return accountDao.createAccount(newAccount);
    }

    public List<Account> getUserAccounts(int userId) {
        return accountDao.getUserAccounts(userId);
    }

    public Account checkSufficientBalanceForWithdraw(Account account, BigDecimal withdrawAmount) {
        if (account.getBalance().compareTo(withdrawAmount) >= 0) {
            BigDecimal newBalance = account.getBalance().subtract(withdrawAmount);
            return accountDao.accountTransaction(account, newBalance);
        } else {
            throw new InsufficientFunds("Insufficient funds to complete transaction.");
        }
    }

    public Account addBalanceForDeposit(Account account, BigDecimal depositAmount) {
        BigDecimal newBalance = account.getBalance().add(depositAmount);
        return accountDao.accountTransaction(account, newBalance);
    }

    public boolean checkBalanceIsZero(Account account) {
        return account.getBalance().compareTo(BigDecimal.ZERO) == 0;
    }

}
