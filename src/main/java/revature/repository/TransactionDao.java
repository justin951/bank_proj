package revature.repository;

import revature.entity.Transaction;

import java.util.List;

public interface TransactionDao {
    Transaction createTransaction(Transaction newTransactionInfo);

    List<Transaction> getTransactionsByAccountId(int accountId);
}
