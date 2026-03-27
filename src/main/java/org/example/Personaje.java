package org.example;

public class Personaje implements Destruible, ElementoDinamico {
    private String nombre; // Nombre del personaje
    private int vida;      // Puntos de vida actuales
    private int posicionX; // Coordenada X en el nivel
    private int posicionY; // Coordenada Y en el nivel

    // Constructor
    public Personaje(String nombre, int vida, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public String getNombre() { return nombre; }    // Retorna el nombre
    public int getVida() { return vida; }           // Retorna la vida actual
    public int getPosicionX() { return posicionX; } // Retorna coordenada X
    public int getPosicionY() { return posicionY; } // Retorna coordenada Y

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
        // Establece nueva posición
    }

    public void recibirDaño(int dano) {
        this.vida -= dano;
        System.out.println(nombre + " recibió daño. Vida actual: " + vida); // Reduce vida según daño recibido
    }

    @Override
    public void mover(String direccion, int distancia) {
        // Implementación de ElementoDinamico - mueve el personaje según dirección (norte, sur, este, oeste) y distancia
        System.out.println("Personaje " + nombre + " se mueve al " + direccion + " " + distancia + " pasos.");
    }

    @Override
    public void destruye() {
        System.out.println("Personaje " + nombre + " destruido."); // Implementación de Destruible
    }
}