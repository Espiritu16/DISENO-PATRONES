/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static ConexionBD instancia;
    private Connection conexion;
    
    // Configuración para SQL Server con SistemaResiduos
    private final String url = "jdbc:sqlserver://localhost:1433;"
                             + "databaseName=SResiduos;"
                             + "encrypt=true;"
                             + "trustServerCertificate=true;"
                             + "loginTimeout=30;";
    
    private final String usuario = "sa"; // Usuario de SQL Server
    private final String clave = "root"; // Cambiar por tu contraseña
    
    private ConexionBD() {
        try {
            // Registrar el driver de SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, clave);
            conexion.setAutoCommit(false); // Manejar transacciones manualmente
            
            System.out.println("Conexión exitosa a SQL Server - SistemaResiduos");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC para SQL Server");
            System.err.println("Asegúrate de incluir el archivo mssql-jdbc.jar en tu classpath");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            System.err.println("URL: " + url);
            System.err.println("Usuario: " + usuario);
            e.printStackTrace();
        }
    }
    
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                }
            }
        }
        return instancia;
    }
    
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Reconectar si la conexión está cerrada
                conexion = DriverManager.getConnection(url, usuario, clave);
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar/conectar: " + e.getMessage());
        }
        return conexion;
    }
    
    // Métodos adicionales para manejo de transacciones
    public void commit() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.commit();
            }
        } catch (SQLException e) {
            System.err.println("Error al hacer commit: " + e.getMessage());
        }
    }
    
    public void rollback() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Error al hacer rollback: " + e.getMessage());
        }
    }
    
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
