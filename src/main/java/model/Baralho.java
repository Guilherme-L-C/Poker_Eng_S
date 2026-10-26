package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private List<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
        this.reiniciar();
    }

    public void reiniciar() {
        this.cartas.clear();
        for (Naipe naipe : Naipe.values()) {
            for (Valor valor : Valor.values()) {
                this.cartas.add(new Carta(valor, naipe));
            }
        }
    }

    public void embaralhar() {
        Collections.shuffle(this.cartas);
    }

    public Carta distribuir() {
        if (this.cartas.isEmpty()) {
            throw new IllegalStateException("O baralho está vazio, não há cartas para distribuir.");
        }
        return this.cartas.remove(0);
    }

    public List<Carta> distribuir(int quantidade) {
        if (quantidade > this.cartas.size()) {
            throw new IllegalStateException("Cartas insuficientes no baralho para distribuir a quantidade solicitada.");
        }
        List<Carta> entregues = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            entregues.add(this.distribuir());
        }
        return entregues;
    }

    public int getQuantidadeCartas() {
        return this.cartas.size();
    }

    public List<Carta> getCartas() {
        return cartas;
    }
}
