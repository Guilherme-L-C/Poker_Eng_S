import io.javalin.Javalin;
import model.Jogador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Poker {
    private static List<Jogador> jogadores = new ArrayList<>();

    public static void main(String[] args) {
        var app = Javalin.create().start(7070);

        // US01 - Cadastro de jogadores
        app.post("/poker/cadastro", ctx -> {
            Jogador dados = ctx.bodyAsClass(Jogador.class);

            if (dados.getNome() == null || dados.getNome().isEmpty() ||
                dados.getEmail() == null || dados.getEmail().isEmpty() ||
                dados.getSenha() == null || dados.getSenha().isEmpty()) {

                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Preencha todos os campos.");
                ctx.status(400).json(erro);
                return;
            }

            Jogador novoJogador = new Jogador(dados.getNome(), dados.getEmail(), dados.getSenha());
            jogadores.add(novoJogador);

            HashMap<String, String> resposta = new HashMap<>();
            resposta.put("id", novoJogador.getId());
            ctx.status(201).json(resposta);
        });
    }
}