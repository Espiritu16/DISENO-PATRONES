package Modelo;

import java.sql.Date;

public class PrototypeClasificacionResiduo implements Cloneable {
    private int idClasificacion;
    private String nombre;
    private String descripcion;
    private String color_codigo;
    private String icono;
    private Date fecha_creacion;

    public PrototypeClasificacionResiduo() {}

    public PrototypeClasificacionResiduo(int idClasificacion, String nombre, String descripcion,
                                        String color_codigo, String icono, Date fecha_creacion) {
        this.idClasificacion = idClasificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color_codigo = color_codigo;
        this.icono = icono;
        this.fecha_creacion = fecha_creacion;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor_codigo() {
        return color_codigo;
    }

    public void setColor_codigo(String color_codigo) {
        this.color_codigo = color_codigo;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public PrototypeClasificacionResiduo clone() throws CloneNotSupportedException {
        return (PrototypeClasificacionResiduo) super.clone();
    }

    @Override
    public String toString() {
        return "PrototypeClasificacionResiduo [idClasificacion=" + idClasificacion + ", nombre=" + nombre +
               ", descripcion=" + descripcion + ", color_codigo=" + color_codigo + ", icono=" + icono +
               ", fecha_creacion=" + fecha_creacion + "]";
    }
}