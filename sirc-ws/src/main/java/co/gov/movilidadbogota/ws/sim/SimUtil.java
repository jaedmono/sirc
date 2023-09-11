/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.ws.sim;

import java.io.StringReader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author mbogota
 */
public class SimUtil {

    public static VehiculoSimDTO getVehiculoSimDTO(String xml) {
        Document doc = getDocumentSimk(xml);
        if (doc != null && validarRespuestaSim(doc)) {
            VehiculoSimDTO vehiculo = new VehiculoSimDTO();
            Element infVehiculo = doc.getRootElement().getChild("infVehiculo");
            vehiculo.setNroPlaca(getStringValue(infVehiculo, "nroPlaca"));
            vehiculo.setIdEstado(getIntValue(infVehiculo, "idEstado"));
            vehiculo.setEstado(getStringValue(infVehiculo, "descEstado"));
            vehiculo.setIdClase(getIntValue(infVehiculo, "idClase"));
            vehiculo.setClase(getStringValue(infVehiculo, "descClase"));
            vehiculo.setIdColor(getLongValue(infVehiculo, "idColor"));
            vehiculo.setColor(getStringValue(infVehiculo, "descColor"));
            vehiculo.setIdServicio(getIntValue(infVehiculo, "idServicio"));
            vehiculo.setServicio(getStringValue(infVehiculo, "descServicio"));
            vehiculo.setIdModalidad(getIntValue(infVehiculo, "runtModalidad"));
            vehiculo.setModalidad(getStringValue(infVehiculo, "descModalidad"));
            vehiculo.setRazonSocialEmpresa(getStringValue(infVehiculo, "empresa"));
            vehiculo.setIdTipoDocEmpresa(getStringValue(infVehiculo, "documentoEmpresa"));
            vehiculo.setTipoDocEmpresa(getStringValue(infVehiculo, "descDocumentoEmpresa"));
            vehiculo.setNumeroDocEmpresa(getStringValue(infVehiculo, "nitEmpresa"));
            vehiculo.setNroTarjetaOperacion(getStringValue(infVehiculo, "tOperacion"));
            vehiculo.setFechaExpTarjetaOperacion(getStringValue(infVehiculo, "fecExpTo"));
            vehiculo.setFechaVenTarjetaOperacion(getStringValue(infVehiculo, "fecVenTo"));
            vehiculo.setEstadoTarjetaOperacion(getStringValue(infVehiculo, "estadoTo"));
            return vehiculo;
        }
        return null;
    }

    private static Document getDocumentSimk(String xml) {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            return saxBuilder.build(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean validarRespuestaSim(Document doc) {
        if (doc != null) {
            Element error = doc.getRootElement().getChild("error");
            Element infVehiculo = doc.getRootElement().getChild("infVehiculo");
            if (error != null || infVehiculo == null) {
                return false;
            }
            return true;
        }
        return false;
    }

    private static String getStringValue(Element element, String cname) {
        String value = element.getChildText(cname);
        if (value != null && !value.isEmpty()) {
            return value.trim();
        }
        return "";
    }

    private static int getIntValue(Element element, String cname) {
        try {
            String value = element.getChildText(cname);
            if (value != null && !value.isEmpty()) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static long getLongValue(Element element, String cname) {
        try {
            String value = element.getChildText(cname);
            if (value != null && !value.isEmpty()) {
                return Long.parseLong(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
