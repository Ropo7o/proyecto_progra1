import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class tabla_posiciones {

    private static final String QUERY_TODOS =
            "SELECT PAIS_ORIGEN, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS FROM EQUIPOS_MUNDIAL";

    private static final String QUERY_POR_GRUPO =
            "SELECT PAIS_ORIGEN, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS FROM EQUIPOS_MUNDIAL WHERE GRUPO = ?";

    private final Pila    historial    = new Pila(30);
    private final Cola    clasificados = new Cola(50);
    private final Scanner sc           = new Scanner(System.in);

    public void flujo_tabla() {
        int opcion;
        do {
            menu_tabla();
            opcion = sc.nextInt();
            switch (opcion) {
                case 0 -> System.out.println("Regresando menu principal...");
                case 1 -> verTablaGeneral();
                case 2 -> verTablaPorGrupo();
                case 3 -> verClasificados();
                case 4 -> verHistorial();
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    public void menu_tabla() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        TABLA DE POSICIONES           ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. TABLA GENERAL                    ║");
        System.out.println("║  2. TABLA POR GRUPO                  ║");
        System.out.println("║  3. VER CLASIFICADOS                 ║");
        System.out.println("║  4. HISTORIAL DE CONSULTAS           ║");
        System.out.println("║  0. SALIR                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione: ");
    }

    private void verTablaGeneral() {
        historial.push("Tabla general");

        // 1. Cargar en lista enlazada
        ListaEnlazada lista = cargarEquipos(null);

        // 2. Ordenar con burbuja (puntos → DG → GF)
        lista.ordenar();

        // 3. Llenar cola de clasificados
        actualizarClasificados(lista);

        // 4. Mostrar como matriz 2D
        System.out.println("\n--- TABLA DE POSICIONES GENERAL ---");
        mostrarComoMatriz(lista);
    }

    private void verTablaPorGrupo() {
        sc.nextLine();
        System.out.print("Grupo (A-Z): ");
        String grupo = sc.nextLine().trim().toUpperCase();
        historial.push("Tabla grupo " + grupo);

        ListaEnlazada lista = cargarEquipos(grupo);
        lista.ordenar();
        actualizarClasificados(lista);

        System.out.println("\n--- TABLA GRUPO " + grupo + " ---");
        mostrarComoMatriz(lista);
    }

    private void verClasificados() {
        historial.push("Ver clasificados");
        System.out.println("\n--- EQUIPOS CLASIFICADOS ---");

        if (clasificados.estaVacia()) {
            System.out.println("Consulte primero la tabla general o por grupo.");
            return;
        }

        System.out.println("Decolando " + clasificados.size() + " equipos clasificados:");
        int num = 1;
        while (!clasificados.estaVacia()) {
            System.out.println("  " + num + ". " + clasificados.desencolar());
            num++;
        }
    }

    private void verHistorial() {
        System.out.println("\n--- HISTORIAL DE CONSULTAS ---");
        historial.mostrar();
    }

    // Carga equipos desde DB a lista enlazada
    private ListaEnlazada cargarEquipos(String grupo) {
        ListaEnlazada lista = new ListaEnlazada();
        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return lista;

        ResultSet rs = (grupo == null)
                ? bd.consultar(QUERY_TODOS)
                : bd.consultar(QUERY_POR_GRUPO, grupo);

        try {
            while (rs != null && rs.next()) {
                lista.insertar(new NodoEquipo(
                        rs.getString("PAIS_ORIGEN"),
                        rs.getString("GRUPO").trim(),
                        rs.getInt("PJ"), rs.getInt("PG"), rs.getInt("PE"), rs.getInt("PP"),
                        rs.getInt("GF"), rs.getInt("GC"), rs.getInt("PUNTOS")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar equipos: " + e.getMessage());
        }
        bd.desconectar();
        return lista;
    }

    // Detecta grupos y encola el top 2 por grupo
    private void actualizarClasificados(ListaEnlazada lista) {
        clasificados.limpiar();

        // Detectar grupos unicos con arreglo
        String[] grupos = new String[26];
        int numGrupos = 0;
        NodoEquipo actual = lista.getCabeza();
        while (actual != null) {
            boolean visto = false;
            for (int i = 0; i < numGrupos; i++) {
                if (grupos[i].equals(actual.grupo)) { visto = true; break; }
            }
            if (!visto) grupos[numGrupos++] = actual.grupo;
            actual = actual.siguiente;
        }

        // Encolar top 2 de cada grupo (la lista ya esta ordenada)
        for (int g = 0; g < numGrupos; g++) {
            int count = 0;
            actual = lista.getCabeza();
            while (actual != null && count < 2) {
                if (actual.grupo.equals(grupos[g])) {
                    clasificados.encolar(
                        "Grupo " + grupos[g] + " - " + actual.pais + " [" + actual.puntos + " pts]"
                    );
                    count++;
                }
                actual = actual.siguiente;
            }
        }
    }

    // Construye matriz String[n+1][11] y la imprime
    private void mostrarComoMatriz(ListaEnlazada lista) {
        int n = lista.size();
        if (n == 0) {
            System.out.println("No hay equipos registrados.");
            return;
        }

        // Matriz: fila 0 = encabezados, filas 1..n = datos
        String[][] m = new String[n + 1][11];
        m[0] = new String[]{"Pos", "Equipo", "Grp", "PJ", "PG", "PE", "PP", "GF", "GC", "DG", "Pts"};

        NodoEquipo actual = lista.getCabeza();
        for (int i = 1; i <= n; i++) {
            m[i][0]  = String.valueOf(i);
            m[i][1]  = actual.pais;
            m[i][2]  = actual.grupo;
            m[i][3]  = String.valueOf(actual.pj);
            m[i][4]  = String.valueOf(actual.pg);
            m[i][5]  = String.valueOf(actual.pe);
            m[i][6]  = String.valueOf(actual.pp);
            m[i][7]  = String.valueOf(actual.gf);
            m[i][8]  = String.valueOf(actual.gc);
            m[i][9]  = String.valueOf(actual.diferencia());
            m[i][10] = String.valueOf(actual.puntos);
            actual = actual.siguiente;
        }

        String sep = "─".repeat(72);
        System.out.println(sep);
        System.out.printf("%-4s %-18s %-4s %-4s %-4s %-4s %-4s %-4s %-4s %-5s %-4s%n",
                m[0][0], m[0][1], m[0][2], m[0][3], m[0][4], m[0][5],
                m[0][6], m[0][7], m[0][8], m[0][9], m[0][10]);
        System.out.println(sep);
        for (int i = 1; i <= n; i++) {
            System.out.printf("%-4s %-18s %-4s %-4s %-4s %-4s %-4s %-4s %-4s %-5s %-4s%n",
                    m[i][0], m[i][1], m[i][2], m[i][3], m[i][4], m[i][5],
                    m[i][6], m[i][7], m[i][8], m[i][9], m[i][10]);
        }
        System.out.println(sep);
    }
}
