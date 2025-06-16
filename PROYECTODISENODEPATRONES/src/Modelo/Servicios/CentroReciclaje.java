package Modelo.Servicios;
import java.util.Date;

public class CentroReciclaje {
    private int idCentro;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ubicacionGPS;
    private Date fechaRegistro;

    // Constructor
    public CentroReciclaje(String nombre, String direccion, String telefono, String ubicacionGPS) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ubicacionGPS = ubicacionGPS;
        this.fechaRegistro = new Date();  // Establece la fecha de registro automáticamente
    }

    // Getters y Setters
    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUbicacionGPS() {
        return ubicacionGPS;
    }

    public void setUbicacionGPS(String ubicacionGPS) {
        this.ubicacionGPS = ubicacionGPS;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "CentroReciclaje{" +
                "idCentro=" + idCentro +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", ubicacionGPS='" + ubicacionGPS + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
