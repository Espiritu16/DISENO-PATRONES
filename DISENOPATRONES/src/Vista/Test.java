package Vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Modelo.Usuario;
import Modelo.UsuarioDAO;

public class Test {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            // Crear usuario base
            Usuario usuarioBase = new Usuario(
                0,                          // idUsuario
                "Admin",                   // nombre
                "Sistema",                 // apellido
                "admin@sistema.com",       // correo
                "1234567890",              // telefono
                "hashedpassword123",       // contrasenaHash
                1,                         // idDireccion (¡asegúrate de que exista en SQL!)
                null,                      // fechaRegistro (auto)
                null,                      // ultimoAcceso
                true,                      // activo
                "Administrador"            // rol
            );

            System.out.println("Usuario base creado: " + usuarioBase);

            // Clonar para crear uno nuevo
            Usuario usuarioNuevo = (Usuario) usuarioBase.clone();
            usuarioNuevo.setCorreo("nuevo.admin@sistema.com");

            int nuevoId = dao.ejecutarCRUD("CREATE", usuarioNuevo);
            System.out.println("Nuevo usuario creado con ID: " + nuevoId);

            // Clonar y actualizar
            Usuario usuarioEditado = (Usuario) usuarioNuevo.clone();
            usuarioEditado.setIdUsuario(nuevoId);
            usuarioEditado.setNombre("Admin Actualizado");
            usuarioEditado.setTelefono("9876543210");

            dao.ejecutarCRUD("UPDATE", usuarioEditado);
            System.out.println("Usuario actualizado con éxito.");

            // Leer usuario actualizado
            Usuario usuarioLeer = new Usuario();
            usuarioLeer.setIdUsuario(nuevoId);
            dao.ejecutarCRUD("READ", usuarioLeer);

            // Eliminar usuario
            int eliminado = dao.ejecutarCRUD("DELETE", usuarioLeer);
            System.out.println("Usuario eliminado: " + (eliminado > 0 ? "Éxito" : "Falló"));

        } catch (CloneNotSupportedException e) {
            System.err.println("Error al clonar usuario:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general en la prueba:");
            e.printStackTrace();
        }
    }
}