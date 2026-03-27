package org.example;

public class Recompensa implements Inventariable {
    private String nombre; // Nombre de la recompensa
    private int valor;     // Puntos o valor de la recompensa
    private String tipo;   // Tipo de recompensa

    // Constructor que inicializa la recompensa
    public Recompensa(String nombre, int valor, String tipo) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getNombre() { return nombre; } // Retorna el nombre
    public int getValor() { return valor; }      // Retorna el valor
    public String getTipo() { return tipo; }     // Retorna el tipo

    @Override
    public void registrar() {
        System.out.println("Recompensa " + nombre + " registrada."); // Implementación de Inventariable
    }

    @Override
    public void borrar() {
        System.out.println("Recompensa " + nombre + " borrada."); // Implementación de Inventariable
    }
}
