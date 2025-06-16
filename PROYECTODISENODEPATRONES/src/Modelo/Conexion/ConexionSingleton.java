package Modelo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSingleton {

    // La única instancia de la conexión (Singleton)
    private static Connection singletonConnection = null;

    // URL de conexión a la base de datos SQL Server con los detalles proporcionados
    private static final String CONNECTION_URL = 
        "jdbc:sqlserver://localhost:1433;" +  // Host y puerto
        "databaseName=GestionResiduo;" +      // Nombre de la base de datos
        "user=sa;" +                          // Usuario
        "password=YourStrongPassword123;" +  // Contraseña (ajusta según tu configuración)
        "encrypt=true;trustServerCertificate=true;"; // Opciones de encriptación

    // Bloque estático para cargar el driver una sola vez al cargar la clase
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver JDBC cargado correctamente.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error al cargar el controlador JDBC.");
        }
    }

    // Constructor privado para evitar instanciación externa
    private ConexionSingleton() {
    }

    // Método para obtener la conexión (Patrón Singleton)
    public static Connection getConexion() {
        try {
            if (singletonConnection == null || singletonConnection.isClosed()) {
                singletonConnection = DriverManager.getConnection(CONNECTION_URL);
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la conexión a la base de datos.");
        }
        return singletonConnection;
    }

    // Método para cerrar la conexión cuando se termine el uso
    public static void closeConnection() {
        try {
            if (singletonConnection != null && !singletonConnection.isClosed()) {
                singletonConnection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexión.");
        }
    }
}