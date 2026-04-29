package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class HelloApplication extends Application {

    private Pokemon jugador;
    private Pokemon rival;
    private Recompensa pocion;

    private ProgressBar barHPJugador;
    private ProgressBar barHPRival;
    private Label lblMensajes;
    private Label lblVidaJugador;
    private Label lblVidaRival;
    private HBox panelBotones;

    @Override
    public void start(Stage stage) {
        inicializarCombate();

        AnchorPane root = new AnchorPane();

        MenuBar menuBar = new MenuBar();
        Menu menuArchivo = new Menu("Partida");
        MenuItem itemGuardar = new MenuItem("Guardar Progreso");
        MenuItem itemCargar = new MenuItem("Cargar Anterior");

        itemGuardar.setOnAction(e -> guardarEstado());
        itemCargar.setOnAction(e -> cargarEstado());

        menuArchivo.getItems().addAll(itemGuardar, itemCargar);
        menuBar.getMenus().add(menuArchivo);

        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);

        Image imgFondo = new Image(getClass().getResource("/images/Fondo.png").toExternalForm());
        BackgroundImage bg = new BackgroundImage(imgFondo, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        root.setBackground(new Background(bg));

        ImageView pidgeotView = crearVistaPokemon("/images/pidgeot.png", 220, 380, 60);
        ImageView houndoomView = crearVistaPokemon("/images/houndoom.png", 220, 60, 200);

        // Instanciamos las variables primero (más claro para el compilador y nivel básico POO)
        barHPRival = new ProgressBar(1.0);
        lblVidaRival = new Label();
        VBox boxVidaRival = crearCajaVida("Pidgeot (Rival)", barHPRival, lblVidaRival);
        AnchorPane.setTopAnchor(boxVidaRival, 50.0);
        AnchorPane.setLeftAnchor(boxVidaRival, 30.0);

        barHPJugador = new ProgressBar(1.0);
        lblVidaJugador = new Label();
        VBox boxVidaJugador = crearCajaVida("Houndoom (Tú)", barHPJugador, lblVidaJugador);
        AnchorPane.setBottomAnchor(boxVidaJugador, 180.0);
        AnchorPane.setRightAnchor(boxVidaJugador, 30.0);

        lblMensajes = new Label("¡Un Pidgeot salvaje apareció!");
        lblMensajes.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        lblMensajes.setTextFill(Color.WHITE);
        lblMensajes.setStyle("-fx-background-color: rgba(0,0,0,0.85); -fx-padding: 15; -fx-border-color: white; -fx-border-radius: 5;");
        lblMensajes.setPrefWidth(660);
        AnchorPane.setBottomAnchor(lblMensajes, 100.0);
        AnchorPane.setLeftAnchor(lblMensajes, 20.0);

        panelBotones = crearMenuAtaques();
        AnchorPane.setBottomAnchor(panelBotones, 25.0);
        AnchorPane.setLeftAnchor(panelBotones, 20.0);
        AnchorPane.setRightAnchor(panelBotones, 20.0);

        root.getChildren().addAll(menuBar, pidgeotView, houndoomView, boxVidaRival, boxVidaJugador, lblMensajes, panelBotones);

        actualizarInterfaz();

        Scene scene = new Scene(root, 700, 480);
        stage.setTitle("Pokémon Battle System - Práctica 10");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void inicializarCombate() {
        jugador = new Pokemon("Houndoom", 150, 0, 150, 0, 0, 85, 45, null);
        jugador.aprenderMovimiento(new Movimiento("Lanzallamas", 55));
        jugador.aprenderMovimiento(new Movimiento("Triturar", 45));

        rival = new Pokemon("Pidgeot", 160, 0, 160, 0, 0, 75, 55, null);
        rival.aprenderMovimiento(new Movimiento("Vendaval", 50));
        rival.aprenderMovimiento(new Movimiento("Ataque Ala", 40));

        pocion = new Recompensa("Poción", 50, "Curación");
    }

    private ImageView crearVistaPokemon(String ruta, double ancho, double x, double y) {
        ImageView view = new ImageView(new Image(getClass().getResource(ruta).toExternalForm()));
        view.setFitWidth(ancho);
        view.setPreserveRatio(true);
        view.setLayoutX(x);
        view.setLayoutY(y);
        return view;
    }

    private VBox crearCajaVida(String nombre, ProgressBar barra, Label lblVida) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: rgba(255,255,255,0.75); -fx-padding: 8; -fx-background-radius: 8; -fx-border-color: #444;");
        Label lblNom = new Label(nombre);
        lblNom.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        barra.setPrefWidth(180);
        box.getChildren().addAll(lblNom, barra, lblVida);
        return box;
    }

    private HBox crearMenuAtaques() {
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        for (int i = 0; i < jugador.getMovimientos().size(); i++) {
            int idx = i;
            Button btn = new Button(jugador.getMovimientos().get(i).getNombre());
            btn.setPrefSize(150, 45);
            btn.setOnAction(e -> ejecutarTurno(idx));
            hbox.getChildren().add(btn);
        }

        Button btnPocion = new Button("Usar Poción");
        btnPocion.setPrefSize(150, 45);
        btnPocion.setStyle("-fx-base: #2980b9; -fx-text-fill: white;");
        btnPocion.setOnAction(e -> {
            jugador.usarPocion(pocion);
            lblMensajes.setText("Houndoom usó Poción.");
            actualizarInterfaz();
            panelBotones.setDisable(true);
            PauseTransition p = new PauseTransition(Duration.seconds(1.2));
            p.setOnFinished(ev -> turnoRival());
            p.play();
        });
        hbox.getChildren().add(btnPocion);

        return hbox;
    }

    private void ejecutarTurno(int index) {
        panelBotones.setDisable(true);
        jugador.atacar(index, rival);
        lblMensajes.setText("¡Houndoom usó " + jugador.getMovimientos().get(index).getNombre() + "!");
        actualizarInterfaz();

        if (rival.getVida() <= 0) {
            lblMensajes.setText("¡Pidgeot se ha debilitado! Has ganado.");
        } else {
            PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
            pausa.setOnFinished(e -> turnoRival());
            pausa.play();
        }
    }

    private void turnoRival() {
        int rnd = (int) (Math.random() * rival.getMovimientos().size());
        rival.atacar(rnd, jugador);
        lblMensajes.setText("¡Pidgeot salvaje usó " + rival.getMovimientos().get(rnd).getNombre() + "!");
        actualizarInterfaz();

        if (jugador.getVida() <= 0) {
            lblMensajes.setText("¡Houndoom se ha debilitado! Has perdido.");
        } else {
            panelBotones.setDisable(false);
        }
    }

    private void actualizarInterfaz() {
        lblVidaJugador.setText("HP: " + jugador.getVida() + " / 150");
        lblVidaRival.setText("HP: " + rival.getVida() + " / 160");

        double pJ = (double) jugador.getVida() / 150;
        double pR = (double) rival.getVida() / 160;

        barHPJugador.setProgress(pJ);
        barHPRival.setProgress(pR);

        barHPJugador.setStyle(pJ < 0.2 ? "-fx-accent: red;" : pJ < 0.5 ? "-fx-accent: yellow;" : "-fx-accent: green;");
        barHPRival.setStyle(pR < 0.2 ? "-fx-accent: red;" : pR < 0.5 ? "-fx-accent: yellow;" : "-fx-accent: green;");
    }

    private void guardarEstado() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("partida_pokemon.txt"))) {
            writer.write("Pokemon Houndoom " + jugador.getVida());
            writer.newLine();
            writer.write("Pokemon Pidgeot " + rival.getVida());
            writer.newLine();
            lblMensajes.setText("Estado guardado en 'partida_pokemon.txt'");
        } catch (IOException e) {
            lblMensajes.setText("Error al guardar: " + e.getMessage());
        }
    }

    private void cargarEstado() {
        File archivo = new File("partida_pokemon.txt");
        if (!archivo.exists()) {
            lblMensajes.setText("No hay partida guardada.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes[1].equals("Houndoom")) {
                    jugador.setVida(Integer.parseInt(partes[2]));
                } else if (partes[1].equals("Pidgeot")) {
                    rival.setVida(Integer.parseInt(partes[2]));
                }
            }

            actualizarInterfaz();

            if (jugador.getVida() > 0 && rival.getVida() > 0) {
                panelBotones.setDisable(false);
                lblMensajes.setText("¡Partida cargada! Es tu turno.");
            } else {
                panelBotones.setDisable(true);
                lblMensajes.setText("Cargado: El combate ya había terminado.");
            }

        } catch (IOException e) {
            lblMensajes.setText("Error al cargar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}