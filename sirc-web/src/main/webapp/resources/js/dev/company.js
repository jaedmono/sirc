
var dataTable = $('#tableX').dataTable({});
$("#email").keyup(function() {
	dataTable.fnFilter(this.value);
});
$("#username").keyup(function() {
	dataTable.fnFilter(this.value);
});

var datatableCompany = $('#tableY')
		.dataTable(
				{
					"columnDefs" : [ {
						"targets" : -1,
						"data" : null,
						"defaultContent" : '<a class="btn eq-ui-btn-fab eq-ui-btn-mini-fab eq-ui-waves-light fa-icon"><i class="fa fa-trash icon"></i></a>'
					} ]
				}).api();

var userSelected;



var $input = $(".typeahead");

var sourceCompanies = new Array();

$.get("companies-by-text?text=" + $input.val(), function(data) {
	$.each(data, function(index, value) {
		sourceCompanies.push({
			id : value.id,
			name : value.nombreRazonSocial
		});
	});
}, 'json');

$input.typeahead({
	source : sourceCompanies,
	autoSelect : true
});

$input.on('input', function(e) {
	sourceCompanies = new Array();
	console.log('Changed!' + $input.val())
	$.get("companies-by-text?text=" + $input.val(), function(data) {
		$.each(data, function(index, value) {
			sourceCompanies.push({
				id : value.id,
				name : value.nombreRazonSocial
			});
		});
	}, 'json').done(function() {
		$input.typeahead({
			source : sourceCompanies
		});
	});

});

//		DESASOCIAR EMPRESA DE USUARIO 

var rowSelectionCompanyUser;
var selectionCompanyUser;

$('#tableY tbody').on('click', 'a', function() {
	selectionCompanyUser = datatableCompany.row($(this).parents('tr')).data();
	removeCompanyToUserDialog();
	rowSelectionCompanyUser = $(this);
	//removeCompanyToUser(data[1], $(this));
});


function removeCompanyToUserDialog() {
	$('#addCompany').modal('hide');
    $('#disasociateCompany').modal('show');
    return false;
};

$('#cancelRemoveCompanyToUser').click(function() {
	$('.alert').alert('close');
	$('#addCompany').modal('show');
});


$('#removeCompanyToUser').click(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var idCompany = selectionCompanyUser[1];
	
	console.log(selectionCompanyUser[0] + " fue desasociada con el id: " + selectionCompanyUser[1]);
	
	var request = $.ajax({
		url : "remove-company-to-user?idUser=" + userSelected + "&idCompany="
				+ idCompany,
		method : "POST",
		data : {
			idUser : userSelected,
			idCompany : idCompany
		},
		dataType : "json",
		contentType : "application/json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		}
	});

	request.done(function(data) {
		if (data.code === 0) {
			Util.Message.Modal.success(data.description);
			datatableCompany.row(rowSelectionCompanyUser.parents('tr')).remove().draw();
		} else {
			Util.Message.Modal.error(data.description);
		}

	});

	request.fail(function(jqXHR, textStatus) {
		Util.Message.Modal.error(textStatus);
	});

    $('#disasociateCompany').modal('hide');
	$('#addCompany').modal('show');

});



function validateAddCompany() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	Util.Message.Modal
			.info("Espere un momento mientras se realiza la operación");

	var current = $input.typeahead("getActive");
	if (current) {
		// Some item from your model is active!
		if (current.name == $input.val()) {
			var request = $.ajax({
				url : "add-company-to-user",
				method : "POST",
				data : {
					idUser : userSelected,
					idCompany : current.id
				},
				dataType : "json",
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				}
			});

			request.done(function(data) {
				if (data.code === 0) {
					Util.Message.Modal.success(data.description);
					datatableCompany.row.add([ current.name, current.id ])
							.draw().node();
				} else {
					Util.Message.Modal.error(data.description);
				}

			});

			request.fail(function(jqXHR, textStatus) {
				Util.Message.Modal.error(textStatus);
			});
		} else {
			Util.Message.Modal.info("Seleccione uno de la lista");
		}
	} else {
		Util.Message.Modal.info("Por favor digite la empresa a asociar");
	}
	$('#company').val('');
}

// CONSULTAR USUARIOS --CONSULTAR USUARIOS --CONSULTAR USUARIOS <--
var rowSelection;

$(".removeRow").click(function() {
	$('#deleteExtUser').attr('value', this.id);
	rowSelection = $(this).closest("tr").get(0);
});

$('#deleteExtUser').click(function() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var idUser = this.value;
	var row = rowSelection;

	var request = $.ajax({
		url : "remove-external-user?idUser=" + idUser,
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
// CONSULTAR USUARIOS --CONSULTAR USUARIOS --CONSULTAR USUARIOS -->

// ASOCIAR EMPRESAS --ASOCIAR EMPRESAS --ASOCIAR EMPRESAS <--
function getCompaniesByIdUser(idUser) {
	clean();
	datatableCompany.clear().draw();

	$.get(
			'companies-by-id-user?idUser=' + idUser,
			function(item) {

				$.each(item, function(index, value) {
					datatableCompany.row.add(
							[ value.nombreRazonSocial, value.id ]).draw()
							.node();
				});
			}).done(function() {
		userSelected = idUser;
	});

}
//ASOCIAR EMPRESAS --ASOCIAR EMPRESAS --ASOCIAR EMPRESAS -->

$('#codigoEmpresa').on('change', function() {
	var selectedRS = $(this).find("option:selected").data("rs");
	console.log(selectedRS);
	$("#company_razonSocial").val(selectedRS);
	$("#company_razonSocial").trigger("focus");
	var selectedNIT = $(this).find("option:selected").data("nit");
	$("#nit").val(selectedNIT);
	$("#nit").trigger("focus");
});


function clean() {
	$("div.alert").hide();
	$('#company').val('');
}
