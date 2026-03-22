public class ColaClientes {

    private NodoCliente frente;
    private int contadorLlegada;

    public ColaClientes() {
        frente = null;
        contadorLlegada = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    // Insertar respetando prioridad y orden
    public void encolar(Cliente cliente) {

        NodoCliente nuevo = new NodoCliente(cliente, contadorLlegada++);

        if (estaVacia()) {
            frente = nuevo;
            return;
        }

        NodoCliente actual = frente;
        NodoCliente anterior = null;

        while (actual != null) {

            // PRIORIDAD mayor primero
            if (cliente.getPrioridad() > actual.getCliente().getPrioridad()) {
                break;
            }

            // MISMA prioridad → FIFO
            if (cliente.getPrioridad() == actual.getCliente().getPrioridad()) {
                if (nuevo.getOrdenLlegada() < actual.getOrdenLlegada()) {
                    break;
                }
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (anterior == null) {
            nuevo.setSiguiente(frente);
            frente = nuevo;
        } else {
            anterior.setSiguiente(nuevo);
            nuevo.setSiguiente(actual);
        }
    }

    public Cliente desencolar() {
        if (estaVacia()) return null;

        Cliente cliente = frente.getCliente();
        frente = frente.getSiguiente();
        return cliente;
    }
}