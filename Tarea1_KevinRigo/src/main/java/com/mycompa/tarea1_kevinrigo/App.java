package com.mycompa.tarea1_kevinrigo;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    private GestorPedido gestor = new GestorPedido();

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10); grid.setVgap(10);

        TextField txtCodigo = new TextField(); 
        TextField txtTiempo = new TextField(); 
        TextField txtGanancia = new TextField(); 
        ComboBox<String> cbPrioridad = new ComboBox<>(); 
        cbPrioridad.getItems().addAll("Alta", "Media", "Baja");
        cbPrioridad.setValue("Media");
        TextField txtValor = new TextField(); 
        TextField txtBono = new TextField(); 
        TextField txtPenalizacion = new TextField(); 
        
        grid.add(new Label("1. Código del Pedido:"), 0, 0); grid.add(txtCodigo, 1, 0);
        grid.add(new Label("2. Tiempo Estimado (min):"), 0, 1); grid.add(txtTiempo, 1, 1);
        grid.add(new Label("3. Ganancia Asociada:"), 0, 2); grid.add(txtGanancia, 1, 2);
        grid.add(new Label("4. Prioridad:"), 0, 3); grid.add(cbPrioridad, 1, 3);
        grid.add(new Label("5. Valor del Pedido:"), 0, 4); grid.add(txtValor, 1, 4);
        grid.add(new Label("6. Ganancia a Tiempo:"), 0, 5); grid.add(txtBono, 1, 5);
        grid.add(new Label("7. Penalización Tarde:"), 0, 6); grid.add(txtPenalizacion, 1, 6);

        TextArea txtReporte = new TextArea();
        txtReporte.setEditable(false);
        txtReporte.setPrefHeight(300);

        Button btnRegistrar = new Button("Registrar Pedido");
        btnRegistrar.setStyle("-fx-background-color: #AB10B0; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRegistrar.setOnAction(e -> {
            if (txtCodigo.getText().isEmpty() || txtTiempo.getText().isEmpty() || 
                txtGanancia.getText().isEmpty() || txtValor.getText().isEmpty() || 
                txtBono.getText().isEmpty() || txtPenalizacion.getText().isEmpty()) {
                mostrarAlerta("Campos Incompletos", "Por favor, llene todos los campos.");
                return;
            }
            try {
                double bono = Double.parseDouble(txtBono.getText().trim().replace(",", "."));
                double penal = Double.parseDouble(txtPenalizacion.getText().trim().replace(",", "."));
                if (bono < 0 || penal < 0) {
                 mostrarAlerta("Error", "El bono y la penalización no pueden ser negativos."); {
                 return; }
}
                Pedido pedido = new Pedido(txtCodigo.getText(), Integer.parseInt(txtTiempo.getText()), 
                           Double.parseDouble(txtGanancia.getText().replace(",", ".")), cbPrioridad.getValue(), 
                           Double.parseDouble(txtValor.getText().replace(",", ".")), bono, penal);
                if (gestor.insertarPedido(pedido)) {
                    txtReporte.appendText("Pedido " + pedido.getCodigoPedido() + " agregado.\n");
                    limpiar(txtCodigo, txtTiempo, txtGanancia, txtValor, txtBono, txtPenalizacion);
                } else {
                    mostrarAlerta("Duplicado", "El código ya existe.");
                }
            } catch (Exception ex) {
                mostrarAlerta("Error", "Revise los formatos numéricos.");   
            }
        });
        Button btnListaPedidosCompleta = new Button("Lista Completa Pedidos");
        btnListaPedidosCompleta.setStyle("-fx-background-color: #4910B0; -fx-text-fill: white; -fx-font-weight: bold;");
        btnListaPedidosCompleta.setOnAction( e-> {
           if (gestor.Listar(gestor.getCabezaPedido()).equalsIgnoreCase("")) {
            mostrarAlerta("Error", "No existen pedidos para mostrar lista");
            return;
        }
        String ListarPedidos = "LISTA PEDIDOS\n";
        ListarPedidos += gestor.Listar(gestor.getCabezaPedido());
        ListarPedidos += "\nGANANCIA TOTAL: $" +
                   String.format("%.2f", gestor.getGananciaTotal());

        txtReporte.setText(ListarPedidos);
        
        });
        Button btnOptimizar = new Button("Generar Lista Voraz");
        btnOptimizar.setStyle("-fx-background-color: #4910B0; -fx-text-fill: white; -fx-font-weight: bold;");
        btnOptimizar.setOnAction(e -> {
        if (gestor.Listar(gestor.getCabezaSolucion()).equalsIgnoreCase("")) {
            mostrarAlerta("Error", "No existen pedidos para generar el algoritmo");
            return;
        }

        gestor.algoritmoVoraz(480);

        String ListarSolucion = "===== REPORTE VORAZ =====\n";
        ListarSolucion += gestor.Listar(gestor.getCabezaSolucion());
        ListarSolucion += "\nGANANCIA TOTAL: $" +
                   String.format("%.2f", gestor.getGananciaTotal());

        txtReporte.setText(ListarSolucion);
    });

        HBox cajaBotones = new HBox(10, btnRegistrar,btnOptimizar);
        VBox root = new VBox(15, grid, cajaBotones, txtReporte);
        root.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(root, 600, 750));
        primaryStage.setTitle("Tarea No.1 - Maven Edition");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void limpiar(TextField... f) { for(TextField x : f) x.clear(); }
    private void mostrarAlerta(String t, String m) { 
        Alert a = new Alert(Alert.AlertType.WARNING); a.setTitle(t); a.setContentText(m); a.show(); 
    }

    public static void main(String[] args) { launch(args); }
}
