/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.dto;

import co.gov.movilidadbogota.esb.schema.conductor.v1.Conductor;
import java.util.Date;

/**
 *
 * @author mbogota
 */
public class TarjetaControlDTO {

    private long idVehiculo = 0;
    private long idConductor;
    private Date fechaValidez;
    private Date fechaVigencia;
    private String nroTarjetaControl;
    private long factorCalidad;
    private long idPersona;
    private Conductor conductor;
    private long idPagoPila;
    private Date fechaFinVigenciaPagoPila;
    private long idEmpresa;

    public long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(long idConductor) {
        this.idConductor = idConductor;
    }

    public Date getFechaValidez() {
        return fechaValidez;
    }

    public void setFechaValidez(Date fechaValidez) {
        this.fechaValidez = fechaValidez;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public String getNroTarjetaControl() {
        return nroTarjetaControl;
    }

    public void setNroTarjetaControl(String nroTarjetaControl) {
        this.nroTarjetaControl = nroTarjetaControl;
    }

    public long getFactorCalidad() {
        return factorCalidad;
    }

    public void setFactorCalidad(long factorCalidad) {
        this.factorCalidad = factorCalidad;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public long getIdPagoPila() {
        return idPagoPila;
    }

    public void setIdPagoPila(long idPagoPila) {
        this.idPagoPila = idPagoPila;
    }

    public Date getFechaFinVigenciaPagoPila() {
        return fechaFinVigenciaPagoPila;
    }

    public void setFechaFinVigenciaPagoPila(Date fechaFinVigenciaPagoPila) {
        this.fechaFinVigenciaPagoPila = fechaFinVigenciaPagoPila;
    }

    public long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
