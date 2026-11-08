@import java.util.Map 

@param Map<String, String> transactions

<h1>Transactions</h1>
<ul>

@for(var entry : transactions.entrySet())
    <li>${entry.getKey()}: ${entry.getValue()}</li>
@else
    <li>You have no transactions to display.</li>
@endfor
</ul>