// Clase ArbolProductos: Árbol binario de búsqueda (ABB) para gestionar el inventario de la Tienda. La llave de comparación es el nombre del Producto (String), comparado alfabéticamente con compareToIgnoreCase().
public class ArbolProductos {

    // Atributos
    private NodoArbol raiz;

    // Constructor
    public ArbolProductos() {
        this.raiz = null;
    }

    // Getters
    public NodoArbol getRaiz() { return raiz; }

    // Setters
    public void setRaiz(NodoArbol raiz) { this.raiz = raiz; }

    // ¿Está vacío el árbol?
    public boolean estaVacio() { return raiz == null; }

    // BUSCAR: recorre el árbol comparando la llave (nombre) hasta encontrar el producto o llegar a null (no encontrado).
    public Producto buscarProducto(String nombre) {

        if (estaVacio()) {
            System.out.println("\nEl inventario está vacío.");
            return null;
        }

        NodoArbol temporal = raiz;

        while (temporal != null) {
            int comparacion = nombre.compareToIgnoreCase(temporal.getProducto().getNombre());

            if (comparacion == 0) {
                // Producto encontrado
                return temporal.getProducto();
            } else if (comparacion < 0) {
                // El nombre buscado es menor: ir a la izquierda
                temporal = temporal.getIzquierdo();
            } else {
                // El nombre buscado es mayor: ir a la derecha
                temporal = temporal.getDerecho();
            }
        }

        // Se sale del bucle al no encontrarlo
        System.out.println("\nProducto \"" + nombre + "\" no fue encontrado en el inventario.");
        return null;
    }

    // INSERTAR: Recorre el árbol igual que buscar pero, al llegar a null, ese espacio es exactamente donde debe ir el nodo nuevo. No se permiten productos con el mismo nombre (llave única).
    public void insertarProducto(Producto producto) {

        NodoArbol nuevoNodo = new NodoArbol(producto);

        // Árbol vacío: el nuevo nodo es la raíz
        if (estaVacio()) {
            raiz = nuevoNodo;
            System.out.println("\n¡Producto agregado al inventario correctamente!");
            System.out.println("\n-------------------------");
            return;
        }

        NodoArbol temporal = raiz;
        NodoArbol padreTemporal;

        while (true) {
            padreTemporal = temporal;
            int comparacion = producto.getNombre().compareToIgnoreCase(temporal.getProducto().getNombre());

            if (comparacion < 0) {
                // Ir a la izquierda
                temporal = temporal.getIzquierdo();
                if (temporal == null) {
                    padreTemporal.setIzquierdo(nuevoNodo);
                    System.out.println("\n¡Producto agregado al inventario correctamente!");
                    System.out.println("\n-------------------------");
                    return;
                }
            } else if (comparacion > 0) {
                // Ir a la derecha
                temporal = temporal.getDerecho();
                if (temporal == null) {
                    padreTemporal.setDerecho(nuevoNodo);
                    System.out.println("\n¡Producto agregado al inventario correctamente!");
                    System.out.println("\n-------------------------");
                    return;
                }
            } else {
                // Producto con el mismo nombre ya existe
                System.out.println("\nEl producto \"" + producto.getNombre() + "\" ya está en el inventario.");
                System.out.println("\n-------------------------");
                return;
            }
        }
    }

    // ELIMINAR: Busca el nodo por nombre y lo elimina considerando los tres casos: sin hijos, un hijo, o dos hijos (usa sucesor).
    public Producto eliminarProducto(String nombre) {

        if (estaVacio()) {
            System.out.println("\nEl inventario está vacío.");
            return null;
        }

        NodoArbol temporal = raiz;
        NodoArbol padreTemporal = raiz;
        boolean esHijoIzquierdo = false;

        // Buscar el nodo a eliminar y recordar su padre
        while (temporal.getProducto().getNombre().compareToIgnoreCase(nombre) != 0) {
            padreTemporal = temporal;
            if (nombre.compareToIgnoreCase(temporal.getProducto().getNombre()) < 0) {
                esHijoIzquierdo = true;
                temporal = temporal.getIzquierdo();
            } else {
                esHijoIzquierdo = false;
                temporal = temporal.getDerecho();
            }
            if (temporal == null) {
                System.out.println("\nProducto \"" + nombre + "\" no encontrado en el inventario.");
                return null;
            }
        }

        // Nodo encontrado: Guardar el producto para retornarlo
        Producto productoEliminado = temporal.getProducto();

        if (temporal.getIzquierdo() == null && temporal.getDerecho() == null) {
            if (temporal == raiz) {
                raiz = null;
            } else if (esHijoIzquierdo) {
                padreTemporal.setIzquierdo(null);
            } else {
                padreTemporal.setDerecho(null);
            }

        } else if (temporal.getIzquierdo() == null) {
            if (temporal == raiz) {
                raiz = temporal.getDerecho();
            } else if (esHijoIzquierdo) {
                padreTemporal.setIzquierdo(temporal.getDerecho());
            } else {
                padreTemporal.setDerecho(temporal.getDerecho());
            }

        } else if (temporal.getDerecho() == null) {
            if (temporal == raiz) {
                raiz = temporal.getIzquierdo();
            } else if (esHijoIzquierdo) {
                padreTemporal.setIzquierdo(temporal.getIzquierdo());
            } else {
                padreTemporal.setDerecho(temporal.getIzquierdo());
            }

        } else {
            NodoArbol sucesor = getSucesor(temporal);
            if (temporal == raiz) {
                raiz = sucesor;
            } else if (esHijoIzquierdo) {
                padreTemporal.setIzquierdo(sucesor);
            } else {
                padreTemporal.setDerecho(sucesor);
            }
            sucesor.setIzquierdo(temporal.getIzquierdo());
        }

        return productoEliminado;
    }

    // SUCESOR: Retorna el nodo con la llave más pequeña dentro de la descendencia derecha del nodo dado (el mínimo de la derecha).
    private NodoArbol getSucesor(NodoArbol nodo) {

        NodoArbol padreSucesor = nodo;
        NodoArbol sucesor = nodo;
        NodoArbol temporal = nodo.getDerecho();

        while (temporal != null) {
            padreSucesor = sucesor;
            sucesor = temporal;
            temporal = temporal.getIzquierdo();
        }

        if (sucesor != nodo.getDerecho()) {
            padreSucesor.setIzquierdo(sucesor.getDerecho());
            sucesor.setDerecho(nodo.getDerecho());
        }

        return sucesor;
    }

    // EN ORDEN: Visita los nodos en orden ascendente por nombre (izquierda → raíz → derecha), lo que produce una lista alfabética del inventario.
    public void enOrden(NodoArbol raizTemporal) {
        if (raizTemporal != null) {
            enOrden(raizTemporal.getIzquierdo());
            System.out.println(raizTemporal.getProducto());
            System.out.println();
            enOrden(raizTemporal.getDerecho());
        }
    }

    // MODIFICAR: busca el producto por nombre y permite cambiar sus atributos. Si se cambia el nombre (llave), elimina el nodo y lo reinserta con el nuevo nombre para preservar el orden del árbol. Retorna el producto modificado, o null si no se encontró.
    public Producto modificarProducto(String nombreActual, String nuevoNombre,
                                      double nuevoPrecio, String nuevaCategoria,
                                      String nuevaFecha, int nuevaCantidad) {

        Producto producto = buscarProducto(nombreActual);

        if (producto == null) {
            return null;
        }

        // Si el nombre cambia, hay que eliminar y reinsertar el nodo porque el nombre es la llave del árbol y su posición depende de él.
        if (!nuevoNombre.equalsIgnoreCase(nombreActual)) {

            // Verificar que el nuevo nombre no exista ya en el árbol
            if (buscarProducto(nuevoNombre) != null) {
                System.out.println("\nYa existe un producto con el nombre \"" + nuevoNombre + "\" en el inventario.");
                System.out.println("\n-------------------------");
                return null;
            }

            // Eliminar el nodo con el nombre viejo
            eliminarProducto(nombreActual);

            // Crear y reinsertar con todos los datos nuevos
            Producto productoModificado = new Producto(nuevoNombre, nuevoPrecio,
                                                       nuevaCategoria, nuevaFecha, nuevaCantidad);
            insertarProducto(productoModificado);
            return productoModificado;
        }

        // Si el nombre no cambia, modificar los atributos directamente
        producto.setPrecio(nuevoPrecio);
        producto.setCategoria(nuevaCategoria);
        producto.setFechaVencimiento(nuevaFecha);
        producto.setCantidad(nuevaCantidad);

        return producto;
    }

    // MOSTRAR INVENTARIO: Imprime el encabezado y llama a enOrden.
    public void mostrarInventario() {
        if (estaVacio()) {
            System.out.println("\nEl inventario está vacío.");
            System.out.println("\n-------------------------");
            return;
        }
        System.out.println("\n----------- INVENTARIO (orden alfabético) -----------\n");
        enOrden(raiz);
        System.out.println("-----------------------------------------------------");
    }
}