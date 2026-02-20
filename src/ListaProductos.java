public class ListaProductos {

    // Atributos
    private Nodo primero;
    private int cantidadProductos;

    // Constructor
    public ListaProductos() {
        this.primero = null;
        this.cantidadProductos = 0;
    }

    // Getters
    public Nodo getPrimero() { return primero; }

    public int getCantidadProductos() { return cantidadProductos; }

    // Setters
    public void setPrimero(Nodo primero) { this.primero = primero; }

    public void setCantidadProductos(int cantidadProductos) { this.cantidadProductos = cantidadProductos; }

    // ¿Está vacía 'ListaProductos'?
    public boolean estaVacia() { return primero == null;  }

    // Operaciones

    // Insetar nuevo Nodo al inicio de 'ListaProductos'
    public void insertarNodoInicio(Producto producto) {
        Nodo nuevoProducto = new Nodo(producto);
        
        nuevoProducto.setSiguiente(primero);
        setPrimero(nuevoProducto);
        
        cantidadProductos ++;
    }

    // Insertar nuevo Nodo al final de 'ListaProductos'
    public void insertarNodoFinal(Producto producto) {
        Nodo nuevoProducto = new Nodo(producto);

        if (estaVacia()) {
        primero = nuevoProducto;
        } else {
            Nodo actual = primero;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoProducto);
        }
        cantidadProductos ++;
    }

    // Busca un Producto por nombre en 'Lista Productos'
    public Producto buscarProducto(String nombreXBuscar) {
        
        if (estaVacia()) {
            System.out.println("El producto buscado no se encuentra en la lista...\n");
            return null;
        }

        Nodo actual = primero;

        while (actual != null) {
            if (actual.getProducto().getNombre().equalsIgnoreCase(nombreXBuscar)) {
                return actual.getProducto();
            }
            actual = actual.getSiguiente(); 
        }

        
        System.out.println("El producto buscado NO se encuentra en la lista...\n");
        return null;
    }

    // Elimina un Producto por nombre de 'ListaProductos'
    public Producto eliminarProducto(String eliminarXNombre) {

        if (estaVacia()) {
            System.out.println("La lista de productos está vacía\n");
            return null;
        }

        Nodo actual = primero;
        Nodo anteriorActual = null;

        while (actual != null) {
            if (actual.getProducto().getNombre().equalsIgnoreCase(eliminarXNombre)) {
                if (anteriorActual == null) {
                    setPrimero(actual.getSiguiente());
                } else {
                    anteriorActual.setSiguiente(actual.getSiguiente());
                }
                cantidadProductos --;
                return actual.getProducto();
            }
            anteriorActual = actual;
            actual = actual.getSiguiente();  
        }
        
        System.out.println("El producto por eliminar NO se encuentra en la lista...\n");
        return null;
    }

    // Modifica un Producto existente en 'ListaProductos'
    public Producto modificarProducto(String modificarXNombre, String nuevoNombre, double nuevoPrecio, String nuevaCategoria, String nuevaFechaVencimiento, int nuevaCantidad) {

        if (estaVacia()) {
            System.out.println("La lista de productos está vacía\n");
            return null;
        }

        Nodo actual = primero;

        while (actual != null) {
            if (actual.getProducto().getNombre().equalsIgnoreCase(modificarXNombre)) {

                actual.getProducto().setNombre(nuevoNombre);
                actual.getProducto().setPrecio(nuevoPrecio);
                actual.getProducto().setCategoria(nuevaCategoria);
                actual.getProducto().setFechaVencimiento(nuevaFechaVencimiento);
                actual.getProducto().setCantidad(nuevaCantidad);

                return actual.getProducto(); 
            }

            actual = actual.getSiguiente();
        }

        System.out.println("El producto a modificar no se encontró.\n");
        return null;
    }

    // Imprime todos los Prodcutos existentes en 'ListaProductos'
    public void imprimirListaProductos() {
        if (estaVacia()) {
            System.out.println("La lista de productos está vacía\n");
            return;
        }

        Nodo actual = primero;

        while (actual != null) {
            System.out.println(actual.getProducto());
            actual = actual.getSiguiente();            
        }
    }

    // Genera un reporte de costos por Producto seleccionado y costos totales
    public void reportarCostos() {
        if (estaVacia()) {
            System.out.println("La lista de productos está vacía\n");
            return;
        }

        Nodo actual = primero;
        double costosTotales = 0;

        System.out.println("------    Reporte de Costos    ------");

        while (actual != null) {

            System.out.println("Producto: " + actual.getProducto().getNombre());
            System.out.println("Costo total del producto: ₡" + actual.getProducto().calcularCostoTotal());
            
            costosTotales += actual.getProducto().calcularCostoTotal();
            actual = actual.getSiguiente();
        }

        System.out.println("\nCostos totales del inventario: " + costosTotales);
    }

}
