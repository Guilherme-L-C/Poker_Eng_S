package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// #CLASSE PARTIDA
public class Partida {
    private String id;
    private String idCriador;
    private List<Jogador> jogadores;
    private int maxJogadores;
    private boolean iniciada;

    // Construtor padrão (necessário para serialização/desserialização JSON)
    public Partida() {
        this.id = UUID.randomUUID().toString();
        this.jogadores = new ArrayList<>();
        this.maxJogadores = 6;
        this.iniciada = false;
    }

    // Construtor com idCriador e maxJogadores
    public Partida(String idCriador, int maxJogadores) {
        this();
        this.idCriador = idCriador;
        this.maxJogadores = maxJogadores;
    }

    // Construtor com idCriador (padrão de 6 jogadores)
    public Partida(String idCriador) {
        this(idCriador, 6);
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(String idCriador) {
        this.idCriador = idCriador;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public int getMaxJogadores() {
        return maxJogadores;
    }

    public void setMaxJogadores(int maxJogadores) {
        this.maxJogadores = maxJogadores;
    }

    public boolean isIniciada() {
        return iniciada;
    }

    public void setIniciada(boolean iniciada) {
        this.iniciada = iniciada;
    }

    // Métodos auxiliares
    public int getNumeroJogadores() {
        return this.jogadores != null ? this.jogadores.size() : 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (this.jogadores == null) {
            this.jogadores = new ArrayList<>();
        }
        this.jogadores.add(jogador);
    }
}
