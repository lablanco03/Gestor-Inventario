import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ListaProductos lista = new ListaProductos();

    public static void main(String[] args) {
        menu();
    }

   
    // ===== MENU PRINCIPAL =====
    
    public static void menu() {

        int opcion;

        do {
            System.out.println("\n\n=========== SISTEMA DE INVENTARIO SUPERMERCADO ===========");
            System.out.println("1. Agregar producto al inicio");
            System.out.println("2. Agregar producto al final");
            System.out.println("3. Buscar producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Mostrar productos");
            System.out.println("7. Reporte de costos");
            System.out.println("8. Salir");

            opcion = leerEntero("Seleccione una opción: ");

            switch(opcion){

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
                    System.out.println();
                    lista.imprimirListaProductos();
                    System.out.println();
                    break;

                case 7:
                    System.out.println();
                    lista.reportarCostos();
                    System.out.println();
                    break;

                case 8:
                    System.out.println("\nSaliendo del sistema...\n");
                    break;

                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
            }

        } while(opcion != 8);
    }

    
    // AGREGAR PRODUCTO
    
    public static void agregarProducto(boolean inicio){

        System.out.println("\n----------- AGREGAR PRODUCTO -----------");

        String nombre = leerTexto("Nombre: ");
        double precio = leerDoublePositivo("Precio: ");
        String categoria = leerTexto("Categoría: ");

        System.out.print("Fecha de vencimiento (o NA): ");
        String fecha = sc.nextLine();
        if(fecha.equalsIgnoreCase("NA")){
            fecha = null;
        }

        int cantidad = leerEnteroPositivo("Cantidad: ");

        Producto nuevo = new Producto(nombre, precio, categoria, fecha, cantidad);

        if(inicio){
            lista.agregarProductoInicio(nuevo);
        }else{
            lista.agregarProductoFinal(nuevo);
        }

        System.out.println("\nProducto agregado correctamente.\n");
    }

    
    // BUSCAR PRODUCTO
    
    public static void buscarProducto(){

        System.out.println("\n----------- BUSCAR PRODUCTO -----------");

        String nombre = leerTexto("Ingrese nombre del producto: ");

        Producto p = lista.buscarProducto(nombre);

        if(p != null){
            System.out.println("\nProducto encontrado:\n");
            System.out.println(p);
        }else{
            System.out.println("\nProducto no encontrado.\n");
        }
    }

    // ELIMINAR PRODUCTO
    
    public static void eliminarProducto(){

        System.out.println("\n----------- ELIMINAR PRODUCTO -----------");

        String nombre = leerTexto("Ingrese nombre del producto a eliminar: ");

        Producto eliminado = lista.eliminarProducto(nombre);

        if(eliminado != null){
            System.out.println("\nProducto eliminado correctamente.\n");
        }else{
            System.out.println("\nProducto no existe.\n");
        }
    }

   
    // MODIFICAR PRODUCTO
    
    public static void modificarProducto(){

        System.out.println("\n----------- MODIFICAR PRODUCTO -----------");

        String nombre = leerTexto("Ingrese nombre del producto a modificar: ");

        if(lista.buscarProducto(nombre) == null){
            System.out.println("\nEl producto no existe.\n");
            return;
        }

        String nuevoNombre = leerTexto("Nuevo nombre: ");
        double precio = leerDoublePositivo("Nuevo precio: ");
        String categoria = leerTexto("Nueva categoría: ");

        System.out.print("Nueva fecha vencimiento (o NA): ");
        String fecha = sc.nextLine();
        if(fecha.equalsIgnoreCase("NA")){
            fecha = null;
        }

        int cantidad = leerEnteroPositivo("Nueva cantidad: ");

        lista.modificarProducto(
                nombre,
                nuevoNombre,
                precio,
                categoria,
                fecha,
                cantidad
        );

        System.out.println("\nProducto modificado correctamente.\n");
    }

   
    // VALIDACIONES DE ENTRADA
    

    public static String leerTexto(String mensaje){
        String texto;

        do{
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            if(texto.isEmpty()){
                System.out.println("Entrada incorrecta. Intente nuevamente.");
            }

        } while(texto.isEmpty());

        return texto;
    }

    public static int leerEntero(String mensaje){
        while(true){
            try{
                System.out.print(mensaje);
                int numero = Integer.parseInt(sc.nextLine());
                return numero;
            }catch(Exception e){
                System.out.println("Entrada incorrecta. Debe ingresar un número.");
            }
        }
    }

    public static int leerEnteroPositivo(String mensaje){
        int numero;
        do{
            numero = leerEntero(mensaje);
            if(numero < 0){
                System.out.println("No se permiten valores negativos.");
            }
        }while(numero < 0);

        return numero;
    }

    public static double leerDoublePositivo(String mensaje){
        while(true){
            try{
                System.out.print(mensaje);
                double numero = Double.parseDouble(sc.nextLine());

                if(numero < 0){
                    System.out.println("No se permiten valores negativos.");
                    continue;
                }

                return numero;

            }catch(Exception e){
                System.out.println("Entrada incorrecta. Debe ingresar un número.");
            }
        }
    }
}