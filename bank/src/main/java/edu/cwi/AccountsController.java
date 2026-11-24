package edu.cwi;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import edu.cwi.data.*;

import io.javalin.http.Context;

public class AccountsController {
    public static void getAll(Context ctx) {
        Integer userId = ctx.cookieStore().get("user-id");

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = session.getMapper(UserMapper.class).getById(userId);
            List<Account> accounts = session.getMapper(AccountMapper.class).getAllByUserId(userId);

            ctx.render("jte/accountsPage.jte", Map.of("user", user, "accounts", accounts));
        }
    }

    public static void getOne(Context ctx) {
        Integer userId = ctx.cookieStore().get("user-id");
        int accountId = Integer.parseInt(ctx.pathParam("id"));

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            Account account = session.getMapper(AccountMapper.class).getById(accountId);

            if (account == null || account.getUserId() != userId) {
                ctx.status(404).result("Account not found");
                return;
            }

            User user = session.getMapper(UserMapper.class).getById(userId);
            List<Transaction> transactions = session.getMapper(TransactionMapper.class).getByAccountId(accountId);

            ctx.render("jte/accountDetailPage.jte", Map.of("user", user, "account", account, "transactions", transactions));
        }
    }
    
}
