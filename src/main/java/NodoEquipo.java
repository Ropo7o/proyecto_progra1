public class NodoEquipo {
    String pais;
    String grupo;
    int pj, pg, pe, pp, gf, gc, puntos;
    NodoEquipo siguiente;

    public NodoEquipo(String pais, String grupo,
                      int pj, int pg, int pe, int pp, int gf, int gc, int puntos) {
        this.pais     = pais;
        this.grupo    = grupo;
        this.pj       = pj;
        this.pg       = pg;
        this.pe       = pe;
        this.pp       = pp;
        this.gf       = gf;
        this.gc       = gc;
        this.puntos   = puntos;
        this.siguiente = null;
    }

    public int diferencia() {
        return gf - gc;
    }
}
