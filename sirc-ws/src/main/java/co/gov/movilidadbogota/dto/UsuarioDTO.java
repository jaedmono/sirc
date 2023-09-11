/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.dto;

import java.util.List;

/**
 *
 * @author mbogota
 */
public class UsuarioDTO {

    private long idUser = -1;
    private long idPersona = -1;
    private List<Long> idEmpresa;
    private long idEstado = -1;
    private long idTipoUsuario = -1;
    private long idPerfil = -1;

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }

    public List<Long> getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(List<Long> idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public long getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(long idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(long idPerfil) {
        this.idPerfil = idPerfil;
    }
}
