package co.gov.movilidadbogota.core.dto;

public class ManagerUserDTO extends AbstractDTO {

	private long id;
	private String username;

	public ManagerUserDTO() {
	}

	public ManagerUserDTO(long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}