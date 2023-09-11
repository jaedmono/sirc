package co.gov.movilidadbogota.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.ReporteConstants;
import co.gov.movilidadbogota.web.util.ReporteEnum;
import co.gov.movilidadbogota.web.util.dto.ResponseDto;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Controller
public class ReporteController extends GenericController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReporteController.class);

	private static String REPORT_PAGE = "reportes/reporte";

	@Autowired
	private MessageSource messageSource;

	@Secured({ "ROLE_SDM" })
	@RequestMapping(value = { ReporteConstants.CONSULTAR_REPORTES }, method = RequestMethod.GET)
	public String getReport(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(
				new BreadcrumbView(ReporteConstants.CONSULTAR_REPORTES, "Reportes"),
				new BreadcrumbView(ReporteConstants.CONSULTAR_REPORTES, "Reporte SIRC")));
		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);
		model.addAttribute("initialDate", new Date());
		model.addAttribute("finalDate", new Date());
		model.addAttribute("textTitle", "Reporte SIRC");
		return REPORT_PAGE;
	}

	@RequestMapping(value = { ReporteConstants.CONSULTAR_REPORTES }, method = RequestMethod.POST)
	@ResponseBody
	@Secured({ "ROLE_SDM" })
	public ResponseDto<UsuarioDTO> searchLdapUser(@RequestParam() String fechaInicial,
			@RequestParam() String fechaFinal, @RequestParam() String tipoReporte, @RequestParam() String opcionFecha, HttpSession session, Locale locale, Model model,
			HttpServletResponse response) {

		try {
			System.out.println(opcionFecha);
			
			if(opcionFecha.equals("completo")) {
				Map<String,Object> params = new HashMap<>();
				params.put("fechaInicial", new Date());
			    params.put("fechaFinal", new Date());
				DateFormat reporteFormat = new SimpleDateFormat("yyyyMMdd");
			    String dateStringIni = reporteFormat.format(new Date());
			    URL url = getClass().getResource(ReporteConstants.URL_REPORTES + ReporteConstants.REPORTE_DOS);
			    System.out.println(url.toURI().getPath());
			    InputStream jasperStream = this.getClass().getResourceAsStream(ReporteConstants.URL_REPORTES + ReporteConstants.REPORTE_DOS);
			    generarReporte(jasperStream, "Reporte_SIRC_Total_" + dateStringIni, ReporteEnum.valueOf(tipoReporte), params, response);
				//generarReporte("/WEB-INF/classes/jasperreports/reporte_vehiculos_sirc_2.jasper", "Reporte_SIRC_Total_" + dateStringIni, ReporteEnum.valueOf(tipoReporte), params, response);
			} else {
//				InputStream jasperStream = this.getClass().getResourceAsStream("/jasperreports/reporte_vehiculos_sirc.jasper");
//				System.out.println(jasperStream);
				InputStream jasperStream = this.getClass().getResourceAsStream(ReporteConstants.URL_REPORTES + ReporteConstants.REPORTE_UNO);
			    Map<String,Object> params = new HashMap<>();
			    
			    DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			    Date dateInitial = sourceFormat.parse(fechaInicial + " 00:00:00");
			    System.out.println(dateInitial); 
			    
			    Date dateEnd = sourceFormat.parse(fechaFinal + " 23:59:59");
			    System.out.println(dateEnd); 
			    
			    params.put("fechaInicial", dateInitial);
			    params.put("fechaFinal", dateEnd);
			    
			    DateFormat reporteFormat = new SimpleDateFormat("yyyyMMdd");
			    String dateStringIni = reporteFormat.format(dateInitial);
			    String dateStringEnd = reporteFormat.format(dateEnd);
			    URL url = getClass().getResource(ReporteConstants.URL_REPORTES + ReporteConstants.REPORTE_UNO);
			    System.out.println(url.toURI().getPath());
			    if(fechaInicial.equals(fechaFinal)) {
			    	// Genera el reporte
				    //generarReporte("/WEB-INF/classes/jasperreports/reporte_vehiculos_sirc.jasper", "Reporte_SIRC_Total_" + dateStringIni, ReporteEnum.valueOf(tipoReporte), params, response);
				    generarReporte(jasperStream, "Reporte_SIRC_" + dateStringIni, ReporteEnum.valueOf(tipoReporte), params, response);
			    } else {
			    	// Genera el reporte
			    	//generarReporte("/WEB-INF/classes/jasperreports/reporte_vehiculos_sirc.jasper", "Reporte_SIRC_" + dateStringIni + "_" + dateStringEnd, ReporteEnum.valueOf(tipoReporte), params, response);
				    generarReporte(jasperStream, "Reporte_SIRC_" + dateStringIni + "_" + dateStringEnd, ReporteEnum.valueOf(tipoReporte), params, response);
			    }
			}
			
		    
		    
		    
		    
//		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
//		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, GetConnection.getJNDIConnection());
//		    
//	        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
//	        ByteArrayOutputStream os = new ByteArrayOutputStream();
//	        
//	        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//	        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "report.xlsx");
	        
	        //uncomment this codes if u are want to use servlet output stream
	        //xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
	        
//	        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
//	        
//	        xlsxExporter.exportReport();
//	        
//	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//	        response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
	        
	        //uncomment this codes if u are want to use servlet output stream
	        //servletOutputStream.write(os.toByteArray());
	        
//	        response.getOutputStream().write(os.toByteArray());
//	        response.getOutputStream().flush();
//	        response.getOutputStream().close();
//	        response.flushBuffer();

			return ResponseDto.success("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));
		}
	}
	
	
	private void generarReporte(InputStream jasperStream, String nombreReporte, ReporteEnum reporteEnum,
			Map<String, Object> params, HttpServletResponse response) throws Exception {

			// Salida del reporte
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			// Crear reporte jasper
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			//JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reporte);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, GetConnection.getJNDIConnection());

			switch (reporteEnum) {
			case excel:

			generarXLSX(jasperReport, jasperPrint, os);

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte + ".xlsx");
			response.addHeader("filename", nombreReporte + ".xlsx");

			break;

			case plano:

			generarCSV(jasperReport, jasperPrint, os);

			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte + ".csv");
			response.addHeader("filename", nombreReporte + ".csv");

			break;

			case word:

			generarWORDX(jasperReport, jasperPrint, os);

			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte + ".docx");
			response.addHeader("filename", nombreReporte + ".docx");

			break;

			default:

			generarPDF(jasperReport, jasperPrint, os);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=" + nombreReporte + ".pdf");
			response.addHeader("filename", nombreReporte + ".pdf");

			break;
			}

			// Enviar reporte
			response.getOutputStream().write(os.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();

			}
	
	
	
	private void generarWORDX(JasperReport jasperReport, JasperPrint jasperPrint, ByteArrayOutputStream os)
	throws Exception {
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
		exporter.exportReport();
	}

	private void generarCSV(JasperReport jasperReport, JasperPrint jasperPrint, ByteArrayOutputStream os)
	throws Exception {
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(os));
	
		SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		configuration.setRecordDelimiter("\r\n");
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}

	private void generarPDF(JasperReport jasperReport, JasperPrint jasperPrint, ByteArrayOutputStream os)
	throws Exception {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
		exporter.exportReport();
	}

	private void generarXLSX(JasperReport jasperReport, JasperPrint jasperPrint, ByteArrayOutputStream os)
	throws Exception {
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
	
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		configuration.setOnePagePerSheet(false);
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(false);
	
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}
}