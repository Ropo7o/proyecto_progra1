import java.sql.SQLOutput;
import java.util.Scanner;

public class gestion_equipo {
    private int id;
    private String paisOrigen;
    private String nombreDT;
    private int cantidadJugadores;
    private int puntosObtenidos;
    private final ConexionBD bd;

    public equipo_mundial(int id, String paisOrigen, String nombreDT,
                          int cantidadJugadores, int puntosObtenidos) {
        this.id = id;
        this.paisOrigen = paisOrigen;
        this.nombreDT = nombreDT;
        this.cantidadJugadores = cantidadJugadores;
        this.puntosObtenidos = puntosObtenidos;
    }

    public void flujo_gestion_team(){
        Scanner sc  = new Scanner(System.in);
        int opcion_equipo;
        do {
            menu_equipo();
            opcion_equipo = sc.nextInt();
            switch (opcion_equipo){
                case 1 -> registrar_equipo();
                case 2 -> consultar_equipo();
                case 3 -> modificar_equipo();
                case 4 -> eliminar_equipo();
                case 5 -> consultar_nombre();
                case 6 -> consultar_puntos();
                case 7 -> consultar_diferencia();
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion_equipo != 0);
    }

    public void menu_equipo(){
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          GESTION EQUIPO 2026         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║            MENUS PRINCIPAL           ║");
        System.out.println("║  1. REGISTRAR EQUIPOS                ║");
        System.out.println("║  2. CONSULTAR EQUIPOS                ║");
        System.out.println("║  3. MODIFICAR EQUIPOS                ║");
        System.out.println("║  4. ELIMINAR  EQUIPOS                ║");
        System.out.println("║  5. CONSULTAR POR NOMBRE             ║");
        System.out.println("║  6. CONSULTAR POR PUNTOS             ║");
        System.out.println("║  7. CONSULTAR POR DIFERENCIA         ║");
        System.out.println("║  0. SALIR                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione: ");
    }

    public void registrar_equipo(){

    }

    public void consultar_equipo(){

    }

    public void modificar_equipo(){

    }

    public void eliminar_equipo(){

    }

    public void consultar_nombre(){

    }

    public void consultar_puntos(){

    }

    public void consultar_diferencia(){

    }

    public gestion_equipo() {
        bd = new ConexionBD();
    }

    public void probarConexion() {
        bd.conectar();
        bd.desconectar();
    }
}
