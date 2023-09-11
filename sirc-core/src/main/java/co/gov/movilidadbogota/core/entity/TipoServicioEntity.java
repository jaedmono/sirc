/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mbogota
 */
@Entity
@Table(name = "SMB_TIPO_SERVICIO")
public class TipoServicioEntity {

    @Id
    @Column(name = "ID_TIPOSERVICIO")
    private long id;

    @Column(name = "DESCRIPCION_TIPOSERVICIO")
    private String descripcion;

    @Column(name = "ID_ESTADO")
    private long idEstado;

    public TipoServicioEntity() {
    }

    public TipoServicioEntity(long id, String descripcion, long idEstado) {
        super();
        this.id = id;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
