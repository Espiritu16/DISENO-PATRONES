
package Modelo.RegistroPrototype;
import java.sql.Date;

public class PrototypeUsuario implements Cloneable {
    
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String contrasena_hash;
    private int idDireccion;
    private Date fecha_registro;
    private Date ultimo_acceso;
    private boolean activo;
    private String rol;

    public PrototypeUsuario() {
    }

    // Constructor
    public PrototypeUsuario(int idUsuario, String nombre, String apellido, String correo, 
                            String telefono, String contrasena_hash, int idDireccion, 
                            Date fecha_registro, Date ultimo_acceso, boolean activo, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena_hash = contrasena_hash;
        this.idDireccion = idDireccion;
        this.fecha_registro = fecha_registro;
        this.ultimo_acceso = ultimo_acceso;
        this.activo = activo;
        this.rol = rol;
    }

    // Métodos getter y setter
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena_hash() {
        return contrasena_hash;
    }

    public void setContrasena_hash(String contrasena_hash) {
        this.contrasena_hash = contrasena_hash;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getUltimo_acceso() {
        return ultimo_acceso;
    }

    public void setUltimo_acceso(Date ultimo_acceso) {
        this.ultimo_acceso = ultimo_acceso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Método para clonar el objeto PrototypeUsuario
    @Override
    public PrototypeUsuario clone() throws CloneNotSupportedException {
        return (PrototypeUsuario) super.clone();
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        return "PrototypeUsuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo
                + ", telefono=" + telefono + ", contrasena_hash=" + contrasena_hash + ", idDireccion=" + idDireccion
                + ", fecha_registro=" + fecha_registro + ", ultimo_acceso=" + ultimo_acceso + ", activo=" + activo
                + ", rol=" + rol + "]";
    }
}