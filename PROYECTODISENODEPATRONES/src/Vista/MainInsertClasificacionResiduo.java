package Vista;
import Controlador.CRUD;
import Controlador.EntidadDAOFactory;
import Controlador.CRUDFactory;
import Modelo.ConexionSingleton;
import Modelo.PrototypeClasificacionResiduo;
import Modelo.PrototypeResiduo;


public class MainInsertClasificacionResiduo {
    public static void main(String[] args) {
        // Crear la fábrica y los DAOs
        CRUDFactory factory = new EntidadDAOFactory();
        CRUD clasificacionDAO = factory.crearClasificacionResiduoCRUD();
        CRUD residuosDAO = factory.crearResiduoCRUD();

        // Crear objeto con datos inicializados para Clasificación
        PrototypeClasificacionResiduo clasificacion = new PrototypeClasificacionResiduo();
        clasificacion.setNombre("Residuos Orgánicos");
        clasificacion.setDescripcion("Residuos biodegradables como restos de comida");
        clasificacion.setColor_codigo("#228B22");  // Verde bosque
        clasificacion.setIcono("icono-organico");

        // Insertar la clasificación
        int idClasificacion = clasificacionDAO.crear(clasificacion);

        if (idClasificacion > 0) {
            System.out.println("¡Clasificación creada con ID: " + idClasificacion);

            // Crear residuo asociado a la clasificación creada
            PrototypeResiduo residuo = new PrototypeResiduo();
            residuo.setNombre("Restos de comida");
            residuo.setIdClasificacion(idClasificacion);
            residuo.setDescripcion("Sobras de alimentos y residuos de cocina");
            residuo.setUnidad_medida("Kilogramos");
            residuo.setPeligrosidad("Baja");

            // Insertar residuo
            int idResiduo = residuosDAO.crear(residuo);

            if (idResiduo > 0) {
                System.out.println("Residuo creado con ID: " + idResiduo);
            } else {
                System.out.println("Error al crear el residuo.");
            }
        } else {
            System.out.println("Error al crear la clasificación.");
        }

        // Cerrar la conexión
        ConexionSingleton.closeConnection();
    }
}