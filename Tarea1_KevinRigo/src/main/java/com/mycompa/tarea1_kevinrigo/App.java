package com.mycompa.tarea1_kevinrigo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private int jornadaMinutos;

    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialogo = new TextInputDialog("");
        dialogo.setTitle("Jornada Laboral");
        dialogo.setContentText("Ingrese la cantidad de horas de la jornada:");

        while (true) {
            dialogo.showAndWait();
            
            String jornadaLabora = dialogo.getEditor().getText().trim();
            
            if (jornadaLabora.isEmpty()) {
                System.exit(0);
            }

            try {
                int horas = Integer.parseInt(jornadaLabora);
                
                if (horas > 0) {
                    jornadaMinutos = horas * 60;
                    break;
                } else {
                    mostrarAlerta("Error", "La jornada debe ser mayor que cero.");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Ingrese un número entero válido.");
            }
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("InterfazGrafica.fxml"));
            Parent root = fxmlLoader.load();
            
            InterfazGraficaController controller = fxmlLoader.getController();
            controller.setJornadaMinutos(jornadaMinutos);

            primaryStage.setScene(new Scene(root, 1200, 600));
            primaryStage.setTitle("Tarea No.1 - Algoritmo Voraz ");
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la interfaz gráfica.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
