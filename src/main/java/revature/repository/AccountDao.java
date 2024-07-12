package revature.repository;

import revature.entity.Account;

import java.util.List;

public interface AccountDao {

    Account createAccount(Account newAccountInfo);

    List<Account> getUserAccounts(int userId);

    Account accountTransaction(Account account, double newBalance);

    Account getAccountById(int Id);

}
