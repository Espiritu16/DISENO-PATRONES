package Modelo;
import Modelo.Conexion.ConexionSingleton;
import java.sql.*;

public class LoginM {

    // Método para verificar el login del usuario con correo y contraseña en texto plano
    public boolean verificarLogin(String correo, String contrasena) {
        String query = "SELECT contrasena_hash, activo FROM Usuario WHERE correo = ?";  // Usamos correo para la validación

        try {
            // Obtener la conexión a la base de datos
            Connection conn = ConexionSingleton.getConexion();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            // Establecer el valor del parámetro (correo del usuario)
            stmt.setString(1, correo);  

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Verificar si el correo existe
            if (rs.next()) {
                // Recuperar la contraseña almacenada en la base de datos
                String contrasenaAlmacenada = rs.getString("contrasena_hash");
                boolean estaActivo = rs.getBoolean("activo");

                // Verificar si el usuario está activo
                if (!estaActivo) {
                    System.out.println("El usuario está desactivado.");
                    return false;
                }

                // Comparar la contraseña ingresada con la almacenada
                if (contrasena.equals(contrasenaAlmacenada)) {
                    // Login exitoso
                    System.out.println("¡Login exitoso! Bienvenido al sistema.");
                    return true;
                } else {
                    // Contraseña incorrecta
                    System.out.println("Login fallido. Verifica tu correo electrónico o contraseña.");
                    return false;
                }
            } else {
                // Usuario no encontrado
                System.out.println("El usuario con ese correo no existe.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al verificar el login.");
            return false;  // Error en la consulta
        }
    }

}