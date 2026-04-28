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
      
      public boolean listaVacia(){ 
          return cabezaPedido == null;
      }
      // Se inserta el producto al final de la lista.
      public boolean insertarProducto(Pedido pedido){
       // Validacion del objeto Pedido, en caso de null, o no tenga un codigo de Pedido
          if (pedido == null || pedido.getCodigoPedido().isEmpty()) {
           return false;
          }
      // Declaracion e inicializacion del nuevo nodoPedido
      NodoPedido nuevoPedido = new NodoPedido();
      nuevoPedido.setPedido(pedido);
      
      // Si la lista esta vacia, el nuevoPedido es la cabeza
          if (listaVacia()) {
          cabezaPedido = nuevoPedido;
          return true;
          }
      // Si la cabeza es igual nuevoPedido, no se puede agregar y retorna false
          if (cabezaPedido.getPedido().getCodigoPedido().equalsIgnoreCase(nuevoPedido.getPedido().getCodigoPedido())) { 
          return false;
          }
     
      // Declaramos una variable auxiliar para recorrer la lista
      NodoPedido auxPedido = cabezaPedido;
        while(auxPedido.getSiguiente() != null){
            // Validacion que evita que un nuevo pedido se encuentre duplicado
            if (auxPedido.getSiguiente().getPedido().getCodigoPedido().equalsIgnoreCase(nuevoPedido.getPedido().getCodigoPedido())) {   
            return false;
            }
        auxPedido = auxPedido.getSiguiente();
        }
        auxPedido.setSiguiente(nuevoPedido);
        return true;
        }
      // Se ordena la lista usando el criterioVoraz que es en base a la relacion Ganancia/Tiempo
      public boolean ordenarLista(){
       // Validar en caso que lista este vacia retorna false;
          if (listaVacia() || cabezaPedido.getSiguiente() == null) {
              return false;
          }
       // declaramos una variable que condiciona el do-while para realizar un cambio.
        boolean cambio;
          do {
         
          cambio = false;
         // Declaramos e inicializamos aux para recorrer la lista
          NodoPedido aux = cabezaPedido;
            while(aux.getSiguiente() != null){
                 if (aux.getPedido().relacion() < aux.getSiguiente().getPedido().relacion()) {
                   // Utilizamos una variable temp para aguardar el pedido temporal que se va a cambiar
                   Pedido temp = aux.getPedido();
                   aux.setPedido(aux.getSiguiente().getPedido());
                   aux.getSiguiente().setPedido(temp);
                   cambio = true;
                }
                 aux = aux.getSiguiente();
            }
          } while (cambio);
      return true;
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
          if (listaVacia()) { 
          return null;
          }
       
        NodoPedido aux = cabezaPedido; // Declaracion e inicialiacion de aux, que recorre la lista ordenada.
            while(aux != null){
                // ---- Determina la condicion de factibilidad ---
                if (tiempoTotalUtilizado + aux.getPedido().getTiempoEstimado() <= tiempolimite) {
                insertarEnSolucion(aux.getPedido());
                    
                  int momentoFinalizacion = tiempoTotalUtilizado + aux.getPedido().getTiempoEstimado();
                  // Ganancia total += ganancia Asociada + gananciaTiempo
                  if (momentoFinalizacion <= aux.getPedido().getTiempoEstimado()) {
                gananciaTotal += (aux.getPedido().getGananciaAsociada() + aux.getPedido().getGananciaTiempo());
                cantidadEntregasTardias++;
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
       
      return null;}
      
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
}   // Listar la lista de Pedidos, en orden que se ingresan
    public String ListarPedidos(){
        if (listaVacia()) {
            return "";
        }
    String lista = "";
    NodoPedido aux = cabezaPedido;
    int contadorPedido = 1;
        while (aux != null) {            
        lista += contadorPedido + ". " + aux.getPedido().toString() + "\n";
        aux = aux.getSiguiente();
        contadorPedido ++;
        }
    return lista;} 
}
