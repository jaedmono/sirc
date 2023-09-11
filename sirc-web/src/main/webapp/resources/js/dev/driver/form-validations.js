function deleteMessages() {
    let msj = document.getElementById("page-message-container");
    while (msj.hasChildNodes()) {
        msj.removeChild(msj.lastChild);
    }
}

$('#first_name').on('blur', function (e) {
    let firstName = $("#first_name").val();
    if (!firstName) {
        Util.Message.error("El campo nombres es requerido");
        $("#first_name").removeClass("success-input");
        $("#first_name").addClass("error-input");
        $('html,body').scrollTop(0);
    } else {
        $("#first_name").removeClass("error-input");
        $("#first_name").addClass("success-input");
        deleteMessages();
    }
});

$('#last_name').on('blur', function (e) {
    let lastName = $("#last_name").val();
    if (!lastName) {
        Util.Message.error("El campo apellidos es requerido");
        $("#last_name").removeClass("success-input");
        $("#last_name").addClass("error-input");
        $('html,body').scrollTop(0);
    } else {
        $("#last_name").removeClass("error-input");
        $("#last_name").addClass("success-input");
        deleteMessages();
    }
});


$('#fechaNacimiento').on('blur', function (e) {
    let Msjerror = "";
    let error = false;
    let birthday = $("#fechaNacimiento").val();
    let todayYear = (new Date()).getFullYear();
    let cutOff19 = new Date();
    cutOff19.setFullYear(todayYear - 18);
    let cutOff95 = new Date();
    cutOff95.setFullYear(todayYear - 95);
    let today = new Date().setHours(0, 0, 0, 0);
    let givenDate = validateDate(birthday).setHours(0,0,0,0);
    console.log(birthday)
    if (givenDate > today) {
        Msjerror = "La fecha de nacimiento no es valida ";
        error = true;
    }else if (givenDate > cutOff19) {
        Msjerror = "El conductor debe ser mayor de edad ";
        error = true;
    } else if (givenDate < cutOff95) {
        Msjerror = "El conductor no puede ser mayor de 95";
        error = true;
    }
    if(error){
        Util.Message.error(Msjerror);
        $("#fechaNacimiento").removeClass("success-input");
        $("#fechaNacimiento").addClass("error-input");
        $('html,body').scrollTop(0);
    }else {
        $("#fechaNacimiento").removeClass("error-input");
        $("#fechaNacimiento").addClass("success-input");
        deleteMessages();
    }
});

$('#loadDocument').on('blur', function (e) {
    let loadDocument = $("#loadDocument").val();
    if (!loadDocument) {
        $("#loadDocument").removeClass("success-input");
        $("#loadDocument").addClass("error-input");
    } else {
        $("#loadDocument").removeClass("error-input");
        $("#loadDocument").addClass("success-input");
        deleteMessages();
    }
});

$('#tipoIdentificacion').on('blur', function (e) {
    let tipoIdentificacion = $("#tipoIdentificacion").val();
    if (!tipoIdentificacion) {
        Util.Message.error("El campo tipo de documento es requerido");
        $("#tipoIdentificacion").removeClass("success-input");
        $("#tipoIdentificacion").addClass("error-input");
        $('html,body').scrollTop(0);
    } else {
        $("#tipoIdentificacion").removeClass("error-input");
        $("#tipoIdentificacion").addClass("success-input");
        deleteMessages();
    }
});

$('#identification_number').on('blur', function (e) {
    let identification = $("#identification_number").val();
    if (!identification) {
        $("#identification_number").removeClass("success-input");
        $("#identification_number").addClass("error-input");
        $('html,body').scrollTop(0);
    } else {
        $("#identification_number").removeClass("error-input");
        $("#identification_number").addClass("success-input");
        deleteMessages();
    }
});

$('#fechaExpedicionDocumento').on('blur', function (e) {
    let Msjerror = "";
    let error = false;
    let issueDate = $("#fechaExpedicionDocumento").val();
    let today = new Date().setHours(0, 0, 0, 0);
    let givenDate = validateDate(issueDate).setHours(0,0,0,0);
    if (!issueDate) {
        Msjerror = "Ingresa la fecha de expedición valida antes de continuar";
        error = true;
    } else if (givenDate > today) {
        Msjerror = "La fecha de expedición no es valida ";
        error = true;
    }
    if(error){
        Util.Message.error(Msjerror);
        $("#fechaExpedicionDocumento").removeClass("success-input");
        $("#fechaExpedicionDocumento").addClass("error-input");
        $('html,body').scrollTop(0);
    }else {
        $("#fechaExpedicionDocumento").removeClass("error-input");
        $("#fechaExpedicionDocumento").addClass("success-input");
        deleteMessages();
    }
});

$('#grupoSanguineo').on('blur', function (e) {
    let grupoSanguineo = $("#grupoSanguineo").val();
    if (!grupoSanguineo) {
        Util.Message.error("El campo grupo sanguineo es requerido");
        $("#grupoSanguineo").removeClass("success-select");
        $("#grupoSanguineo").addClass("error-select");
        $('html,body').scrollTop(0);
    } else {
        $("#grupoSanguineo").removeClass("error-select");
        $("#grupoSanguineo").addClass("success-select");
        deleteMessages();
    }
});

$('#rh').on('blur', function (e) {
    let rh = $("#rh").val();
    if (!rh) {
        Util.Message.error("El campo rh es requerido");
        $("#rh").removeClass("success-select");
        $("#rh").addClass("error-select");
        $('html,body').scrollTop(0);
    } else {
        $("#rh").removeClass("error-select");
        $("#rh").addClass("success-select");
        deleteMessages();
    }
});

$('#home_address').on('blur', function (e) {
    let address = $("#home_address").val();
    if (!address) {
        Util.Message.error("El campo dirección de domicilio es requerido");
        $("#home_address").removeClass("success-input");
        $("#home_address").addClass("error-input");
        $('html,body').scrollTop(0);
    } else {
        $("#home_address").removeClass("error-input");
        $("#home_address").addClass("success-input");
        deleteMessages();
    }
});


$('#eps').on('blur', function (e) {
    if (!$("#eps").val()) {
        Util.Message.error("El campo eps es requerido");
        $("#eps").removeClass("success-select");
        $("#eps").addClass("error-select");
        $('html,body').scrollTop(0);
    } else {
        $("#eps").removeClass("error-select");
        $("#eps").addClass("success-select");
        deleteMessages();
    }
});

$('#afp').on('blur', function (e) {
    if (!$("#afp").val()) {
        Util.Message.error("El campo fondo de pensiones es requerido");
        $("#afp").removeClass("success-select");
        $("#afp").addClass("error-select");
        $('html,body').scrollTop(0);
    } else {
        $("#afp").removeClass("error-select");
        $("#afp").addClass("success-select");
        deleteMessages();
    }
});

$('#arl').on('blur', function (e) {
    if (!$("#arl").val()) {
        Util.Message.error("El campo ARL es requerido");
        $("#arl").removeClass("success-select");
        $("#arl").addClass("error-select");
        $('html,body').scrollTop(0);
    } else {
        $("#arl").removeClass("error-select");
        $("#arl").addClass("success-select");
        deleteMessages();
    }
});

$('#idOperadorPila').on('blur', function (e) {
    if (!$("#idOperadorPila").val()) {
        Util.Message.error("El campo operador de recaudo es requerido");
        $("#idOperadorPila").removeClass("success-select");
        $("#idOperadorPila").addClass("error-select");
        $('html,body').scrollTop(0);
    } else {
        $("#idOperadorPila").removeClass("error-select");
        $("#idOperadorPila").addClass("success-select");
        deleteMessages();
    }
});

$('#driver_payment_period').on('blur', function (e) {
    let Msjerror = "";
    let error = false;
    let driverPaymentPeriod = $("#driver_payment_period").val();
    let regexVar = /^([0-9]{2})\/([0-9]{4})$/;
    let regexVarTest = regexVar.test(driverPaymentPeriod);
    let today = new Date();
    today.setDate(1);
    today.setHours(12);
    today.setMinutes(0);
    today.setSeconds(0);
    today.setMilliseconds(0);
    let lastMonth = new Date(today.getFullYear(), today.getMonth() - 2, 1, 12, 0, 0, 0);
    let nextMonth = new Date(today.getFullYear(), today.getMonth() + 1 , 1, 12, 0, 0, 0);
    let period = driverPaymentPeriod.split("/");
    let datePeriod = new Date(period[1], period[0] - 1, 1, 12, 0, 0, 0);

    if (!regexVarTest) { // Test this before the other tests
        Msjerror = "Ingrese un periodo de pago con el formato mm/aaaa";
        error = true;
    } else if (isNaN(datePeriod)) {
        Msjerror = "Ingresa un periodo de pago antes de continuar";
        error = true;
    } else if (datePeriod.getTime() > nextMonth.getTime() || datePeriod.getTime() < lastMonth.getTime()) {
        Msjerror += "Periodo de pago invalido.";
        error = true;
    }

    if(error){
        Util.Message.error(Msjerror);
        $("#driver_payment_period").removeClass("success-input");
        $("#driver_payment_period").addClass("error-input");
        $('html,body').scrollTop(0);
    }else {
        $("#driver_payment_period").removeClass("error-input");
        $("#driver_payment_period").addClass("success-input");
        deleteMessages();
    }

});

$('#fechaVencimientoSoat').on('blur', function (e) {
    let Msjerror = "";
    let error = false;
    let dueDateValue = $("#fechaVencimientoSoat").val();
    let today = new Date().setHours(0, 0, 0, 0);
    let givenDate = validateDate(dueDateValue).setHours(0,0,0,0);
    if (!dueDateValue ) {
        Msjerror = "Ingresa la fecha de vencimiento SOAT valida antes de continuar";
        error = true;
    } else if (givenDate < today) {
        Msjerror = "La fecha de vencimiento SOAT no puede ser inferior a hoy ";
        error = true;
    }
    if(error){
        Util.Message.error(Msjerror);
        $("#fechaVencimientoSoat").removeClass("success-input");
        $("#fechaVencimientoSoat").addClass("error-input");
        $('html,body').scrollTop(0);
    }else {
        $("#fechaVencimientoSoat").removeClass("error-input");
        $("#fechaVencimientoSoat").addClass("success-input");
        deleteMessages();
    }
});

$('#fechaVencimientoRtm').on('blur', function (e) {
    let Msjerror = "";
    let error = false;
    let dueDateValue = $("#fechaVencimientoRtm").val();
    let today = new Date().setHours(0, 0, 0, 0);
    let givenDate = validateDate(dueDateValue).setHours(0,0,0,0);
    if (!dueDateValue ) {
        Msjerror = "Ingresa la fecha de vencimiento RTM valida antes de continuar";
        error = true;
    } else if ( givenDate < today ) {
        Msjerror = "La fecha de vencimiento RTM no puede ser inferior a hoy ";
        error = true;
    }
    if(error){
        Util.Message.error(Msjerror);
        $("#fechaVencimientoRtm").removeClass("success-input");
        $("#fechaVencimientoRtm").addClass("error-input");
        $('html,body').scrollTop(0);
    }else {
        $("#fechaVencimientoRtm").removeClass("error-input");
        $("#fechaVencimientoRtm").addClass("success-input");
        deleteMessages();
    }
});

$('#fechaVencimientoTO').on('blur', function (e) {
    let Msjerror = "";
    let error = false;
    let dueDateValue = $("#fechaVencimientoTO").val();
    let today = new Date().setHours(0, 0, 0, 0);
    let givenDate = validateDate(dueDateValue).setHours(0,0,0,0);
    if (!dueDateValue ) {
        Msjerror = "Ingresa la fecha de vencimiento TO valida antes de continuar";
        error = true;
    } else if (givenDate < today) {
        Msjerror = "La fecha de vencimiento TO no puede ser inferior a hoy ";
        error = true;
    }
    if(error){
        Util.Message.error(Msjerror);
        $("#fechaVencimientoTO").removeClass("success-input");
        $("#fechaVencimientoTO").addClass("error-input");
        $('html,body').scrollTop(0);
    }else {
        $("#fechaVencimientoTO").removeClass("error-input");
        $("#fechaVencimientoTO").addClass("success-input");
        deleteMessages();
    }
});

$('#phone_number').on('blur', function (e) {
    if (!isValidPhoneNumber()) {
        Util.Message.error("El tamaño del numero de telefono debe ser de 10 digitos. \n");
        $('html,body').scrollTop(0);
    }else{
        pageMessageError: null;
        deleteMessages();
    }
});

$('#approbal_number').on('blur', function (e) {
    if (!isValidApprovalNumber()) {
        Util.Message.error("El tamaño del numero de planilla debe ser de 12 digitos. \n");
        $('html,body').scrollTop(0);
    }else{
        pageMessageError: null;
        deleteMessages();
    }
});

$("#picture-profile").on('change', function () {
    $(".alert").alert("close");
    $('#fotoConductor').val("");
    var size = document.getElementById('picture-profile').files[0].size;
    size = size / 1024;

    var FileUploadPath = document.getElementById('picture-profile').value;

    if (size <= 300) {
        if (FileUploadPath != '') {
            var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
            if (Extension == "jpg") {
                pdffile = document.getElementById("picture-profile").files[0];
                pdffile_url = URL.createObjectURL(pdffile);
                $('#previewPicture').attr('src', pdffile_url);
            } else {
                Util.Message.error("La extensión de la imagen debe ser .jpg");
                $('html,body').scrollTop(0);
            }
        }
    } else {
        Util.Message.error("La imagen no debe sobrepasar los 300kb.");
        $('html,body').scrollTop(0);
    }
});

$("#nroSOAT").on('blur', function () {
    if (!isValidNumber($("#nroSOAT").val())) {
        $("#nroSOAT").removeClass("success-input");
        $("#nroSOAT").addClass("error-input");
        Util.Message.error("El campo Número SOAT solamente admite valores numericos. \n");
        $('html,body').scrollTop(0);
    }else{
        $("#nroSOAT").removeClass("error-input");
        $("#nroSOAT").addClass("success-input");
        deleteMessages();
    }
});

$("#nroTarjetaOperacion").on('blur', function () {
    if (!isValidNumber($("#nroTarjetaOperacion").val())) {
        $("#nroTarjetaOperacion").removeClass("success-input");
        $("#nroTarjetaOperacion").addClass("error-input");
        Util.Message.error("El campo Número Tarjeta Operacion solamente admite valores numericos. \n");
        $('html,body').scrollTop(0);
    }else{
        $("#nroTarjetaOperacion").removeClass("error-input");
        $("#nroTarjetaOperacion").addClass("success-input");
        deleteMessages();
    }
});

$("#nroRTM").on('blur', function () {
    if (!isValidNumber($("#nroRTM").val())) {
        $("#nroRTM").removeClass("success-input");
        $("#nroRTM").addClass("error-input");
        Util.Message.error("El campo Número RTM solamente admite valores numericos. \n");
        $('html,body').scrollTop(0);
    }else{
        $("#nroRTM").removeClass("error-input");
        $("#nroRTM").addClass("success-input");
        deleteMessages();
    }
});

$("#codigoEmpresa").on('change', function () {
    if ( $("#codigoEmpresa").val() && $("#car_id").val()) {
        /*$("#formDriver").attr("action", "consultar-factor-qa?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formDriver').submit();*/
    } else {
        $("#factorCalidad").val(false);
        $("#factorCalidad").attr('checked', false);
        deleteMessages();
    }

});

$('#car_id').on('blur', function () {
    if(isValidPlate()){
        deleteMessages();
        $("#formDriver").attr("action", "check-plate-driver?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formDriver').submit();

    }else{
        Util.Message.error("El formato de la placa es invalido, deberia ser AAA999. \n");
        $('html,body').scrollTop(0);
    }

});

$('#tab-vehicle').on('focus', function (e){
    if(!validateDriverData()){
        $("#tab-driver").click();
    }else{
        deleteMessages();
    }
});

$('#tab-manage-card').on('focus', function (e){
    if(!validateDriverData()){
        $("#tab-driver").click();
    } else if(!validateVehicleData()){
        $("#tab-vehicle").click();
    }else{
        deleteMessages();
    }
});

function validateVehicleData(){
    let invalid = false;
    if (!$("#car_id").val()) {
        $("#car_id").addClass("error-select");
        invalid = true;
    } else {
        $("#car_id").removeClass("error-select");
        $("#car_id").addClass("success-select");
    }
    if ($("#codigoEmpresa").val() == null) {
        $("#codigoEmpresa").addClass("error-select");
        invalid = true;
    } else {
        $("#codigoEmpresa").removeClass("error-select");
        $("#codigoEmpresa").addClass("success-select");
    }
    if (!$("#company_razonSocial").val()) {
        $("#company_razonSocial").addClass("error-select");
        invalid = true;
    } else {
        $("#company_razonSocial").removeClass("error-select");
        $("#company_razonSocial").addClass("success-select");
    }
    if (!$("#nit").val()) {
        $("#nit").addClass("error-select");
        invalid = true;
    } else {
        $("#nit").removeClass("error-select");
        $("#nit").addClass("success-select");
    }
    if (!$("#fechaVencimientoSoat").val()) {
        $("#fechaVencimientoSoat").addClass("error-select");
        invalid = true;
    } else {
        $("#fechaVencimientoSoat").removeClass("error-select");
        $("#fechaVencimientoSoat").addClass("success-select");
    }
    if (!$("#nroSOAT").val()) {
        $("#nroSOAT").addClass("error-select");
        invalid = true;
    } else {
        $("#nroSOAT").removeClass("error-select");
        $("#nroSOAT").addClass("success-select");
    }
    if (!$("#fechaVencimientoRtm").val()) {
        $("#fechaVencimientoRtm").addClass("error-select");
        invalid = true;
    } else {
        $("#fechaVencimientoRtm").removeClass("error-select");
        $("#fechaVencimientoRtm").addClass("success-select");
    }
    if (!$("#nroRTM").val()) {
        $("#nroRTM").addClass("error-select");
        invalid = true;
    } else {
        $("#nroRTM").removeClass("error-select");
        $("#nroRTM").addClass("success-select");
    }
    if (!$("#nroRTM").val()) {
        $("#nroRTM").addClass("error-select");
        invalid = true;
    } else {
        $("#nroRTM").removeClass("error-select");
        $("#nroRTM").addClass("success-select");
    }
    if (!$("#nroTarjetaOperacion").val()) {
        $("#nroTarjetaOperacion").addClass("error-input");
        invalid = true;
    } else {
        $("#nroTarjetaOperacion").removeClass("error-input");
        $("#nroTarjetaOperacion").addClass("success-input");
    }
    if (!$("#fechaVencimientoTO").val()) {
        $("#fechaVencimientoTO").addClass("error-input");
        invalid = true;
    } else {
        $("#fechaVencimientoTO").removeClass("error-input");
        $("#fechaVencimientoTO").addClass("success-input");
    }
    if ($("#idMetodoCobro").val() == null) {
        $("#idMetodoCobro").addClass("error-select");
        invalid = true;
    } else {
        $("#idMetodoCobro").removeClass("error-select");
        $("#idMetodoCobro").addClass("success-select");
    }
    if ($("#idTipoServicio").val() == null) {
        $("#idTipoServicio").addClass("error-select");
        invalid = true;
    } else {
        $("#idTipoServicio").removeClass("error-select");
        $("#idTipoServicio").addClass("success-select");
    }
    if (invalid) {
        Util.Message.error("Existen campos obligatorios que no se han registrado. \n");
        $('html,body').scrollTop(0);
        return false;
    }
    return true;
}

function validateDriverData(){
    let invalid = false;
    if (!$("#first_name").val()) {
        $("#first_name").addClass("error-select");
        invalid = true;
    } else {
        $("#first_name").removeClass("error-select");
        $("#first_name").addClass("success-select");
    }
    if (!$("#last_name").val()) {
        $("#last_name").addClass("error-select");
        invalid = true;
    } else {
        $("#last_name").removeClass("error-select");
        $("#last_name").addClass("success-select");
    }
    if (!$("#fechaNacimiento").val()) {
        $("#fechaNacimiento").addClass("error-select");
        invalid = true;
    } else {
        $("#fechaNacimiento").removeClass("error-select");
        $("#fechaNacimiento").addClass("success-select");
    }
    console.log($("#uriFotoCOnductor").val());
    if (!$("#loadDocument").val() && !$("#fotoConductor").val() && !$("#uriFotoCOnductor").val()) {
        $("#loadDocument").addClass("error-select");
        invalid = true;
    } else {
        $("#loadDocument").removeClass("error-select");
        $("#loadDocument").addClass("success-select");
    }
    if ($("#tipoIdentificacion").val() == null) {
        $("#tipoIdentificacion").addClass("error-select");
        invalid = true;
    } else {
        $("#tipoIdentificacion").removeClass("error-select");
        $("#tipoIdentificacion").addClass("success-select");
    }
    if (!$("#identification_number").val()) {
        $("#identification_number").addClass("error-select");
        invalid = true;
    } else {
        $("#identification_number").removeClass("error-select");
        $("#identification_number").addClass("success-select");
    }
    if (!$("#fechaExpedicionDocumento").val()) {
        $("#fechaExpedicionDocumento").addClass("error-select");
        invalid = true;
    } else {
        $("#fechaExpedicionDocumento").removeClass("error-select");
        $("#fechaExpedicionDocumento").addClass("success-select");
    }
    if ($("#grupoSanguineo").val() == null) {
        $("#grupoSanguineo").addClass("error-select");
        invalid = true;
    } else {
        $("#grupoSanguineo").removeClass("error-select");
        $("#grupoSanguineo").addClass("success-select");
    }
    if ($("#rh").val() == null) {
        $("#rh").addClass("error-select");
        invalid = true;
    } else {
        $("#rh").removeClass("error-select");
        $("#rh").addClass("success-select");
    }
    if (!$("#phone_number").val()) {
        $("#phone_number").addClass("error-select");
        invalid = true;
    } else {
        $("#phone_number").removeClass("error-select");
        $("#phone_number").addClass("success-select");
    }
    if (!$("#home_address").val()) {
        $("#home_address").addClass("error-select");
        invalid = true;
    } else {
        $("#home_address").removeClass("error-select");
        $("#home_address").addClass("success-select");
    }
    if ($("#arl").val() == null) {
        $("#arl").addClass("error-select");
        invalid = true;
    } else {
        $("#arl").removeClass("error-select");
        $("#arl").addClass("success-select");
    }
    if ($("#afp").val() == null) {
        $("#afp").addClass("error-select");
        invalid = true;
    } else {
        $("#afp").removeClass("error-select");
        $("#afp").addClass("success-select");
    }
    if ($("#eps").val() == null) {
        $("#eps").addClass("error-select");
        invalid = true;
    } else {
        $("#eps").removeClass("error-select");
        $("#eps").addClass("success-select");
    }
    if (!$("#idOperadorPila").val() ) {
        $("#idOperadorPila").addClass("error-select");
        invalid = true;
    } else {
        $("#idOperadorPila").removeClass("error-select");
        $("#idOperadorPila").addClass("success-select");
    }
    if (!$("#driver_payment_period").val()) {
        $("#driver_payment_period").addClass("error-input");
        invalid = true;
    } else {
        $("#driver_payment_period").removeClass("error-input");
        $("#driver_payment_period").addClass("success-input");
    }
    if (!$("#approbal_number").val()) {
        $("#approbal_number").addClass("error-input");
        invalid = true;
    } else {
        $("#approbal_number").removeClass("error-input");
        $("#approbal_number").addClass("success-input");
    }
    if (invalid) {
        Util.Message.error("Existen campos obligatorios que no se han registrado. \n");
        $('html,body').scrollTop(0);
        return false;
    }
    return true;
}

function validateCompany() {
    if ($("#codigoEmpresa").val()) {
        $('#message-company').show();
    } else {
        $('#message-company').hide();
    }
}

function isValidNumber(numericValue){
    let regexVar = /^\d+$/;
    return regexVar.test(numericValue)
}

function isValidApprovalNumber(){
    let regexVar = /^[0-9]{12}$/;
    let approvalNumber = $("#approbal_number").val();
    let statusValidation;
    if (regexVar.test(approvalNumber)) {
        $("#approbal_number").removeClass("error-input");
        $("#approbal_number").addClass("success-input");
        statusValidation = true;
    } else {
        $("#approbal_number").removeClass("success-input");
        $("#approbal_number").addClass("error-input");
        statusValidation = false;
    }
    return statusValidation;
}

function isValidPhoneNumber(){
    let regexVar = /^[0-9]{10}$/;
    let phoneNumber = $("#phone_number").val();
    let statusValidation;
    if (regexVar.test(phoneNumber)) {
        $("#phone_number").removeClass("error-input");
        $("#phone_number").addClass("success-input");
        statusValidation = true;
    } else {
        $("#phone_number").removeClass("success-input");
        $("#phone_number").addClass("error-input");
        statusValidation = false;
    }

    return statusValidation;

}


function validateDate(date){
    let regexVar = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
    let regexVarTest = regexVar.test(date);
    console.log(regexVarTest)
    if (!regexVarTest) {
        return false;
    }

    return new Date(date.replace(regexVar, "$3-$2-$1"));
}

function isValidPlate(){
    let plate = $("#car_id").val();
    let statusValidation;
    let regexVar = /^[A-Za-z]{3}[0-9]{3}$/;
    if (!regexVar.test(plate)) {
        $("#car_id").removeClass("success-input");
        $("#car_id").addClass("error-input");
        statusValidation = false;
    } else {
        $("#car_id").removeClass("error-input");
        $("#car_id").addClass("success-input");
        statusValidation = true;
    }
    return statusValidation;
}

function isValidDate(date) {
    let regexVar = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
    let year = date.replace(regexVar, "$3");
    let month = Number(date.replace(regexVar, "$2"))-1;
    let day = date.replace(regexVar, "$1");
    let d = new Date(date.replace(regexVar, "$3-$2-$1"));
    if (d.getFullYear() == year && d.getMonth() == month && d.getDate() == day) {
        return true;
    }
    return false;
}

function fotoConductor(file) {
    let reader = new FileReader();
    reader.onload = function () {
        let binaryString = this.result;
        let foto = window.btoa(binaryString);
        $('#fotoConductor').val(foto);
    };
    if (file) {
        reader.readAsBinaryString(file);
    } else {
        $('#fotoConductor').val("");
    }
}

