package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Personaje implements Destruible {
    private int puntosAtaque;
    private int puntosDefensa;
    private List<Movimiento> movimientos;

    public Pokemon(String nombre, int maxVidaH, int x, int vida, int posX, int posY, int ataque, int defensa, String[][] strings) {
        super(nombre, vida, posX, posY);
        this.puntosAtaque = ataque;
        this.puntosDefensa = defensa;
        this.movimientos = new ArrayList<>();
    }

    public void aprenderMovimiento(Movimiento m) {
        if (movimientos.size() < 4) movimientos.add(m);
    }

    public List<Movimiento> getMovimientos() { return movimientos; }

    public void atacar(int indiceMov, Pokemon objetivo) {
        if (indiceMov < 0 || indiceMov >= movimientos.size()) return;

        Movimiento m = movimientos.get(indiceMov);
        // Fórmula de daño: (Ataque + Potencia) - (Defensa del rival / 2)
        int danoFinal = (this.puntosAtaque + m.getPotencia()) - (objetivo.puntosDefensa / 2);
        if (danoFinal < 10) danoFinal = 10; // Daño mínimo

        System.out.println("¡" + getNombre() + " usa " + m.getNombre() + "!");
        objetivo.recibirDaño(danoFinal);
    }

    public void usarPocion(Recompensa pocion) {
        System.out.println(getNombre() + " usa " + pocion.getNombre() + ".");
        this.curar(pocion.getValor());
    }
}