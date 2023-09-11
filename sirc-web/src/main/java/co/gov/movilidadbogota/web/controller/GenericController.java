package co.gov.movilidadbogota.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import co.gov.movilidadbogota.core.dto.AbstractDTO;
import co.gov.movilidadbogota.core.dto.validator.AbstractValidator;
import co.gov.movilidadbogota.web.constants.AdministrationConstants;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;

@Controller
public class GenericController {

	@Autowired
	private MessageSource messageSource;

	protected List<BreadcrumbView> breadcrumb = new ArrayList<>();
	
	public static final String LOG_PREFIX="[SIRC-WEB]";

	protected void initbreadcrumb(Locale locale, List<BreadcrumbView> breadcrumbViews) {
		breadcrumb = new ArrayList<>();
		breadcrumb.add(new BreadcrumbView(AdministrationConstants.HOME_MAPPING,
				messageSource.getMessage("header.administration", null, locale)));
		breadcrumb.addAll(breadcrumbViews);
	}

	protected boolean genericValidator(AbstractValidator validator, Model model, BindingResult result,
			AbstractDTO dto, String nameObject) {

		boolean validation = false;

		validator.validate(dto, result);

		if (result.hasErrors()) {

			StringBuilder errors = new StringBuilder();
			
			for (ObjectError error : result.getAllErrors()) {
				if (errors.length() > 0) {
					errors.append("<br>");
				}
				errors.append(error.getCode());
			}
			
			model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, errors.toString());
			model.addAttribute(nameObject, dto);

			validation = true;
		}

		return validation;
	}

}
