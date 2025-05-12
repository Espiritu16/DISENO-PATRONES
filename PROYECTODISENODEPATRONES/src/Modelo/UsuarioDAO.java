
package Modelo;
import Controlador.CRUD;
import java.sql.*;

public class UsuarioDAO implements CRUD {

    // Método para crear un nuevo usuario
    @Override
    public int crear(Object objeto) {
       // Asegurarse de que el objeto es del tipo esperado (PrototypeUsuario)
    if (objeto instanceof PrototypeUsuario) {
        PrototypeUsuario usuario = (PrototypeUsuario) objeto;  // Convertir el objeto a PrototypeUsuario
        
        // Ahora, todo el código de inserción que tenías en insertarUsuario se coloca aquí directamente
        String sql = "{CALL sp_crud_usuario_simple(?, ?, ?, ?, ?, ?, ?, ?, ?)}";  // Llamada al procedimiento almacenado
        int resultado = -1;

        try (Connection connection = ConexionSingleton.getConexion();
             CallableStatement stmt = connection.prepareCall(sql)) {

            // Establecer los parámetros para el procedimiento almacenado
            stmt.setString(1, "CREATE");  // Opción 'CREATE' para insertar un nuevo usuario
            stmt.setNull(2, Types.INTEGER); // El parámetro idUsuario se maneja como auto-generado por la base de datos
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getContrasena_hash());
            stmt.setInt(8, usuario.getIdDireccion());  // El parámetro idDireccion (clave foránea)
            
            stmt.registerOutParameter(9, Types.INTEGER);  // Parámetro de salida para el resultado

            // Ejecutar el procedimiento almacenado
            stmt.execute();

            // Obtener el resultado de la operación
            resultado = stmt.getInt(9);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al insertar usuario.");
        }
        
        // Retornar el resultado de la operación
        return resultado;
    } else {
        System.out.println("El objeto no es del tipo esperado (PrototypeUsuario)");
        return -1;  // Retornar un valor de error si el tipo no es el esperado
    }
    }

    @Override
    public void leer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}