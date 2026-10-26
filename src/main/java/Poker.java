import controller.PokerController;
import io.javalin.Javalin;
import service.PokerService;

public class Poker {
    public static void main(String[] args) {
        // inicia o servidor na porta 7070
        var app = Javalin.create().start(7070);

        // instancia servico e rotas
        PokerService service = new PokerService();
        new PokerController(app, service);
    }
}
