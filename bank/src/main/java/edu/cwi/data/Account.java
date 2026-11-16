package edu.cwi.data;

import java.math.BigDecimal;

public class Account {
    private int id;
    private int userId;
    private String accountType = "CHECKING";
    private BigDecimal balance;

    public Account() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}