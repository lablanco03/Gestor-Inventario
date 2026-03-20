import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Tienda tienda = new Tienda();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int opcion;

        do {
            System.out.println("\n----------- SISTEMA DE GESTIÓN DE INVENTARIO -----------\n");
            System.out.println("--- Inventario ---");
            System.out.println("1. Agregar producto al inventario");
            System.out.println("2. Buscar producto en inventario");
            System.out.println("3. Eliminar producto del inventario");
            System.out.println("4. Modificar producto del inventario");
            System.out.println("5. Mostrar inventario");
            System.out.println("--- Clientes ---");
            System.out.println("6. Agregar cliente a la cola");
            System.out.println("7. Atender siguiente cliente");
            System.out.println("-------------------------");
            System.out.println("8. Salir\n");

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
                    tienda.getInventario().mostrarInventario();
                    break;
                case 6:
                    agregarCliente();
                    break;
                case 7:
                    atenderCliente();
                    break;
                case 8:
                    System.out.println("\n-------------------------");
                    System.out.println("Saliendo del sistema...");
                    System.out.println("-------------------------\n");
                    break;
                default:
                    System.out.println("\n-------------------------");
                    System.out.println("Opción inválida.");
                    System.out.println("-------------------------\n");
            }

        } while (opcion != 8);
    }

    // ================= INVENTARIO =================

    public static void agregarProductoInventario() {
        System.out.println("\n----------- AGREGAR PRODUCTO -----------\n");

        String nombre = leerTexto("Nombre del producto: ");

        if (tienda.getInventario().buscarProducto(nombre) != null) {
            System.out.println("\nEse producto ya existe.");
            return;
        }

        double precio = leerDoublePositivo("Precio: ");
        String categoria = leerTexto("Categoría: ");

        System.out.print("Fecha (Enter si no aplica): ");
        String fecha = sc.nextLine().trim();
        if (fecha.isEmpty()) fecha = null;

        int cantidad = leerEnteroPositivo("Cantidad: ");

        Producto p = new Producto(nombre, precio, categoria, fecha, cantidad);
        tienda.getInventario().insertarProducto(p); // ✅ CORREGIDO
    }

    public static void buscarProductoInventario() {
        String nombre = leerTexto("Producto a buscar: ");
        Producto p = tienda.getInventario().buscarProducto(nombre);

        if (p != null) {
            System.out.println(p);
        }
    }

    public static void eliminarProductoInventario() {
        String nombre = leerTexto("Producto a eliminar: ");
        Producto p = tienda.getInventario().eliminarProducto(nombre);

        if (p != null) {
            System.out.println("Eliminado:\n" + p);
        }
    }

    public static void modificarProductoInventario() {
        String nombre = leerTexto("Producto a modificar: ");
        Producto p = tienda.getInventario().buscarProducto(nombre);

        if (p == null) return;

        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double nuevoPrecio = leerDoublePositivo("Nuevo precio: ");
        String nuevaCategoria = leerTexto("Nueva categoría: ");

        System.out.print("Nueva fecha (Enter si no aplica): ");
        String nuevaFecha = sc.nextLine().trim();
        if (nuevaFecha.isEmpty()) nuevaFecha = null;

        int nuevaCantidad = leerEnteroPositivo("Nueva cantidad: ");

        tienda.getInventario().modificarProducto(
                nombre, nuevoNombre, nuevoPrecio, nuevaCategoria, nuevaFecha, nuevaCantidad
        );
    }

    // ================= PERSONA 2 =================

    public static void agregarCliente() {

        System.out.println("\n----------- AGREGAR CLIENTE -----------\n");

        String nombre = leerTexto("Nombre del cliente: ");

        int prioridad;
        do {
            prioridad = leerEntero("Prioridad (1-3): ");
        } while (prioridad < 1 || prioridad > 3);

        Cliente cliente = new Cliente(nombre, prioridad);

        String opcion;

        do {
            String nombreProducto = leerTexto("Producto a agregar: ");

            Producto producto = tienda.getInventario().buscarProducto(nombreProducto);

            if (producto != null) {
                cliente.getCarrito().agregarProductoFinal(producto);
            }

            opcion = leerTexto("¿Agregar otro? (s/n): ");

        } while (opcion.equalsIgnoreCase("s"));

        tienda.getCola().encolar(cliente);

        System.out.println("\nCliente agregado correctamente.");
    }

    public static void atenderCliente() {

        System.out.println("\n----------- ATENDER CLIENTE -----------\n");

        if (tienda.getCola().estaVacia()) {
            System.out.println("No hay clientes.");
            return;
        }

        Cliente cliente = tienda.getCola().desencolar();

        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Prioridad: " + cliente.getPrioridad());

        if (cliente.getCarrito().estaVacia()) {
            System.out.println("Carrito vacío.");
        } else {
            cliente.getCarrito().imprimirListaProductos();

            double total = 0;
            NodoLista actual = cliente.getCarrito().getPrimero();

            while (actual != null) {
                total += actual.getProducto().calcularCostoTotal(); // 🔥 mejor versión
                actual = actual.getSiguiente();
            }

            System.out.println("\nTOTAL: ₡" + total);
        }
    }

    // ================= VALIDACIONES =================

    public static String leerTexto(String msg) {
        String texto;
        do {
            System.out.print(msg);
            texto = sc.nextLine().trim();
        } while (texto.isEmpty());
        return texto;
    }

    public static int leerEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static int leerEnteroPositivo(String msg) {
        int n;
        do {
            n = leerEntero(msg);
        } while (n < 0);
        return n;
    }

    public static double leerDoublePositivo(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                double n = Double.parseDouble(sc.nextLine());
                if (n >= 0) return n;
            } catch (Exception e) {}
            System.out.println("Número inválido.");
        }
    }
}