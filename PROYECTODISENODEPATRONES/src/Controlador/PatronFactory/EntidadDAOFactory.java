package Controlador.PatronFactory;

import Controlador.CRUD;
import Modelo.RegistroDAO.ClasificacionResiduosDAO;
import Modelo.RegistroDAO.DireccionDAO;
import Modelo.RegistroDAO.ResiduosDAO;
import Modelo.RegistroDAO.UsuarioDAO;

public class EntidadDAOFactory implements CRUDFactory {
    @Override
    public CRUD crearUsuarioCRUD() {
        return new UsuarioDAO();  // Devuelve una nueva instancia de UsuarioDAO
    }

    @Override
    public CRUD crearResiduoCRUD() {
        return new ResiduosDAO();  // Devuelve una nueva instancia de ResiduosDAO
    }
      @Override
    public CRUD crearDireccionCRUD() {
        return new DireccionDAO(); // Asegurate de tener DireccionDAO implementado
    }

    @Override
    public CRUD crearClasificacionResiduoCRUD() {
        return new ClasificacionResiduosDAO(); // Asegurate de tener ClasificacionResiduosDAO implementado
    }
    
}
