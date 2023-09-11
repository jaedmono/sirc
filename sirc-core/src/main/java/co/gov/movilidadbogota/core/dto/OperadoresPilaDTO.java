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
public class OperadoresPilaDTO extends AbstractDTO implements Comparable<OperadoresPilaDTO> {

    private long idOperador;
    private String codigoOperador;
    private String nombre;
    private String operador;

    public OperadoresPilaDTO() {
    }

    public long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(long idOperador) {
        this.idOperador = idOperador;
    }

    public String getCodigoOperador() {
        return codigoOperador;
    }

    public void setCodigoOperador(String codigoOperador) {
        this.codigoOperador = codigoOperador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    @Override
    public int compareTo(OperadoresPilaDTO o) {
        return nombre.compareTo(o.nombre);
    }

}
