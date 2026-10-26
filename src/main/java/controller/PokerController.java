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

        // US12 - inicio de uma partida
        app.post("/poker/{idPartida}/iniciar", ctx -> {
            String idPartida = ctx.pathParam("idPartida");

            // identificacao do jogador por query param ou body
            String idJogador = ctx.queryParam("idJogador");
            if (idJogador == null || idJogador.trim().isEmpty()) {
                try {
                    HashMap<?, ?> body = ctx.bodyAsClass(HashMap.class);
                    if (body != null && body.containsKey("idJogador") && body.get("idJogador") != null) {
                        idJogador = body.get("idJogador").toString();
                    }
                } catch (Exception ignored) {
                }
            }

            if (idJogador == null || idJogador.trim().isEmpty()) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Identificador do jogador (idJogador) não informado.");
                ctx.status(400).json(erro);
                return;
            }

            // busca a partida no servico
            Partida partida = service.buscarPartidaPorId(idPartida);

            if (partida == null) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Partida não encontrada.");
                ctx.status(404).json(erro);
                return;
            }

            // validacoes de dominio
            try {
                partida.iniciar(idJogador);

                HashMap<String, Object> resposta = new HashMap<>();
                resposta.put("mensagem", "Partida iniciada com sucesso.");
                resposta.put("idPartida", partida.getId());
                resposta.put("status", partida.getStatus());
                ctx.status(200).json(resposta);
            } catch (SecurityException e) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", e.getMessage());
                ctx.status(403).json(erro);
            } catch (IllegalStateException | IllegalArgumentException e) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", e.getMessage());
                ctx.status(400).json(erro);
            }
        });
    }
}
