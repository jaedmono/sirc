/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author USER-LENOVO
 */
public class Utils {

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

    public static String getFormatDate(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String formatted = format1.format(date);
        return formatted;
    }
    
    public static String getFormatDateWithHour(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formatted = format1.format(date);
        return formatted;
    }

    public static boolean validarFecha(String value, String formato) {
        try {
            if (formato.equals("MM/yyyy")) {
                value = "01/" + value;
                formato = "dd/MM/yyyy";
            }
            Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                Calendar cal = Calendar.getInstance();
                cal.setLenient(false);
                SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
                cal.setTime(formatoFecha.parse(value));
                cal.getTime();
                return true;
            }
            return false;
        } catch (ParseException ex2) {
            return false;
        }
    }

    public static Date cadenaAFecha(String fecha, String formato) {
        DateFormat df = new SimpleDateFormat(formato);
        Date date = null;
        try {
            date = df.parse(fecha);
            df.format(date);
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

    public static boolean validarPlaca(String value) {
        Pattern pattern = Pattern.compile("^[A-Za-z]{3}[0-9]{3}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static String claveUsuario(String clave) {
        String pass = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            pass = Base64.getEncoder().encodeToString(messageDigest.digest(clave.getBytes()));
        } catch (NoSuchAlgorithmException arg2) {
            throw new RuntimeException(arg2);
        }
        return pass;
    }
}
