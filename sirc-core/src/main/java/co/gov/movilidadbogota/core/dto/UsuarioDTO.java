package co.gov.movilidadbogota.core.dto;

import java.util.List;

public class UsuarioDTO extends AbstractDTO {

	private long id;
	private String loginUsuario;
	private String claveUsuario;
	private String rol;
	private String nombre;
	private String apellido;
	private String nuevaClave;
	private String email;
	private List<EmpresaDTO> empresas;

	public UsuarioDTO() {
	}

	public UsuarioDTO(long id, String loginUsuario) {
		this.id = id;
		this.loginUsuario = loginUsuario;
	}

	public UsuarioDTO(long id, String loginUsuario, String rol) {
		super();
		this.id = id;
		this.loginUsuario = loginUsuario;
		this.rol = rol;
	}

	public UsuarioDTO(String loginUsuario, String email) {
		super();
		this.loginUsuario = loginUsuario;
		this.email = email;
	}

	public UsuarioDTO(String loginUsuario, String email, List<EmpresaDTO> empresas) {
		super();
		this.loginUsuario = loginUsuario;
		this.email = email;
		this.empresas = empresas;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNuevaClave() {
		return nuevaClave;
	}

	public void setNuevaClave(String nuevaClave) {
		this.nuevaClave = nuevaClave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<EmpresaDTO> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<EmpresaDTO> empresas) {
		this.empresas = empresas;
	}

}