package Controlador;
public interface CRUDFactory {
    CRUD crearUsuarioCRUD();   // Método para crear UsuarioDAO
    CRUD crearResiduoCRUD();   // Método para crear ResiduosDAO
    
    CRUD crearDireccionCRUD();
    CRUD crearClasificacionResiduoCRUD();
}
