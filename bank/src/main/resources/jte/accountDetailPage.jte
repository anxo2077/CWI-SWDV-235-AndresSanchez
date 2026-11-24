@import java.util.List
@import edu.cwi.data.User
@import edu.cwi.data.Account
@import edu.cwi.data.Transaction

@param User user
@param Account account
@param List<Transaction> transactions

@template.jte.bankLayout(
    title = "Account Detail",
    content = @`
    <div class="card">
      <div class="card-header text-end">
        <a href="/accounts">Back to Accounts</a>
      </div>
      <div class="card-body">
        <h4 class="card-title">${user.getFirstName()} ${user.getLastName()}</h4>
        <h6 class="card-subtitle mb-2 text-body-secondary">User Account</h6>
        <table class="table table-striped">
          <thead>
            <tr><th>Account #</th><th>Balance</th></tr>
          </thead>
          <tbody>
            <tr><th>${account.getId()}</th><td>${account.formatBalance()}</td></tr>
          </tbody>
        </table>

        <h5 class="mt-4">Transactions</h5>
        <table class="table table-striped">
          <thead>
            <tr><th>#</th><th>Description</th><th>Amount</th></tr>
          </thead>
          <tbody>
          @for(var t : transactions)
            <tr>
              <th>${t.getId()}</th>
              <td>${t.getDescription()}</td>
              <td>${t.formatAmount()}</td>
            </tr>
          @endfor
          </tbody>
        </table>
      </div>
    </div>
    `
)