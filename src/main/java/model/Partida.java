package model; 

import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // gera um identificador único para cada jogador

public class Partida {
    private String idPartida; 
    private int maxParticipantes;
    private String status;
    private List<Jogador> jogadores;
    private String idCriador; 

    // construtor
    public Partida() {  
    }
    // construtor com parâmetros
     public Partida(int maxParticipantes, String idCriador) {
        this.idPartida = UUID.randomUUID().toString();
        this.maxParticipantes = maxParticipantes;
        this.status = "ABERTA";
        this.jogadores = new ArrayList<>();
        this.idCriador = idCriador;
    }
    // REGRAS DE NEGOCIO
    public boolean isDisponivel(){
        return "ABERTA".equals(this.status) && this.jogadores.size() < this.maxParticipantes;
        // verifica se a partida está aberta e se ainda há vagas disponíveis
    }
    // getters e setters
    public String getIdPartida() {
        return idPartida;
    }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public int getJogadoresPresentes() {
        return this.jogadores.size();
    }

    public String getIdCriador() {
        return idCriador;
    }
    
}

