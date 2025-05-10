package Controlador;
import Modelo.Usuario;
import Modelo.UsuarioDAO;
import java.util.Date;

public class UsuarioControlador implements UsuarioFactory {

    private UsuarioDAO usuarioDAO;
    
    public UsuarioControlador() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    public int crearUsuario(Usuario usuario) {
        // Validaciones básicas antes de crear
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
            usuario.getApellido() == null || usuario.getApellido().isEmpty() ||
            usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            return -1; // Código de error para datos inválidos
        }
        
        // Establecer valores por defecto si no están seteados
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(new Date());
        }
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("Usuario");
        }
        if (usuario.getContrasenaHash() == null) {
            // Aquí podrías generar un hash de una contraseña por defecto
            return -2; // Código de error para contraseña faltante
        }
        
        return usuarioDAO.ejecutarCRUD("CREATE", usuario);
    }

    @Override
    public Usuario obtenerUsuario(int idUsuario) {
        // Crear un usuario temporal solo con el ID
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        
        // Ejecutar la operación READ
        int resultado = usuarioDAO.ejecutarCRUD("READ", usuario);
        
        if (resultado > 0) {
            // En una implementación real, aquí obtendrías el usuario completo de la BD
            // Esto es solo un ejemplo - necesitarías modificar tu DAO para devolver objetos Usuario
            return usuario;
        } else {
            return null; // Usuario no encontrado
        }
    }

    @Override
    public int actualizarUsuario(Usuario usuario) {
        // Validar que el usuario exista y tenga ID
        if (usuario.getIdUsuario() <= 0) {
            return -1; // ID inválido
        }
        
        return usuarioDAO.ejecutarCRUD("UPDATE", usuario);
    }

    @Override
    public int eliminarUsuario(int idUsuario) {
        // Crear un usuario temporal solo con el ID
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        
        return usuarioDAO.ejecutarCRUD("DELETE", usuario);
    }
    
    // Métodos adicionales específicos del negocio
    public int desactivarUsuario(int idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            usuario.setActivo(false);
            return actualizarUsuario(usuario);
        }
        return -1; // Usuario no encontrado
    }
    
}