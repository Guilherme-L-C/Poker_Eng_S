package model;

public enum Naipe {
    COPAS("Copas"),
    OUROS("Ouros"),
    ESPADAS("Espadas"),
    PAUS("Paus");

    private final String nome;

    Naipe(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
