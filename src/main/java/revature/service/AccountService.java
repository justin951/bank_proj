package revature.service;

import revature.entity.Account;
import revature.repository.AccountDao;

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

}
