package revature.service;

import revature.repository.AccountDao;

// ENFORCE SOFTWARE REQUIREMENTS and BUSINESS LOGIC
public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

}
