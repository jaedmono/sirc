package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SMI_CONTROLALERTA")
public class ControlAlertaEntity {

	@Id
	@Column(name = "ID_CTRL_ALERTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_ctrl_alerta")
	@SequenceGenerator(name = "sq_ctrl_alerta", sequenceName = "sq_ctrl_alerta", allocationSize=1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_EMPRESA")
	private EmpresaEntity empresa;

	@Column(name = "INDICA_NOTIF_SINVALIDEZ")
	private String indicaNotifSinValidez;

	@Column(name = "INDICA_NOTIF_SINVIGENCIA")
	private String indicaNotifSinVigencia;

	@Column(name = "INDICA_NOTIF_PROXIMO")
	private String indicaNotifProximo;

	@Column(name = "FECHA_NOTIFICACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaNotificacion;

	@Column(name = "INDICA_NOTIF_SINVALIDEZ_GA")
	private String indicaNotifSinValidezGa;

	@Column(name = "INDICA_NOTIF_PROXIMO_GA")
	private String indicaNotifSinProximoGa;

	@Column(name = "FECHA_REVISION_GA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRevisionGa;

	@Column(name = "ID_ESTADO")
	private long idEstado;

	@Column(name = "PERIODICIDAD")
	private String periodicidad;

	@Column(name = "USR_REVISION_GA")
	private String usrRevisionGa;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	public ControlAlertaEntity() {
	}

	public ControlAlertaEntity(long id, String indicaNotifSinValidez, String indicaNotifSinVigencia,
			String indicaNotifProximo, Date fechaNotificacion, String indicaNotifSinValidezGa,
			String indicaNotifSinProximoGa, Date fechaRevisionGa, long idEstado, String periodicidad,
			String usrRevisionGa, String correoElectronico) {
		super();
		this.id = id;
		this.indicaNotifSinValidez = indicaNotifSinValidez;
		this.indicaNotifSinVigencia = indicaNotifSinVigencia;
		this.indicaNotifProximo = indicaNotifProximo;
		this.fechaNotificacion = fechaNotificacion;
		this.indicaNotifSinValidezGa = indicaNotifSinValidezGa;
		this.indicaNotifSinProximoGa = indicaNotifSinProximoGa;
		this.idEstado = idEstado;
		this.periodicidad = periodicidad;
		this.usrRevisionGa = usrRevisionGa;
		this.correoElectronico = correoElectronico;
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

	public String getIndicaNotifSinValidez() {
		return indicaNotifSinValidez;
	}

	public void setIndicaNotifSinValidez(String indicaNotifSinValidez) {
		this.indicaNotifSinValidez = indicaNotifSinValidez;
	}

	public String getIndicaNotifSinVigencia() {
		return indicaNotifSinVigencia;
	}

	public void setIndicaNotifSinVigencia(String indicaNotifSinVigencia) {
		this.indicaNotifSinVigencia = indicaNotifSinVigencia;
	}

	public String getIndicaNotifProximo() {
		return indicaNotifProximo;
	}

	public void setIndicaNotifProximo(String indicaNotifProximo) {
		this.indicaNotifProximo = indicaNotifProximo;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getIndicaNotifSinValidezGa() {
		return indicaNotifSinValidezGa;
	}

	public void setIndicaNotifSinValidezGa(String indicaNotifSinValidezGa) {
		this.indicaNotifSinValidezGa = indicaNotifSinValidezGa;
	}

	public String getIndicaNotifSinProximoGa() {
		return indicaNotifSinProximoGa;
	}

	public void setIndicaNotifSinProximoGa(String indicaNotifSinProximoGa) {
		this.indicaNotifSinProximoGa = indicaNotifSinProximoGa;
	}

	public Date getFechaRevisionGa() {
		return fechaRevisionGa;
	}

	public void setFechaRevisionGa(Date fechaRevisionGa) {
		this.fechaRevisionGa = fechaRevisionGa;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public String getUsrRevisionGa() {
		return usrRevisionGa;
	}

	public void setUsrRevisionGa(String usrRevisionGa) {
		this.usrRevisionGa = usrRevisionGa;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	
	
	

}
