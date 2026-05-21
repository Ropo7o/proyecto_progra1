public class QueriesPartido {

    public static final String INSERTAR =
            "INSERT INTO PARTIDOS_MUNDIAL (ID_LOCAL, ID_VISITANTE, GOLES_LOCAL, GOLES_VISITANTE, FECHA, FASE) " +
            "VALUES (?, ?, NULL, NULL, ?, ?)";

    public static final String CONSULTAR_TODOS =
            "SELECT p.ID, el.PAIS_ORIGEN AS LOCAL, ev.PAIS_ORIGEN AS VISITANTE, " +
            "p.GOLES_LOCAL, p.GOLES_VISITANTE, p.FECHA, p.FASE " +
            "FROM PARTIDOS_MUNDIAL p " +
            "JOIN EQUIPOS_MUNDIAL el ON p.ID_LOCAL  = el.ID " +
            "JOIN EQUIPOS_MUNDIAL ev ON p.ID_VISITANTE = ev.ID";

    public static final String CONSULTAR_POR_ID =
            "SELECT p.ID, p.ID_LOCAL, p.ID_VISITANTE, p.GOLES_LOCAL, p.GOLES_VISITANTE, p.FECHA, p.FASE, " +
            "el.PAIS_ORIGEN AS LOCAL, ev.PAIS_ORIGEN AS VISITANTE " +
            "FROM PARTIDOS_MUNDIAL p " +
            "JOIN EQUIPOS_MUNDIAL el ON p.ID_LOCAL  = el.ID " +
            "JOIN EQUIPOS_MUNDIAL ev ON p.ID_VISITANTE = ev.ID " +
            "WHERE p.ID = ?";

    public static final String CONSULTAR_POR_EQUIPO =
            "SELECT p.ID, el.PAIS_ORIGEN AS LOCAL, ev.PAIS_ORIGEN AS VISITANTE, " +
            "p.GOLES_LOCAL, p.GOLES_VISITANTE, p.FECHA, p.FASE " +
            "FROM PARTIDOS_MUNDIAL p " +
            "JOIN EQUIPOS_MUNDIAL el ON p.ID_LOCAL  = el.ID " +
            "JOIN EQUIPOS_MUNDIAL ev ON p.ID_VISITANTE = ev.ID " +
            "WHERE p.ID_LOCAL = ? OR p.ID_VISITANTE = ?";

    public static final String HISTORIAL_JUGADOS =
            "SELECT p.ID, el.PAIS_ORIGEN AS LOCAL, ev.PAIS_ORIGEN AS VISITANTE, " +
            "p.GOLES_LOCAL, p.GOLES_VISITANTE, p.FECHA, p.FASE " +
            "FROM PARTIDOS_MUNDIAL p " +
            "JOIN EQUIPOS_MUNDIAL el ON p.ID_LOCAL  = el.ID " +
            "JOIN EQUIPOS_MUNDIAL ev ON p.ID_VISITANTE = ev.ID " +
            "WHERE p.GOLES_LOCAL IS NOT NULL";

    public static final String ACTUALIZAR_RESULTADO =
            "UPDATE PARTIDOS_MUNDIAL SET GOLES_LOCAL = ?, GOLES_VISITANTE = ? WHERE ID = ?";

    public static final String ELIMINAR =
            "DELETE FROM PARTIDOS_MUNDIAL WHERE ID = ?";

    public static final String TODOS_EQUIPOS =
            "SELECT ID, PAIS_ORIGEN FROM EQUIPOS_MUNDIAL ORDER BY ID";

    public static final String PARTIDOS_CON_RESULTADO =
            "SELECT ID_LOCAL, ID_VISITANTE, GOLES_LOCAL, GOLES_VISITANTE " +
            "FROM PARTIDOS_MUNDIAL WHERE GOLES_LOCAL IS NOT NULL";

    // Aplicar estadisticas
    public static final String STATS_VICTORIA =
            "UPDATE EQUIPOS_MUNDIAL SET PJ=PJ+1, PG=PG+1, GF=GF+?, GC=GC+?, PUNTOS=PUNTOS+3 WHERE ID=?";

    public static final String STATS_EMPATE =
            "UPDATE EQUIPOS_MUNDIAL SET PJ=PJ+1, PE=PE+1, GF=GF+?, GC=GC+?, PUNTOS=PUNTOS+1 WHERE ID=?";

    public static final String STATS_DERROTA =
            "UPDATE EQUIPOS_MUNDIAL SET PJ=PJ+1, PP=PP+1, GF=GF+?, GC=GC+? WHERE ID=?";

    // Revertir estadisticas
    public static final String REVERT_VICTORIA =
            "UPDATE EQUIPOS_MUNDIAL SET PJ=PJ-1, PG=PG-1, GF=GF-?, GC=GC-?, PUNTOS=PUNTOS-3 WHERE ID=?";

    public static final String REVERT_EMPATE =
            "UPDATE EQUIPOS_MUNDIAL SET PJ=PJ-1, PE=PE-1, GF=GF-?, GC=GC-?, PUNTOS=PUNTOS-1 WHERE ID=?";

    public static final String REVERT_DERROTA =
            "UPDATE EQUIPOS_MUNDIAL SET PJ=PJ-1, PP=PP-1, GF=GF-?, GC=GC-? WHERE ID=?";
}
