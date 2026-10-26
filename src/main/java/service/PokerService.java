package service;

import model.Jogador;
import model.Partida;

import java.util.ArrayList;
import java.util.List;

public class PokerService {
    // listas em memoria
    private List<Jogador> jogadores = new ArrayList<>();
    private List<Partida> partidas = new ArrayList<>();

    public PokerService() {
        // mesas de teste iniciais
        partidas.add(new Partida("sistema", 6));
        partidas.add(new Partida("sistema", 8));
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    // US01: cadastra jogador e valida campos obrigatorios
    public Jogador cadastrarJogador(String nome, String email, String senha) {
        if (nome == null || nome.isEmpty() ||
            email == null || email.isEmpty() ||
            senha == null || senha.isEmpty()) {
            return null;
        }

        Jogador novo = new Jogador(nome, email, senha);
        jogadores.add(novo);
        return novo;
    }

    // US09: retorna apenas partidas abertas com vagas
    public List<Partida> listarPartidasAbertas() {
        List<Partida> abertas = new ArrayList<>();
        for (Partida p : partidas) {
            if (p.isDisponivel()) {
                abertas.add(p);
            }
        }
        return abertas;
    }

    // busca jogador por id
    public Jogador buscarJogadorPorId(String id) {
        for (Jogador j : jogadores) {
            if (j.getId().equals(id)) {
                return j;
            }
        }
        return null;
    }

    // busca partida por id
    public Partida buscarPartidaPorId(String id) {
        for (Partida p : partidas) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
