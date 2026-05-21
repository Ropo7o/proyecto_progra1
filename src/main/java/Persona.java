public class Persona {
    protected String nombre;
    protected int    edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad   = edad;
    }

    public String getNombre() { return nombre; }
    public int    getEdad()   { return edad;   }

    public void mostrarInfo() {
        System.out.println("Nombre : " + nombre);
        System.out.println("Edad   : " + edad);
    }
}
