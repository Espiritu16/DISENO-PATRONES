package Modelo;

import java.util.Date;

public class Residuos {
    private int idResiduos;
    private String nombre;
    private int idClasificacion;
    private String descripcion;
    private String unidadMedida;
    private String peligrosidad;
    private Date fechaRegistro;

    // Constructor
    
    public Residuos() {
    }

    public Residuos(int idResiduos, String nombre, int idClasificacion, String descripcion, String unidadMedida, String peligrosidad, Date fechaRegistro) {
        this.idResiduos = idResiduos;
        this.nombre = nombre;
        this.idClasificacion = idClasificacion;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.peligrosidad = peligrosidad;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
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

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getPeligrosidad() {
        return peligrosidad;
    }

    public void setPeligrosidad(String peligrosidad) {
        this.peligrosidad = peligrosidad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Residuo{" +
                "idResiduos=" + idResiduos +
                ", nombre='" + nombre + '\'' +
                ", idClasificacion=" + idClasificacion +
                ", descripcion='" + descripcion + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", peligrosidad='" + peligrosidad + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }

}