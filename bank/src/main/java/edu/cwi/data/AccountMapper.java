package edu.cwi.data;

import java.util.List;

import org.apache.ibatis.annotations.*;

public interface AccountMapper {
    @Select("SELECT account_id as id, user_id, account_type, balance FROM accounts WHERE user_id = #{userId} LIMIT 1")
    Account getByUserId(int userId);

    @Insert("INSERT INTO accounts (user_id, account_type, balance) VALUES (#{userId}, #{accountType}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Account account);

    @Select("SELECT account_id as id, user_id, account_type, balance FROM accounts WHERE user_id = #{userId}")
    List<Account> getAllByUserId(int userId);

    @Select("SELECT account_id as id, user_id, account_type, balance FROM accounts WHERE account_id = #{id}")
    Account getById(int id);
}