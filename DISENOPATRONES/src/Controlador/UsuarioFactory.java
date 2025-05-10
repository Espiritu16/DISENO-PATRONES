package Controlador;

import Modelo.Usuario;

public interface UsuarioFactory {
    int crearUsuario(Usuario usuario);
    Usuario obtenerUsuario(int idUsuario);
    int actualizarUsuario(Usuario usuario);
    int eliminarUsuario(int idUsuario);
}
