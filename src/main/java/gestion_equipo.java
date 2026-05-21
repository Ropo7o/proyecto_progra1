import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class gestion_equipo {
    private int id;
    private String paisOrigen;
    private String nombreDT;
    private String grupo;
    private int pj;
    private int pg;
    private int pe;
    private int pp;
    private int gf;
    private int gc;
    private int puntos;

    private final Scanner sc = new Scanner(System.in);

    public gestion_equipo() {}

    public gestion_equipo(int id, String paisOrigen, String nombreDT, String grupo,
                          int pj, int pg, int pe, int pp, int gf, int gc, int puntos) {
        this.id = id;
        this.paisOrigen = paisOrigen;
        this.nombreDT = nombreDT;
        this.grupo = grupo;
        this.pj = pj;
        this.pg = pg;
        this.pe = pe;
        this.pp = pp;
        this.gf = gf;
        this.gc = gc;
        this.puntos = puntos;
    }

    public void flujo_gestion_team() {
        int opcion_equipo;
        do {
            menu_equipo();
            opcion_equipo = sc.nextInt();
            switch (opcion_equipo) {
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

    public void menu_equipo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          GESTION EQUIPO 2026         ║");
        System.out.println("╠══════════════════════════════════════╣");
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

    public void registrar_equipo() {
        System.out.println("\n--- REGISTRAR EQUIPO ---");
        sc.nextLine();

        System.out.print("Pais de origen : ");
        this.paisOrigen = sc.nextLine();

        System.out.print("Nombre del DT  : ");
        this.nombreDT = sc.nextLine();

        System.out.print("Grupo (A-Z)    : ");
        this.grupo = sc.nextLine().trim().toUpperCase();

        System.out.print("PJ (partidos jugados)   : ");
        this.pj = sc.nextInt();

        System.out.print("PG (partidos ganados)   : ");
        this.pg = sc.nextInt();

        System.out.print("PE (partidos empatados) : ");
        this.pe = sc.nextInt();

        System.out.print("PP (partidos perdidos)  : ");
        this.pp = sc.nextInt();

        System.out.print("GF (goles a favor)      : ");
        this.gf = sc.nextInt();

        System.out.print("GC (goles en contra)    : ");
        this.gc = sc.nextInt();

        System.out.print("Puntos                  : ");
        this.puntos = sc.nextInt();

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
                this.grupo, this.pj, this.pg, this.pe, this.pp, this.gf, this.gc, this.puntos);
        if (filas > 0) {
            System.out.println("Equipo registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el equipo.");
        }

        bd.desconectar();
    }

    public void consultar_equipo() {
        System.out.println("\n--- CONSULTAR EQUIPOS ---");

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(QueriesEquipo.CONSULTAR_TODOS);
        try {
            boolean hayResultados = false;
            while (rs != null && rs.next()) {
                hayResultados = true;
                imprimirEquipo(rs);
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

    public void modificar_equipo() {
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

            this.paisOrigen = rs.getString("PAIS_ORIGEN");
            this.nombreDT   = rs.getString("NOMBRE_DT");
            this.grupo      = rs.getString("GRUPO").trim();
            this.pj         = rs.getInt("PJ");
            this.pg         = rs.getInt("PG");
            this.pe         = rs.getInt("PE");
            this.pp         = rs.getInt("PP");
            this.gf         = rs.getInt("GF");
            this.gc         = rs.getInt("GC");
            this.puntos     = rs.getInt("PUNTOS");
            rs.close();

            System.out.println("\nDatos actuales:");
            System.out.println("──────────────────────────────────────");
            System.out.println("Pais de origen : " + this.paisOrigen);
            System.out.println("Nombre DT      : " + this.nombreDT);
            System.out.println("Grupo          : " + this.grupo);
            System.out.println("PJ             : " + this.pj);
            System.out.println("PG             : " + this.pg);
            System.out.println("PE             : " + this.pe);
            System.out.println("PP             : " + this.pp);
            System.out.println("GF             : " + this.gf);
            System.out.println("GC             : " + this.gc);
            System.out.println("Puntos         : " + this.puntos);
            System.out.println("──────────────────────────────────────");
            System.out.println("(Deje en blanco para conservar el valor actual)\n");
            sc.nextLine();

            System.out.print("Pais de origen : ");
            String inputPais = sc.nextLine().trim();
            if (!inputPais.isEmpty()) this.paisOrigen = inputPais;

            System.out.print("Nombre DT      : ");
            String inputDt = sc.nextLine().trim();
            if (!inputDt.isEmpty()) this.nombreDT = inputDt;

            System.out.print("Grupo (A-Z)    : ");
            String inputGrupo = sc.nextLine().trim().toUpperCase();
            if (!inputGrupo.isEmpty()) this.grupo = inputGrupo.substring(0, 1);

            System.out.print("PJ             : ");
            String inputPj = sc.nextLine().trim();
            if (!inputPj.isEmpty()) this.pj = Integer.parseInt(inputPj);

            System.out.print("PG             : ");
            String inputPg = sc.nextLine().trim();
            if (!inputPg.isEmpty()) this.pg = Integer.parseInt(inputPg);

            System.out.print("PE             : ");
            String inputPe = sc.nextLine().trim();
            if (!inputPe.isEmpty()) this.pe = Integer.parseInt(inputPe);

            System.out.print("PP             : ");
            String inputPp = sc.nextLine().trim();
            if (!inputPp.isEmpty()) this.pp = Integer.parseInt(inputPp);

            System.out.print("GF             : ");
            String inputGf = sc.nextLine().trim();
            if (!inputGf.isEmpty()) this.gf = Integer.parseInt(inputGf);

            System.out.print("GC             : ");
            String inputGc = sc.nextLine().trim();
            if (!inputGc.isEmpty()) this.gc = Integer.parseInt(inputGc);

            System.out.print("Puntos         : ");
            String inputPuntos = sc.nextLine().trim();
            if (!inputPuntos.isEmpty()) this.puntos = Integer.parseInt(inputPuntos);

            int filas = bd.modificar(QueriesEquipo.MODIFICAR, this.paisOrigen, this.nombreDT,
                    this.grupo, this.pj, this.pg, this.pe, this.pp, this.gf, this.gc, this.puntos, this.id);
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

    public void eliminar_equipo() {
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

    public void ordenar_nombre() {
        System.out.println("\n--- EQUIPOS ORDENADOS POR NOMBRE ---");
        mostrarTabla(QueriesEquipo.ORDENAR_POR_NOMBRE);
    }

    public void ordenar_puntos() {
        System.out.println("\n--- EQUIPOS ORDENADOS POR PUNTOS ---");
        mostrarTabla(QueriesEquipo.ORDENAR_POR_PUNTOS);
    }

    public void ordenar_diferencia() {
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
                imprimirEquipo(rs);
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

    private void imprimirEquipo(ResultSet rs) throws SQLException {
        System.out.println("──────────────────────────────────────");
        System.out.println("ID             : " + rs.getInt("ID"));
        System.out.println("Pais de origen : " + rs.getString("PAIS_ORIGEN"));
        System.out.println("Nombre DT      : " + rs.getString("NOMBRE_DT"));
        System.out.println("Grupo          : " + rs.getString("GRUPO").trim());
        System.out.println("PJ             : " + rs.getInt("PJ"));
        System.out.println("PG             : " + rs.getInt("PG"));
        System.out.println("PE             : " + rs.getInt("PE"));
        System.out.println("PP             : " + rs.getInt("PP"));
        System.out.println("GF             : " + rs.getInt("GF"));
        System.out.println("GC             : " + rs.getInt("GC"));
        System.out.println("Dif. de goles  : " + (rs.getInt("GF") - rs.getInt("GC")));
        System.out.println("Puntos         : " + rs.getInt("PUNTOS"));
    }
}
