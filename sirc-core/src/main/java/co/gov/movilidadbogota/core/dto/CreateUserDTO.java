package co.gov.movilidadbogota.core.dto;

public class CreateUserDTO extends AbstractDTO{
	
	private String email;
	
	public CreateUserDTO() {
	}
	
	public CreateUserDTO(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}