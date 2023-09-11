package co.gov.movilidadbogota.web.util;

import co.gov.movilidadbogota.core.exception.QRGeneratorException;
import co.gov.movilidadbogota.web.constants.QRConstants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class QRGenerator {

    public static final String URL_SIMUR = "https://www.simur.gov.co/taxis/consulta-conductores-tarjeta?text=";

    public static BufferedImage generateQRCodeImage(String tarjetaControl, String urlSimur) throws Exception {

        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(urlSimur.concat(tarjetaControl), BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static void createQRFile(BufferedImage bufferedImage, Path basePath, String fileName) throws QRGeneratorException {
        try {
            File path = basePath.toFile();
            String qrFileLocation = path.getAbsolutePath()
                    .concat(File.separator)
                    .concat(QRConstants.PATH_QR_IMAGE)
                    .concat((File.separator))
                    .concat(fileName).concat(QRConstants.EXTENSION_QR_IMAGE);
            File outfile = new File(qrFileLocation);
            ImageIO.write(bufferedImage, "png", outfile);
        }catch (Exception e){
            throw new QRGeneratorException();
        }
    }
}
