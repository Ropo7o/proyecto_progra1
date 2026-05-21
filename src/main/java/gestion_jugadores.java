import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class gestion_jugadores {
    private int id;
    private String nombreJugador;
    private int numeroJugador;
    private String posicionJugador;
    private int codigoPaisJugador;
    private float estaturaJugador;
    private int edadJugador;
    private int golesJugador;

    private final Scanner sc = new Scanner(System.in);

    public gestion_jugadores() {}

    public gestion_jugadores(int id, String nombreJugador, int numeroJugador, String posicionJugador,
                              int codigoPaisJugador, float estaturaJugador, int edadJugador, int golesJugador) {
        this.id = id;
        this.nombreJugador = nombreJugador;
        this.numeroJugador = numeroJugador;
        this.posicionJugador = posicionJugador;
        this.codigoPaisJugador = codigoPaisJugador;
        this.estaturaJugador = estaturaJugador;
        this.edadJugador = edadJugador;
        this.golesJugador = golesJugador;
    }

    public void flujo_gestion_jugador() {
        int opcion;
        do {
            menu_jugador();
            opcion = sc.nextInt();
            switch (opcion) {
                case 0 -> System.out.println("Regresando menu principal...");
                case 1 -> registrar_jugador();
                case 2 -> consultar_todos();
                case 3 -> consultar_por_equipo();
                case 4 -> buscar_por_nombre();
                case 5 -> buscar_por_numero();
                case 6 -> modificar_jugador();
                case 7 -> eliminar_jugador();
                case 8 -> ordenar_nombre();
                case 9 -> ordenar_goles();
                case 10 -> buscar_secuencial();
                case 11 -> buscar_binaria();
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    public void menu_jugador() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        GESTION JUGADORES 2026        ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. REGISTRAR JUGADOR                ║");
        System.out.println("║  2. CONSULTAR TODOS                  ║");
        System.out.println("║  3. CONSULTAR POR EQUIPO             ║");
        System.out.println("║  4. BUSCAR POR NOMBRE                ║");
        System.out.println("║  5. BUSCAR POR NUMERO                ║");
        System.out.println("║  6. MODIFICAR JUGADOR                ║");
        System.out.println("║  7. ELIMINAR JUGADOR                 ║");
        System.out.println("║  8. ORDENAR POR NOMBRE               ║");
        System.out.println("║  9. ORDENAR POR GOLES                ║");
        System.out.println("║  10. BUSCAR SECUENCIAL (POSICION)   ║");
        System.out.println("║  11. BUSCAR BINARIA (NUMERO)        ║");
        System.out.println("║  0. SALIR                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione: ");
    }

    public void registrar_jugador() {
        System.out.println("\n--- REGISTRAR JUGADOR ---");
        sc.nextLine();

        System.out.print("Nombre del jugador   : ");
        this.nombreJugador = sc.nextLine();

        System.out.print("Numero del jugador   : ");
        this.numeroJugador = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Posicion             : ");
        this.posicionJugador = sc.nextLine();

        System.out.print("ID del equipo        : ");
        this.codigoPaisJugador = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Estatura en metros   : ");
        this.estaturaJugador = Float.parseFloat(sc.nextLine().trim());

        System.out.print("Edad                 : ");
        this.edadJugador = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Goles                : ");
        this.golesJugador = Integer.parseInt(sc.nextLine().trim());

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        int filas = bd.insertar(QueriesJugador.INSERTAR, this.nombreJugador, this.numeroJugador,
                this.posicionJugador, this.codigoPaisJugador, this.estaturaJugador,
                this.edadJugador, this.golesJugador);
        if (filas > 0) {
            System.out.println("Jugador registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el jugador.");
        }

        bd.desconectar();
    }

    public void consultar_todos() {
        System.out.println("\n--- CONSULTAR TODOS LOS JUGADORES ---");
        mostrarTabla(QueriesJugador.CONSULTAR_TODOS);
    }

    public void consultar_por_equipo() {
        System.out.println("\n--- CONSULTAR JUGADORES POR EQUIPO ---");
        System.out.print("ID del equipo: ");
        int idEquipo = sc.nextInt();
        mostrarTabla(QueriesJugador.CONSULTAR_POR_EQUIPO, idEquipo);
    }

    public void buscar_por_nombre() {
        System.out.println("\n--- BUSCAR JUGADOR POR NOMBRE ---");
        sc.nextLine();
        System.out.print("Nombre a buscar: ");
        String nombre = sc.nextLine().trim();
        mostrarTabla(QueriesJugador.BUSCAR_POR_NOMBRE, "%" + nombre + "%");
    }

    public void buscar_por_numero() {
        System.out.println("\n--- BUSCAR JUGADOR POR NUMERO ---");
        System.out.print("Numero del jugador: ");
        int numero = sc.nextInt();
        mostrarTabla(QueriesJugador.BUSCAR_POR_NUMERO, numero);
    }

    public void modificar_jugador() {
        System.out.println("\n--- MODIFICAR JUGADOR ---");
        System.out.print("ID del jugador a modificar: ");
        this.id = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        ResultSet rs = bd.consultar(QueriesJugador.CONSULTAR_POR_ID, this.id);
        try {
            if (rs == null || !rs.next()) {
                System.out.println("No se encontro un jugador con ese ID.");
                bd.desconectar();
                return;
            }

            this.nombreJugador     = rs.getString("NOMBRE_JUGADOR");
            this.numeroJugador     = rs.getInt("NUMERO_JUGADOR");
            this.posicionJugador   = rs.getString("POSICION_JUGADOR");
            this.codigoPaisJugador = rs.getInt("CODIGO_PAIS_JUGADOR");
            this.estaturaJugador   = rs.getFloat("ESTATURA_JUGADOR");
            this.edadJugador       = rs.getInt("EDAD_JUGADOR");
            this.golesJugador      = rs.getInt("GOLES_JUGADOR");
            rs.close();

            System.out.println("\nDatos actuales:");
            System.out.println("──────────────────────────────────────");
            System.out.println("Nombre    : " + this.nombreJugador);
            System.out.println("Numero    : " + this.numeroJugador);
            System.out.println("Posicion  : " + this.posicionJugador);
            System.out.println("ID equipo : " + this.codigoPaisJugador);
            System.out.println("Estatura  : " + this.estaturaJugador);
            System.out.println("Edad      : " + this.edadJugador);
            System.out.println("Goles     : " + this.golesJugador);
            System.out.println("──────────────────────────────────────");
            System.out.println("(Deje en blanco para conservar el valor actual)\n");
            sc.nextLine();

            System.out.print("Nombre    : ");
            String inputNombre = sc.nextLine().trim();
            if (!inputNombre.isEmpty()) this.nombreJugador = inputNombre;

            System.out.print("Numero    : ");
            String inputNumero = sc.nextLine().trim();
            if (!inputNumero.isEmpty()) this.numeroJugador = Integer.parseInt(inputNumero);

            System.out.print("Posicion  : ");
            String inputPosicion = sc.nextLine().trim();
            if (!inputPosicion.isEmpty()) this.posicionJugador = inputPosicion;

            System.out.print("ID equipo : ");
            String inputCodigo = sc.nextLine().trim();
            if (!inputCodigo.isEmpty()) this.codigoPaisJugador = Integer.parseInt(inputCodigo);

            System.out.print("Estatura  : ");
            String inputEstatura = sc.nextLine().trim();
            if (!inputEstatura.isEmpty()) this.estaturaJugador = Float.parseFloat(inputEstatura);

            System.out.print("Edad      : ");
            String inputEdad = sc.nextLine().trim();
            if (!inputEdad.isEmpty()) this.edadJugador = Integer.parseInt(inputEdad);

            System.out.print("Goles     : ");
            String inputGoles = sc.nextLine().trim();
            if (!inputGoles.isEmpty()) this.golesJugador = Integer.parseInt(inputGoles);

            int filas = bd.modificar(QueriesJugador.MODIFICAR, this.nombreJugador, this.numeroJugador,
                    this.posicionJugador, this.codigoPaisJugador, this.estaturaJugador,
                    this.edadJugador, this.golesJugador, this.id);
            if (filas > 0) {
                System.out.println("Jugador modificado correctamente.");
            } else {
                System.out.println("No se pudo modificar el jugador.");
            }

        } catch (SQLException e) {
            System.out.println("Error al leer datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Valor numerico invalido. No se realizaron cambios.");
        }

        bd.desconectar();
    }

    public void eliminar_jugador() {
        System.out.println("\n--- ELIMINAR JUGADOR ---");
        System.out.print("ID del jugador a eliminar: ");
        this.id = sc.nextInt();

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return;

        int filas = bd.eliminar(QueriesJugador.ELIMINAR, this.id);
        if (filas > 0) {
            System.out.println("Jugador eliminado correctamente.");
        } else {
            System.out.println("No se encontro un jugador con ese ID.");
        }

        bd.desconectar();
    }

    public void ordenar_nombre() {
        System.out.println("\n--- JUGADORES ORDENADOS POR NOMBRE ---");
        mostrarTabla(QueriesJugador.ORDENAR_POR_NOMBRE);
    }

    public void ordenar_goles() {
        System.out.println("\n--- JUGADORES ORDENADOS POR GOLES ---");
        mostrarTabla(QueriesJugador.ORDENAR_POR_GOLES);
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
                imprimirJugador(rs);
            }
            if (!hayResultados) {
                System.out.println("No hay jugadores registrados.");
            }
            System.out.println("──────────────────────────────────────");
        } catch (SQLException e) {
            System.out.println("Error al leer resultados: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        }

        bd.desconectar();
    }

    public void buscar_secuencial() {
        System.out.println("\n--- BUSQUEDA SECUENCIAL POR POSICION ---");
        sc.nextLine();
        System.out.println("Posiciones: Portero / Defensa / Mediocampo / Delantero");
        System.out.print("Posicion a buscar: ");
        String busqueda = sc.nextLine().trim();

        Jugador[] jugadores = cargarJugadoresEnMemoria();
        System.out.println("Buscando en " + jugadores.length + " jugadores...");

        boolean encontrado = false;
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getPosicion().equalsIgnoreCase(busqueda)) {
                Persona p = jugadores[i]; // polimorfismo
                p.mostrarInfo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron jugadores con esa posicion.");
        }
        System.out.println("──────────────────────────────────────");
    }

    public void buscar_binaria() {
        System.out.println("\n--- BUSQUEDA BINARIA POR NUMERO ---");
        System.out.print("Numero del jugador a buscar: ");
        int numero = sc.nextInt();

        Jugador[] jugadores = cargarJugadoresEnMemoria();

        // Ordenar por numero con insercion antes de buscar
        ordenarPorNumero(jugadores);
        System.out.println("Arreglo ordenado. Aplicando busqueda binaria...");

        int indice = busquedaBinaria(jugadores, numero);

        if (indice == -1) {
            System.out.println("No se encontro un jugador con el numero " + numero);
        } else {
            // Expandir desde el indice encontrado para mostrar todos con ese numero
            int izq = indice;
            while (izq > 0 && jugadores[izq - 1].getNumero() == numero) izq--;
            int der = indice;
            while (der < jugadores.length - 1 && jugadores[der + 1].getNumero() == numero) der++;

            for (int i = izq; i <= der; i++) {
                Persona p = jugadores[i]; // polimorfismo
                p.mostrarInfo();
            }
        }
        System.out.println("──────────────────────────────────────");
    }

    // Ordenamiento por insercion sobre Jugador[] por numero
    private void ordenarPorNumero(Jugador[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Jugador clave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].getNumero() > clave.getNumero()) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = clave;
        }
    }

    // Busqueda binaria: retorna el indice del elemento encontrado o -1
    private int busquedaBinaria(Jugador[] arr, int numero) {
        int inicio = 0;
        int fin    = arr.length - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (arr[medio].getNumero() == numero) {
                return medio;
            } else if (arr[medio].getNumero() < numero) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    private Jugador[] cargarJugadoresEnMemoria() {
        Jugador[] temporal = new Jugador[200];
        int count = 0;

        ConexionBD bd = new ConexionBD();
        if (!bd.conectar()) return new Jugador[0];

        ResultSet rs = bd.consultar(QueriesJugador.CONSULTAR_TODOS);
        try {
            while (rs != null && rs.next() && count < temporal.length) {
                temporal[count++] = new Jugador(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE_JUGADOR"),
                        rs.getInt("NUMERO_JUGADOR"),
                        rs.getString("POSICION_JUGADOR"),
                        rs.getInt("CODIGO_PAIS_JUGADOR"),
                        rs.getString("PAIS_ORIGEN"),
                        rs.getFloat("ESTATURA_JUGADOR"),
                        rs.getInt("EDAD_JUGADOR"),
                        rs.getInt("GOLES_JUGADOR")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar jugadores: " + e.getMessage());
        }
        bd.desconectar();

        // Ajustar al tamanio real
        Jugador[] resultado = new Jugador[count];
        for (int i = 0; i < count; i++) resultado[i] = temporal[i];
        return resultado;
    }

    private void imprimirJugador(ResultSet rs) throws SQLException {
        // Polimorfismo: referencia Persona apunta a un objeto Jugador
        Persona p = new Jugador(
                rs.getInt("ID"),
                rs.getString("NOMBRE_JUGADOR"),
                rs.getInt("NUMERO_JUGADOR"),
                rs.getString("POSICION_JUGADOR"),
                rs.getInt("CODIGO_PAIS_JUGADOR"),
                rs.getString("PAIS_ORIGEN"),
                rs.getFloat("ESTATURA_JUGADOR"),
                rs.getInt("EDAD_JUGADOR"),
                rs.getInt("GOLES_JUGADOR")
        );
        p.mostrarInfo(); // llama Jugador.mostrarInfo() por polimorfismo
    }
}
