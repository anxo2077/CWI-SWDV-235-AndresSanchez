@import java.util.List
@import edu.cwi.data.User
@import edu.cwi.data.Account

@param User user
@param List<Account> accounts

@template.jte.bankLayout(
    title = "Accounts",
    content = @`
    <div class="card">
      <div class="card-body">
        <h4 class="card-title">${user.getFirstName()} ${user.getLastName()}</h4>
        <h6 class="card-subtitle mb-2 text-body-secondary">User Accounts</h6>
        <table class="table table-striped">
          <thead>
            <tr><th>Account</th><th>Balance</th><th></th></tr>
          </thead>
          <tbody>
          @for(var account : accounts)
            <tr>
              <th>${account.getId()}</th>
              <td>${account.formatBalance()}</td>
              <td><a href="/account/${account.getId()}">Detail</a></td>
            </tr>
          @endfor
          </tbody>
        </table>
      </div>
    </div>
    `
)