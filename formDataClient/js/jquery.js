var URL = 'http://localhost:9080/test/prueba/formData/';
var files = [];

$(document).ready(function(){
	console.log('jquery.js loaded');
	
	//Ponemos todos lo message a disable
	disableMessage();
	
	//Resetar form
	restForm();
	
	//Capturando el evento change de input file
	$('input[type=file]').on('change',function(event){
		$("#uploadFile").val(this.value);
		for (var i = 0; i < event.currentTarget.files.length; i++) {
			files.push(event.currentTarget.files[i]);
		}
	});
	
	//Capturando el evento click de button
	$('form button').on('click', enviar);
	
});

function restForm(){
	$('form')[0].reset();
	//var inputFile = $('input[type=file]');
	//inputFile.replaceWith( inputFile.val('').clone( true ) );
	//$("#uploadFile").val('');
}

function disableMessage(){
	$('.alert-box').each(function(index){
		$(this).hide();
	});
}

function enviar(event){
	event.preventDefault();
	
	var formData = new FormData();
	
	 //Metenmos el nombre
	var values = {};   
	$.each($('form').serializeArray(), function(indexInArray,input){
	  values[ input.name ] = input.value;
	})
	formData.append("model",JSON.stringify(values));
	
	
	//Ahora metemos todos lo ficheros
	for (var i = 0; i < files.length; i++) {
		formData.append("file_"+i, files[i]);
	}

	$.ajax({
	  url: URL,
	  data: formData,
	  dataType: 'json',
	  processData: false,
	  contentType: false,
	  type: 'POST',
	  success: function(data){
		  disableMessage();
		  $('.alert-box.success').show();
	  },
	  error: function (xhr, ajaxOptions, thrownError) {
		  disableMessage();
		  $('.alert-box.error').show();
	  }
	});
}
			