package Modelo;

import Modelo.Registro.PrototypeClasificacionResiduo;
import Modelo.Registro.PrototypeDireccion;
import Modelo.Registro.PrototypeResiduo;
import Modelo.Registro.PrototypeUsuario;
import Modelo.SistemaGestionResiduosFacade;
import Controlador.CRUD;
import Controlador.PatronFactory.CRUDFactory;
import Controlador.PatronFactory.EntidadDAOFactory;
import Modelo.*;

public class SistemaGestionResiduosFacadeImpl implements SistemaGestionResiduosFacade {

    private final CRUD direccionDAO;
    private final CRUD usuarioDAO;
    private final CRUD clasificacionDAO;
    private final CRUD residuoDAO;

    public SistemaGestionResiduosFacadeImpl() {
        CRUDFactory factory = new EntidadDAOFactory();
        direccionDAO = factory.crearDireccionCRUD();
        usuarioDAO = factory.crearUsuarioCRUD();
        clasificacionDAO = factory.crearClasificacionResiduoCRUD();
        residuoDAO = factory.crearResiduoCRUD();
    }

    @Override
    public boolean registrarFlujoCompleto(
        PrototypeUsuario usuario,
        PrototypeDireccion direccion,
        PrototypeClasificacionResiduo clasificacion,
        PrototypeResiduo residuo
    ) {
        try {
            int idDireccion = direccionDAO.crear(direccion);
            if (idDireccion <= 0) throw new Exception("Error al insertar Dirección");
            usuario.setIdDireccion(idDireccion);

            int idUsuario = usuarioDAO.crear(usuario);
            if (idUsuario <= 0) throw new Exception("Error al insertar Usuario");

            int idClas = clasificacionDAO.crear(clasificacion);
            if (idClas <= 0) throw new Exception("Error al insertar Clasificación");

            residuo.setIdClasificacion(idClas);
            int idResiduo = residuoDAO.crear(residuo);
            if (idResiduo <= 0) throw new Exception("Error al insertar Residuo");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}