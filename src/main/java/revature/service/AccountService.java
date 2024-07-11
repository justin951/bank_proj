package revature.service;

import revature.entity.Account;
import revature.repository.AccountDao;
import revature.repository.SqliteAccountDao;

import java.util.List;

// ENFORCE SOFTWARE REQUIREMENTS and BUSINESS LOGIC
public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account createAccount(int userId, String accountName) {
        Account newAccount = new Account();
        newAccount.setPrimary_user(userId);
        newAccount.setAccount_name(accountName);
        return accountDao.createAccount(newAccount);
    }

    public List<Account> retrieveUserAccounts() {
        List<Account> userAccounts = SqliteAccountDao.
    }
}
