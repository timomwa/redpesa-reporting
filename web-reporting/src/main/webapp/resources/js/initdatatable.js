var sayhello = function() {
	alert('hello at initdatatables.js');

}
var addDataTableTags = function(ref) {
	console.log('--->here!');

	$(ref).DataTable({
		dom : 'Bfrtip',
		buttons : [ 'copy', 'csv', 'excel', 'pdf', 'print' ]
	});

}