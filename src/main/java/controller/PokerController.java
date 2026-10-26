package controller;

import io.javalin.Javalin;
import model.Jogador;
import model.Partida;
import service.PokerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PokerController {
    private PokerService service;

    public PokerController(Javalin app, PokerService service) {
        this.service = service;
        this.registrarRotas(app);
    }

    private void registrarRotas(Javalin app) {
        // US01 - cadastro de jogadores
        app.post("/poker/cadastro", ctx -> {
            Jogador dados = ctx.bodyAsClass(Jogador.class);

            Jogador novo = service.cadastrarJogador(dados.getNome(), dados.getEmail(), dados.getSenha());

            if (novo == null) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Preencha todos os campos.");
                ctx.status(400).json(erro);
                return;
            }

            HashMap<String, String> resposta = new HashMap<>();
            resposta.put("id", novo.getId());
            ctx.status(201).json(resposta);
        });

        // US09 - consulta de partidas abertas
        app.get("/poker/partidasAbertas", ctx -> {
            List<Partida> partidasAbertas = service.listarPartidasAbertas();

            List<HashMap<String, Object>> resposta = new ArrayList<>();
            for (Partida p : partidasAbertas) {
                HashMap<String, Object> resumo = new HashMap<>();
                resumo.put("idPartida", p.getIdPartida());
                resumo.put("jogadoresPresentes", p.getJogadoresPresentes());
                resumo.put("maxParticipantes", p.getMaxParticipantes());
                resposta.add(resumo);
            }

            ctx.json(resposta);
        });
    }
}
