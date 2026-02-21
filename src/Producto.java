import java.util.ArrayList;

public class Producto {

    // Atributos
    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento;
    private int cantidad;
    private ArrayList<String> listaImagenes;

    // Constructor
    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad) {

        this.nombre = nombre;
        if (precio < 0) { throw new IllegalArgumentException("El precio no puede ser negativo..."); }
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        if (cantidad < 0) { throw new IllegalArgumentException("La cantidad no puede ser negativa..."); }
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<>();
    }

    // Getters
    public String getNombre() { return nombre; }

    public double getPrecio() { return precio; }

    public String getCategoria() { return categoria; }

    public String getFechaVencimiento() { return fechaVencimiento; }

    public int getCantidad() { return cantidad; }

    public ArrayList<String> getListaImagenes() { return listaImagenes; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setPrecio(double precio) { 
        if (precio < 0) { throw new IllegalArgumentException("El precio no puede ser negativo"); }
        this.precio = precio; 
    }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public void setCantidad(int cantidad) { 
        if (cantidad < 0) { throw new IllegalArgumentException("La cantidad no puede ser negativa"); }
        this.cantidad = cantidad; 
    }

    // Agregar imagen al Producto
    public void agregarImagen(String ruta) {
        if (ruta != null && !ruta.isEmpty()) {
            listaImagenes.add(ruta);
        }
    }

    // Calcular el costo actual del Producto
    public double calcularCostoTotal() { return precio * cantidad; }

    // toString
    @Override
    public String toString() {
        return "Nombre: " + nombre +
                "\nCategoría: " + categoria +
                "\nPrecio: " + String.format("%.2f", precio) + " colones" +
                "\nCantidad: " + cantidad +
                "\nFecha de Vencimiento: " + (fechaVencimiento != null ? fechaVencimiento : "No aplica") +
                "\nCantidad de Imágenes: " + listaImagenes.size() +
                "\n-----------------------------------";
    }
}