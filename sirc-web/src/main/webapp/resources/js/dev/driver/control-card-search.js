let dateTable;
$(document).ready(function() {
    dateTable = $('#controlCardSearch').DataTable( {
        scrollY:        "900px",
        scrollX:        true,
        scrollCollapse: true,
        columnDefs: [
            { width: '60%', targets: 0 }
        ],
        fixedColumns: true
    } );
} );


$('#btn-search').click(function () {

    $("#formDriver").attr("action",  "query-control-cards?_csrf=" + $("meta[name='_csrf']").attr("content"));
    $('#formDriver').submit();

});

$(window).load(function () {
    $("#issuesDate").datetimepicker({format: 'DD/MM/YYYY'});
    $("#expiryDate").datetimepicker({format: 'DD/MM/YYYY'});
});

function exportTableToExcel(){
    const  filename = 'Listado_Tarjetas_'+new Date().getTime()+'.xls';
    tableToExcel('dataToExport', filename)
}

var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        const  filename = 'Listado_Tarjetas_'+new Date().getTime()+'.xls';
        var link = document.createElement('a');
        link.download = filename;
        //window.location.href = uri + base64(format(template, ctx))
        link.href = uri + base64(format(template, ctx));
        link.click();
    }
})();
