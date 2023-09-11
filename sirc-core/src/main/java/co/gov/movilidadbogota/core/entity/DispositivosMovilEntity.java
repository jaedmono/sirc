/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "SMB_DISPOSITIVO_MOVIL_VEHICULO")
public class DispositivosMovilEntity {

    @Id
    @Column(name = "ID_DISPOSITIVO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DISPOSITIVO_MOVIL_VEHICULO")
    @SequenceGenerator(name = "SQ_DISPOSITIVO_MOVIL_VEHICULO", sequenceName = "SQ_DISPOSITIVO_MOVIL_VEHICULO", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_VEHICULO")
    private ConductorVehiculoEntity vehiculo;

    @Column(name = "MACID_UNO")
    private String macIdUno;

    @Column(name = "MACID_DOS")
    private String macIdDos;

    @Column(name = "IMEI_UNO")
    private String imeiUno;

    @Column(name = "IMEI_DOS")
    private String imeiDos;

    @Column(name = "IMEI_TRES")
    private String imeiTres;

    @Column(name = "IMEI_CUATRO")
    private String imeiCuatro;

    public DispositivosMovilEntity() {
    }

    public DispositivosMovilEntity(long id, ConductorVehiculoEntity vehiculo, String macIdUno, String macIdDos, String imeiUno, String imeiDos, String imeiTres, String imeiCuatro) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.macIdUno = macIdUno;
        this.macIdDos = macIdDos;
        this.imeiUno = imeiUno;
        this.imeiDos = imeiDos;
        this.imeiTres = imeiTres;
        this.imeiCuatro = imeiCuatro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ConductorVehiculoEntity getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(ConductorVehiculoEntity vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getMacIdUno() {
        return macIdUno;
    }

    public void setMacIdUno(String macIdUno) {
        this.macIdUno = macIdUno;
    }

    public String getMacIdDos() {
        return macIdDos;
    }

    public void setMacIdDos(String macIdDos) {
        this.macIdDos = macIdDos;
    }

    public String getImeiUno() {
        return imeiUno;
    }

    public void setImeiUno(String imeiUno) {
        this.imeiUno = imeiUno;
    }

    public String getImeiDos() {
        return imeiDos;
    }

    public void setImeiDos(String imeiDos) {
        this.imeiDos = imeiDos;
    }

    public String getImeiTres() {
        return imeiTres;
    }

    public void setImeiTres(String imeiTres) {
        this.imeiTres = imeiTres;
    }

    public String getImeiCuatro() {
        return imeiCuatro;
    }

    public void setImeiCuatro(String imeiCuatro) {
        this.imeiCuatro = imeiCuatro;
    }

}
