 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompa.tarea1_kevinrigo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class InterfazGraficaController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtTiempo;
    @FXML
    private TextField txtGanancia;
    @FXML
    private ComboBox<String> cbPrioridad;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtBono;
    @FXML
    private TextField txtPenalizacion;
    @FXML
    private TextArea txtReporte;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnListaPedidosCompleta;
    @FXML
    private Button btnAlgoritmoVoraz;

    private GestorPedido gestor = new GestorPedido();
    private int jornadaMinutos;

    public void setJornadaMinutos(int jornadaMinutos) {
        this.jornadaMinutos = jornadaMinutos;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPrioridad.getItems().addAll("Alta", "Media", "Baja");
        cbPrioridad.setValue("Media");
    }    

    @FXML
    private void registrarPedido(ActionEvent event) {
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
                mostrarAlerta("Error", "El bono y la penalización no pueden ser negativos."); 
                return; 
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
    }

    @FXML
    private void mostrarListaCompleta(ActionEvent event) {
        if (gestor.Listar(gestor.getCabezaPedido()).equalsIgnoreCase("")) {
            mostrarAlerta("Error", "No existen pedidos para mostrar lista");
            return;
        }
        String ListarPedidos = "LISTA PEDIDOS\n";
        ListarPedidos += gestor.Listar(gestor.getCabezaPedido());
        txtReporte.setText(ListarPedidos);
    }

    @FXML
    private void generarAlgoritmoVoraz(ActionEvent event) {
        if (gestor.Listar(gestor.getCabezaPedido()).equalsIgnoreCase("")) {
            mostrarAlerta("Error", "No existen pedidos para generar el algoritmo");
            return;
        }
        gestor.algoritmoVoraz(jornadaMinutos);

        String reporte = "===== REPORTE VORAZ =====\n\n";
        reporte += gestor.Listar(gestor.getCabezaSolucion());

        reporte += "\n===== RESUMEN DEL ALGORITMO =====\n" +
                "Total Pedidos Seleccionado " + gestor.getTotalPedidosSeleccionados()+ "\n" 
                + "Ganancia Total: $" + gestor.getGananciaTotal() + "\n" 
                + "Entregas Puntuales: " + gestor.getCantidadEntregasPuntales() + "\n"
                + "Entregas Tardías: " + gestor.getCantidadEntregasTardias() + "\n"
                + "Penalización Total: $" + gestor.getPenalizacionTotal() + "\n"
                + "Tiempo Total Utilizado: " + gestor.getTiempoTotalUtilizado() + " minutos";

        txtReporte.setText(reporte);
    }

    private void limpiar(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
