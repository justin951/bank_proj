package revature.repository;

import revature.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account createAccount(Account newAccountInfo);

    List<Account> getUserAccounts(int userId);

    Account accountTransaction(Account account, BigDecimal newBalance);

    Account getAccountById(int Id);

    String deleteAccountById(int Id);
}
