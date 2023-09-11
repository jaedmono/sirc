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
public class DispositivoMovilDTO {

    private long id;
    private long idVehiculo;
    private String macIdUno;
    private String macIdDos;
    private String imeiUno;
    private String imeiDos;
    private String imeiTres;
    private String imeiCuatro;

    public DispositivoMovilDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(long idVehiculo) {
        this.idVehiculo = idVehiculo;
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
