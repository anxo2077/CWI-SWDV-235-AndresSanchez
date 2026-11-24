package edu.cwi;

import java.math.BigDecimal;

import org.apache.ibatis.session.SqlSession;

import edu.cwi.data.*;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionController implements CrudHandler {

    @Override
    public void create(Context ctx) {
        Integer userId = ctx.cookieStore().get("user-id");
        int accountId = Integer.parseInt(ctx.formParam("accountId"));

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {

            Account account = session.getMapper(AccountMapper.class).getById(accountId);

            if (account == null || account.getUserId() != userId) {
                ctx.status(404).result("Account not found");
                return;
            }

            Transaction transaction = new Transaction();
            transaction.setAccountId(accountId);
            transaction.setTransactionType(TransactionType.valueOf(ctx.formParam("transactionType")));
            transaction.setAmount(new BigDecimal(ctx.formParam("amount")));
            transaction.setDescription(ctx.formParam("description"));

            TransactionMapper mapper = session.getMapper(TransactionMapper.class);
            mapper.insert(transaction);
            session.commit();

            ctx.redirect("/account/" + accountId);
        }
    }

    @Override
    public void getAll(Context ctx) {
        ctx.status(404);
    }

    @Override
    public void getOne(Context ctx, String resourceId) {
        ctx.status(404);
    }

    @Override
    public void update(Context ctx, String resourceId) {
        ctx.status(404);
    }

    @Override
    public void delete(Context ctx, String resourceId) {
        ctx.status(404);
    }
}