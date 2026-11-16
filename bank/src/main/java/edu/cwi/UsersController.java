package edu.cwi;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;

import edu.cwi.data.*;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class UsersController implements CrudHandler {

    @Override
    public void getAll(Context ctx) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            ctx.json(mapper.getAllUsers());
        }
    }

    @Override
    public void getOne(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            ctx.json(mapper.getById(Integer.parseInt(resourceId)));
        }
    }

    @Override
    public void create(Context ctx) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {

            UserMapper userMapper = session.getMapper(UserMapper.class);
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            TransactionMapper transactionMapper = session.getMapper(TransactionMapper.class);

            User user = ctx.bodyAsClass(User.class);

            BigDecimal deposit = BigDecimal.valueOf(5.00);

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

            userMapper.insert(user);

            Account account = new Account();

            account.setUserId(user.getId());
            account.setBalance(deposit);

            accountMapper.insert(account);

            Transaction transaction = new Transaction();

            transaction.setAccountId(account.getId());
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setAmount(deposit);
            transaction.setDescription("Opening deposit.");

            transactionMapper.insert(transaction);

            session.commit();

            ctx.json(user);
        }
    }

    @Override
    public void update(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = ctx.bodyAsClass(User.class);
            user.setId(Integer.parseInt(resourceId));
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
            ctx.json(user);
        }
    }

    @Override
    public void delete(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(Integer.parseInt(resourceId));
            session.commit();
            ctx.json(Map.of("id", resourceId, "deleted", true));
        }
    }

    public User authenticateUser(Context ctx, String username, String password) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getByUsername(username);

            if (user != null) {
                try {
                    if (BCrypt.checkpw(password, user.getPassword())) {
                        return user;
                    }
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }

            return null;
        }
    }

    public UserAccount getUserAccount(int userId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = session.getMapper(UserMapper.class).getById(userId);
            Account account = session.getMapper(AccountMapper.class).getByUserId(userId);

            Map<String, String> transactions = new LinkedHashMap<>();

            for (Transaction t : session.getMapper(TransactionMapper.class).getByAccountId(account.getId())) {
                transactions.put(String.valueOf(t.getId()), t.getDescription());
            }

            return new UserAccount(
                String.valueOf(user.getId()),
                user.getFirstName() + " " + user.getLastName(),
                user.getRole(),
                account.getBalance().doubleValue(),
                transactions
            );
        }
    }
}