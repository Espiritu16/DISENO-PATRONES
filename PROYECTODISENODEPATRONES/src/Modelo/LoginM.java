package Modelo;
import Modelo.Conexion.ConexionSingleton;
import java.sql.*;
import java.time.LocalDateTime; // Necesario para obtener la fecha y hora actual en Java 8+
import java.time.format.DateTimeFormatter; // Para formatear la fecha y hora si es necesario

public class LoginM {

    public boolean verificarLogin(String correo, String contrasena) {
        // Consulta para obtener el hash de la contraseña y el estado activo del usuario
        String selectQuery = "SELECT contrasena_hash, activo FROM Usuario WHERE correo = ?";
        // Consulta para actualizar la fecha del último acceso
        String updateQuery = "UPDATE Usuario SET ultimo_acceso = GETDATE() WHERE correo = ?"; // GETDATE() para SQL Server

        try {
            Connection conn = ConexionSingleton.getConexion(); // Obtener la conexión

            // --- PASO 1: VERIFICAR CREDENCIALES ---
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, correo);
                try (ResultSet rs = selectStmt.executeQuery()) {

                    if (rs.next()) {
                        String contrasenaAlmacenada = rs.getString("contrasena_hash");
                        boolean estaActivo = rs.getBoolean("activo");

                        if (!estaActivo) {
                            System.out.println("El usuario está desactivado.");
                            return false;
                        }

                        // ¡ATENCIÓN! ESTA ES LA COMPARACIÓN QUE DEBE MEJORARSE CON HASHING SEGURO
                        // Por ahora, usamos la comparación directa como está en tu código.
                        // RECUERDA: DEBERÍAS HASHEAR 'contrasena' Y COMPARAR CON 'contrasenaAlmacenada' (si es un hash real).
                        if (contrasena.equals(contrasenaAlmacenada)) {
                            // --- PASO 2: ACTUALIZAR ULTIMO_ACCESO SI EL LOGIN ES EXITOSO ---
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setString(1, correo);
                                int rowsAffected = updateStmt.executeUpdate();

                                if (rowsAffected > 0) {
                                    System.out.println("Fecha de último acceso actualizada para " + correo);
                                } else {
                                    System.out.println("No se pudo actualizar la fecha de último acceso.");
                                }
                            }

                            System.out.println("¡Login exitoso! Bienvenido al sistema.");
                            return true; // Login exitoso y último_acceso actualizado
                        } else {
                            System.out.println("Login fallido. Verifica tu correo electrónico o contraseña.");
                            return false; // Contraseña incorrecta
                        }
                    } else {
                        System.out.println("El usuario con ese correo no existe.");
                        return false; // Usuario no encontrado
                    }
                }
            } // selectStmt y rs se cierran automáticamente aquí por try-with-resources

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al verificar el login o actualizar el último acceso.");
            return false; // Error en la consulta o conexión
        }
    }
}