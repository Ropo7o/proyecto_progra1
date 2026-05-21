import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class gestion_equipo {
    private int id;
    private String paisOrigen;
    private String nombreDT;
    private int cantidadJugadores;
    private int puntosObtenidos;
    private int diferenciaGoles;

    private final Scanner sc = new Scanner(System.in);

    public gestion_equipo() {}

    public gestion_equipo(int id, String paisOrigen, String nombreDT,
                          int cantidadJugadores, int puntosObtenidos, int diferenciaGoles) {
        this.id = id;
        this.paisOrigen = paisOrigen;
        this.nombreDT = nombreDT;
        this.cantidadJugadores = cantidadJugadores;
        this.puntosObtenidos = puntosObtenidos;
        this.diferenciaGoles = diferenciaGoles;
    }

    public void flujo_gestion_team(){
        int opcion_equipo;
        do {
            menu_equipo();
            opcion_equipo = sc.nextInt();
            switch (opcion_equipo){
                case 0 -> System.out.println("Regresando menu principal...");
                case 1 -> registrar_equipo();
                case 2 -> consultar_equipo();
                case 3 -> modificar_equipo();
                case 4 -> eliminar_equipo();
                case 5 -> ordenar_nombre();
                case 6 -> ordenar_puntos();
                case 7 -> ordenar_diferencia();
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
        System.out.println("\n--- REGISTRAR EQUIPO ---");

        System.out.print("Pais de origen: ");
        sc.nextLine();
        this.paisOrigen = sc.nextLine();

        System.out.print("Nombre del DT: ");
        this.nombreDT = sc.nextLine();

        System.out.print("Cantidad de jugadores: ");
        this.cantidadJugadores = sc.nextInt();

        System.out.print("Puntos obtenidos: ");
        this.puntosObtenidos = sc.nextInt();

        System.out.print("Diferencia de goles: ");
        this.diferenciaGoles = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        try {
            ResultSet rsVerificar = bd.consultar(QueriesEquipo.VERIFICAR_PAIS_EXISTENTE, this.paisOrigen);
            if (rsVerificar != null && rsVerificar.next() && rsVerificar.getInt(1) > 0) {
                System.out.println("Ya existe un equipo registrado con ese pais.");
                bd.desconectar();
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el pais: " + e.getMessage());
            bd.desconectar();
            return;
        }

        int filas = bd.insertar(QueriesEquipo.INSERTAR, this.paisOrigen, this.nombreDT,
                this.cantidadJugadores, this.puntosObtenidos, this.diferenciaGoles);
        if (filas > 0) {
            System.out.println("Equipo registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el equipo.");
        }

        bd.desconectar();
    }

    public void consultar_equipo(){
        System.out.println("\n--- CONSULTAR EQUIPOS ---");

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(QueriesEquipo.CONSULTAR_TODOS);
        try {
            boolean hayResultados = false;
            while (rs != null && rs.next()) {
                hayResultados = true;
                System.out.println("──────────────────────────────────────");
                System.out.println("ID              : " + rs.getInt("ID"));
                System.out.println("Pais de origen  : " + rs.getString("PAIS_ORIGEN"));
                System.out.println("Nombre DT       : " + rs.getString("NOMBRE_DT"));
                System.out.println("Cant. jugadores : " + rs.getInt("CANTIDAD_JUGADORES"));
                System.out.println("Puntos obtenidos: " + rs.getInt("PUNTOS_OBTENIDOS"));
                System.out.println("Cant. goles     : " + rs.getInt("CANTIDAD_GOLES"));
            }
            if (!hayResultados) {
                System.out.println("No hay equipos registrados.");
            }
            System.out.println("──────────────────────────────────────");
        } catch (SQLException e) {
            System.out.println("Error al leer resultados: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        }

        bd.desconectar();
    }

    public void modificar_equipo(){
        System.out.println("\n--- MODIFICAR EQUIPO ---");
        System.out.print("ID del equipo a modificar: ");
        this.id = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(QueriesEquipo.CONSULTAR_POR_ID, this.id);
        try {
            if (rs == null || !rs.next()) {
                System.out.println("No se encontro un equipo con ese ID.");
                bd.desconectar();
                return;
            }

            this.paisOrigen        = rs.getString("PAIS_ORIGEN");
            this.nombreDT          = rs.getString("NOMBRE_DT");
            this.cantidadJugadores = rs.getInt("CANTIDAD_JUGADORES");
            this.puntosObtenidos   = rs.getInt("PUNTOS_OBTENIDOS");
            this.diferenciaGoles   = rs.getInt("CANTIDAD_GOLES");
            rs.close();

            System.out.println("\nDatos actuales:");
            System.out.println("──────────────────────────────────────");
            System.out.println("Pais de origen  : " + this.paisOrigen);
            System.out.println("Nombre DT       : " + this.nombreDT);
            System.out.println("Cant. jugadores : " + this.cantidadJugadores);
            System.out.println("Puntos obtenidos: " + this.puntosObtenidos);
            System.out.println("Dif. de goles   : " + this.diferenciaGoles);
            System.out.println("──────────────────────────────────────");
            System.out.println("(Deje en blanco para conservar el valor actual)\n");
            sc.nextLine();

            System.out.print("Pais de origen  : ");
            String inputPais = sc.nextLine().trim();
            if (!inputPais.isEmpty()) this.paisOrigen = inputPais;

            System.out.print("Nombre DT       : ");
            String inputDt = sc.nextLine().trim();
            if (!inputDt.isEmpty()) this.nombreDT = inputDt;

            System.out.print("Cant. jugadores : ");
            String inputJugadores = sc.nextLine().trim();
            if (!inputJugadores.isEmpty()) this.cantidadJugadores = Integer.parseInt(inputJugadores);

            System.out.print("Puntos obtenidos: ");
            String inputPuntos = sc.nextLine().trim();
            if (!inputPuntos.isEmpty()) this.puntosObtenidos = Integer.parseInt(inputPuntos);

            System.out.print("Dif. de goles   : ");
            String inputDiferencia = sc.nextLine().trim();
            if (!inputDiferencia.isEmpty()) this.diferenciaGoles = Integer.parseInt(inputDiferencia);

            int filas = bd.modificar(QueriesEquipo.MODIFICAR, this.paisOrigen, this.nombreDT,
                    this.cantidadJugadores, this.puntosObtenidos, this.diferenciaGoles, this.id);
            if (filas > 0) {
                System.out.println("Equipo modificado correctamente.");
            } else {
                System.out.println("No se pudo modificar el equipo.");
            }

        } catch (SQLException e) {
            System.out.println("Error al leer datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Valor numerico invalido. No se realizaron cambios.");
        }

        bd.desconectar();
    }

    public void eliminar_equipo(){
        System.out.println("\n--- ELIMINAR EQUIPO ---");
        System.out.print("ID del equipo a eliminar: ");
        this.id = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        int filas = bd.eliminar(QueriesEquipo.ELIMINAR, this.id);
        if (filas > 0) {
            System.out.println("Equipo eliminado correctamente.");
        } else {
            System.out.println("No se encontro un equipo con ese ID.");
        }

        bd.desconectar();
    }

    public void ordenar_nombre(){
        System.out.println("\n--- EQUIPOS ORDENADOS POR NOMBRE ---");
        mostrarTabla(QueriesEquipo.ORDENAR_POR_NOMBRE);
    }

    public void ordenar_puntos(){
        System.out.println("\n--- EQUIPOS ORDENADOS POR PUNTOS ---");
        mostrarTabla(QueriesEquipo.ORDENAR_POR_PUNTOS);
    }

    public void ordenar_diferencia(){
        System.out.println("\n--- EQUIPOS ORDENADOS POR DIFERENCIA DE GOLES ---");
        mostrarTabla(QueriesEquipo.ORDENAR_POR_DIFERENCIA);
    }

    private void mostrarTabla(String query) {
        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(query);
        try {
            boolean hayResultados = false;
            while (rs != null && rs.next()) {
                hayResultados = true;
                System.out.println("──────────────────────────────────────");
                System.out.println("ID              : " + rs.getInt("ID"));
                System.out.println("Pais de origen  : " + rs.getString("PAIS_ORIGEN"));
                System.out.println("Nombre DT       : " + rs.getString("NOMBRE_DT"));
                System.out.println("Cant. jugadores : " + rs.getInt("CANTIDAD_JUGADORES"));
                System.out.println("Puntos obtenidos: " + rs.getInt("PUNTOS_OBTENIDOS"));
                System.out.println("Cant. goles     : " + rs.getInt("CANTIDAD_GOLES"));
            }
            if (!hayResultados) {
                System.out.println("No hay equipos registrados.");
            }
            System.out.println("──────────────────────────────────────");
        } catch (SQLException e) {
            System.out.println("Error al leer resultados: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        }

        bd.desconectar();
    }
}
