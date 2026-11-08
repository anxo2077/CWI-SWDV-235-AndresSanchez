@import edu.cwi.UserAccount

@param String title
@param UserAccount account

@template.jte.accountLayout(
    title = title,
    account = @`@template.jte.userAccount(account.name(), account.formatBalance())`,
    detail = @`@template.jte.transactionsList(account.transactions())`
)