package Vista;

import Controlador.*;
import Modelo.*;

public class MainConDAO {
    public static void main(String[] args) {
        // Crear instancias de las fábricas y DAOs
        CRUDFactory factory = new EntidadDAOFactory();
        CRUD direccionDAO = factory.crearDireccionCRUD();
        CRUD usuarioDAO = factory.crearUsuarioCRUD();

        // Crear e inicializar una dirección
        PrototypeDireccion direccion = new PrototypeDireccion();
        direccion.setDireccion("Av. Los Rosales 456");
        direccion.setCiudad("Lima");
        direccion.setDistrito("Miraflores");
        direccion.setCodigo_postal("15074");
        direccion.setPais("Perú");
        direccion.setReferencia("Frente al parque Kennedy");

        // Insertar la dirección en la BD
        int idDireccion = direccionDAO.crear(direccion);
        System.out.println("Dirección creada con ID: " + idDireccion);

        // Verificar que se insertó correctamente antes de crear el usuario
        if (idDireccion > 0) {
            // Crear e inicializar un usuario
            PrototypeUsuario usuario = new PrototypeUsuario();
            usuario.setNombre("Lisa");
            usuario.setApellido("Simpson");
            usuario.setCorreo("lisa.simpson@example.com");
            usuario.setTelefono("999-888-777");
            usuario.setContrasena_hash("segura123"); // ⚠️ Idealmente usar hash real
            usuario.setIdDireccion(idDireccion); // Relación con dirección

            // Insertar el usuario en la BD
            int idUsuario = usuarioDAO.crear(usuario);
            System.out.println("Usuario creado con ID: " + idUsuario);
            
            //si o si colocar esto al finalizar el codigo por completo
            ConexionSingleton.closeConnection();
        } else {
            System.out.println("No se pudo crear el usuario porque falló la inserción de la dirección.");
        }
        
        
    }
}