package Controlador;

import Modelo.ResiduosDAO;
import Modelo.UsuarioDAO;

public class EntidadDAOFactory implements CRUDFactory {
    @Override
    public CRUD crearUsuarioCRUD() {
        return new UsuarioDAO();  // Devuelve una nueva instancia de UsuarioDAO
    }

    @Override
    public CRUD crearResiduoCRUD() {
        return new ResiduosDAO();  // Devuelve una nueva instancia de ResiduosDAO
    }
    
}
