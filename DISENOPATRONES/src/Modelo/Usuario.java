/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author felix
 */
public class Usuario implements Cloneable {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String contrasenaHash;
    private int idDireccion;
    private Date fechaRegistro;
    private Date ultimoAcceso;
    private boolean activo;
    private String rol;

    public Usuario() {
        this.activo = true;
        this.rol = "Usuario";
        this.fechaRegistro = new Date(); // Fecha actual por defecto
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correo, 
                  String telefono, String contrasenaHash, int idDireccion, 
                  Date fechaRegistro, Date ultimoAcceso, boolean activo, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasenaHash = contrasenaHash;
        this.idDireccion = idDireccion;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : new Date();
        this.ultimoAcceso = ultimoAcceso;
        this.activo = activo;
        this.rol = rol != null ? rol : "Usuario";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }
    
    public int getIdDireccion() { return idDireccion; }
    public void setIdDireccion(int idDireccion) { this.idDireccion = idDireccion; }
    
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Date getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(Date ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    
    @Override
    public String toString() {
    return "ID: " + idUsuario + " | Nombre: " + nombre + " " + apellido + " | Teléfono: " + telefono;
    }
    
}

