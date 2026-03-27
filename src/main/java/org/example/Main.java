package org.example;

public class Main {
    public static void main(String[] args) {
        // Implementar las clases del diagrama anexo, agregar una clase Main en donde se cree un objeto del tipo Nivel con todos sus atributos
        Inventario miInventario = new Inventario(5);
        Nivel nivel1 = new Nivel("Ruinas Antiguas", 1, "Difícil", miInventario);

        Personaje prota = new Personaje("Aventurero", 100, 0, 0);
        Utileria antorcha = new Utileria("Antorcha", "Ilumina el camino", 2, 2);
        Obstaculo trampa = new Obstaculo("Trampa de Picos", 30, 5, 5);
        CheckPoint guardado = new CheckPoint("Campamento", 10, 10);

        nivel1.agregarElementoDinamico(prota);
        nivel1.agregarElementoDinamico(antorcha);
        nivel1.agregarObstaculo(trampa);
        nivel1.agregarCheckPoint(guardado);

        nivel1.mostrarEstado();

        nivel1.moverElementosDinamicos();
    }
}