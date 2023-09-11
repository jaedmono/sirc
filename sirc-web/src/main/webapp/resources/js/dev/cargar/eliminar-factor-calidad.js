
var idFQ = "";

$(window).load(function () {
    idFQ = $("#idFactorQa").val();
    document.getElementById("divFactorQa").style.display = "none";
    if (idFQ) {
        document.getElementById("divConsultarFQ").style.display = "none";
        $("#nroPlaca").prop('disabled', true);
        $("#btn-consultar-factor-qa").attr('disabled', true);
        document.getElementById("divFactorQa").style.display = "";
    }else {
        cancelar();
    }
});

$('#btnCancelar').click(function () {
    borrarMnesajes();
    cancelar();
});

$("#btn-eliminar-factor-qa").on("click", function () {
    idFQ = $("#idFactorQa").val();
    if (idFQ) {
        $("#formEliminarFactorCalidad").attr("method", "POST").attr("action",
                "modificar-factor-calidad-vehiculo" + "?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formEliminarFactorCalidad').submit();
    } else {
        cancelar();
    }
});

function cancelar() {
    idFQ = "";
    document.getElementById("divConsultarFQ").style.display = "";
    $("#nroPlaca").prop('disabled', false);
    $("#btn-consultar-factor-qa").attr('disabled', false);
    document.getElementById("divFactorQa").style.display = "none";
    $("#formEliminarFactorCalidad").attr("method", "GET").attr("action", "modificar-factor-calidad");
}

function borrarMnesajes() {
    var msj = document.getElementById("page-message-container");
    while (msj.hasChildNodes()) {
        msj.removeChild(msj.lastChild);
    }
}