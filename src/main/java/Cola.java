public class Cola {
    private final String[] datos;
    private int frente;
    private int fin;
    private int tamanio;

    public Cola(int capacidad) {
        this.datos   = new String[capacidad];
        this.frente  = 0;
        this.fin     = 0;
        this.tamanio = 0;
    }

    public void encolar(String item) {
        if (tamanio < datos.length) {
            datos[fin] = item;
            fin = (fin + 1) % datos.length;
            tamanio++;
        }
    }

    public String desencolar() {
        if (estaVacia()) return null;
        String item = datos[frente];
        frente = (frente + 1) % datos.length;
        tamanio--;
        return item;
    }

    public boolean estaVacia() { return tamanio == 0; }

    public int size() { return tamanio; }

    public void limpiar() { frente = 0; fin = 0; tamanio = 0; }

    public void mostrar() {
        if (estaVacia()) {
            System.out.println("No hay equipos clasificados en cola.");
            return;
        }
        for (int i = 0; i < tamanio; i++) {
            System.out.println("  " + (i + 1) + ". " + datos[(frente + i) % datos.length]);
        }
    }
}
