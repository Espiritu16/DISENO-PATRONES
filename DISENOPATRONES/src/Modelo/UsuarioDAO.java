/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UsuarioDAO {

     public int ejecutarCRUD(String operacion, Usuario usuario) {
        int resultado = -1;

        try (Connection con = ConexionBD.getSingleton().getConexion();
             // ✅ Cambiado a 9 parámetros (agregamos un '?' adicional para el parámetro OUTPUT)
             CallableStatement stmt = con.prepareCall("{call sp_crud_usuario_simple(?,?,?,?,?,?,?,?,?)}")) {

            // Parámetros de entrada
            stmt.setString(1, operacion);
            stmt.setObject(2, (operacion.equals("CREATE")) ? null : usuario.getIdUsuario());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getContrasenaHash());
            stmt.setObject(8, usuario.getIdDireccion());

            // ✅ Parámetro de salida: @resultado
            stmt.registerOutParameter(9, Types.INTEGER);

            // Ejecutar el procedimiento
            stmt.execute();

            // Leer el valor de retorno (por ejemplo, ID generado o código de resultado)
            resultado = stmt.getInt(9);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
     
}

