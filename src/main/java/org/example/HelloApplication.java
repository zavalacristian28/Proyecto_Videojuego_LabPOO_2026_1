package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    // Lógica del juego
    private Pokemon jugador;
    private Pokemon rival;
    private Recompensa pocion;

    // Componentes de la Interfaz
    private ProgressBar barHPJugador;
    private ProgressBar barHPRival;
    private Label lblMensajes;
    private Label lblVidaJugador;
    private Label lblVidaRival;
    private HBox panelBotones;

    @Override
    public void start(Stage stage) {
        inicializarCombate();

        // CONTENEDOR RAÍZ
        AnchorPane root = new AnchorPane();

        // 1. Fondo de batalla
        Image imgFondo = new Image(getClass().getResource("/images/Fondo.png").toExternalForm());
        BackgroundImage bg = new BackgroundImage(imgFondo, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        root.setBackground(new Background(bg));

        // 2. Posicionamiento de Sprites (Invertidos según tu petición)
        // Pidgeot (Rival) en la parte superior derecha
        ImageView pidgeotView = crearVistaPokemon("/images/pidgeot.png", 220, 380, 40);

        // Houndoom (Jugador) en la parte inferior izquierda
        ImageView houndoomView = crearVistaPokemon("/images/houndoom.png", 220, 60, 180);

        // 3. Barras de Vida (Acomodadas cerca de cada Pokémon)
        VBox boxVidaRival = crearCajaVida("Pidgeot", barHPRival = new ProgressBar(1.0), lblVidaRival = new Label());
        AnchorPane.setTopAnchor(boxVidaRival, 30.0);
        AnchorPane.setLeftAnchor(boxVidaRival, 40.0);

        VBox boxVidaJugador = crearCajaVida("Houndoom", barHPJugador = new ProgressBar(1.0), lblVidaJugador = new Label());
        AnchorPane.setBottomAnchor(boxVidaJugador, 160.0);
        AnchorPane.setRightAnchor(boxVidaJugador, 40.0);

        // 4. Cuadro de mensajes (Consola inferior)
        lblMensajes = new Label("¡Un Pidgeot salvaje apareció!");
        lblMensajes.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        lblMensajes.setTextFill(Color.WHITE);
        lblMensajes.setStyle("-fx-background-color: rgba(0,0,0,0.8); -fx-padding: 15; -fx-border-color: white; -fx-border-width: 2;");
        lblMensajes.setPrefWidth(660);
        lblMensajes.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setBottomAnchor(lblMensajes, 90.0);
        AnchorPane.setLeftAnchor(lblMensajes, 20.0);

        // 5. Panel de Ataques (Más abajo como solicitaste)
        panelBotones = crearMenuAtaques();
        AnchorPane.setBottomAnchor(panelBotones, 20.0);
        AnchorPane.setLeftAnchor(panelBotones, 20.0);
        AnchorPane.setRightAnchor(panelBotones, 20.0);

        root.getChildren().addAll(pidgeotView, houndoomView, boxVidaRival, boxVidaJugador, lblMensajes, panelBotones);

        actualizarInterfaz();

        Scene scene = new Scene(root, 700, 450);
        stage.setTitle("Duelo Pokémon: Houndoom vs Pidgeot");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void inicializarCombate() {
        // Creamos a los Pokémon con sus estadísticas
        jugador = new Pokemon("Houndoom", 150, 0, 150, 0, 0, 85, 45, null);
        jugador.aprenderMovimiento(new Movimiento("Lanzallamas", 55));
        jugador.aprenderMovimiento(new Movimiento("Triturar", 45));

        rival = new Pokemon("Pidgeot", 160, 0, 160, 0, 0, 75, 55, null);
        rival.aprenderMovimiento(new Movimiento("Vendaval", 50));
        rival.aprenderMovimiento(new Movimiento("Ataque Ala", 40));

        pocion = new Recompensa("Poción Máxima", 50, "Curación");
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
        box.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #333;");
        Label lblNombre = new Label(nombre);
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        barra.setPrefWidth(180);
        barra.setStyle("-fx-accent: #2ecc71;");
        box.getChildren().addAll(lblNombre, barra, lblVida);
        return box;
    }

    private HBox crearMenuAtaques() {
        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));

        for (int i = 0; i < jugador.getMovimientos().size(); i++) {
            int index = i;
            Button btn = new Button(jugador.getMovimientos().get(i).getNombre());
            btn.setPrefSize(160, 50);
            btn.setOnAction(e -> turnoJugador(index));
            hbox.getChildren().add(btn);
        }

        Button btnPocion = new Button("Usar Poción");
        btnPocion.setPrefSize(160, 50);
        btnPocion.setStyle("-fx-base: #3498db; -fx-text-fill: white;");
        btnPocion.setOnAction(e -> usarPocion());
        hbox.getChildren().add(btnPocion);

        return hbox;
    }

    private void turnoJugador(int indiceMov) {
        panelBotones.setDisable(true); // Bloquear botones durante la animación

        Movimiento mov = jugador.getMovimientos().get(indiceMov);
        jugador.atacar(indiceMov, rival);
        lblMensajes.setText("¡Houndoom usó " + mov.getNombre() + "!");

        actualizarInterfaz();

        if (rival.getVida() <= 0) {
            lblMensajes.setText("¡El Pidgeot rival se ha debilitado! ¡Victoria!");
        } else {
            // Esperar 1.5 segundos antes del turno del rival
            PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
            pausa.setOnFinished(e -> turnoRival());
            pausa.play();
        }
    }

    private void usarPocion() {
        panelBotones.setDisable(true);
        jugador.usarPocion(pocion);
        lblMensajes.setText("Houndoom recuperó salud con la Poción.");
        actualizarInterfaz();

        PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
        pausa.setOnFinished(e -> turnoRival());
        pausa.play();
    }

    private void turnoRival() {
        int movAzar = (int) (Math.random() * rival.getMovimientos().size());
        rival.atacar(movAzar, jugador);
        lblMensajes.setText("¡Pidgeot salvaje usó " + rival.getMovimientos().get(movAzar).getNombre() + "!");

        actualizarInterfaz();

        if (jugador.getVida() <= 0) {
            lblMensajes.setText("¡Houndoom se ha debilitado! Has perdido el combate.");
        } else {
            panelBotones.setDisable(false); // Devolver el control al jugador
        }
    }

    private void actualizarInterfaz() {
        // Actualizar valores numéricos
        lblVidaJugador.setText("HP: " + jugador.getVida() + " / 150");
        lblVidaRival.setText("HP: " + rival.getVida() + " / 160");

        // Calcular progreso
        double progresoJ = (double) jugador.getVida() / 150;
        double progresoR = (double) rival.getVida() / 160;

        barHPJugador.setProgress(progresoJ);
        barHPRival.setProgress(progresoR);

        // Cambiar color de barra según salud
        gestionarColorBarra(barHPJugador, progresoJ);
        gestionarColorBarra(barHPRival, progresoR);
    }

    private void gestionarColorBarra(ProgressBar barra, double porcentaje) {
        if (porcentaje < 0.2) {
            barra.setStyle("-fx-accent: #e74c3c;"); // Rojo
        } else if (porcentaje < 0.5) {
            barra.setStyle("-fx-accent: #f1c40f;"); // Amarillo
        } else {
            barra.setStyle("-fx-accent: #2ecc71;"); // Verde
        }
    }

    public static void main(String[] args) {
        launch();
    }
}