/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.core.dto;

/**
 *
 * @author mbogota
 */
public class MetodoCobroDTO extends AbstractDTO implements Comparable<MetodoCobroDTO> {

    private long idMetodoCobro;
    private String descripcion;
    private long idEstado;

    public long getIdMetodoCobro() {
        return idMetodoCobro;
    }

    public void setIdMetodoCobro(long idMetodoCobro) {
        this.idMetodoCobro = idMetodoCobro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int compareTo(MetodoCobroDTO o) {
        return descripcion.compareTo(o.descripcion);
    }

}
