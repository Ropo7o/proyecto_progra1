public class QueriesJugador {

    public static final String INSERTAR =
            "INSERT INTO JUGADORES_MUNDIAL (NOMBRE_JUGADOR, NUMERO_JUGADOR, POSICION_JUGADOR, " +
            "CODIGO_PAIS_JUGADOR, ESTATURA_JUGADOR, EDAD_JUGADOR, GOLES_JUGADOR) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String CONSULTAR_TODOS =
            "SELECT j.ID, j.NOMBRE_JUGADOR, j.NUMERO_JUGADOR, j.POSICION_JUGADOR, " +
            "j.CODIGO_PAIS_JUGADOR, ISNULL(e.PAIS_ORIGEN, 'Sin equipo') AS PAIS_ORIGEN, " +
            "j.ESTATURA_JUGADOR, j.EDAD_JUGADOR, j.GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL j " +
            "LEFT JOIN EQUIPOS_MUNDIAL e ON j.CODIGO_PAIS_JUGADOR = e.ID";

    public static final String CONSULTAR_POR_ID =
            "SELECT ID, NOMBRE_JUGADOR, NUMERO_JUGADOR, POSICION_JUGADOR, " +
            "CODIGO_PAIS_JUGADOR, ESTATURA_JUGADOR, EDAD_JUGADOR, GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL WHERE ID = ?";

    public static final String CONSULTAR_POR_EQUIPO =
            "SELECT j.ID, j.NOMBRE_JUGADOR, j.NUMERO_JUGADOR, j.POSICION_JUGADOR, " +
            "j.CODIGO_PAIS_JUGADOR, ISNULL(e.PAIS_ORIGEN, 'Sin equipo') AS PAIS_ORIGEN, " +
            "j.ESTATURA_JUGADOR, j.EDAD_JUGADOR, j.GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL j " +
            "LEFT JOIN EQUIPOS_MUNDIAL e ON j.CODIGO_PAIS_JUGADOR = e.ID " +
            "WHERE j.CODIGO_PAIS_JUGADOR = ?";

    public static final String BUSCAR_POR_NOMBRE =
            "SELECT j.ID, j.NOMBRE_JUGADOR, j.NUMERO_JUGADOR, j.POSICION_JUGADOR, " +
            "j.CODIGO_PAIS_JUGADOR, ISNULL(e.PAIS_ORIGEN, 'Sin equipo') AS PAIS_ORIGEN, " +
            "j.ESTATURA_JUGADOR, j.EDAD_JUGADOR, j.GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL j " +
            "LEFT JOIN EQUIPOS_MUNDIAL e ON j.CODIGO_PAIS_JUGADOR = e.ID " +
            "WHERE j.NOMBRE_JUGADOR LIKE ?";

    public static final String BUSCAR_POR_NUMERO =
            "SELECT j.ID, j.NOMBRE_JUGADOR, j.NUMERO_JUGADOR, j.POSICION_JUGADOR, " +
            "j.CODIGO_PAIS_JUGADOR, ISNULL(e.PAIS_ORIGEN, 'Sin equipo') AS PAIS_ORIGEN, " +
            "j.ESTATURA_JUGADOR, j.EDAD_JUGADOR, j.GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL j " +
            "LEFT JOIN EQUIPOS_MUNDIAL e ON j.CODIGO_PAIS_JUGADOR = e.ID " +
            "WHERE j.NUMERO_JUGADOR = ?";

    public static final String ORDENAR_POR_NOMBRE =
            "SELECT j.ID, j.NOMBRE_JUGADOR, j.NUMERO_JUGADOR, j.POSICION_JUGADOR, " +
            "j.CODIGO_PAIS_JUGADOR, ISNULL(e.PAIS_ORIGEN, 'Sin equipo') AS PAIS_ORIGEN, " +
            "j.ESTATURA_JUGADOR, j.EDAD_JUGADOR, j.GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL j " +
            "LEFT JOIN EQUIPOS_MUNDIAL e ON j.CODIGO_PAIS_JUGADOR = e.ID " +
            "ORDER BY j.NOMBRE_JUGADOR ASC";

    public static final String ORDENAR_POR_GOLES =
            "SELECT j.ID, j.NOMBRE_JUGADOR, j.NUMERO_JUGADOR, j.POSICION_JUGADOR, " +
            "j.CODIGO_PAIS_JUGADOR, ISNULL(e.PAIS_ORIGEN, 'Sin equipo') AS PAIS_ORIGEN, " +
            "j.ESTATURA_JUGADOR, j.EDAD_JUGADOR, j.GOLES_JUGADOR " +
            "FROM JUGADORES_MUNDIAL j " +
            "LEFT JOIN EQUIPOS_MUNDIAL e ON j.CODIGO_PAIS_JUGADOR = e.ID " +
            "ORDER BY j.GOLES_JUGADOR DESC";

    public static final String MODIFICAR =
            "UPDATE JUGADORES_MUNDIAL " +
            "SET NOMBRE_JUGADOR = ?, NUMERO_JUGADOR = ?, POSICION_JUGADOR = ?, " +
            "CODIGO_PAIS_JUGADOR = ?, ESTATURA_JUGADOR = ?, EDAD_JUGADOR = ?, GOLES_JUGADOR = ? " +
            "WHERE ID = ?";

    public static final String ELIMINAR =
            "DELETE FROM JUGADORES_MUNDIAL WHERE ID = ?";
}
