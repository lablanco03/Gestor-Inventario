import java.util.Scanner;

// NOTA PARA INTEGRACIÓN:
// Este archivo contiene la parte del Main correspondiente a Persona 1 (gestión del inventario). Persona 2 debe agregar sus métodos aquí e integrar sus opciones en el switch del menú principal.
public class Main {

    static Scanner sc = new Scanner(System.in);

    // La Tienda es el objeto central que une inventario (ArbolProductos) y cola de clientes (ColaClientes). Persona 2 implementará la clase Tienda.
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
            System.out.println("5. Mostrar inventario");
            System.out.println("--- Clientes ---");
            System.out.println("6. Agregar cliente a la cola");     // Persona 2
            System.out.println("7. Atender siguiente cliente");     // Persona 2
            System.out.println("-------------------------");
            System.out.println("8. Salir");
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
                    tienda.getInventario().mostrarInventario();
                    break;
                case 6:
                    agregarCliente(); // Persona 2 implementa este método
                    break;
                case 7:
                    atenderCliente(); // Persona 2 implementa este método
                    break;
                case 8:
                    System.out.println("\n-------------------------");
                    System.out.println("Saliendo del sistema...");
                    System.out.println("-------------------------\n");
                    break;
                default:
                    System.out.println("\n-------------------------");
                    System.out.println("Opción inválida. Intente nuevamente.");
                    System.out.println("-------------------------\n");
            }

        } while (opcion != 8);
    }

    // AGREGAR PRODUCTO AL INVENTARIO (Persona 1)
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
        tienda.getInventario().ins ertarProducto(productoNuevo);
    }

    // BUSCAR PRODUCTO EN INVENTARIO (Persona 1)
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

    // ELIMINAR PRODUCTO DEL INVENTARIO (Persona 1)
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

    // MODIFICAR PRODUCTO DEL INVENTARIO (Persona 1)
    // Muestra el producto actual y permite elegir qué atributo modificar.Si el nombre cambia, el árbol elimina y reinserta el nodo automáticamente para preservar el orden.
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

    // AGREGAR CLIENTE (Persona 2 implementa el cuerpo)
    // Declarado aquí para que el switch compile sin errores.
    public static void agregarCliente() {
        // Persona 2 implementa todo este método acá
        System.out.println("\n[Función pendiente - Persona 2]");
    }

    // ATENDER CLIENTE (Persona 2 implementa el cuerpo)
    public static void atenderCliente() {
        // Persona 2 implementa todo este método acá
        System.out.println("\n[Función pendiente - Persona 2]");
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