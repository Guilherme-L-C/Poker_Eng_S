import controller.PokerController;
import io.javalin.Javalin;
import service.PokerService;

public class Poker {
    public static void main(String[] args) {
        // inicia o servidor na porta 7070
        var app = Javalin.create().start(7070);

<<<<<<< HEAD
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

        // US10 - Entrada em uma partida
        app.post("/poker/{idPartida}/entrar", ctx -> {
            String idPartida = ctx.pathParam("idPartida");

            // Extração do parâmetro de consulta (?idJogador=...) ou corpo JSON como fallback
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

            // 1. Verifica se a partida existe (404)
            Partida partida = null;
            for (Partida p : partidas) {
                if (p.getId() != null && p.getId().equals(idPartida)) {
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

            // 2. Verifica se o jogador existe (404)
            Jogador jogador = null;
            for (Jogador j : jogadores) {
                if (j.getId() != null && j.getId().equals(idJogador)) {
                    jogador = j;
                    break;
                }
            }

            if (jogador == null) {
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", "Jogador não encontrado.");
                ctx.status(404).json(erro);
                return;
            }

            // 3. Executa as validações da entidade e adiciona o jogador
            try {
                partida.entrar(jogador);

                // Resposta de sucesso conforme US10.md (200 OK)
                HashMap<String, String> resposta = new HashMap<>();
                resposta.put("mensagem", "Jogador adicionado com sucesso.");
                resposta.put("idPartida", partida.getId());
                resposta.put("idJogador", jogador.getId());
                ctx.status(200).json(resposta);
            } catch (IllegalStateException | IllegalArgumentException e) {
                // 400 Bad Request se a partida já estiver iniciada, mesa cheia ou jogador já presente
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", e.getMessage());
                ctx.status(400).json(erro);
            }
        });

        // US12 - Início de uma partida
        app.post("/poker/{idPartida}/iniciar", ctx -> {
            String idPartida = ctx.pathParam("idPartida");

            // Identificação do jogador responsável: pode vir via query parameter (?idJogador=...) ou corpo JSON
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

            // 1. Verifica se a partida existe (404)
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

            // Executa as validações de domínio da entidade Partida
            try {
                partida.iniciar(idJogador);

                // Resposta de sucesso conforme US12.md (200 OK)
                HashMap<String, Object> resposta = new HashMap<>();
                resposta.put("mensagem", "Partida iniciada com sucesso.");
                resposta.put("idPartida", partida.getId());
                resposta.put("status", partida.getStatus());
                ctx.status(200).json(resposta);
            } catch (SecurityException e) {
                // 403 Forbidden - solicitante não é o criador da partida
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", e.getMessage());
                ctx.status(403).json(erro);
            } catch (IllegalStateException | IllegalArgumentException e) {
                // 400 Bad Request - partida já iniciada ou jogadores insuficientes
                HashMap<String, String> erro = new HashMap<>();
                erro.put("erro", e.getMessage());
                ctx.status(400).json(erro);
            }
        });
=======
        // instancia servico e rotas
        PokerService service = new PokerService();
        new PokerController(app, service);
>>>>>>> 4abbb92049bf83659a4800037d267e8c7046f9f8
    }
}
