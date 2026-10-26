package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// #CLASSE PARTIDA
public class Partida {
    public static final String STATUS_ABERTA = "ABERTA";
    public static final String STATUS_EM_ANDAMENTO = "EM_ANDAMENTO";
    public static final String STATUS_FINALIZADA = "FINALIZADA";

    private String id;
    private String idCriador;
    private List<Jogador> jogadores;
    private int maxJogadores;
    private String status;

    // Construtor padrão (necessário para serialização/desserialização JSON)
    public Partida() {
        this.id = UUID.randomUUID().toString();
        this.jogadores = new ArrayList<>();
        this.maxJogadores = 6;
        this.status = STATUS_ABERTA;
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

    // Regras de negócio da US12
    public void iniciar(String idSolicitante) {
        if (isEmAndamentoOuFinalizada()) {
            throw new IllegalStateException("A partida já está em andamento ou foi finalizada.");
        }
        if (!isCriador(idSolicitante)) {
            throw new SecurityException("Apenas o jogador responsável pela mesa pode iniciar a partida.");
        }
        if (!temJogadoresSuficientes()) {
            throw new IllegalArgumentException("Número insuficiente de jogadores para iniciar a partida (mínimo de 2 jogadores).");
        }

        this.status = STATUS_EM_ANDAMENTO;
        distribuirCartas();
    }

    // Rotina da US13 (Distribuição de cartas) acionada ao iniciar
    public void distribuirCartas() {
        // Ponto de integração para a US13 quando as classes Baralho e Carta forem implementadas
    }

    public boolean isCriador(String idJogador) {
        return this.idCriador != null && this.idCriador.equals(idJogador);
    }

    public boolean isEmAndamentoOuFinalizada() {
        return STATUS_EM_ANDAMENTO.equals(this.status) || STATUS_FINALIZADA.equals(this.status);
    }

    public boolean temJogadoresSuficientes() {
        return this.jogadores != null && this.jogadores.size() >= 2;
    }

    // Método de entrada de jogador (US10)
    public void entrar(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo.");
        }
        if (isEmAndamentoOuFinalizada()) {
            throw new IllegalStateException("A partida já foi iniciada.");
        }
        if (this.jogadores == null) {
            this.jogadores = new ArrayList<>();
        }
        if (this.jogadores.size() >= this.maxJogadores) {
            throw new IllegalStateException("A mesa atingiu o limite máximo de participantes.");
        }
        for (Jogador j : this.jogadores) {
            if (j.getId() != null && j.getId().equals(jogador.getId())) {
                throw new IllegalArgumentException("Jogador já está presente na mesa.");
            }
        }
        this.jogadores.add(jogador);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIniciada() {
        return STATUS_EM_ANDAMENTO.equals(this.status);
    }

    public void setIniciada(boolean iniciada) {
        this.status = iniciada ? STATUS_EM_ANDAMENTO : STATUS_ABERTA;
    }

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
