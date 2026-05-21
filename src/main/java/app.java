import java.util.Scanner;
public class app {
    static Scanner sc  = new Scanner(System.in);

    public void flujo_principal(){
        int opcion = 0;
        do {
            menu_principal();
            opcion = sc.nextInt();
            switch(opcion){
                case 0:
                    System.out.println("Feliz dia :)");
                    break;
                case 1:
                    gestion_equipo GE = new gestion_equipo();
                    GE.flujo_gestion_team();
                    break;
                case 2:
                    gestion_jugadores GJ = new gestion_jugadores();
                    GJ.flujo_gestion_jugador();
                    break;
                case 3:
                    gestion_partidos GP = new gestion_partidos();
                    GP.flujo_gestion_partidos();
                    break;
                case 4:
                    tabla_posiciones TP = new tabla_posiciones();
                    TP.flujo_tabla();
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);

    }

    static void menu_principal(){
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║             MUNDIAL 2026             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║            MENUS PRINCIPAL           ║");
        System.out.println("║  1. GESTION DE EQUIPOS               ║");
        System.out.println("║  2. GESTION DE JUGADORES             ║");
        System.out.println("║  3. GESTION DE PARTIDOS              ║");
        System.out.println("║  4. TABLA DE POSICIONES              ║");
        System.out.println("║  0. SALIR                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione: ");

    }
}
