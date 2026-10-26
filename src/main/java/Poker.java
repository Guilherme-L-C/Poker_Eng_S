import io.javalin.Javalin;
import model.Jogador;
import model.Partida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Poker {
    private static List<Jogador> jogadores = new ArrayList<>();
    private static List<Partida> partidas = new ArrayList<>();

    public static List<Jogador> getJogadores() {
        return jogadores;
    }

    public static List<Partida> getPartidas() {
        return partidas;
    }

    public static void main(String[] args) {
        var app = Javalin.create().start(7070); // inicia o servidor na porta 7070

        // US01 - Cadastro de jogadores
        app.post("/poker/cadastro", ctx -> { // caminho do endpoint
            Jogador dados = ctx.bodyAsClass(Jogador.class);

            // validação dos dados recebidos
            if (dados.getNome() == null || dados.getNome().isEmpty() ||
                dados.getEmail() == null || dados.getEmail().isEmpty() ||
                dados.getSenha() == null || dados.getSenha().isEmpty()) {
                
                // cria um HashMap para enviar a mensagem de erro
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

        // US12 - Início de uma partida
        app.post("/poker/{idPartida}/iniciar", ctx -> {
            String idPartida = ctx.pathParam("idPartida");

            // Identificação do jogador responsável: pode vir via parâmetro de consulta (?idJogador=...) ou corpo JSON
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

            // 1. Verifica se a partida existe
            Partida partida = null;
            for (Partida p : partidas) {
                if (p.getId().equals(idPartida)) {
                    partida = p;
                    break;
                }
            }

            if (partida == null) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Partida não encontrada.");
                ctx.status(404).json(erro);
                return;
            }

            // 2. Verifica se a partida já foi iniciada
            if (partida.isIniciada()) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "A partida já foi iniciada.");
                ctx.status(400).json(erro);
                return;
            }

            // 3. Verifica se o jogador responsável possui autorização para iniciá-la
            if (!idJogador.equals(partida.getIdCriador())) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Apenas o jogador responsável pela mesa pode iniciar a partida.");
                ctx.status(403).json(erro);
                return;
            }

            // 4. Verifica se há participantes suficientes para iniciar o jogo (mínimo de 2 participantes)
            if (partida.getJogadores() == null || partida.getJogadores().size() < 2) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Número insuficiente de jogadores para iniciar a partida (mínimo de 2 participantes).");
                ctx.status(400).json(erro);
                return;
            }

            // Inicia a partida com sucesso
            partida.setIniciada(true);

            HashMap<String, Object> resposta = new HashMap<>();
            resposta.put("status", "Partida iniciada com sucesso.");
            resposta.put("idPartida", partida.getId());
            ctx.status(200).json(resposta);
        });
    }
}
