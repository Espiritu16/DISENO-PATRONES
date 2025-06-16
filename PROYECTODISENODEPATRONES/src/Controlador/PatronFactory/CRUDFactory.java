package Controlador.PatronFactory;

import Controlador.CRUD;

public interface CRUDFactory {
    CRUD crearUsuarioCRUD();   // Método para crear UsuarioDAO
    CRUD crearResiduoCRUD();   // Método para crear ResiduosDAO
    CRUD crearDireccionCRUD(); // Método para crear DireccionDAO
    CRUD crearClasificacionResiduoCRUD(); // Método para crear ClasificacionDAO
}
