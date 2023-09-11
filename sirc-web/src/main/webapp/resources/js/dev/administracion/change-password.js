$(document).ready(function() {

});

$("#cPassButton").on("click", function(e) {
	var pass1 = $("#password").val();
	var pass2 = $("#password2").val();
	if (pass1 == pass2) {
		$('#formChangePassword').submit();
	}else{
		Util.Message.error("La confirmación de la contraseña no coincide con la ingresada. Por favor validar.");
	}
})