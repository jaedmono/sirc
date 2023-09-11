/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mbogota
 */
@Entity
@Table(name = "SMB_VEHICULO_FACTORCALIDAD")
public class VehiculoFactorCalidadEntity {

    @Id
    @Column(name = "ID_FACTORCALIDAD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FACTOR_CALIDAD")
    @SequenceGenerator(name = "SQ_FACTOR_CALIDAD", sequenceName = "SQ_FACTOR_CALIDAD", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    private EmpresaEntity empresa;

    @Column(name = "NRO_PLACA")
    private String nroPlaca;

    @Column(name = "ID_ESTADO")
    private long idEstado;

    @Column(name = "FECHA_REGISTRO")
    private Date fechaRegistro;

    @Column(name = "FECHA_NOVEDAD")
    private Date fechaNovedad;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UsuarioEntity usuario;

    public VehiculoFactorCalidadEntity() {
    }

    public VehiculoFactorCalidadEntity(long id, EmpresaEntity empresa, String nroPlaca, long idEstado, Date fechaRegistro, Date fechaNovedad, UsuarioEntity usuario) {
        this.id = id;
        this.empresa = empresa;
        this.nroPlaca = nroPlaca;
        this.idEstado = idEstado;
        this.fechaRegistro = fechaRegistro;
        this.fechaNovedad = fechaNovedad;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EmpresaEntity getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public String getNroPlaca() {
        return nroPlaca;
    }

    public void setNroPlaca(String nroPlaca) {
        this.nroPlaca = nroPlaca;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

}
