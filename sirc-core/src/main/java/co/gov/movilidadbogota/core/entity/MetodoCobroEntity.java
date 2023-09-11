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
@Table(name = "SMB_METODO_COBRO")
public class MetodoCobroEntity {

    @Id
    @Column(name = "ID_METODOCOBRO")
    private long id;

    @Column(name = "DESCRIPCION_METCOBRO")
    private String descripcion;

    @Column(name = "ID_ESTADO")
    private long idEstado;

    public MetodoCobroEntity() {
    }

    public MetodoCobroEntity(long id, String descripcion, long idEstado) {
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
