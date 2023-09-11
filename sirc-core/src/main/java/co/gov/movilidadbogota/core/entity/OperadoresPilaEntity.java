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
@Table(name = "SMB_OPERADORES_PILA")
public class OperadoresPilaEntity {

    @Id
    @Column(name = "ID_OPERADOR")
    private long id;

    @Column(name = "CODIGO_OPERADOR")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "OPERADOR")
    private String operador;

    public OperadoresPilaEntity() {
    }

    public OperadoresPilaEntity(long id, String codigo, String nombre, String operador) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.operador = operador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

}
