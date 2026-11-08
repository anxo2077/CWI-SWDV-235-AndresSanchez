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

                get("/account", ctx -> {
                    String id = ctx.cookieStore().get("user-id");
                    UserAccount account = AuthController.getUser(id).get();

                    ctx.render("jte/accountPage.jte", Map.of("title", "My Account", "account", account));
                }, AppRole.CUSTOMER);

                path("/transactions", () -> {
                    crud("/{id}", new TransactionsController());
                });
            });
        }

        ).start(7070);
    }
}