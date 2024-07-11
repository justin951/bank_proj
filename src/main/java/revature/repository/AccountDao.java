package revature.repository;

import revature.entity.Account;

import java.util.List;

public interface AccountDao {

    Account createAccount(Account newAccountInfo);

    List<Account> getAllAccounts();
//
//    Account getAccountById();

}
