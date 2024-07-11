package revature.service;

import revature.entity.Account;
import revature.repository.AccountDao;

// ENFORCE SOFTWARE REQUIREMENTS and BUSINESS LOGIC
public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account createAccount(int userId) {
        Account newAccount = new Account();
        newAccount.setPrimary_user(userId);
        return accountDao.createAccount(newAccount);
    }

}
