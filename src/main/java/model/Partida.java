package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// classe Partida
public class Partida {
    public static final String STATUS_ABERTA = "ABERTA";
    public static final String STATUS_EM_ANDAMENTO = "EM_ANDAMENTO";
    public static final String STATUS_FINALIZADA = "FINALIZADA";

    private String id;
    private String idCriador;
    private List<Jogador> jogadores;
    private int maxJogadores;
    private String status;

    // construtor padrao
    public Partida() {
        this.id = UUID.randomUUID().toString();
        this.jogadores = new ArrayList<>();
        this.maxJogadores = 6;
        this.status = STATUS_ABERTA;
    }

    // construtores com parametros
    public Partida(String idCriador, int maxJogadores) {
        this();
        this.idCriador = idCriador;
        this.maxJogadores = maxJogadores;
    }

    public Partida(int maxJogadores, String idCriador) {
        this(idCriador, maxJogadores);
    }

    public Partida(String idCriador) {
        this(idCriador, 6);
    }

    // regras de negocio da US09
    public boolean isDisponivel() {
        return STATUS_ABERTA.equals(this.status) && (this.jogadores == null || this.jogadores.size() < this.maxJogadores);
    }

    // regras de negocio da US12
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

    // rotina da US13 acionada ao iniciar
    public void distribuirCartas() {
        // ponto de integracao para US13
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

    // entrada de jogador (auxiliar para US10)
    public void entrar(Jogador jogador) {
        if (isEmAndamentoOuFinalizada()) {
            throw new IllegalStateException("A partida já foi iniciada.");
        }
        if (this.jogadores.size() >= this.maxJogadores) {
            throw new IllegalStateException("A mesa atingiu o limite máximo de participantes.");
        }
        for (Jogador j : this.jogadores) {
            if (j.getId().equals(jogador.getId())) {
                throw new IllegalArgumentException("Jogador já está presente na mesa.");
            }
        }
        this.jogadores.add(jogador);
    }

    // getters e setters (compativeis com US09 e US12)
    public String getId() {
        return id;
    }

    public String getIdPartida() {
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

    public int getMaxParticipantes() {
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

    public int getJogadoresPresentes() {
        return getNumeroJogadores();
    }

    public void adicionarJogador(Jogador jogador) {
        if (this.jogadores == null) {
            this.jogadores = new ArrayList<>();
        }
        this.jogadores.add(jogador);
    }
}
