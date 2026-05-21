import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class ConexionBD {

    private final String servidor;
    private final String baseDatos;
    private final String usuario;
    private final String contrasena;
    private Connection conexion;

    public ConexionBD() {
        Dotenv dotenv = Dotenv.load();
        this.servidor  = dotenv.get("DB_SERVER");
        this.baseDatos = dotenv.get("DB_NAME");
        this.usuario   = dotenv.get("DB_USER");
        this.contrasena = dotenv.get("DB_PASSWORD");
    }

    public ConexionBD(String servidor, String baseDatos, String usuario, String contrasena) {
        this.servidor   = servidor;
        this.baseDatos  = baseDatos;
        this.usuario    = usuario;
        this.contrasena = contrasena;
    }

    public boolean conectar() {
        String url = "jdbc:sqlserver://" + servidor
                + ";databaseName=" + baseDatos
                + ";encrypt=true;trustServerCertificate=true";
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexion exitosa a la base de datos: " + baseDatos);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return false;
        }
    }

    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexion: " + e.getMessage());
        }
    }

    public ResultSet consultar(String sql, Object... params) {
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            setParams(ps, params);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error en consulta: " + e.getMessage());
            return null;
        }
    }

    // Devuelve la cantidad de filas insertadas (1 si tuvo exito, -1 si fallo).
    public int insertar(String sql, Object... params) {
        return ejecutarActualizacion(sql, params);
    }

    // Devuelve la cantidad de filas modificadas (-1 si fallo).
    public int modificar(String sql, Object... params) {
        return ejecutarActualizacion(sql, params);
    }

    // Devuelve la cantidad de filas eliminadas (-1 si fallo).
    public int eliminar(String sql, Object... params) {
        return ejecutarActualizacion(sql, params);
    }

    private int ejecutarActualizacion(String sql, Object... params) {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            setParams(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en operacion: " + e.getMessage());
            return -1;
        }
    }

    private void setParams(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}
