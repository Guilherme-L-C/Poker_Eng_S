package model; 

import java.util.UUID; // gera um identificador único para cada jogador

public class Jogador {
    private String id;
    private String nome;
    private String email;
    private String senha;

    // construtores
    public Jogador(){
    }
    // construtor com parâmetros
    public Jogador(String nome, String email, String senha) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // getters
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
}   