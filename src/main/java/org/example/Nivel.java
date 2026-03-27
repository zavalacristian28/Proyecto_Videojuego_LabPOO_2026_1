package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nivel {
    private String nombre;                                 // Nombre del nivel
    private int numero;                                    // Número de nivel
    private String dificultad;                             // Dificultad (fácil, normal, difícil)
    private List<Obstaculo> obstaculos;                    // Lista de obstáculos en el nivel
    private List<CheckPoint> checkPoints;                  // Lista de checkpoints en el nivel
    private List<ElementoDinamico> elementosDinamicos;     // Lista de elementos dinámicos
    private Inventario inventario;                         // Inventario asociado al nivel

    // Constructor
    public Nivel(String nombre, int numero, String dificultad, Inventario inventario) {
        this.nombre = nombre;
        this.numero = numero;
        this.dificultad = dificultad;
        this.inventario = inventario;
        this.obstaculos = new ArrayList<>();
        this.checkPoints = new ArrayList<>();
        this.elementosDinamicos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }       // Retorna el nombre del nivel
    public int getNumero() { return numero; }          // Retorna el número de nivel
    public String getDificultad() { return dificultad; }// Retorna la dificultad

    public void agregarObstaculo(Obstaculo obstaculo) {
        obstaculos.add(obstaculo); // Añade un obstáculo al nivel
    }

    public void agregarCheckPoint(CheckPoint checkPoint) {
        checkPoints.add(checkPoint); // Añade un checkpoint al nivel
    }

    public void agregarElementoDinamico(ElementoDinamico elemento) {
        elementosDinamicos.add(elemento); // Añade un elemento dinámico
    }

    public void moverElementosDinamicos() {
        // Itera sobre todos los elementos dinámicos e invoca su método mover() con parámetros aleatorios
        System.out.println("\n--- Movimiento de Elementos Dinámicos ---");
        String[] direcciones = {"norte", "sur", "este", "oeste"};
        Random rnd = new Random();

        for (ElementoDinamico elemento : elementosDinamicos) {
            String dir = direcciones[rnd.nextInt(direcciones.length)];
            int dist = rnd.nextInt(10) + 1;
            elemento.mover(dir, dist);
        }
    }

    public void mostrarEstado() {
        System.out.println("=== Nivel: " + nombre + " | Dificultad: " + dificultad + " ==="); // Muestra el estado actual del nivel
        System.out.println("Obstáculos: " + obstaculos.size());
        System.out.println("CheckPoints: " + checkPoints.size());
        System.out.println("Elementos Dinámicos: " + elementosDinamicos.size());
    }
}
