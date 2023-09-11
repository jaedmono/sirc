package co.gov.movilidadbogota.web.controller;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dto.CreateDriverDTO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.web.constants.DriverConstants;
import co.gov.movilidadbogota.web.util.QRGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import static co.gov.movilidadbogota.web.constants.QRConstants.*;

@Controller
@Secured({"ROLE_EXTERNAL"})
public class DownloadController extends GenericController{

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class+LOG_PREFIX);

    @Autowired
    private ParametroSimurDAO parametroSimurDAO;


    @RequestMapping(value = {DOWNLOAD_QR_IMAGE})
    public void downloadQRImage(HttpServletRequest request,
                                HttpServletResponse response, @PathVariable("fileName") String fileName) {
        try {
            // getting the path to file
            ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
            Path file = Paths.get(rutaImg.getValorParametro().concat(File.separator).concat(PATH_QR_IMAGE), fileName.concat(EXTENSION_QR_IMAGE));
            if(!Files.exists(file)){
                String errorMessage = "El QR no existe en el servidor.";
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
                outputStream.close();
            }
            // getting mimetype from context
            String mimeType= request.getServletContext().getMimeType(
                    file.getFileName().toString());
            if(mimeType == null){
                // Not able to detect mimetype taking default
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.addHeader("Content-Disposition", "attachment; filename="+fileName+".png");
            Files.copy(file, response.getOutputStream());

        } catch (IOException e) {
            LOGGER.error("Error descargando el codigo QR", e);
        }

    }
}
