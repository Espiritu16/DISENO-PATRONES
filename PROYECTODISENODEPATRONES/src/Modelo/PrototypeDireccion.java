package Modelo;

import java.sql.Date;

public class PrototypeDireccion implements Cloneable {

    private int idDireccion;
    private String direccion;
    private String ciudad;
    private String distrito;
    private String codigo_postal;
    private String pais;
    private String referencia;
    private Date fecha_creacion;
    private Date fecha_actualizacion;

    public PrototypeDireccion() {
    }

    public PrototypeDireccion(int idDireccion, String direccion, String ciudad, String distrito,
                             String codigo_postal, String pais, String referencia,
                             Date fecha_creacion, Date fecha_actualizacion) {
        this.idDireccion = idDireccion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.distrito = distrito;
        this.codigo_postal = codigo_postal;
        this.pais = pais;
        this.referencia = referencia;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    // Getters y setters
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    // Método para clonar el objeto PrototypeDireccion
    @Override
    public PrototypeDireccion clone() throws CloneNotSupportedException {
        return (PrototypeDireccion) super.clone();
    }

    @Override
    public String toString() {
        return "PrototypeDireccion [idDireccion=" + idDireccion + ", direccion=" + direccion + ", ciudad=" + ciudad
                + ", distrito=" + distrito + ", codigo_postal=" + codigo_postal + ", pais=" + pais + ", referencia="
                + referencia + ", fecha_creacion=" + fecha_creacion + ", fecha_actualizacion=" + fecha_actualizacion + "]";
    }
}