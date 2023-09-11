package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "SMI_PAGOPILA_COND_VEHI")
public class PagoPilaCondVehiEntity {

	@Id
	@Column(name = "ID_PAGOPILA_COND_VEHI")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_pago_pila")
	@SequenceGenerator(name = "sq_pago_pila", sequenceName = "sq_pago_pila", allocationSize=1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_CONDUCTOR")
	private ConductorEntity conductor;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_VEHICULO")
	private ConductorVehiculoEntity vehiculo;

	@Column(name = "NRO_APROBACION_PAGO")
	private String nroAprobacionPago;

	@Column(name = "PERIODO_PAGO_PILA")
	private Long  periodoPagoPila;
	
	@Column(name = "ID_ESTADO", nullable = false)
	private boolean idEstado;

	@Column(name = "FECHA_INICIO_VIGENCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIniciaVigencia;
	
	@Column(name = "FECHA_FIN_VIGENCIA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFinVigencia;

	@ManyToOne
	@JoinColumn(name = "ID_EMPRESA")
	private EmpresaEntity empresa;
        
        @ManyToOne
	@JoinColumn(name = "ID_OPERADOR")
	private OperadoresPilaEntity operador;
	

	public PagoPilaCondVehiEntity() {
	}

	public PagoPilaCondVehiEntity(long id, ConductorEntity conductor, ConductorVehiculoEntity vehiculo,
			String nroAprobacionPago, boolean idEstado, Date fechaIniciaVigencia,
			Date fechaFinVigencia, EmpresaEntity empresa, OperadoresPilaEntity operador) {
		super();
		this.id = id;
		this.conductor = conductor;
		this.vehiculo = vehiculo;
		this.nroAprobacionPago = nroAprobacionPago;
		this.idEstado = idEstado;
		this.fechaIniciaVigencia = fechaIniciaVigencia;
		this.fechaFinVigencia = fechaFinVigencia;
		this.empresa = empresa;
                this.operador = operador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ConductorEntity getConductor() {
		return conductor;
	}

	public void setConductor(ConductorEntity conductor) {
		this.conductor = conductor;
	}

	public ConductorVehiculoEntity getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(ConductorVehiculoEntity vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getNroAprobacionPago() {
		return nroAprobacionPago;
	}

	public void setNroAprobacionPago(String nroAprobacionPago) {
		this.nroAprobacionPago = nroAprobacionPago;
	}

	public long getPeriodoPagoPila() {
		return periodoPagoPila;
	}

	public void setPeriodoPagoPila(long periodoPagoPila) {
		this.periodoPagoPila = periodoPagoPila;
	}


	public boolean isIdEstado() {
		return idEstado;
	}

	public void setIdEstado(boolean idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFechaIniciaVigencia() {
		return fechaIniciaVigencia;
	}

	public void setFechaIniciaVigencia(Date fechaIniciaVigencia) {
		this.fechaIniciaVigencia = fechaIniciaVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public EmpresaEntity getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaEntity empresa) {
		this.empresa = empresa;
	}

        public OperadoresPilaEntity getOperador() {
            return operador;
        }

        public void setOperador(OperadoresPilaEntity operador) {
            this.operador = operador;
        }

}
