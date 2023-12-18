package hexlet.code;

import io.javalin.Javalin;

public final class App {

    public static Javalin getApp() {

        Javalin app = Javalin.create();
        app.get("/", ctx -> {
            ctx.result("Hello World");
        });
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
