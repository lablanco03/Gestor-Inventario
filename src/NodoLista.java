public class NodoLista {

    // Atributos
    private Producto producto;
    private NodoLista siguiente;

    // Constructor
    public NodoLista(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // Getters
    public Producto getProducto() { return producto; }

    public NodoLista getSiguiente() { return siguiente; }

    // Setters
    public void setProducto(Producto producto) { this.producto = producto; }

    public void setSiguiente(NodoLista siguiente) { this.siguiente = siguiente; }

}