package org.example;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean seguirJugando = true;

        while (seguirJugando) {

            // Se ajustan los parámetros para coincidir con el constructor de Pokemon.java
            // (nombre, maxVidaH, x, vida, posX, posY, ataque, defensa, strings)
            Pokemon houndoom = new Pokemon("Houndoom", 150, 0, 150, 0, 0, 80, 40, null);
            houndoom.aprenderMovimiento(new Movimiento("Lanzallamas", 50));
            houndoom.aprenderMovimiento(new Movimiento("Triturar", 40));

            Pokemon pidgeot = new Pokemon("Pidgeot", 160, 0, 160, 0, 0, 75, 50, null);
            pidgeot.aprenderMovimiento(new Movimiento("Vendaval", 55));
            pidgeot.aprenderMovimiento(new Movimiento("Ataque Ala", 35));

            Recompensa pocion = new Recompensa("Poción", 40, "Curativo");

            batalla(houndoom, pidgeot, pocion);

            System.out.println("\n¿Quieres otra batalla?");
            System.out.println("1. Sí");
            System.out.println("2. No");

            int opcion = scanner.nextInt();

            if (opcion != 1) {
                seguirJugando = false;
            }
        }

        System.out.println("Gracias por jugar.");
    }

    public static void batalla(Pokemon jugador, Pokemon rival, Recompensa pocion) {

        System.out.println("¡Un " + rival.getNombre() + " salvaje aparece!");
        System.out.println("¡Adelante, " + jugador.getNombre() + "!");

        while (jugador.getVida() > 0 && rival.getVida() > 0) {

            turnoJugador(jugador, rival, pocion);

            if (rival.getVida() <= 0) {
                rival.destruye();
                break;
            }

            turnoRival(jugador, rival);

            if (jugador.getVida() <= 0) {
                jugador.destruye();
                break;
            }
        }

        System.out.println("\n==============================");

        if (jugador.getVida() > 0) {
            System.out.println("¡" + rival.getNombre() + " se ha debilitado! ¡HAS GANADO!");
        } else {
            System.out.println("¡" + jugador.getNombre() + " se ha debilitado! HAS PERDIDO...");
        }

        System.out.println("==============================");
    }

    public static void turnoJugador(Pokemon jugador, Pokemon rival, Recompensa pocion) {

        System.out.println("\n----------------------------------");
        System.out.println(jugador.getNombre() + ": " + jugador.getVida() + " HP | " +
                rival.getNombre() + ": " + rival.getVida() + " HP");

        System.out.println("¿Qué hará " + jugador.getNombre() + "?");

        for (int i = 0; i < jugador.getMovimientos().size(); i++) {
            System.out.println((i + 1) + ". " + jugador.getMovimientos().get(i).getNombre());
        }

        System.out.println("3. Usar " + pocion.getNombre());

        int opcion = scanner.nextInt();

        if (opcion == 3) {
            jugador.usarPocion(pocion);
        } else {
            jugador.atacar(opcion - 1, rival);
        }
    }

    public static void turnoRival(Pokemon jugador, Pokemon rival) {

        System.out.println("\nTurno de " + rival.getNombre() + "...");

        if (rival.getVida() < 40 && Math.random() > 0.5) {
            rival.curar(30);
        } else {
            int movimiento = (int)(Math.random() * rival.getMovimientos().size());
            rival.atacar(movimiento, jugador);
        }
    }
}