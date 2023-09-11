package co.gov.movilidadbogota.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import co.gov.movilidadbogota.core.dao.ConductorVehiculoDAO;
import co.gov.movilidadbogota.core.dto.ReportRefrenderDto;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.ReportConstans;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Controller
@Secured({ "ROLE_SDM" })
public class ReportController extends GenericController {

	@Autowired
	private ConductorVehiculoDAO conductorVehiculoDAO;

	@Autowired
	private MessageSource messageSource;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	private static final String URL_REPORTE_REFRENDACION = "report/report-refrender";
	private static final String COMMAND_NAME = "reportRefrenderDto";
	private static final String COMMAND_NAME_DATE_INIT = "datepickerInicio";
	private static final String COMMAND_NAME_DATE_FINISH = "datepickerFinal";
	private static final String SUMMARY_ROWS = "sumaryRows";
	
	SimpleDateFormat formatReport = new SimpleDateFormat("yyyy/MM/dd hh:mm");
	
	@RequestMapping(value = { ReportConstans.REPORT_REFRENDACION }, method = RequestMethod.GET)
	public String seachReportRefrender(HttpSession session, Locale locale, Model model,
			RedirectAttributes redirectAttributes) {
		setBreadcrumbCreateExternalUser(model, locale);
		return URL_REPORTE_REFRENDACION;
	}

	@RequestMapping(value = { ReportConstans.SEARCH_REPORT_REFRENDACION }, method = RequestMethod.GET)
	public String seachReportRefrender(HttpSession session, Locale locale, Model model,
			@RequestParam String datepickerInicio, @RequestParam String datepickerFinal,
			RedirectAttributes redirectAttributes) {

		LOGGER.info("*** Generacion del reporte de refrendacion");

		if (!datepickerInicio.isEmpty() && !datepickerFinal.isEmpty()) {
			LOGGER.info("*** Generando reporte desde {} hasta {}", datepickerInicio, datepickerFinal);

			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try {
				Date fechaInicial = df.parse(datepickerInicio+" 00:00:00");
				Date fechaFinal = df.parse(datepickerFinal+" 23:59:59");

				if (fechaInicial != null && fechaFinal != null && fechaInicial.before(fechaFinal)) {
					List<ConductorVehiculoEntity> result = conductorVehiculoDAO
							.consultarReporteRefrendacion(datepickerInicio, datepickerFinal);

					if (!result.isEmpty()) {
						LOGGER.info("*** Cantidad de registros consultados {}", result.size());
						model.addAttribute(COMMAND_NAME, result);

					} else {
						model.addAttribute(CommonConstants.PAGE_MESSAGE_INFO, messageSource
								.getMessage("header.administration.reporte.refrendacion.resultados", null, locale));
					}
					model.addAttribute(SUMMARY_ROWS, result.size());
				} else {
					LOGGER.info(
							"*** Es necesario realizar la validacion de las fechas que la inicial sea menor a la final");
					model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource
							.getMessage("header.administration.reporte.refrendacion.errorconsulta", null, locale));
				}

			} catch (ParseException e) {
				LOGGER.info("*** Se genero un error al convertir las fechas");
				model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource
						.getMessage("header.administration.reporte.refrendacion.exceptiondate", null, locale));
			}

		} else {
			LOGGER.info("*** Una de las fechas solicitadas para el reporte no estan digitados");
			model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
					messageSource.getMessage("header.administration.reporte.refrendacion.errorconsulta", null, locale));
		}
		model.addAttribute(COMMAND_NAME_DATE_INIT, datepickerInicio);
		model.addAttribute(COMMAND_NAME_DATE_FINISH, datepickerFinal);
		setBreadcrumbCreateExternalUser(model, locale);

		return URL_REPORTE_REFRENDACION;
	}

	@RequestMapping(value = { ReportConstans.SEARCH_REPORT_REFRENDACION_DONWLOAD }, method = RequestMethod.GET)
	public void seachReportRefrenderDownload(HttpServletResponse response, @RequestParam String datepickerInicio,
			@RequestParam String datepickerFinal, @RequestParam String type) throws IOException, WriteException {

		if(type.equalsIgnoreCase("csv")) {
			String csvFileName = "Reporte_Refrendacion_" + datepickerInicio + "_" + datepickerFinal + ".csv";
			response.setContentType("text/csv");
			createHeders(response, csvFileName);
			// uses the Super CSV API to generate CSV data from the model data
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			String[] header = { "tarjetaControl", "placaSerialVehiculo", "nombreRazonSocial", "nombreConductor",
					"apellidosConductor", "estado", "fechaExpedicion", "fechaValidez", "fechaVigencia",
			"fechaRefrendacion" };
			csvWriter.writeHeader(header);
			List<ReportRefrenderDto> result = conductorVehiculoDAO.consultarReporteRefrendacionDTO(datepickerInicio,
					datepickerFinal);
			writerCsv(csvWriter, header, result);
		}else {
			String csvFileName = "Reporte_Refrendacion_" + datepickerInicio + "_" + datepickerFinal + ".xls"; 
			WritableWorkbook writableWorkbook = null;
			response.setContentType("application/vnd.ms-excel");
			createHeders(response, csvFileName);
			writableWorkbook = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet excelOutputsheet = writableWorkbook.createSheet("Excel Output", 0);
	        addExcelOutputHeader(excelOutputsheet);
	        List<ReportRefrenderDto> result = conductorVehiculoDAO.consultarReporteRefrendacionDTO(datepickerInicio,
					datepickerFinal);
	        writeExcelOutputData(excelOutputsheet, result);
			writableWorkbook.write();
			writableWorkbook.close();
		}
	}
	
	@RequestMapping(value = { ReportConstans.SEARCH_REPORT_REFRENDACION_DONWLOAD_ALL }, method = RequestMethod.GET)
	public void seachReportRefrenderDownloadAll(@RequestParam String type, HttpServletResponse response) throws IOException, WriteException {

		if(type.equalsIgnoreCase("csv")) {
			String csvFileName = "Reporte_Refrendaciones_Total_" +formatReport.format(Calendar.getInstance().getTime())+ ".csv"; 
			response.setContentType("text/csv");
			// creates mock data
			createHeders(response, csvFileName);
			// uses the Super CSV API to generate CSV data from the model data
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			String[] header = { "tarjetaControl", "placaSerialVehiculo", "nombreRazonSocial", "nombreConductor",
					"apellidosConductor", "estado", "fechaExpedicion", "fechaValidez", "fechaVigencia",
			"fechaRefrendacion" };
			csvWriter.writeHeader(header);
			List<ReportRefrenderDto> result = conductorVehiculoDAO.consultarReporteRefrendacionAllDTO();
			writerCsv(csvWriter, header, result);
		}else {
			String csvFileName = "Reporte_Refrendaciones_Total_" +formatReport.format(Calendar.getInstance().getTime())+ ".xls"; 
			WritableWorkbook writableWorkbook = null;
			response.setContentType("application/vnd.ms-excel");
			createHeders(response, csvFileName);
			writableWorkbook = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet excelOutputsheet = writableWorkbook.createSheet("Excel Output", 0);
	        addExcelOutputHeader(excelOutputsheet);
	        List<ReportRefrenderDto> result = conductorVehiculoDAO.consultarReporteRefrendacionAllDTO();
	        writeExcelOutputData(excelOutputsheet, result);
			writableWorkbook.write();
			writableWorkbook.close();
		}
	}

	private void writeExcelOutputData(WritableSheet excelOutputsheet, List<ReportRefrenderDto> result) throws WriteException {
		int count = 1;
		
		for(ReportRefrenderDto reportLine : result) {
			excelOutputsheet.addCell(new Label(0, count, reportLine.getTarjetaControl()));
			excelOutputsheet.addCell(new Label(1, count, reportLine.getPlacaSerialVehiculo()));
			excelOutputsheet.addCell(new Label(2, count, reportLine.getNombreRazonSocial()));
			excelOutputsheet.addCell(new Label(3, count, reportLine.getNombreConductor()));
			excelOutputsheet.addCell(new Label(4, count, reportLine.getApellidosConductor()));
			excelOutputsheet.addCell(new Label(5, count, reportLine.getEstado()));
			excelOutputsheet.addCell(new Label(6, count, formatReport.format(reportLine.getFechaExpedicion())));
			excelOutputsheet.addCell(new Label(7, count, formatReport.format(reportLine.getFechaValidez())));
			excelOutputsheet.addCell(new Label(8, count, formatReport.format(reportLine.getFechaVigencia())));
			excelOutputsheet.addCell(new Label(9, count, formatReport.format(reportLine.getFechaRefrendacion())));
			count++;
		}
	}

	private void addExcelOutputHeader(WritableSheet excelOutputsheet) throws WriteException{
		excelOutputsheet.addCell(new Label(0, 0, "tarjetaControl"));
		excelOutputsheet.addCell(new Label(1, 0, "placaSerialVehiculo"));
		excelOutputsheet.addCell(new Label(2, 0, "nombreRazonSocial"));
		excelOutputsheet.addCell(new Label(3, 0, "nombreConductor"));
		excelOutputsheet.addCell(new Label(4, 0, "apellidosConductor"));
		excelOutputsheet.addCell(new Label(5, 0, "estado"));
		excelOutputsheet.addCell(new Label(6, 0, "fechaExpedicion"));
		excelOutputsheet.addCell(new Label(7, 0, "fechaValidez"));
		excelOutputsheet.addCell(new Label(8, 0, "fechaVigencia"));
		excelOutputsheet.addCell(new Label(9, 0, "fechaRefrendacion"));
	}

	private void createHeders(HttpServletResponse response, String csvFileName) {
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);
	}

	private void writerCsv(ICsvBeanWriter csvWriter, String[] header, List<ReportRefrenderDto> result)
			throws IOException {
		for (ReportRefrenderDto aBook : result) {
			csvWriter.write(aBook, header);
		}
		csvWriter.close();
	}
	
	private void setBreadcrumbCreateExternalUser(Model model, Locale locale) {
		initbreadcrumb(locale,
				Arrays.asList(
						new BreadcrumbView(ReportConstans.REPORT_REFRENDACION,
								messageSource.getMessage("header.administration.reporte", null, locale)),
						new BreadcrumbView(ReportConstans.REPORT_REFRENDACION,
								messageSource.getMessage("header.administration.reporte.refrendacion", null, locale))));
		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);
	}
}
