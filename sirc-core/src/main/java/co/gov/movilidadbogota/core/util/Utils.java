package co.gov.movilidadbogota.core.util;

import co.gov.movilidadbogota.core.dto.CreateDriverDTO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static boolean isNullOrEmpty(String arg) {
        return arg == null || arg.trim().isEmpty();
    }

    public static String generateRadomSerial() {
        Random rn = new Random();
        StringBuilder cal = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int result = rn.nextInt(100);
            cal.append(result);
        }
        return cal.toString();
    }

    public static void createFile(String fileName, CreateDriverDTO driverDTO, Path basePath) throws Exception {
        try {
            // DEFINIR DE DONDE SE VA A OPTENER LA RUTA(ARCHIVO DE PROPIEDADES O DE BBDD)
            File path = basePath.toFile();
            // VALIDA QUE EL PATH EXISTA, EN CASO CONTRARIO LO CREA
            if (!path.exists()) {
                boolean result = false;
                try {
                    result = path.mkdirs();
                } catch (SecurityException se) {
                    LOGGER.error("Ha ocurrido un error al intentar crear el directorio:", se);
                }
                if (result) {
                    LOGGER.info("DIR created");
                } else {
                    LOGGER.info("Dir not found");
                }
            }
            // CREA DOCUMENTO
            if (driverDTO.getFile() != null && !driverDTO.getFile().isEmpty()
                    && driverDTO.getFile().getSize() > 0 && driverDTO.getFile().getBytes() != null) {
                FileOutputStream newFile = new FileOutputStream(path.getAbsolutePath().concat(File.separator) + fileName);
                newFile.write(driverDTO.getFile().getBytes());
                newFile.flush();
                newFile.close();
            } else if (driverDTO.getConductorDTO() != null && driverDTO.getConductorDTO().getFoto() != null
                    && !driverDTO.getConductorDTO().getFoto().isEmpty()) {
                File outputfile = new File(path.getAbsolutePath().concat(File.separator) + fileName);
                ImageIO.write(decodeToImage(driverDTO.getConductorDTO().getFoto()), "jpg", outputfile);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static boolean periodoMenorMesActual(String periodo) {
        try {
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            Date periodoDate = format.parse(periodo);

            Calendar validezFecha = Calendar.getInstance();
            validezFecha.add(Calendar.MONTH, 1);

            if (periodoDate.after(validezFecha.getTime())) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validarPlaca(String value) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{3,3}\\d{3,3}");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static String getFormatDate(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String formatted = format1.format(date);
        return formatted;
    }

    public static Date cadenaAFecha(String fecha, String formato) {
        DateFormat df = new SimpleDateFormat(formato);
        Date date = null;
        try {
            date = df.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String fechaACadena(Date fecha, String formato) {
        if (fecha != null) {
            SimpleDateFormat format1 = new SimpleDateFormat(formato);
            return format1.format(fecha);
        }
        return "";
    }

}
