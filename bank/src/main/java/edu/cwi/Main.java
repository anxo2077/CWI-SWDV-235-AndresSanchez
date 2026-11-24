package edu.cwi;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main 
{
    public static void main( String[] args )
    {
        Javalin.create( config -> {
            config.fileRenderer(new JavalinJte());

            config.staticFiles.add("/public", Location.CLASSPATH);

            config.routes.beforeMatched(ctx -> {
                if (ctx.routeRoles().isEmpty()) {
                    return; 
                }

                Object role = ctx.cookieStore().get("user-role");

                if (role == null || !ctx.routeRoles().contains(AppRole.valueOf(role.toString()))) {
                    throw new UnauthorizedResponse();
                }
            });

            config.routes.apiBuilder(() -> {

                post("/login", AuthController::authenticate);
                get("/logout", AuthController::logout);

                get("/accounts", AccountsController::getAll, AppRole.CUSTOMER);
                get("/account/{id}", AccountsController::getOne, AppRole.CUSTOMER);

                path("/transaction", () -> {
                    crud("/{id}", new TransactionController(), AppRole.CUSTOMER);
                });

                path("/transactions", () -> {
                    crud("/{id}", new TransactionsController());
                });

                path("/users", () -> {
                    crud("/{id}", new UsersController(), AppRole.ADMIN);
                });
            });
        }

        ).start(7070);
    }
}