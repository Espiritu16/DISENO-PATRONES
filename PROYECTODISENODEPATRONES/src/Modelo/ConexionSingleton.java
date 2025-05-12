
package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSingleton {

    // La única instancia de la conexión (Singleton)
    private static Connection singletonConnection = null;

    // URL de conexión a la base de datos SQL Server con los detalles proporcionados
    private static final String CONNECTION_URL = 
        "jdbc:sqlserver://localhost:1433;" +  // Host y puerto
        "databaseName=GestionResiduo;" +      // Nombre de la base de datos (ajústalo si es diferente)
        "user=sa;" +                         // Usuario
        "password=YourStrongPassword123;" +  // Contraseña
        "encrypt=true;trustServerCertificate=true;"; // Opciones de encriptación

    // Constructor privado para evitar la creación de objetos fuera de la clase
    private ConexionSingleton() {
    }

    // Método para obtener la conexión (Patrón Singleton)
    public static Connection getConexion() {
        if (singletonConnection == null) {
            try {
                // Cargar el driver de SQL Server
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                // Establecer la conexión
                singletonConnection = DriverManager.getConnection(CONNECTION_URL);
                System.out.println("Conexión exitosa a la base de datos.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error al cargar el controlador JDBC.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error en la conexión a la base de datos.");
            }
        }
        return singletonConnection;
    }

    // Método para cerrar la conexión
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