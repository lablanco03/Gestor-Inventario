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

    // Insertar nuevo Producto al inicio de 'ListaProductos'
    public void agregarProductoInicio(Producto producto) {
        
        if (buscarProducto(producto.getNombre()) == null) {
            Nodo nuevoProducto = new Nodo(producto);
        
            nuevoProducto.setSiguiente(primero);
            setPrimero(nuevoProducto);
        
            cantidadProductos ++;
            System.out.println("\n¡Producto agregado correctamente!");
            System.out.println("\n-------------------------");
        } else {
            System.out.println("\nProducto ya se encuentra en la lista...");
            System.out.println("\n-------------------------");
        }
    }

    // Insertar nuevo Producto al final de 'ListaProductos'
    public void agregarProductoFinal(Producto producto) {

        if (buscarProducto(producto.getNombre()) == null) {
        Nodo nuevoProducto = new Nodo(producto);

        if (estaVacia()) {
            setPrimero(nuevoProducto);
        } else {
            Nodo actual = primero;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoProducto);
        }
        System.out.println("\n¡Producto agregado correctamente!");
        System.out.println("\n-------------------------");
        cantidadProductos ++;
        } else {
            System.out.println("\nProducto ya se encuentra en la lista...");
            System.out.println("\n-------------------------");
        }
    }

    // Busca un Producto por nombre en 'Lista Productos'
    public Producto buscarProducto(String nombreXBuscar) {

        Nodo actual = primero;

        while (actual != null) {
            if (actual.getProducto().getNombre().equalsIgnoreCase(nombreXBuscar)) {
                return actual.getProducto();
            }
            actual = actual.getSiguiente(); 
        }
        return null;
    }

    // Elimina un Producto por nombre de 'ListaProductos'
    public Producto eliminarProducto(String eliminarXNombre) {

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
        
        return null;
    }

    // Modifica un Producto existente en 'ListaProductos'
    public Producto modificarProducto(String modificarXNombre, String nuevoNombre, double nuevoPrecio, String nuevaCategoria, String nuevaFechaVencimiento, int nuevaCantidad) {

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
        return null;
    }

    //Agrega una ruta de imagen a un Producto existente en la lista
    public void agregarImagenProducto(Producto producto, String rutaImagen) {
        if (buscarProducto(producto.getNombre()) != null) {
            producto.agregarImagen(rutaImagen);
        } else {
            System.out.println("Producto NO se encuentra en la lista...");
        }
    }

    // Imprime todos los Prodcutos existentes en 'ListaProductos'
    public void imprimirListaProductos() {
        if (estaVacia()) {
            System.out.println("La lista de productos está vacía...");
            System.out.println();
            return;
        }

        Nodo actual = primero;

        while (actual != null) {
            System.out.println(actual.getProducto());
            actual = actual.getSiguiente();
            System.out.println();          
        }
    }

    // Genera un reporte de costos por Producto y costos totales
    public void reportarCostos() {
        if (estaVacia()) {
            System.out.println("\n----------- REPORTE DE COSTOS -----------\n");
            System.out.println("La lista de productos está vacía...");
            System.out.println("\n-------------------------");
            return;
        }

        Nodo actual = primero;
        double costosTotales = 0;

        System.out.println("\n------------------------    REPORTE DE COSTOS    ------------------------\n");
        System.out.printf("%-25s %10s %15s %20s%n", "Producto", "Cantidad", "Precio Unit.", "Costo Total");
        System.out.println("\n-------------------------------------------------------------------------\n");

        while (actual != null) {
            Producto producto = actual.getProducto();
            double costoProducto = producto.calcularCostoTotal();
            // Fila de datos
            System.out.printf("%-25s %10d %15.2f %20.2f%n",
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio(),
                    costoProducto);
            costosTotales += costoProducto;
            actual = actual.getSiguiente();
        }

        System.out.println("\n-------------------------------------------------------------------------\n");
        System.out.printf("TOTAL DEL INVENTARIO: %51.2f%n", costosTotales);
        System.out.println("\n-------------------------------------------------------------------------");
    }
}