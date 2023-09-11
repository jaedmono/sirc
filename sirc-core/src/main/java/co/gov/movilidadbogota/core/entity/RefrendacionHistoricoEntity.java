package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMB_REFRENDACION_HISTORICO")
public class RefrendacionHistoricoEntity {

	@Id
	@Column(name = "ID_REFRENDACION_HISTORICO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REFRENDACION_HISTORICO")
	@SequenceGenerator(name = "SEQ_REFRENDACION_HISTORICO", sequenceName = "SEQ_REFRENDACION_HISTORICO", allocationSize = 1)
	private long idRefrendacionHistorico;

	@Column(name = "FECHA_REFRENDACION")
	private Date fechaRefrendacion;

	@Column(name = "ID_VEHICULO")
	private long idVehiculo;

	public RefrendacionHistoricoEntity() {
		super();
	}

	public long getIdRefrendacionHistorico() {
		return idRefrendacionHistorico;
	}

	public void setIdRefrendacionHistorico(long idRefrendacionHistorico) {
		this.idRefrendacionHistorico = idRefrendacionHistorico;
	}

	public Date getFechaRefrendacion() {
		return fechaRefrendacion;
	}

	public void setFechaRefrendacion(Date fechaRefrendacion) {
		this.fechaRefrendacion = fechaRefrendacion;
	}

	public long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

}
