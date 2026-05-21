public class QueriesEquipo {

    public static final String INSERTAR =
            "INSERT INTO EQUIPOS_MUNDIAL (PAIS_ORIGEN, NOMBRE_DT, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String CONSULTAR_TODOS =
            "SELECT ID, PAIS_ORIGEN, NOMBRE_DT, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS " +
            "FROM EQUIPOS_MUNDIAL";

    public static final String CONSULTAR_POR_ID =
            "SELECT ID, PAIS_ORIGEN, NOMBRE_DT, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS " +
            "FROM EQUIPOS_MUNDIAL WHERE ID = ?";

    public static final String VERIFICAR_PAIS_EXISTENTE =
            "SELECT COUNT(*) FROM EQUIPOS_MUNDIAL WHERE PAIS_ORIGEN = ?";

    public static final String ORDENAR_POR_NOMBRE =
            "SELECT ID, PAIS_ORIGEN, NOMBRE_DT, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS " +
            "FROM EQUIPOS_MUNDIAL ORDER BY PAIS_ORIGEN ASC";

    public static final String ORDENAR_POR_PUNTOS =
            "SELECT ID, PAIS_ORIGEN, NOMBRE_DT, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS " +
            "FROM EQUIPOS_MUNDIAL ORDER BY PUNTOS DESC";

    public static final String ORDENAR_POR_DIFERENCIA =
            "SELECT ID, PAIS_ORIGEN, NOMBRE_DT, GRUPO, PJ, PG, PE, PP, GF, GC, PUNTOS " +
            "FROM EQUIPOS_MUNDIAL ORDER BY (GF - GC) DESC";

    public static final String MODIFICAR =
            "UPDATE EQUIPOS_MUNDIAL " +
            "SET PAIS_ORIGEN = ?, NOMBRE_DT = ?, GRUPO = ?, PJ = ?, PG = ?, PE = ?, PP = ?, GF = ?, GC = ?, PUNTOS = ? " +
            "WHERE ID = ?";

    public static final String ELIMINAR =
            "DELETE FROM EQUIPOS_MUNDIAL WHERE ID = ?";
}
