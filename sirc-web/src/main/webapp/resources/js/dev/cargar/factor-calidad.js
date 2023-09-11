
var registros = [];
var registrosFallidos = '';
var totalReg = 0;
var regValidos = 0;
var regFallidos = 0;

$(window).load(function () {
    $("#formFactorCalidad").find("input[type=file]").prop('disabled', false);
    document.getElementById("btnUpload").style.display = "";
    document.getElementById("btnsCargue").style.display = "none";
    document.getElementById("divDetalle").style.display = "none";
    document.getElementById("divDetalleFallidos").style.display = "none";
    document.getElementById("regFallidos").innerHTML = "";
    $("#formEliminarFactorCalidad").attr('disabled', false);
});

document.querySelector('#factorqa-upload').addEventListener('change', function () {
    borrarMnesajes();
    if (this.files && this.files.length > 0) {
        var reader = new FileReader();
        reader.onload = function () {
            registros = [];
            registrosFallidos = '<br />';
            totalReg = 0;
            regValidos = 0;
            regFallidos = 0;
            var lines = this.result.split('\n');
            if (lines && lines.length > 0) {
                for (var i = 0; i < lines.length; i++) {
                    if (lines[i] && lines[i].trim().length > 0) {
                        totalReg++;
                        if (validarInformacion(lines[i])) {
                            registros.push(lines[i]);
                            regValidos++;
                        } else {
                            var fallido = 'Líena: ' + i + ', Registro: ' + lines[i] + '<br />';
                            registrosFallidos += fallido;
                            regFallidos++;
                        }
                    }
                }
                document.getElementById("btnUpload").style.display = "none";
                document.getElementById("btnsCargue").style.display = "";
                document.getElementById("divDetalle").style.display = "";
                document.getElementById("registrosValidos").value = registros;
                document.getElementById("totalRegArchivo").innerHTML = totalReg;
                document.getElementById("regValidos").innerHTML = regValidos;
                document.getElementById("cantidadFallidos").innerHTML = regFallidos;
                if (regFallidos > 0) {
                    document.getElementById("divDetalleFallidos").style.display = "";
                    document.getElementById("regFallidos").innerHTML = registrosFallidos;
                }
            } else {
                Util.Message.error("Archivo sin datos.");
            }
        };
        if (this.files[0].type && this.files[0].type === 'text/plain') {
            document.getElementById("nombreArchivo").value = this.files[0].name;
            reader.readAsBinaryString(this.files[0]);
        } else {
            Util.Message.error("La extensión del archivo debe ser .txt.");
        }
    }
}, false);

function validarInformacion(registro) {
    var dato = registro.split('-');
    if (dato && dato.length === 2) {
        var placa = dato[0];
        var empresa = dato[1];
        var re = new RegExp("^[a-zA-Z]{3}[0-9]{3}$");
        if (empresa && empresa.trim().length > 0 && placa.match(re) !== null) {
            return true;
        }
    }
    return false;
}

$('#btnCancelar').click(function () {
    var $el = $('#factorqa-upload');
    $el.wrap('<form>').closest('form').get(0).reset();
    $el.unwrap();
    document.getElementById("nombreArchivo").value = '';
    document.getElementById("btnUpload").style.display = "";
    document.getElementById("btnsCargue").style.display = "none";
    document.getElementById("divDetalle").style.display = "none";
    document.getElementById("divDetalleFallidos").style.display = "none";
    document.getElementById("regFallidos").innerHTML = "";
});

function borrarMnesajes() {
    var msj = document.getElementById("page-message-container");
    while (msj.hasChildNodes()) {
        msj.removeChild(msj.lastChild);
    }
}

$("#btnCargar").on("click", function () {
    var nomArchivo = document.getElementById("nombreArchivo").value;
    var registros = document.getElementById("registrosValidos").value;
    if (nomArchivo && registros && registros.length > 0) {
        $("#formCargarArchivo").attr("action", "cargar-archivo-factor-calidad" + "?_csrf=" + $("meta[name='_csrf']").attr("content"));
        $('#formCargarArchivo').submit();
    } else {
        Util.Message.error("Debe cargar un archivo con registros validos.");
    }
});

$('#descargarFallidos').click(function () {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    var today = dd + '/' + mm + '/' + yyyy;
    var detalle = document.getElementById("detalleFallidos").textContent;
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(detalle));
    element.setAttribute('download', 'Registros fallidos cargue_' + today);
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
});