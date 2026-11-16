package edu.cwi.data;

import org.apache.ibatis.annotations.*;
import java.util.List;

public interface TransactionMapper {
    @Select("SELECT transaction_id as id, account_id, transaction_type, amount, description, created_at FROM transactions WHERE account_id = #{accountId} ORDER BY created_at")
    List<Transaction> getByAccountId(int accountId);

    @Insert("INSERT INTO transactions (account_id, transaction_type, amount, description) VALUES (#{accountId}, #{transactionType}, #{amount}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Transaction transaction);
}