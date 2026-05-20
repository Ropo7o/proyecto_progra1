import java.util.Scanner;
import java.util.List;
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
                    new gestion_equipo().probarConexion();
                    break;
                case 2:
                    System.out.println("Gestion de jugadores");
                    break;
                case 3:
                    System.out.println("Gestion partidos");
                    break;

                default:
                    System.out.println("Opción no válida.");
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
        System.out.println("║  0. SALIR                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione: ");

    }
}
