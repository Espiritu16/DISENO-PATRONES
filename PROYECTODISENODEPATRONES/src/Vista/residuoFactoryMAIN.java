package Vista;

import Controlador.CRUD;
import Controlador.CRUDFactory;
import Controlador.EntidadDAOFactory;
import Modelo.PrototypeResiduo;
import java.sql.Date;

public class residuoFactoryMAIN {
  public static void main(String[] args) {
        // Crear una instancia de la fábrica
        CRUDFactory factory = new EntidadDAOFactory();
        
        // Usar la fábrica para obtener una instancia de CRUD (ResiduosDAO)
        CRUD residuosDAO = factory.crearResiduoCRUD();

        // Crear un nuevo residuo
        PrototypeResiduo nuevoResiduo = new PrototypeResiduo(
                0, // idResiduos
                "Plástico", // nombre
                5, // idClasificacion (por ejemplo, clasificación 2)
                "Plástico desechable usado en envases", // descripción
                "kg", // unidad_medida
                "No Peligroso", // peligrosidad
                new Date(System.currentTimeMillis()) // fecha_registro
        );

        // Usar el método crear de ResiduosDAO
        int resultadoResiduo = residuosDAO.crear(nuevoResiduo);
        
        // Imprimir el resultado
        System.out.println("Resultado de creación de residuo: " + resultadoResiduo);
    }
}
