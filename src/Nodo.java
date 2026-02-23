public class Nodo {
    
    // Atributos
    private Producto producto;
    private Nodo siguiente;

    // Constructor
    public Nodo(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // Getters
    public Producto getProducto() { return producto; }

    public Nodo getSiguiente() { return siguiente; }
    
    // Setters
    public void setProducto(Producto producto) { this.producto = producto; }

    public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }

}
