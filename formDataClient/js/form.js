$(document).ready(function(){
	console.log('form.js loaded');
	
	//Capturando el evento change de input file
	$('input[type=file]').on('change',function(event){
		$("#uploadFile").val(this.value);
	});
	
});