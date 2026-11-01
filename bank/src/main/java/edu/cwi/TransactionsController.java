package edu.cwi;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


import static j2html.TagCreator.*;

public class TransactionsController implements CrudHandler {
    
    private final Map<String, String> transactions = new ConcurrentHashMap<>();

    @Override
    public void getAll(Context ctx) {
        ctx.html(renderPage());
    }

    @Override
    public void getOne(Context ctx, String resourceId) {

        String transaction = transactions.get(resourceId);

        if (transaction != null) {
            ctx.html(h1(resourceId + ": " + transaction).render());
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    @Override
    public void create(Context ctx) {

        String body = ctx.formParam("text");
        String id = String.valueOf(transactions.size() + 1);

        transactions.put(id, body);

		ctx.redirect("/transactions");
    }

    @Override
    public void update(Context ctx, String resourceId) {

        if (transactions.containsKey(resourceId)) {
            transactions.put(resourceId, ctx.formParam("text"));

            ctx.html(renderPage());
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    @Override
    public void delete(Context ctx, String resourceId) {

        transactions.remove(resourceId);

		// Use ctx to render all transactions html
        ctx.html(renderPage());
    }

    private String renderPage(){
        return html(body(
            h1("Transaction"),
            
            form().withMethod("post").withAction("./transactions").with(
                input().withName("text"),
                button("Add")
            ),

            each(transactions.keySet(), id -> div(
                span(id + ": " + transactions.get(id)),
                form().attr("onsubmit", "send(event, 'PATCH', " + id + ")").with(
                    input().withName("text"),
                    button("Update")
                ),
                form().attr("onsubmit", "send(event, 'DELETE', " + id + ")").with(
                    button("Delete")
                )
            )),
            script(rawHtml(
                "async function send(e, method, id) {" +
                " e.preventDefault();" +
                " await fetch('/transactions/' + id, { method: method, body: new URLSearchParams(new FormData(e.target)) });" +
                " location.reload();" +
                "}"
            ))
        )).render();
    }
}
