<%-- Java imports --%>
@import java.util.Map 

<%-- Parameters --%>
@param Map<String, String> transactions

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Transactions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <h1>Transactions</h1>
    <form method="post" action="/transactions">
        <input name="text">
        <button>Add</button>
    </form>
    <ul>
		<%-- Loop through the transactions map --%>
		@for(var entry : transactions.entrySet())
			<li>
                ${entry.getKey()}: ${entry.getValue()}
                
                <form data-id="${entry.getKey()}" onsubmit="send(event, 'PATCH')">
                    <input name="text">
                    <button>Update</button>
                </form>

                <form data-id="${entry.getKey()}" onsubmit="send(event, 'DELETE')">
                    <button>Delete</button>
                </form>
            </li>
		@endfor
    </ul>
    <script>
        async function send(e, method) {
            e.preventDefault();
            await fetch('/transactions/' + e.target.dataset.id, {method: method, body: new URLSearchParams(new FormData(e.target))});
            location.reload();
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>