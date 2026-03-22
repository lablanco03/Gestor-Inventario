public class NodoCliente {

    private Cliente cliente;
    private NodoCliente siguiente;
    private int ordenLlegada;

    public NodoCliente(Cliente cliente, int ordenLlegada) {
        this.cliente = cliente;
        this.ordenLlegada = ordenLlegada;
        this.siguiente = null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    public int getOrdenLlegada() {
        return ordenLlegada;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }
}