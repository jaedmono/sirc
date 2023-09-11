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
public class TipoServicioDTO extends AbstractDTO implements Comparable<TipoServicioDTO> {

    private long idTipoServicio;
    private String descripcion;
    private long idEstado;

    public long getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(long idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
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
    public int compareTo(TipoServicioDTO o) {
        return descripcion.compareTo(o.descripcion);
    }

}
