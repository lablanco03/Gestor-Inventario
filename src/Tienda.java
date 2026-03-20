public class Tienda {

    private ArbolProductos inventario;
    private ColaClientes cola;

    public Tienda() {
        inventario = new ArbolProductos();
        cola = new ColaClientes();
    }

    public ArbolProductos getInventario() {
        return inventario;
    }

    public ColaClientes getCola() {
        return cola;
    }
}