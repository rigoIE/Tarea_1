package com.mycompa.tarea1_kevinrigo;

public class GestorPedido {
    // Las cabezas de las lista, tanto de pedidos y del algoritmoVoraz
      private NodoPedido cabezaPedido;
      private NodoPedido cabezaSolucion;
      private double gananciaTotal;
      private int tiempoTotalUtilizado;
      private int cantidadEntregasTardias;
      private int cantidadEntregasPuntales;
      private Double penalizacionTotal;
    // constructor
    public GestorPedido() {
        this.cabezaPedido = null;
        this.cabezaSolucion = null;
        this.gananciaTotal = 0;
        this.tiempoTotalUtilizado = 0;
        this.cantidadEntregasTardias = 0;
        this.cantidadEntregasPuntales = 0;
        this.penalizacionTotal = 0.0;
    }
    
    // Getters de los atributos
    public NodoPedido getCabezaPedido() {
        return cabezaPedido;
    }

    public NodoPedido getCabezaSolucion() {
        return cabezaSolucion;
    }

    public double getGananciaTotal() {
        return gananciaTotal;
    }

    public int getTiempoTotalUtilizado() {
        return tiempoTotalUtilizado;
    }

    public int getCantidadEntregasTardias() {
        return cantidadEntregasTardias;
    }

    public Double getPenalizacionTotal() {
        return penalizacionTotal;
    }

    public int getCantidadEntregasPuntales() {
        return cantidadEntregasPuntales;
    }
      
      public boolean listaVacia(NodoPedido cabeza){ 
          return cabeza == null;
      }
      // Se inserta el producto al final de la lista.
      public boolean insertarPedido(Pedido pedido){
           // verificar que el nuevo pedido no se encuentre duplicado
     if (pedidoDuplicado(pedido.getCodigoPedido())) {
        return false;
    }
      // Declaracion e inicializacion del nuevo nodoPedido
      NodoPedido nuevoPedido = new NodoPedido();
      nuevoPedido.setPedido(pedido);
      
      // Si la lista esta vacia o si relacion de la cabeza es menor al del nuevo pedido
          if (listaVacia(cabezaPedido) || cabezaPedido.getPedido().relacion() <= nuevoPedido.getPedido().relacion()) {
          nuevoPedido.setSiguiente(cabezaPedido);
          cabezaPedido = nuevoPedido;
          return true;
          }
   
      // Declaramos una variable auxiliar para recorrer la lista
      NodoPedido auxPedido = cabezaPedido;
        while(auxPedido.getSiguiente() != null && auxPedido.getSiguiente().getPedido().relacion() >= nuevoPedido.getPedido().relacion()){
        auxPedido = auxPedido.getSiguiente();
        }
        nuevoPedido.setSiguiente(auxPedido.getSiguiente());
        auxPedido.setSiguiente(nuevoPedido);
        return true;
        }
    // Recorre la lista para evitar que se encuentre un pedido repetido o duplicado
    public boolean pedidoDuplicado(String codigo) {
    NodoPedido aux = cabezaPedido;
    while (aux != null) {
        if (aux.getPedido().getCodigoPedido().equalsIgnoreCase(codigo)) {
            return true;
        }
        aux = aux.getSiguiente();
    }
    return false;
}
     /* -  En este algortimo se utiliza un criterio voraz, de relacion Ganancia/tiempo, es una opcion donde se maximizan las ganacias de un pedido en relacion a tiempo y ganancia,
       a travez de un metodo dentro de la clase "Pedido" llamado relacion() que retorna un double calculando la ganancia Asociada / tiempo Estimado. 
      Usando este criterio se ordena la lista de pedidos, en el metodo ordenarLista() haciendo llamados al metodo relacion().
      -  De este modo ya en el metodo algoritmoVoraz(), se toma la decicion si alcanza otro pedido o no, durante un turno de trabajo, asi como se calcula la ganancia Total,
      tiempo Total Utilizado, cantidad Entregas Tardias y penalizacion Total.
      */
      public NodoPedido algoritmoVoraz(int turnoDeTrabajo){
      int tiempolimite = turnoDeTrabajo;
      cabezaSolucion = null;
      tiempoTotalUtilizado = 0;
      gananciaTotal = 0;
      cantidadEntregasTardias = 0;
      cantidadEntregasPuntales =0;
      penalizacionTotal = 0.0;
        // en caso que lista este vacia
          if (listaVacia(cabezaPedido)) { 
          return null;
          }
       
        NodoPedido aux = cabezaPedido; // Declaracion e inicialiacion de aux, que recorre la lista ordenada.
            while(aux != null){
                // ---- Determina la condicion de factibilidad ---
                if (tiempoTotalUtilizado + aux.getPedido().getTiempoEstimado() <= tiempolimite) {
                insertarEnSolucion(aux.getPedido());
                    
                  int momentoFinalizacion = tiempoTotalUtilizado + aux.getPedido().getTiempoEstimado();
                  // Ganancia total += ganancia Asociada + gananciaTiempo
                  if (tiempoTotalUtilizado <= aux.getPedido().getTiempoEstimado()) {
                gananciaTotal += (aux.getPedido().getGananciaAsociada() + aux.getPedido().getGananciaTiempo());
                cantidadEntregasPuntales++;
                }
                  // Ganancia Total += ganancia Asociada - penalizacion 
             else {
                cantidadEntregasTardias++;
                penalizacionTotal += aux.getPedido().getPenalizacion();
                gananciaTotal += (aux.getPedido().getGananciaAsociada() - aux.getPedido().getPenalizacion());
            }
            
            tiempoTotalUtilizado = momentoFinalizacion;
                }
                aux = aux.getSiguiente();
            }
       
      return cabezaSolucion;}
      
    public void insertarEnSolucion(Pedido p) {
    NodoPedido nuevo = new NodoPedido();
    nuevo.setPedido(p);
    if (cabezaSolucion == null) {
        cabezaSolucion = nuevo;
    } else {
        NodoPedido aux = cabezaSolucion;
        while (aux.getSiguiente() != null) aux = aux.getSiguiente();
        aux.setSiguiente(nuevo);
    }
}
    // Listar la lista que desee si la del algortimo o lista de entrada pedidos.
    public String Listar(NodoPedido cabeza){
        if (listaVacia(cabeza)) {
            return "";
        }
    String lista = "";
    NodoPedido aux = cabeza;
    int contadorPedido = 1;
        while (aux != null) {            
        lista += contadorPedido + ". Relacion: " + aux.getPedido().relacion() + " | " +aux.getPedido().toString() + "\n";
        aux = aux.getSiguiente();
        contadorPedido ++;
        }
    return lista;} }
