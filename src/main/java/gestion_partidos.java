import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class gestion_partidos {
    private int id;
    private int idLocal;
    private int idVisitante;
    private int golesLocal;
    private int golesVisitante;
    private String fecha;
    private String fase;

    private static final String[] FASES = {
        "Fase de Grupos",
        "Octavos de Final",
        "Cuartos de Final",
        "Semifinal",
        "Tercer Lugar",
        "Final"
    };

    private final Scanner sc = new Scanner(System.in);

    public gestion_partidos() {}

    public gestion_partidos(int id, int idLocal, int idVisitante,
                            int golesLocal, int golesVisitante, String fecha, String fase) {
        this.id = id;
        this.idLocal = idLocal;
        this.idVisitante = idVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.fecha = fecha;
        this.fase = fase;
    }

    public void flujo_gestion_partidos() {
        int opcion;
        do {
            menu_partidos();
            opcion = sc.nextInt();
            switch (opcion) {
                case 0 -> System.out.println("Regresando menu principal...");
                case 1 -> registrar_partido();
                case 2 -> registrar_resultado();
                case 3 -> consultar_todos();
                case 4 -> consultar_por_equipo();
                case 5 -> mostrar_historial();
                case 6 -> mostrar_matriz();
                case 7 -> eliminar_partido();
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    public void menu_partidos() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         GESTION PARTIDOS 2026        ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. REGISTRAR PARTIDO                ║");
        System.out.println("║  2. REGISTRAR RESULTADO              ║");
        System.out.println("║  3. CONSULTAR TODOS                  ║");
        System.out.println("║  4. CONSULTAR POR EQUIPO             ║");
        System.out.println("║  5. MOSTRAR HISTORIAL                ║");
        System.out.println("║  6. MATRIZ DE RESULTADOS             ║");
        System.out.println("║  7. ELIMINAR PARTIDO                 ║");
        System.out.println("║  0. SALIR                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione: ");
    }

    public void registrar_partido() {
        System.out.println("\n--- REGISTRAR PARTIDO ---");
        mostrarEquiposDisponibles();
        sc.nextLine();

        System.out.print("ID equipo local     : ");
        this.idLocal = Integer.parseInt(sc.nextLine().trim());

        System.out.print("ID equipo visitante : ");
        this.idVisitante = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Fecha (YYYY-MM-DD)  : ");
        this.fecha = sc.nextLine().trim();

        mostrarFases();
        System.out.print("Seleccione la fase  : ");
        int opcionFase = Integer.parseInt(sc.nextLine().trim());
        if (opcionFase < 1 || opcionFase > FASES.length) {
            System.out.println("Fase invalida.");
            return;
        }
        this.fase = FASES[opcionFase - 1];

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        int filas = bd.insertar(QueriesPartido.INSERTAR, this.idLocal, this.idVisitante, this.fecha, this.fase);
        if (filas > 0) {
            System.out.println("Partido registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el partido.");
        }
        bd.desconectar();
    }

    public void registrar_resultado() {
        System.out.println("\n--- REGISTRAR RESULTADO ---");
        System.out.print("ID del partido: ");
        int idPartido = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(QueriesPartido.CONSULTAR_POR_ID, idPartido);
        try {
            if (rs == null || !rs.next()) {
                System.out.println("No se encontro un partido con ese ID.");
                bd.desconectar();
                return;
            }

            int idLoc             = rs.getInt("ID_LOCAL");
            int idVis             = rs.getInt("ID_VISITANTE");
            String local          = rs.getString("LOCAL");
            String visitante      = rs.getString("VISITANTE");
            boolean tieneResultado = rs.getObject("GOLES_LOCAL") != null;
            int golesLocAnterior  = tieneResultado ? rs.getInt("GOLES_LOCAL") : -1;
            int golesVisAnterior  = tieneResultado ? rs.getInt("GOLES_VISITANTE") : -1;
            rs.close();

            System.out.println("Local     : " + local);
            System.out.println("Visitante : " + visitante);
            if (tieneResultado) {
                System.out.println("Resultado actual : " + golesLocAnterior + " - " + golesVisAnterior);
            }

            sc.nextLine();
            System.out.print("Goles local     : ");
            this.golesLocal = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Goles visitante : ");
            this.golesVisitante = Integer.parseInt(sc.nextLine().trim());

            if (tieneResultado) {
                revertirEstadisticas(bd, idLoc, idVis, golesLocAnterior, golesVisAnterior);
            }

            bd.modificar(QueriesPartido.ACTUALIZAR_RESULTADO, this.golesLocal, this.golesVisitante, idPartido);
            aplicarEstadisticas(bd, idLoc, idVis, this.golesLocal, this.golesVisitante);

            System.out.println("Resultado registrado y estadisticas actualizadas.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        bd.desconectar();
    }

    public void consultar_todos() {
        System.out.println("\n--- TODOS LOS PARTIDOS ---");
        mostrarTabla(QueriesPartido.CONSULTAR_TODOS);
    }

    public void consultar_por_equipo() {
        System.out.println("\n--- PARTIDOS POR EQUIPO ---");
        mostrarEquiposDisponibles();
        System.out.print("ID del equipo: ");
        int idEquipo = sc.nextInt();
        mostrarTabla(QueriesPartido.CONSULTAR_POR_EQUIPO, idEquipo, idEquipo);
    }

    public void mostrar_historial() {
        System.out.println("\n--- HISTORIAL DE PARTIDOS JUGADOS ---");
        mostrarTabla(QueriesPartido.HISTORIAL_JUGADOS);
    }

    public void mostrar_matriz() {
        System.out.println("\n--- MATRIZ DE RESULTADOS ---");

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        // Cargar equipos en arreglos
        int[]    teamIds   = new int[50];
        String[] teamNames = new String[50];
        int numEquipos = 0;

        ResultSet rsEquipos = bd.consultar(QueriesPartido.TODOS_EQUIPOS);
        try {
            while (rsEquipos != null && rsEquipos.next()) {
                teamIds[numEquipos]   = rsEquipos.getInt("ID");
                teamNames[numEquipos] = rsEquipos.getString("PAIS_ORIGEN");
                numEquipos++;
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar equipos: " + e.getMessage());
            bd.desconectar();
            return;
        }

        if (numEquipos == 0) {
            System.out.println("No hay equipos registrados.");
            bd.desconectar();
            return;
        }

        // Construir matriz de resultados
        String[][] matriz = new String[numEquipos][numEquipos];
        for (int i = 0; i < numEquipos; i++) {
            for (int j = 0; j < numEquipos; j++) {
                matriz[i][j] = (i == j) ? " -- " : "  ?  ";
            }
        }

        // Cargar partidos jugados en ArrayList y llenar la matriz
        ArrayList<int[]> partidos = new ArrayList<>();
        ResultSet rsPartidos = bd.consultar(QueriesPartido.PARTIDOS_CON_RESULTADO);
        try {
            while (rsPartidos != null && rsPartidos.next()) {
                int[] p = new int[4];
                p[0] = rsPartidos.getInt("ID_LOCAL");
                p[1] = rsPartidos.getInt("ID_VISITANTE");
                p[2] = rsPartidos.getInt("GOLES_LOCAL");
                p[3] = rsPartidos.getInt("GOLES_VISITANTE");
                partidos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar partidos: " + e.getMessage());
        }

        for (int[] p : partidos) {
            int iLoc = -1, iVis = -1;
            for (int k = 0; k < numEquipos; k++) {
                if (teamIds[k] == p[0]) iLoc = k;
                if (teamIds[k] == p[1]) iVis = k;
            }
            if (iLoc != -1 && iVis != -1) {
                matriz[iLoc][iVis] = p[2] + " - " + p[3];
            }
        }

        // Mostrar matriz
        System.out.printf("%-5s", "");
        for (int i = 0; i < numEquipos; i++) {
            System.out.printf(" [%2d] ", teamIds[i]);
        }
        System.out.println();

        for (int i = 0; i < numEquipos; i++) {
            System.out.printf("[%2d]  ", teamIds[i]);
            for (int j = 0; j < numEquipos; j++) {
                System.out.printf("%-6s", matriz[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nReferencia:");
        for (int i = 0; i < numEquipos; i++) {
            System.out.println("[" + teamIds[i] + "] " + teamNames[i]);
        }

        bd.desconectar();
    }

    public void eliminar_partido() {
        System.out.println("\n--- ELIMINAR PARTIDO ---");
        System.out.print("ID del partido a eliminar: ");
        int idPartido = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(QueriesPartido.CONSULTAR_POR_ID, idPartido);
        try {
            if (rs == null || !rs.next()) {
                System.out.println("No se encontro un partido con ese ID.");
                bd.desconectar();
                return;
            }

            int     idLoc          = rs.getInt("ID_LOCAL");
            int     idVis          = rs.getInt("ID_VISITANTE");
            boolean tieneResultado = rs.getObject("GOLES_LOCAL") != null;
            int     gLoc           = tieneResultado ? rs.getInt("GOLES_LOCAL") : -1;
            int     gVis           = tieneResultado ? rs.getInt("GOLES_VISITANTE") : -1;
            rs.close();

            if (tieneResultado) {
                revertirEstadisticas(bd, idLoc, idVis, gLoc, gVis);
                System.out.println("Estadisticas revertidas.");
            }

            int filas = bd.eliminar(QueriesPartido.ELIMINAR, idPartido);
            if (filas > 0) {
                System.out.println("Partido eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el partido.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        bd.desconectar();
    }

    private void mostrarTabla(String query) {
        mostrarTabla(query, new Object[]{});
    }

    private void mostrarTabla(String query, Object... params) {
        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(query, params);
        try {
            boolean hayResultados = false;
            while (rs != null && rs.next()) {
                hayResultados = true;
                imprimirPartido(rs);
            }
            if (!hayResultados) {
                System.out.println("No hay partidos registrados.");
            }
            System.out.println("──────────────────────────────────────");
        } catch (SQLException e) {
            System.out.println("Error al leer resultados: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        }
        bd.desconectar();
    }

    private void imprimirPartido(ResultSet rs) throws SQLException {
        boolean jugado = rs.getObject("GOLES_LOCAL") != null;
        String resultado = jugado
                ? rs.getInt("GOLES_LOCAL") + " - " + rs.getInt("GOLES_VISITANTE")
                : "Pendiente";

        System.out.println("──────────────────────────────────────");
        System.out.println("ID        : " + rs.getInt("ID"));
        System.out.println("Local     : " + rs.getString("LOCAL"));
        System.out.println("Visitante : " + rs.getString("VISITANTE"));
        System.out.println("Resultado : " + resultado);
        System.out.println("Fecha     : " + rs.getString("FECHA"));
        System.out.println("Fase      : " + rs.getString("FASE"));
    }

    private void mostrarEquiposDisponibles() {
        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;
        ResultSet rs = bd.consultar(QueriesPartido.TODOS_EQUIPOS);
        System.out.println("Equipos disponibles:");
        try {
            while (rs != null && rs.next()) {
                System.out.println("  [" + rs.getInt("ID") + "] " + rs.getString("PAIS_ORIGEN"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        bd.desconectar();
    }

    private void mostrarFases() {
        System.out.println("Fases disponibles:");
        for (int i = 0; i < FASES.length; i++) {
            System.out.println("  " + (i + 1) + ". " + FASES[i]);
        }
    }

    private void aplicarEstadisticas(ConexionBD bd, int idLocal, int idVisitante,
                                      int golesLocal, int golesVisitante) {
        if (golesLocal > golesVisitante) {
            bd.modificar(QueriesPartido.STATS_VICTORIA, golesLocal,     golesVisitante, idLocal);
            bd.modificar(QueriesPartido.STATS_DERROTA,  golesVisitante, golesLocal,     idVisitante);
        } else if (golesLocal == golesVisitante) {
            bd.modificar(QueriesPartido.STATS_EMPATE, golesLocal,     golesVisitante, idLocal);
            bd.modificar(QueriesPartido.STATS_EMPATE, golesVisitante, golesLocal,     idVisitante);
        } else {
            bd.modificar(QueriesPartido.STATS_DERROTA,  golesLocal,     golesVisitante, idLocal);
            bd.modificar(QueriesPartido.STATS_VICTORIA, golesVisitante, golesLocal,     idVisitante);
        }
    }

    private void revertirEstadisticas(ConexionBD bd, int idLocal, int idVisitante,
                                       int golesLocal, int golesVisitante) {
        if (golesLocal > golesVisitante) {
            bd.modificar(QueriesPartido.REVERT_VICTORIA, golesLocal,     golesVisitante, idLocal);
            bd.modificar(QueriesPartido.REVERT_DERROTA,  golesVisitante, golesLocal,     idVisitante);
        } else if (golesLocal == golesVisitante) {
            bd.modificar(QueriesPartido.REVERT_EMPATE, golesLocal,     golesVisitante, idLocal);
            bd.modificar(QueriesPartido.REVERT_EMPATE, golesVisitante, golesLocal,     idVisitante);
        } else {
            bd.modificar(QueriesPartido.REVERT_DERROTA,  golesLocal,     golesVisitante, idLocal);
            bd.modificar(QueriesPartido.REVERT_VICTORIA, golesVisitante, golesLocal,     idVisitante);
        }
    }
}
