
package Modelo;
import java.sql.Date;

public class PrototypeResiduo implements Cloneable {

    private int idResiduos;
    private String nombre;
    private int idClasificacion;
    private String descripcion;
    private String unidad_medida;
    private String peligrosidad;
    private Date fecha_registro;

    public PrototypeResiduo() {
    }

    
    // Constructor
    public PrototypeResiduo(int idResiduos, String nombre, int idClasificacion, String descripcion, 
                            String unidad_medida, String peligrosidad, Date fecha_registro) {
        this.idResiduos = idResiduos;
        this.nombre = nombre;
        this.idClasificacion = idClasificacion;
        this.descripcion = descripcion;
        this.unidad_medida = unidad_medida;
        this.peligrosidad = peligrosidad;
        this.fecha_registro = fecha_registro;
    }

    // Métodos getter y setter
    public int getIdResiduos() {
        return idResiduos;
    }

    public void setIdResiduos(int idResiduos) {
        this.idResiduos = idResiduos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public String getPeligrosidad() {
        return peligrosidad;
    }

    public void setPeligrosidad(String peligrosidad) {
        this.peligrosidad = peligrosidad;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    // Método para clonar el objeto PrototypeResiduo
    @Override
    public PrototypeResiduo clone() throws CloneNotSupportedException {
        return (PrototypeResiduo) super.clone();
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        return "PrototypeResiduo [idResiduos=" + idResiduos + ", nombre=" + nombre + ", idClasificacion=" + idClasificacion 
                + ", descripcion=" + descripcion + ", unidad_medida=" + unidad_medida + ", peligrosidad=" + peligrosidad 
                + ", fecha_registro=" + fecha_registro + "]";
    }
}