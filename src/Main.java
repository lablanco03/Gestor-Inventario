import java.util.Scanner;

// NOTA PARA INTEGRACIÓN:
// Este archivo contiene la parte del Main correspondiente a la gestión del inventario y sus métodos como opciones en el switch del menú principal.
public class Main {

    static Scanner sc = new Scanner(System.in);

    // La Tienda es el objeto central que une inventario (ArbolProductos) y cola de clientes (ColaClientes). 
    static Tienda tienda = new Tienda();

    // RUTINA PRINCIPAL
    public static void main(String[] args) {
        menu();
    }

    // MENÚ PRINCIPAL
    public static void menu() {
        int opcion;

        do {
            System.out.println("\n----------- SISTEMA DE GESTIÓN DE INVENTARIO -----------\n");
            System.out.println("--- Inventario ---");
            System.out.println("1. Agregar producto al inventario");
            System.out.println("2. Buscar producto en inventario");
            System.out.println("3. Eliminar producto del inventario");
            System.out.println("4. Modificar producto del inventario");
            System.out.println("5. Agregar imagen a producto del inventario");
            System.out.println("6. Mostrar inventario");
            System.out.println("--- Clientes ---");
            System.out.println("7. Agregar cliente a la cola");
            System.out.println("8. Atender siguiente cliente");
            System.out.println("-------------------------");
            System.out.println("9. Salir");
            System.out.println();

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarProductoInventario();
                    break;
                case 2:
                    buscarProductoInventario();
                    break;
                case 3:
                    eliminarProductoInventario();
                    break;
                case 4:
                    modificarProductoInventario();
                    break;
                case 5:
                    agregarImagenProducto();
                    break;
                case 6:
                    tienda.getInventario().mostrarInventario();
                    break;
                case 7:
                    agregarCliente();
                    break;
                case 8:
                    atenderCliente();
                    break;
                case 9:
                    System.out.println("\n-------------------------");
                    System.out.println("Saliendo del sistema...");
                    System.out.println("-------------------------\n");
                    break;
                default:
                    System.out.println("\n-------------------------");
                    System.out.println("Opción inválida. Intente nuevamente.");
                    System.out.println("-------------------------\n");
            }

        } while (opcion != 9);
    }

    // AGREGAR PRODUCTO AL INVENTARIO
    // Solicita los datos del producto y lo inserta en el ArbolProductos del inventario de la Tienda.
    public static void agregarProductoInventario() {
        System.out.println("\n----------- AGREGAR PRODUCTO AL INVENTARIO -----------\n");

        String nombre = leerTexto("Nombre del producto: ");

        // Validar que no exista ya en el inventario antes de pedir más datos
        if (tienda.getInventario().buscarProducto(nombre) != null) {
            System.out.println("\nEse producto ya existe en el inventario.");
            System.out.println("\n-------------------------");
            return;
        }

        double precio = leerDoublePositivo("Precio en colones: ");
        String categoria = leerTexto("Categoría: ");

        System.out.print("Fecha de vencimiento (DD/MM/AAAA) o 'Enter' si no aplica: ");
        String fecha = sc.nextLine().trim();
        if (fecha.isEmpty()) { fecha = null; }

        int cantidad = leerEnteroPositivo("Cantidad en inventario: ");

        Producto productoNuevo = new Producto(nombre, precio, categoria, fecha, cantidad);
        tienda.getInventario().insertarProducto(productoNuevo);
    }

    // BUSCAR PRODUCTO EN INVENTARIO
    // Solicita un nombre y muestra el producto si existe.
    public static void buscarProductoInventario() {
        System.out.println("\n----------- BUSCAR PRODUCTO EN INVENTARIO -----------\n");

        String nombre = leerTexto("Nombre del producto a buscar: ");
        Producto producto = tienda.getInventario().buscarProducto(nombre);

        if (producto != null) {
            System.out.println("\nProducto encontrado:\n");
            System.out.println(producto);
            System.out.println("\n-------------------------");
        }
    }

    // ELIMINAR PRODUCTO DEL INVENTARIO
    // Solicita un nombre y elimina el producto si existe.
    public static void eliminarProductoInventario() {
        System.out.println("\n----------- ELIMINAR PRODUCTO DEL INVENTARIO -----------\n");

        String nombre = leerTexto("Nombre del producto a eliminar: ");
        Producto eliminado = tienda.getInventario().eliminarProducto(nombre);

        if (eliminado != null) {
            System.out.println("\nProducto eliminado correctamente:");
            System.out.println(eliminado);
            System.out.println("\n-------------------------");
        }
    }

    // MODIFICAR PRODUCTO DEL INVENTARIO
    // Muestra el producto actual y permite elegir qué atributo modificar. Si el nombre cambia, el árbol elimina y reinserta el nodo automáticamente para preservar el orden.
    public static void modificarProductoInventario() {
        System.out.println("\n----------- MODIFICAR PRODUCTO DEL INVENTARIO -----------\n");

        String nombreActual = leerTexto("Nombre del producto a modificar: ");
        Producto producto = tienda.getInventario().buscarProducto(nombreActual);

        if (producto == null) {
            System.out.println("\n-------------------------");
            return;
        }

        System.out.println("\nProducto actual:\n");
        System.out.println(producto);
        System.out.println();

        // El usuario elige qué campo modificar
        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Precio");
        System.out.println("3. Categoría");
        System.out.println("4. Fecha de vencimiento");
        System.out.println("5. Cantidad");
        System.out.println("6. Todos los campos");
        System.out.println();

        int campo = leerEntero("Seleccione una opción: ");

        // Cargar valores actuales como base — solo se reemplaza lo que el usuario elija
        String nuevoNombre    = producto.getNombre();
        double nuevoPrecio    = producto.getPrecio();
        String nuevaCategoria = producto.getCategoria();
        String nuevaFecha     = producto.getFechaVencimiento();
        int nuevaCantidad     = producto.getCantidad();

        switch (campo) {
            case 1:
                nuevoNombre = leerTexto("Nuevo nombre: ");
                break;
            case 2:
                nuevoPrecio = leerDoublePositivo("Nuevo precio en colones: ");
                break;
            case 3:
                nuevaCategoria = leerTexto("Nueva categoría: ");
                break;
            case 4:
                System.out.print("Nueva fecha de vencimiento (DD/MM/AAAA) o 'Enter' si no aplica: ");
                String fechaInput = sc.nextLine().trim();
                nuevaFecha = fechaInput.isEmpty() ? null : fechaInput;
                break;
            case 5:
                nuevaCantidad = leerEnteroPositivo("Nueva cantidad: ");
                break;
            case 6:
                nuevoNombre    = leerTexto("Nuevo nombre: ");
                nuevoPrecio    = leerDoublePositivo("Nuevo precio en colones: ");
                nuevaCategoria = leerTexto("Nueva categoría: ");
                System.out.print("Nueva fecha de vencimiento (DD/MM/AAAA) o 'Enter' si no aplica: ");
                String fechaInputTodos = sc.nextLine().trim();
                nuevaFecha    = fechaInputTodos.isEmpty() ? null : fechaInputTodos;
                nuevaCantidad = leerEnteroPositivo("Nueva cantidad: ");
                break;
            default:
                System.out.println("\nOpción inválida. No se realizaron cambios.");
                System.out.println("\n-------------------------");
                return;
        }

        Producto modificado = tienda.getInventario().modificarProducto(
                nombreActual, nuevoNombre, nuevoPrecio, nuevaCategoria, nuevaFecha, nuevaCantidad);

        if (modificado != null) {
            System.out.println("\n¡Producto modificado correctamente!\n");
            System.out.println(modificado);
            System.out.println("\n-------------------------");
        }
    }

    // AGREGAR IMAGEN A PRODUCTO
    // Solicita el nombre del producto y la ruta de la imagen, y la agrega si el producto existe.
    public static void agregarImagenProducto() {
        System.out.println("\n----------- AGREGAR IMAGEN A PRODUCTO -----------\n");

        String nombre = leerTexto("Nombre del producto: ");
        Producto producto = tienda.getInventario().buscarProducto(nombre);

        if (producto == null) {
            System.out.println("\n-------------------------");
            return;
        }

        System.out.print("Ruta de la imagen (ej: imagenes/foto.jpg) o 'Enter' para cancelar: ");
        String ruta = sc.nextLine().trim();

        if (ruta.isEmpty()) {
            System.out.println("\nOperación cancelada.");
            System.out.println("\n-------------------------");
            return;
        }

        producto.agregarImagen(ruta);
        System.out.println("\n¡Imagen agregada correctamente!");
        System.out.println("\n-------------------------");
    }

    // AGREGAR CLIENTE
    // Crea un cliente, llena su carrito con productos del inventario y lo encola en la ColaClientes.
    public static void agregarCliente() {
        System.out.println("\n----------- AGREGAR CLIENTE -----------\n");

        String nombre = leerTexto("Nombre del cliente: ");

        int prioridad;
        do {
            prioridad = leerEntero("Prioridad (1 = básico, 2 = afiliado, 3 = premium): ");
            if (prioridad < 1 || prioridad > 3) {
                System.out.println("\nPrioridad inválida. Ingrese 1, 2 o 3.\n");
            }
        } while (prioridad < 1 || prioridad > 3);

        Cliente cliente = new Cliente(nombre, prioridad);

        // Llenar el carrito con productos del inventario
        System.out.println("\nAhora puede agregar productos al carrito. Ingrese 'fin' para terminar.\n");

        while (true) {
            System.out.print("Nombre del producto a agregar (o 'fin' para terminar): ");
            String nombreProducto = sc.nextLine().trim();

            if (nombreProducto.equalsIgnoreCase("fin")) { break; }

            Producto producto = tienda.getInventario().buscarProducto(nombreProducto);

            if (producto == null) {
                System.out.println("\nProducto no encontrado en el inventario. Intente nuevamente.\n");
                continue;
            }

            int cantidadDeseada = leerEnteroPositivo("Cantidad a agregar: ");

            if (producto.getCantidad() == 0) {
                System.out.println("\nNo hay unidades disponibles de ese producto en el inventario.\n");
                continue;
            }

            if (cantidadDeseada > producto.getCantidad()) {
                System.out.println("\nStock insuficiente. Disponible: " + producto.getCantidad() + "\n");
                continue;
            }

            Producto itemCarrito = new Producto(producto.getNombre(), producto.getPrecio(),
                                                producto.getCategoria(), producto.getFechaVencimiento(),
                                                cantidadDeseada);
            cliente.getCarrito().agregarProductoFinal(itemCarrito);
            producto.setCantidad(producto.getCantidad() - cantidadDeseada);
            System.out.println("\nProducto agregado al carrito. Stock restante: " + producto.getCantidad());
            System.out.println("\n-------------------------");
        }

        tienda.getCola().encolar(cliente);
        System.out.println("\n¡Cliente " + cliente.getNombre() + " agregado a la cola correctamente!");
        System.out.println("\n-------------------------");
    }

    // ATENDER CLIENTE
    // Saca al cliente con mayor prioridad de la cola e imprime su factura.
    public static void atenderCliente() {
        System.out.println("\n----------- ATENDER CLIENTE -----------\n");

        Cliente cliente = tienda.getCola().desencolar();

        if (cliente == null) {
            System.out.println("No hay clientes en la cola.");
            System.out.println("\n-------------------------");
            return;
        }

        System.out.println("Atendiendo a: " + cliente.getNombre());
        System.out.println("Prioridad: " + cliente.getPrioridad());
        System.out.println();

        // Imprimir factura usando el reporte de costos del carrito
        cliente.getCarrito().reportarCostos();
        System.out.println("\n-------------------------");
    }

    // VALIDACIONES DE ENTRADA
    // Estos métodos se encargan de comprobar que lo que ingrese el usuario sea un dato del tipo correcto para cada entrada

    // Que lo ingresado por usuario sea un String cuando corresponda
    public static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("\nEntrada incorrecta. Intente nuevamente.\n");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Que lo ingresado por usuario sea un número Entero cuando corresponda
    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("\nEntrada incorrecta. Debe ingresar un número entero.\n");
            }
        }
    }

    // Que lo ingresado por usuario sea un número Entero Positivo cuando corresponda
    public static int leerEnteroPositivo(String mensaje) {
        int numero;
        do {
            numero = leerEntero(mensaje);
            if (numero < 0) {
                System.out.println("\nNo se permiten valores negativos.\n");
            }
        } while (numero < 0);
        return numero;
    }

    // Que lo ingresado por usuario sea un número Double Positivo cuando corresponda
    public static double leerDoublePositivo(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double numero = Double.parseDouble(sc.nextLine().trim());
                if (numero < 0) {
                    System.out.println("\nNo se permiten valores negativos.\n");
                    continue;
                }
                return numero;
            } catch (Exception e) {
                System.out.println("\nEntrada incorrecta. Debe ingresar un número.\n");
            }
        }
    }
}
