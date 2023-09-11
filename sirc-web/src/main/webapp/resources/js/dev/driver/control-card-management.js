var expedir = false;

$(window).load(function () {
    $('#btn_save').css('display', 'none');
    $('#btn_cancel').css('display', 'none');
    let tipoTramite = $("#tipoTramite").val();
    console.log(tipoTramite)
    if(tipoTramite === 'CANCELADO'){
        $('#manageCardData').removeClass('hidden');
        $('#btn_save').text('Expedir Tarjeta de Control');
        $('#btn_save').css('display', 'inline');
        $('#btn_cancel').css('display', 'none');
        expedir = false;
    }else if(tipoTramite === 'REFRENDADA'){
        $('#manageCardData').removeClass('hidden');
        $('#btn_save').text('Refrendar Tarjeta de Control');
        $('#btn_save').css('display', 'inline');
        $('#btn_cancel').css('display', 'inline');
        expedir = false;
    }

});

$('#btn_cancel_target').click(function () {
    $('#cancelTarget').modal('hide');
    $('#tipoTransaccion').val('3');
    cancelar = true;
    $("#formConsultarTarjeta").attr("method",  "POST");
    $("#formConsultarTarjeta").attr("action",  "create-driver-cancelacion?_csrf=" + $("meta[name='_csrf']").attr("content"));
    $("#formConsultarTarjeta").submit();
});

$('#btn_cancel').click(function () {
    $('#cancelTarget').modal('show');
});

$('#newCardLink').click(function () {
    $('#manageCardData').removeClass('hidden');
    $('#btn_save').text('Expedir Tarjeta de Control');
    $('#btn_save').css('display', 'inline');
    $('#btn_cancel').css('display', 'none');
    expedir = true;
});

$('#btn_save').on('click', function (e){
    if(expedir){
        $("#formConsultarTarjeta").attr("action",  "create-control-card?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formConsultarTarjeta').submit();
    }else{
        $("#formConsultarTarjeta").attr("action",  "manage-control-card?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formConsultarTarjeta').submit();
    }
});

$('formControlCard').on('focus', 'input[type=number]', function (e) {
    $(this).on('mousewheel.disableScroll', function (e) {
        e.preventDefault()
    })
})

$('formControlCard').on('blur', 'input[type=number]', function (e) {
    $(this).off('mousewheel.disableScroll')
})




