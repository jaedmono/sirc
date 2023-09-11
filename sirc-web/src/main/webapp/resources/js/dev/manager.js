// CONSULTA USUARIOS ADMINISTRADORES <--
var dataTable = $('#tableX').dataTable();

$("#username").keyup(function() {
	dataTable.fnFilter(this.value);
});

var rowSelection;

$(".removeManagerRow").click(function() {
	$('#deleteExtUser').attr('value', this.id);
	rowSelection = $(this).closest("tr").get(0);
});

$('#searchButton').click(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var username = $('#usernameldap').val();
	
	var request = $.ajax({
		url : "consult-manager-user?username="+username,
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
	});

	request.done(function(data) {
		if (data.code === 0) {
			var user = data.object;
			Util.Message.Modal.success('Nombre: '+user.nombre +' '+ user.apellido + ' Email: '+user.email);
			$('#addLdapUserButton').prop('disabled', false);
		} else {
			Util.Message.Modal.error(data.description);
		}
	});

	request.fail(function(jqXHR, textStatus) {
		Util.Message.Modal.error(textStatus);
	});
});

$('#addLdapUserButton').click(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var username = $('#usernameldap').val();
	
	var request = $.ajax({
		url : "add-manager-user?username="+username,
		method : "POST",
		dataType : "json",
		contentType : "application/json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
	});

	request.done(function(data) {
		if (data.code === 0) {
			var user = data.object;
			localStorage.setItem("message",data.description)
		    window.location.reload(); 
		} else {
			Util.Message.error(data.description);
		}
	});

	request.fail(function(jqXHR, textStatus) {
		Util.Message.error(textStatus);
	});
});

if(localStorage.getItem("message"))
{
	Util.Message.success(localStorage.getItem("message"));
    localStorage.clear();
}






//REMOVE
var rowSelection;

$(".removeManagerRow").click(function() {
	$('#deleteExtUser').attr('value', this.id);
	rowSelection = $(this).closest("tr").get(0);
});

$('#deleteExtUser').click(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var idUser = this.value;
	var row = rowSelection;

	var request = $.ajax({
		url : "remove-manager-user?idUser=" + idUser,
		method : "POST",
		data : {
			idUser : idUser
		},
		dataType : "json",
		contentType : "application/json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
	});

	request.done(function(data) {
		if (data.code === 0) {
			Util.Message.success(data.description);
			dataTable.fnDeleteRow(dataTable.fnGetPosition(row));
		} else {
			Util.Message.error(data.description);
		}

	});

	request.fail(function(jqXHR, textStatus) {
		Util.Message.error(textStatus);
	});
});
// CONSULTA USUARIOS ADMINISTRADORES -->

