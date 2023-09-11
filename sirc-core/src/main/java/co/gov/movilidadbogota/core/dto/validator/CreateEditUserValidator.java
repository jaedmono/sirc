package co.gov.movilidadbogota.core.dto.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import co.gov.movilidadbogota.core.dto.CreateUserDTO;

public class CreateEditUserValidator extends AbstractValidator implements Validator{

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public boolean supports(Class clazz) {
		return CreateUserDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateUserDTO userDTO = (CreateUserDTO) target;

		if (userDTO != null) {
			if (StringUtils.isEmpty(userDTO.getEmail())) {
				errors.rejectValue("email", "El correo electrónico se encuentra vacio o nulo.");
			} else {
				if (userDTO.getEmail().length() > 100) {
					errors.rejectValue("email", "El correo electrónico supera los 100 caracteres.");
				}
				Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(userDTO.getEmail());
				if (!matcher.find()) {
					errors.rejectValue("email", "El correo electrónico ingresado no coincide con un correo valido.");
				}
			}
		} else {
			errors.rejectValue("object", "El objeto se encuentra vacio o nulo.");
		}

	}

	// Unit Test RUN AS JAVA APPLICATION
	private static void validateEmail(final String email, final Errors errors) {

		if (StringUtils.isEmpty(email)) {
			errors.rejectValue("email", "El correo electrónico se encuentra vacio o nulo.");
		} else {
			if (email.length() > 100) {
				errors.rejectValue("email", "El correo electrónico supera los 100 caracteres.");
			}
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
			if (!matcher.find()) {
				errors.rejectValue("email", "El correo electrónico ingresado no coincide con un correo valido.");
			}
		}

	}

	public static void main(String[] args) {

		CreateUserDTO userDTO = new CreateUserDTO("franzjrcarvajal@gmail.com");

		Errors errors = new BeanPropertyBindingResult(userDTO, "userDTO");
		validateEmail(userDTO.getEmail(), errors);

		if (errors.hasErrors()) {
			for (ObjectError error : errors.getAllErrors()) {
				System.err.println(error);
			}
		} else {
			System.out.println("No se encontraron errores.");
		}

	}

}