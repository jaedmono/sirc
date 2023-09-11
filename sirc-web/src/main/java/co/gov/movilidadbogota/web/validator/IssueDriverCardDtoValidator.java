package co.gov.movilidadbogota.web.validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import co.gov.movilidadbogota.core.dto.CreateDriverDTO;

public class IssueDriverCardDtoValidator {
	
	private CreateDriverDTO createDriverDTO;
	private LocalDate currentDate; 
	
	Predicate<LocalDate> dateNotGreaterThanToday = (localDate) -> localDate.isBefore(currentDate);
		
	Predicate<LocalDate> userIsOfLegalAge = (localDate) -> localDate.isBefore(currentDate.minus(18, ChronoUnit.YEARS));
	
	Predicate<LocalDate> dateIsGreaterThanToday = (localDate) -> localDate.isAfter(currentDate);
	
	Predicate<Object> isNotNull = (object) -> object != null;
		
	
	public IssueDriverCardDtoValidator(CreateDriverDTO createDriverDTO) {
		this.createDriverDTO = createDriverDTO;
		this.currentDate = LocalDate.now();
	}
	
	public boolean validateDto() {
		LocalDate userBirthDate = createDriverDTO.getConductorDTO().getPersona().getFechaNacimiento().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		LocalDate userNationalIdIssueDate =  createDriverDTO.getConductorDTO().getPersona().getFechaExpedicionDocumento().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		LocalDate dueDateSoat = createDriverDTO.getFechaVencimientoSoat().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dueDateRTM = createDriverDTO.getFechaVencimientoRtm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dueDateTO = createDriverDTO.getFechaVencimientoTO().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return dateNotGreaterThanToday.and(userIsOfLegalAge).test(userBirthDate) 
				&& dateNotGreaterThanToday.test(userNationalIdIssueDate)
				&& dateIsGreaterThanToday.test(dueDateSoat)
				&& dateIsGreaterThanToday.test(dueDateRTM)
				&& dateIsGreaterThanToday.test(dueDateTO);
	}

}
