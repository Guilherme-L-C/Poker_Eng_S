package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // gera um identificador único para cada jogador

// #CLASSE JOGADOR
public class Jogador {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private List<Carta> cartas;

    // construtores
    public Jogador() {
        this.cartas = new ArrayList<>();
    }

    // construtor com parâmetros
    public Jogador(String nome, String email, String senha) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cartas = new ArrayList<>();
    }

    // getters e setters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public void receberCarta(Carta carta) {
        if (this.cartas == null) {
            this.cartas = new ArrayList<>();
        }
        this.cartas.add(carta);
    }

    public void limparCartas() {
        if (this.cartas != null) {
            this.cartas.clear();
        }
    }
}
