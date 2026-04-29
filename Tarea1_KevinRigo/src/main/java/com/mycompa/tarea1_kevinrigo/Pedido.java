
package com.mycompa.tarea1_kevinrigo;

public class Pedido {
 private String codigoPedido; 
    private int tiempoEstimado;
    private double gananciaAsociada;
    private String prioridad;
    private double valorPedido;
    private double gananciaTiempo;
    private double penalizacion;

    public Pedido(String codigoPedido, int tiempoEstimado, double gananciaAsociada, String prioridad, double valorPedido, double gananciaTiempo, double penalizacion) {
        this.codigoPedido = codigoPedido;
        this.tiempoEstimado = tiempoEstimado;
        this.gananciaAsociada = gananciaAsociada;
        this.prioridad = prioridad;
        this.valorPedido = valorPedido;
        this.gananciaTiempo = gananciaTiempo;
        this.penalizacion = penalizacion;
    }
    // Calcula y retorna la relacion de ganancia Asociada, con tiempo Estimado
    public double relacion(){
    return (double) this.gananciaAsociada / this.tiempoEstimado;
    }
    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public double getGananciaAsociada() {
        return gananciaAsociada;
    }

    public void setGananciaAsociada(double gananciaAsociada) {
        this.gananciaAsociada = gananciaAsociada;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public double getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(double valorPedido) {
        this.valorPedido = valorPedido;
    }

    public double getGananciaTiempo() {
        return gananciaTiempo;
    }

    public void setGananciaTiempo(double gananciaTiempo) {
        this.gananciaTiempo = gananciaTiempo;
    }

    public double getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(double penalizacion) {
        this.penalizacion = penalizacion;
    }

    @Override
public String toString() {
    return  codigoPedido + " | Tiempo: " + tiempoEstimado + " min" +" | Ganancia: $" + gananciaAsociada + " | Prioridad: " + prioridad;
}

    
    
        
}
