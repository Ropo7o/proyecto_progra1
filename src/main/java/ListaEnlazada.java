public class ListaEnlazada {
    private NodoEquipo cabeza;

    public ListaEnlazada() {
        this.cabeza = null;
    }

    public void insertar(NodoEquipo nuevo) {
        nuevo.siguiente = null;
        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }
        NodoEquipo actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo;
    }

    // Ordenamiento burbuja: puntos DESC → diferencia DESC → GF DESC
    public void ordenar() {
        if (cabeza == null || cabeza.siguiente == null) return;
        boolean cambio;
        do {
            cambio = false;
            NodoEquipo actual = cabeza;
            while (actual.siguiente != null) {
                NodoEquipo sig = actual.siguiente;
                if (debeIntercambiar(actual, sig)) {
                    intercambiarDatos(actual, sig);
                    cambio = true;
                }
                actual = actual.siguiente;
            }
        } while (cambio);
    }

    private boolean debeIntercambiar(NodoEquipo a, NodoEquipo b) {
        if (a.puntos != b.puntos)         return a.puntos < b.puntos;
        if (a.diferencia() != b.diferencia()) return a.diferencia() < b.diferencia();
        return a.gf < b.gf;
    }

    private void intercambiarDatos(NodoEquipo a, NodoEquipo b) {
        String tmpStr;
        int tmp;
        tmpStr = a.pais;   a.pais   = b.pais;   b.pais   = tmpStr;
        tmpStr = a.grupo;  a.grupo  = b.grupo;  b.grupo  = tmpStr;
        tmp = a.pj;    a.pj    = b.pj;    b.pj    = tmp;
        tmp = a.pg;    a.pg    = b.pg;    b.pg    = tmp;
        tmp = a.pe;    a.pe    = b.pe;    b.pe    = tmp;
        tmp = a.pp;    a.pp    = b.pp;    b.pp    = tmp;
        tmp = a.gf;    a.gf    = b.gf;    b.gf    = tmp;
        tmp = a.gc;    a.gc    = b.gc;    b.gc    = tmp;
        tmp = a.puntos; a.puntos = b.puntos; b.puntos = tmp;
    }

    public NodoEquipo getCabeza() { return cabeza; }

    public int size() {
        int count = 0;
        NodoEquipo actual = cabeza;
        while (actual != null) { count++; actual = actual.siguiente; }
        return count;
    }
}
