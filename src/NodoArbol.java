// NodoArbol: Nodo del árbol binario de búsqueda de productos. La llave del nodo es el nombre del Producto (String), lo cual permite ordenar y buscar productos alfabéticamente dentro del árbol.
public class NodoArbol {

    // Atributos
    private Producto producto;       
    private NodoArbol izquierdo;     
    private NodoArbol derecho;       

    // Constructor
    public NodoArbol(Producto producto) {
        this.producto = producto;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters
    public Producto getProducto() { return producto; }

    public NodoArbol getIzquierdo() { return izquierdo; }

    public NodoArbol getDerecho() { return derecho; }

    // Setters
    public void setProducto(Producto producto) { this.producto = producto; }

    public void setIzquierdo(NodoArbol izquierdo) { this.izquierdo = izquierdo; }

    public void setDerecho(NodoArbol derecho) { this.derecho = derecho; }

    // toString
    @Override
    public String toString() {
        return producto.toString();
    }
}
