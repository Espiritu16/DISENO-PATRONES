package Modelo.RegistroDAO;

import Modelo.Registro.PrototypeUsuario;
import Modelo.Conexion.ConexionSingleton;
import Controlador.CRUD;
import java.sql.*;
import javax.swing.JOptionPane;

public class UsuarioDAO implements CRUD {

   @Override
    public int crear(Object objeto) {
        if (objeto instanceof PrototypeUsuario) {
            PrototypeUsuario usuario = (PrototypeUsuario) objeto;

            String sql = "{CALL sp_crud_usuario_simple(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            int resultado = -1;

            try {
                Connection connection = ConexionSingleton.getConexion(); // NO SE CIERRA aquí
                CallableStatement stmt = connection.prepareCall(sql);

              
                stmt.setString(1, "CREATE");
                stmt.setNull(2, Types.INTEGER);
                stmt.setString(3, usuario.getNombre());
                stmt.setString(4, usuario.getApellido());
                stmt.setString(5, usuario.getCorreo());
                stmt.setString(6, usuario.getTelefono());
                stmt.setString(7, usuario.getContrasena_hash());
                stmt.setInt(8, usuario.getIdDireccion());

                stmt.registerOutParameter(9, Types.INTEGER);

                stmt.execute();
                resultado = stmt.getInt(9);

                stmt.close(); // Cerramos solo el statement

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al insertar usuario.");
            }

            return resultado;
        } else {
            System.out.println("El objeto no es del tipo esperado (PrototypeUsuario)");
            return -1;
        }
    }

    @Override
    public void leer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
   
}