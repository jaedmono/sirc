$(document).ready(function() {
	// Validate form (Generic)
	EqUI.forms.add_form_for_submit_validate($('.form-util'));
	$('.button-validation').on('click', function(e) {
		$('.form-util').submit();
	});
});

$(document).ready(function() {
	// Clean form (Generic)
	$('.button-clean').on('click', function(e) {
		$('.form-validation').trigger("reset");
		dataTable.fnFilter('');
	});

	$('[data-toggle="tooltip"]').tooltip();
});

// DESHABILITAR BOTONES DESPUES DEL ENVIO
$('.form-util').submit(function() {
	loading();
});

function loading() {
	var isValid = true;
	
	$('.form-util .button-validation').addClass('disabled');

	$(".form-util").each(function() {
		$('input,textarea,select').filter('[required]:visible').each(
			function(i, requiredField) {
				if ($(requiredField).val() == null || $(requiredField).val() == '') {
					isValid = false;
				}
			});
		});
	
	if (isValid) {
		Util.Message.warning('Espere un momento por favor...');

		setTimeout(function() {
			$('.form-util .button-validation').removeClass('disabled');
			$("div.alert").hide();
		}, 2000);
	}else{
		$('.form-util .button-validation').removeClass('disabled');
		Util.Message.error('Hay campos sin diligenciar');
	}

	
}
