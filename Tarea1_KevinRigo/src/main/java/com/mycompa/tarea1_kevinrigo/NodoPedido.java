package com.mycompa.tarea1_kevinrigo;

public class NodoPedido {
    
       private Pedido pedido;
        private NodoPedido siguiente;
        
        public NodoPedido(){
        this.pedido = pedido;
        this.siguiente = null;
        }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public NodoPedido getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPedido siguiente) {
        this.siguiente = siguiente;
    }
        
        
}    


