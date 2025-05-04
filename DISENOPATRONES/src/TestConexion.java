
import Modelo.ConexionBD;
import java.sql.Connection;



/**
 *
 * @author felix
 */
public class TestConexion {

    public static void main(String[] args) {

        ConexionBD conexionBD = ConexionBD.getInstancia();

        Connection conexion = conexionBD.getConexion();

        if (conexion != null) {

            System.out.println("¡Conexión obtenida correctamente!");

        } else {

            System.out.println("No se pudo establecer conexión.");

        }

    }
}
