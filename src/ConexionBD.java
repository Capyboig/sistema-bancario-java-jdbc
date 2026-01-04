
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/banco_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection conectar() {
    Connection conexion = null;

    try {
        conexion = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("[EXISTO] Conexión establecida");
    } catch (SQLException e) {
        System.out.println("[ERROR] No se pudo conectar ¿La base de datos esta encendida?");
        e.printStackTrace();
    }

    return conexion;
    }
}
