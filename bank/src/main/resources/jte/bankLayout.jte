@import gg.jte.Content

@param String title
@param Content content

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
  </head>
  <body>
    <nav class="navbar bg-body-tertiary mb-5">
      <div class="container-fluid">
        <span class="navbar-brand">Javalin Bank</span>
        <a class="nav-link link-primary" href="/logout">Log Out</a>
      </div>
    </nav>

    <div class="container">
      ${content}
    </div>

    <footer class="container mt-5">
      <p>&copy; 2026 Javalin Bank</p>
    </footer>
  </body>
</html>