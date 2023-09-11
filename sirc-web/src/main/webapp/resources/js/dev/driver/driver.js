var cancelar = false;

$(window).load(function () {
    $('.datepicker').datetimepicker({
        format: 'DD/MM/YYYY'
    });
    $("#formDriver").find("input[type=text], textarea").prop('readonly', false);;
    $("#formDriver").find("input[type=file]").prop('readonly', false);;
    $("#formDriver").find("select").prop('readonly', false);;
    $("#btn-examinar").attr('disabled', false);
    $("#tipoTransaccionConsul").val($("#tipoTransaccion option:selected").val());
    $("#car_id").prop('readonly', true);
    $("#btn-agregar-planilla").attr('disabled', false);
    $("#btn-eliminar-planilla").attr('disabled', false);
    
    if ($("#tipoTransaccion").val() && $("#tipoTransaccion").val() == 1 && $("#codigoEmpresa").val()) {
        $("#car_id").prop('readonly', false);
    }
    $("#formDriver").find("input[type=file]").prop('readonly', true);
    $("#first_name").prop('readonly', true);
    $("#last_name").prop('readonly', true);
    $("#fechaNacimiento").prop('readonly', true);
    $("#tipoIdentificacion").attr('disabled', 'disabled');
    $("#tipoTransaccion").prop('readonly', false);;
    $("#identification_number").prop('readonly', true);
    $("#fechaExpedicionDocumento").prop('readonly', true);
    $("#grupoSanguineo").attr('disabled', 'disabled');
    $("#rh").attr('disabled', 'disabled');
    $("#codigoEmpresa").attr('disabled', 'disabled');
    $("#company_razonSocial").prop('readonly', true);
    $("#nit").prop('readonly', true);
    $("#fechaVencimientoSoat").prop('readonly', false);
    $("#nroSOAT").prop('readonly', false);
    $("#fechaVencimientoRtm").prop('readonly', false);
    $("#nroRTM").prop('readonly', false);
    $("#nroTarjetaOperacion").prop('readonly', false);
    $("#fechaVencimientoTO").prop('readonly', false);
    $("#factorCalidad").prop('readonly', true);
    $("#idTipoServicio").prop('readonly', true);
    $('#fechasTC').css('display', 'inline');
    $("#idMetodoCobro").prop('disabled', true);

    $("#fechaVencimientoSoat").datetimepicker({format: 'DD/MM/YYYY'});
    $("#fechaVencimientoRtm").datetimepicker({format: 'DD/MM/YYYY'});
    $("#fechaVencimientoTO").datetimepicker({format: 'DD/MM/YYYY'});

    document.getElementById("fechaNacimiento").pattern = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d";
    document.getElementById("fechaNacimiento").required = true;
    document.getElementById("fechaExpedicionDocumento").pattern = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d";
    document.getElementById("fechaExpedicionDocumento").required = true;
    document.getElementById("fechaVencimientoSoat").pattern = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d";
    document.getElementById("fechaVencimientoSoat").required = true;
    document.getElementById("fechaVencimientoRtm").pattern = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d";
    document.getElementById("fechaVencimientoRtm").required = true;
    document.getElementById("fechaVencimientoTO").pattern = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d";
    document.getElementById("fechaVencimientoTO").required = true;
    document.getElementById("driver_payment_period").pattern = "(0[1-9]|1[012])[/](19|20)\\d\\d";
    document.getElementById("driver_payment_period").required = true;

    let tipoTramite = $("#tipoTramite").val();
    if(tipoTramite === 'CANCELADO'){
        $('#btn_save').text('Expedir Tarjeta de Control');
        $('#btn_save').css('display', 'inline');
        $('#btn_cancelar').css('display', 'none');
        $('#tipoTransaccion').val(1);
        $('#dueDate').css('display', 'none');
		$("#formDriver").attr("action", "create-driver-reexpedicion?_csrf=" + $("meta[name='_csrf']").attr("content"));
    }else if(tipoTramite === 'REFRENDADA'){
        $('#btn_save').text('Refrendar Tarjeta de Control');
        $('#btn_save').css('display', 'inline');
        $('#tipoTransaccion').val(2);
        $('#btn_cancelar').css('display', 'inline');
		$("#formDriver").attr("action",  "create-driver-refrendacion?_csrf=" + $("meta[name='_csrf']").attr("content"));
    }else{
        $('#btn_save').css('display', 'none');
        $('#btn_cancelar').css('display', 'none');
    }

});

$('formDriver').on('focus', 'input[type=number]', function (e) {
    $(this).on('mousewheel.disableScroll', function (e) {
        e.preventDefault()
    })
})

$('formDriver').on('blur', 'input[type=number]', function (e) {
    $(this).off('mousewheel.disableScroll')
})

$("#btn-next-driver").on('click', function () {
    if(validateDriverData()){
        $("#tab-vehicle").click();
    }
});

$("#btn-next-vehicle").on('click', function () {
    if(validateVehicleData()){
        $("#tab-manage-card").click();
    }
});

$('#btn_save').on('click', function (e){
    if(!validateDriverData()){
        $("#tab-driver").click();
    } else if(!validateVehicleData()){
        $("#tab-vehicle").click();
    } else{
        $('#formDriver').submit();
    }
});

$("#btn-agregar-planilla").on('click', function () {
    $("#btn-agregar-planilla").prop('readonly', true);
    let tipoTramite = $("#tipoTramite").val();
    $("#tipoTransaccion").val(5);
    $("#tipoIdentificacion").prop('disabled', false);
    $("#grupoSanguineo").prop('disabled', false);
    $("#codigoEmpresa").prop('disabled', false);
    $("#rh").prop('disabled', false);
    $("#formDriver").attr("action", "agregar-planilla?action=add&t="+tipoTramite+"&_csrf=" + $("meta[name='_csrf']").attr("content"));
    $('#formDriver').submit();
    $("#btn-agregar-planilla").prop('readonly', false);;
});

$("#btn-eliminar-planilla").on('click', function () {
    $("#btn-eliminar-planilla").prop('readonly', true);
    let tipoTramite = $("#tipoTramite").val();
    $("#tipoTransaccion").val(5);
    $("#tipoIdentificacion").prop('disabled', false);
    $("#grupoSanguineo").prop('disabled', false);
    $("#codigoEmpresa").prop('disabled', false);
    $("#rh").prop('disabled', false);
    $("#formDriver").attr("action", "agregar-planilla?action=delete&t="+tipoTramite+"&_csrf=" + $("meta[name='_csrf']").attr("content"));
    $('#formDriver').submit();
    $("#btn-eliminar-planilla").prop('readonly', false);;
});



$('#btn_cancel_target').click(function () {
    $('#cancelTarget').modal('hide');
    $('#tipoTransaccion').val('3');
    cancelar = true;
	$("#formDriver").attr("action",  "create-driver-cancelacion?_csrf=" + $("meta[name='_csrf']").attr("content"));
    $("#formDriver").submit();
});

$('#btn_cancelar').click(function () {
    $('#cancelTarget').modal('show');
});


$('#previewPicture').on('load', function () {
    $('#previewPicture').contents().find('img').css({
        'width': '100%',
        'height': '90%'
    });
});

