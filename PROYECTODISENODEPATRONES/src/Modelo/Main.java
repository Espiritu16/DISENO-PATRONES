package Modelo;

import Modelo.Proxy.LoginM;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de la clase Login
        LoginM login = new LoginM();

        // Datos de ejemplo (correo y contraseña en texto plano)
        String correo = "kevinespiirtu@gmail.com";   // Correo de ejemplo (ajusta según lo que tengas en la base de datos)
        String contrasena = "12345678";  // Contraseña en texto plano de ejemplo

        // Intentar verificar el login
        boolean loginExitoso = login.verificarLogin(correo, contrasena);

        // Mostrar el resultado de la verificación
        if (loginExitoso) {
            System.out.println("Acceso concedido.");
        } else {
            System.out.println("Acceso denegado.");
        }
    }
}