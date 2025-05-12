
package Vista;
import Modelo.PrototypeResiduo;
import Modelo.ResiduosDAO;
import java.sql.Date;

public class ResiduoVistaConsola {

    public static void main(String[] args) {
        // Valores de ejemplo para los campos del residuo
        String nombre = "Plástico PET";
        int idClasificacion = 1;  // Asumimos que esta clasificación ya existe en la base de datos
        String descripcion = "Residuos plásticos PET reciclables";
        String unidadMedida = "Kilogramos";
        String peligrosidad = "Bajo";

        // Crear un objeto PrototypeResiduo con los datos de ejemplo
        PrototypeResiduo nuevoResiduo = new PrototypeResiduo(
                0, nombre, idClasificacion, descripcion, unidadMedida, peligrosidad, new Date(System.currentTimeMillis())
        );

        // Crear un objeto ResiduosDAO
        ResiduosDAO residuosDAO = new ResiduosDAO();

        // Llamar al método para insertar el residuo en la base de datos
        int resultado = residuosDAO.crear(nuevoResiduo);

        // Verificar el resultado de la inserción
        if (resultado > 0) {
            System.out.println("Residuo insertado con éxito. ID del nuevo residuo: " + resultado);
        } else {
            System.out.println("Error al insertar el residuo.");
        }
    }
}