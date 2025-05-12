
package Vista;

import java.sql.Connection;  // Importa la clase Connection adecuada
import Modelo.ConexionSingleton;  // Asegúrate de que esta ruta sea correcta

public class TestConexion {
    public static void main(String[] args) {
        // Intentar obtener la conexión usando el patrón Singleton
        Connection connection = ConexionSingleton.getConexion();

        // Verificar si la conexión fue exitosa
        if (connection != null) {
            System.out.println("¡Conexión exitosa a la base de datos!");
        } else {
            System.out.println("No se pudo establecer la conexión.");
        }

        // Cerrar la conexión (opcional, ya que el Singleton gestiona la conexión)
        ConexionSingleton.closeConnection();
    }
}