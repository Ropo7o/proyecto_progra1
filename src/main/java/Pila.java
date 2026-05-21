public class Pila {
    private final String[] datos;
    private int tope;

    public Pila(int capacidad) {
        this.datos = new String[capacidad];
        this.tope  = -1;
    }

    public void push(String item) {
        if (tope < datos.length - 1) {
            datos[++tope] = item;
        }
    }

    public String pop() {
        return estaVacia() ? null : datos[tope--];
    }

    public boolean estaVacia() { return tope == -1; }

    public int size() { return tope + 1; }

    public void mostrar() {
        if (estaVacia()) {
            System.out.println("El historial esta vacio.");
            return;
        }
        System.out.println("Historial (mas reciente primero):");
        for (int i = tope; i >= 0; i--) {
            System.out.println("  " + (tope - i + 1) + ". " + datos[i]);
        }
    }
}
