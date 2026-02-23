import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ListaProductos lista = new ListaProductos();

    public static void main(String[] args) {
        menu();
    }

    // MENU PRINCIPAL =====
    public static void menu() {
        int opcion;

        do {
            System.out.println("\n----------- SISTEMA DE INVENTARIO SUPERMERCADO -----------\n");
            System.out.println("1. Agregar producto al inicio");
            System.out.println("2. Agregar producto al final");
            System.out.println("3. Buscar producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Mostrar productos");
            System.out.println("7. Reporte de costos");
            System.out.println("8. Agregar imagen a producto");
            System.out.println("9. Salir");
            System.out.println();

            opcion = leerEntero("Seleccione una opción: ");

            switch(opcion) {
                case 1:
                    agregarProducto(true);
                    break;
                case 2:
                    agregarProducto(false);
                    break;
                case 3:
                    buscarProducto();
                    break;
                case 4:
                    modificarProducto();
                    break;
                case 5:
                    eliminarProducto();
                    break;
                case 6:
                    System.out.println("\n----------- LISTA DE PRODUCTOS -----------\n");
                    lista.imprimirListaProductos();
                    System.out.println("-------------------------");
                    break;
                case 7:
                    lista.reportarCostos();
                    break;
                case 8:
                    agregarImagenAProducto();
                    break;
                case 9:
                    System.out.println("\n-------------------------\n");
                    System.out.println("Saliendo del sistema...");
                    System.out.println("\n-------------------------\n");
                    break;
                default:
                    System.out.println("\n-------------------------\n");
                    System.out.println("Opción inválida. Intente nuevamente.");
                    System.out.println("\n-------------------------\n");
            }
        } while(opcion != 9);
    }

    // AGREGAR PRODUCTO
    public static void agregarProducto(boolean inicio) {
        System.out.println("\n----------- AGREGAR PRODUCTO -----------\n");

        String nombre = leerTexto("Nombre: ");
        double precio = leerDoublePositivo("Precio en colones: ");
        String categoria = leerTexto("Categoría: ");

        System.out.print("Fecha de vencimiento (DD/MM/AAAA) o 'Enter' si no aplica: ");
        String fecha = sc.nextLine();
        if (fecha.isEmpty()) { fecha = null; }

        int cantidad = leerEnteroPositivo("Cantidad: ");

        Producto nuevo = new Producto(nombre, precio, categoria, fecha, cantidad);

        if(inicio) {
            lista.agregarProductoInicio(nuevo);
        } else {
            lista.agregarProductoFinal(nuevo);
        }
    }

    // BUSCAR PRODUCTO
    public static void buscarProducto() {
        System.out.println("\n----------- BUSCAR PRODUCTO -----------\n");

        String nombre = leerTexto("Ingrese nombre del producto: ");

        Producto p = lista.buscarProducto(nombre);

        if(p != null) {
            System.out.println("\nProducto encontrado:\n");
            System.out.println(p);
            System.out.println("\n-------------------------");
        } else {
            System.out.println("\nProducto no encontrado.");
            System.out.println("\n-------------------------");
        }
    }

    // ELIMINAR PRODUCTO
    public static void eliminarProducto() {
        System.out.println("\n----------- ELIMINAR PRODUCTO -----------\n");

        String nombre = leerTexto("Ingrese nombre del producto a eliminar: ");

        Producto eliminado = lista.eliminarProducto(nombre);

        if(eliminado != null) {
            System.out.println("\nProducto eliminado correctamente.");
            System.out.println("\n-------------------------");
        } else {
            System.out.println("\nProducto no existe.");
            System.out.println("\n-------------------------");
        }
    }

    // MODIFICAR PRODUCTO
    public static void modificarProducto() {
        System.out.println("\n----------- MODIFICAR PRODUCTO -----------\n");

        String nombre = leerTexto("Ingrese nombre del producto a modificar: ");

        if(lista.buscarProducto(nombre) == null) {
            System.out.println("\nEl producto no existe.");
            System.out.println("\n-------------------------");
            return;
        }

        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double precio = leerDoublePositivo("Nuevo precio: ");
        String categoria = leerTexto("Nueva categoría: ");

        System.out.print("Nueva fecha de vencimiento (DD/MM/AAAA) o 'Enter' si no aplica: ");
        String fecha = sc.nextLine();
        if (fecha.isEmpty()) { fecha = null; }

        int cantidad = leerEnteroPositivo("Nueva cantidad: ");

        lista.modificarProducto(
                nombre,
                nuevoNombre,
                precio,
                categoria,
                fecha,
                cantidad
        );

        System.out.println("\nProducto modificado correctamente.");
        System.out.println("\n-------------------------");
    }

    // AGREGAR IMAGEN A PRODUCTO
    public static void agregarImagenAProducto() {
        System.out.println("\n----------- AGREGAR IMAGEN A PRODUCTO -----------\n");

        String nombre = leerTexto("Ingrese el nombre del producto: ");
        Producto producto = lista.buscarProducto(nombre);

        if (producto == null) {
            System.out.println("\nProducto no encontrado.");
            System.out.println("\n-------------------------");
            return;
        }

        System.out.print("Ingrese la ruta de la imagen (ej: imagenes/foto.jpg): ");
        String ruta = sc.nextLine().trim();

        if (ruta.isEmpty()) {
            System.out.println("\nRuta no válida. No se agregó la imagen.");
            System.out.println("\n-------------------------");
            return;
        }

        lista.agregarImagenProducto(producto, ruta);
        System.out.println("\nImagen agregada correctamente (si la ruta era válida).");
        System.out.println("\n-------------------------");
    }

    // VALIDACIONES DE ENTRADA
    public static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if(texto.isEmpty()) {
                System.out.println();
                System.out.println("Entrada incorrecta. Intente nuevamente.");
                System.out.println();
            }
        } while(texto.isEmpty());
        return texto;
    }

    public static int leerEntero(String mensaje) {
        while(true) {
            try {
                System.out.print(mensaje);
                int numero = Integer.parseInt(sc.nextLine());
                return numero;
            } catch(Exception e) {
                System.out.println();
                System.out.println("Entrada incorrecta. Debe ingresar un número.");
                System.out.println();
            }
        }
    }

    public static int leerEnteroPositivo(String mensaje) {
        int numero;
        do {
            numero = leerEntero(mensaje);
            if(numero < 0) {
                System.out.println();
                System.out.println("No se permiten valores negativos.");
                System.out.println();
            }
        } while(numero < 0);
        return numero;
    }

    public static double leerDoublePositivo(String mensaje) {
        while(true) {
            try {
                System.out.print(mensaje);
                double numero = Double.parseDouble(sc.nextLine());
                if(numero < 0) {
                    System.out.println();
                    System.out.println("No se permiten valores negativos.");
                    System.out.println();
                    continue;
                }
                return numero;
            } catch(Exception e) {
                System.out.println();
                System.out.println("Entrada incorrecta. Debe ingresar un número.");
                System.out.println();
            }
        }
    }
}