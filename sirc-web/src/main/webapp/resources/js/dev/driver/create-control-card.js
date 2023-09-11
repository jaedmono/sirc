let cancelar = false;

$(window).load(function () {
    $('#btn_save').text('Expedir tarjeta de control');
    $('#btn_save').css('display', 'inline');

    $("#formDriver").find("input[type=text], textarea").prop('readonly', false);
    $("#formDriver").find("input[type=file]").prop('readonly', false);
    $("#btn-examinar").attr('disabled', false);
    $("#btn-agregar-planilla").attr('disabled', false);
    $("#btn-eliminar-planilla").attr('disabled', false);
    $("#btn-consultarT").attr('disabled', false);
    $("#company_razonSocial").prop('readonly', true);
    $("#nit").prop('readonly', true);
    $("#idMetodoCobro").prop('disabled', true);

    $("#fechaNacimiento").datetimepicker({format: 'DD/MM/YYYY'});
    $("#fechaExpedicionDocumento").datetimepicker({format: 'DD/MM/YYYY'});
    $("#fechaVencimientoSoat").datetimepicker({format: 'DD/MM/YYYY'});
    $("#fechaVencimientoRtm").datetimepicker({format: 'DD/MM/YYYY'});
    $("#fechaVencimientoTO").datetimepicker({format: 'DD/MM/YYYY'});


    if($("#plateValidated").val() === "1"){
        $("#tab-vehicle").click();
    }


    if (document.getElementById("fotoConductor").value != "") {
        document.getElementById("loadDocument").value = "FOTO.JPG";
    }
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

});

$('formDriver').on('focus', 'input[type=number]', function (e) {
    $(this).on('mousewheel.disableScroll', function (e) {
        e.preventDefault()
    })
})

$('formDriver').on('blur', 'input[type=number]', function (e) {
    $(this).off('mousewheel.disableScroll')
})



$('#previewPicture').on('load', function () {
    $('#previewPicture').contents().find('img').css({
        'width': '100%',
        'height': '90%'
    });
});

$("#control_card_number").on('change', function () {
    $("#nroTarjeta").val(this.value);
});

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

$("#btn-agregar-planilla").on('click', function () {
    $("#btn-agregar-planilla").prop('readonly', true);
    let tipoTramite = $("#tipoTramite").val();
    $("#tipoTransaccion").val(5);
    $("#formDriver").attr("action", "agregar-planilla?action=add&t="+tipoTramite+"&_csrf=" + $("meta[name='_csrf']").attr("content"));
    $('#formDriver').submit();
    $("#btn-agregar-planilla").prop('readonly', false);;
});

$("#btn-eliminar-planilla").on('click', function () {
    $("#btn-eliminar-planilla").prop('readonly', true);
    let tipoTramite = $("#tipoTramite").val();
    $("#tipoTransaccion").val(5);
    $("#formDriver").attr("action", "agregar-planilla?action=delete&t="+tipoTramite+"&_csrf=" + $("meta[name='_csrf']").attr("content"));
    $('#formDriver').submit();
    $("#btn-eliminar-planilla").prop('readonly', false);;
});

$('#btn_save').on('click', function (e){
    if(!validateDriverData()){
        $("#tab-driver").click();
    } else if(!validateVehicleData()){
        $("#tab-vehicle").click();
    } else{
        $("#idMetodoCobro").prop('disabled', false);
        $("#formDriver").attr("action", "create-driver-expedicion?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formDriver').submit();
    }
});



$('#btn_cancel_target').click(function () {
    $('#cancelTarget').modal('hide');
    cancelar = true;
    $("#formDriver").submit();
});

$(function () {
    $(document).on('change', ':file', function () {
        var size = document.getElementById('picture-profile').files[0].size;
        size = size / 1024;
        var FileUploadPath = document.getElementById('picture-profile').value;
        if (size <= 300) {
            if (FileUploadPath != '') {
                var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
                if (Extension == "jpg") {
                    var input = $(this), numFiles = input.get(0).files ? input.get(0).files.length : 1, label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                    input.trigger('fileselect', [numFiles, label]);
                }
            }
        }
    });
    $(document).ready(function () {
        $(':file').on('fileselect', function (event, numFiles, label) {
            var size = document.getElementById('picture-profile').files[0].size;
            size = size / 1024;
            var FileUploadPath = document.getElementById('picture-profile').value;
            if (size <= 300) {
                if (FileUploadPath != '') {
                    var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
                    if (Extension == "jpg") {
                        var input = $(this).parents('.input-group').find(':text'), log = numFiles > 1 ? numFiles + ' files selected' : label;
                        if (input.length) {
                            input.val(log);
                        } else {
                            if (log)
                                console.log(log);
                        }
                        fotoConductor(document.getElementById('picture-profile').files[0]);
                    }
                }
            }
        });
    });
});
