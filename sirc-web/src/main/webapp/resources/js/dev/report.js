$(window).load(function () {
	 $('#fecha-final').datetimepicker({
		locale : 'es',
		format: 'DD/MM/YYYY'
	});
	
	$('#fecha-inicial').datetimepicker({
		locale : 'es',
		format: 'DD/MM/YYYY',
	});

//	$('#fecha-final').datetimepicker({
//        useCurrent: false //Important! See issue #1075
//    });
	$("#fecha-final").on("dp.change", function (e) {
    	$('#fecha-inicial').data("DateTimePicker").maxDate(e.date);
	});
	$("#fecha-inicial").on("dp.change", function (e) {
        $('#fecha-final').data("DateTimePicker").minDate(e.date);
    });
    
    
//    $('.datepicker').on('dp.change', function(e) {
//    	var fechaInicial = $("#fechaInicial").val();
//    	var fechaFinal = $("#fechaFinal").val();
//        var matchUno = /(\d+)\/(\d+)\/(\d+)/.exec(fechaInicial);
//        var matchDos = /(\d+)\/(\d+)\/(\d+)/.exec(fechaFinal);
//        var fechaInicialReal = new Date(matchUno[3], matchUno[2], matchUno[1]);
//        var fechaFinalReal = new Date(matchDos[3], matchDos[2], matchDos[1]);
//    	if(fechaInicialReal > fechaFinalReal){
//    		$("#fechaInicial").val($("#fechaFinal").val());
//    	}
//    })
});

$("#btn-reporte-csv").click(function() {
	$("#txt-tipo-reporte").val("plano");
	$("#form-reporte").submit();
});

$("#btn-reporte-excel").click(function() {
	$("#txt-tipo-reporte").val("excel");
	$("#form-reporte").submit();
});