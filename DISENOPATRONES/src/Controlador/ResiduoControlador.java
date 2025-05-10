package Controlador;
import Modelo.ResiduoDAO;
import Modelo.Residuos;
import java.util.Date;

public class ResiduoControlador implements ResiduoFactory {

    private ResiduoDAO residuoDAO;

    public ResiduoControlador() {
        this.residuoDAO = new ResiduoDAO();
    }

    @Override
    public int crearResiduo(Residuos residuo) {
        // Validaciones básicas antes de crear
        if (residuo.getNombre() == null || residuo.getNombre().isEmpty() ||
            residuo.getDescripcion() == null || residuo.getDescripcion().isEmpty()) {
            return -1; // Código de error para datos inválidos
        }

        // Establecer valores por defecto si no están seteados
        if (residuo.getFechaRegistro() == null) {
            residuo.setFechaRegistro(new Date());
        }
        if (residuo.getUnidadMedida() == null || residuo.getUnidadMedida().isEmpty()) {
            residuo.setUnidadMedida("kg");
        }
        if (residuo.getPeligrosidad() == null || residuo.getPeligrosidad().isEmpty()) {
            residuo.setPeligrosidad("Bajo");
        }

        return residuoDAO.ejecutarCRUD("CREATE", residuo);
    }

    @Override
    public Residuos obtenerResiduo(int idResiduo) {
        // Crear un residuo temporal solo con el ID
        Residuos residuo = new Residuos();
        residuo.setIdResiduos(idResiduo);
        
        // Ejecutar la operación READ
        int resultado = residuoDAO.ejecutarCRUD("READ", residuo);
        
        if (resultado > 0) {
            // En una implementación real, aquí obtendrás el residuo completo de la BD
            // Necesitarías modificar tu DAO para devolver objetos Residuo completos
            return residuo;
        } else {
            return null; // Residuo no encontrado
        }
    }

    @Override
    public int actualizarResiduo(Residuos residuo) {
        // Validar que el residuo exista y tenga ID
        if (residuo.getIdResiduos() <= 0) {
            return -1; // ID inválido
        }

        return residuoDAO.ejecutarCRUD("UPDATE", residuo);
    }

    @Override
    public int eliminarResiduo(int idResiduo) {
        // Crear un residuo temporal solo con el ID
        Residuos residuo = new Residuos();
        residuo.setIdResiduos(idResiduo);
        
        return residuoDAO.ejecutarCRUD("DELETE", residuo);
    }
    
    // Métodos adicionales específicos del negocio
    public int cambiarClasificacionResiduo(int idResiduo, int nuevaClasificacion) {
        Residuos residuo = obtenerResiduo(idResiduo);
        if (residuo != null) {
            residuo.setIdClasificacion(nuevaClasificacion);
            return actualizarResiduo(residuo);
        }
        return -1; // Residuo no encontrado
    }
    
    public int desactivarResiduo(int idResiduo) {
        Residuos residuo = obtenerResiduo(idResiduo);
        if (residuo != null) {
            residuo.setPeligrosidad("Bajo");
            return actualizarResiduo(residuo);
        }
        return -1; // Residuo no encontrado
    }
}

