public class Jugador extends Persona {
    private int    id;
    private int    numero;
    private String posicion;
    private int    codigoPais;
    private String nombrePais;
    private float  estatura;
    private int    goles;

    public Jugador(int id, String nombre, int numero, String posicion,
                   int codigoPais, String nombrePais, float estatura, int edad, int goles) {
        super(nombre, edad);
        this.id         = id;
        this.numero     = numero;
        this.posicion   = posicion;
        this.codigoPais = codigoPais;
        this.nombrePais = nombrePais;
        this.estatura   = estatura;
        this.goles      = goles;
    }

    public int    getId()        { return id;         }
    public int    getNumero()    { return numero;     }
    public String getPosicion()  { return posicion;   }
    public int    getCodigoPais(){ return codigoPais; }
    public String getNombrePais(){ return nombrePais; }
    public float  getEstatura()  { return estatura;   }
    public int    getGoles()     { return goles;      }

    @Override
    public void mostrarInfo() {
        System.out.println("──────────────────────────────────────");
        System.out.println("ID       : " + id);
        System.out.println("Nombre   : " + nombre);
        System.out.println("Numero   : " + numero);
        System.out.println("Posicion : " + posicion);
        System.out.println("Equipo   : " + nombrePais);
        System.out.println("Estatura : " + estatura + " m");
        System.out.println("Edad     : " + edad);
        System.out.println("Goles    : " + goles);
    }
}
