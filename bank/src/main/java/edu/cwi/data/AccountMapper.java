package edu.cwi.data;

import org.apache.ibatis.annotations.*;

public interface AccountMapper {
    @Select("SELECT account_id as id, user_id, account_type, balance FROM accounts WHERE user_id = #{userId} LIMIT 1")
    Account getByUserId(int userId);

    @Insert("INSERT INTO accounts (user_id, account_type, balance) VALUES (#{userId}, #{accountType}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Account account);
}