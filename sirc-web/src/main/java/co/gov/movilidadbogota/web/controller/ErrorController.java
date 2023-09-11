package co.gov.movilidadbogota.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.gov.movilidadbogota.web.constants.AdministrationConstants;
import co.gov.movilidadbogota.web.constants.CommonConstants;

@Controller
public class ErrorController {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = AdministrationConstants.BAD_REQUEST)
	public String error400(Model model, Locale locale, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
				messageSource.getMessage("common.error.badrequest", null, locale));
		return "redirect:" + AdministrationConstants.HOME_MAPPING;
	}

	@RequestMapping(value = AdministrationConstants.NOT_FOUND)
	public String error404(Model model, Locale locale, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
				messageSource.getMessage("common.error.notfound", null, locale));
		return "redirect:" + AdministrationConstants.HOME_MAPPING;
	}

	@RequestMapping(value = AdministrationConstants.INTERNAL_SERVER_ERROR)
	public String error500(Model model, Locale locale, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
				messageSource.getMessage("common.error.internalserver", null, locale));
		return "redirect:" + AdministrationConstants.HOME_MAPPING;
	}

	@RequestMapping(value = AdministrationConstants.NOT_SUPPORT_METHOD)
	public String error405(Model model, Locale locale, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
				messageSource.getMessage("common.error.notsupportmethod", null, locale));
		return "redirect:" + AdministrationConstants.HOME_MAPPING;
	}

	@RequestMapping(value = AdministrationConstants.ICO, method = RequestMethod.GET)
	public String favicon(HttpSession session) {
		return "forward:/resources/images/favicon.png";
	}
	
}