import io.javalin.Javalin;

    public class Poker {
        public static void main(String[] args) {
            var app = Javalin.create().start(7070);
            app.get("/poker", ctx -> ctx.result("API do Poker online!"));
        }
    }