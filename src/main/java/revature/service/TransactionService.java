package revature.service;

import revature.entity.Transaction;
import revature.repository.TransactionDao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public Transaction createTransaction(Transaction newTransaction) {
        return transactionDao.createTransaction(newTransaction);
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        return transactionDao.getTransactionsByAccountId(accountId);
    }
}
