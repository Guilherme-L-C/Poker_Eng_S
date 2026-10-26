package model;

import java.util.Objects;

public class Carta {
    private Valor valor;
    private Naipe naipe;

    public Carta() {
    }

    public Carta(Valor valor, Naipe naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public Valor getValor() {
        return valor;
    }

    public void setValor(Valor valor) {
        this.valor = valor;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public void setNaipe(Naipe naipe) {
        this.naipe = naipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carta)) return false;
        Carta carta = (Carta) o;
        return valor == carta.valor && naipe == carta.naipe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, naipe);
    }

    @Override
    public String toString() {
        return valor.getSimbolo() + " de " + naipe.getNome();
    }
}
