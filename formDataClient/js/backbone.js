var URL = 'http://localhost:9080/test/prueba/formData/';

$(document).ready(function(){
	console.log('backbone.js loaded');
	
	var Vista = Backbone.View.extend({
		events: {
			"submit form" : "submit",
			"change input[type=file]" : "encodeFile"
		},
		initialize : function ($el) {
			this.$el = $el;
			this.files = [];
			
			//Ponemos todos lo message a disable
			this.$el.find('.alert-box').each(function(index){
				$(this).hide();
			});
			
			//Reset Form
			this.$el.find('form')[0].reset();
			
		},
		encodeFile: function (event) {
			for (var i = 0; i < event.currentTarget.files.length; i++) {
				this.files.push(event.currentTarget.files[i]);
				this.$el.find("#uploadFile").val(event.currentTarget.files[i].name);
			}
		},
		submit: function (event) {
			event.preventDefault();
			//Creamos el objecto de 
			var formData = new FormData();

			//Metenmos el nombre en un json
			var values = {};
			_.each(this.$('form').serializeArray(), function(input){
			  values[ input.name ] = input.value;
			})
			formData.append("model",JSON.stringify(values));

			//Ahora vamos con el ficher	
			for (var i = 0; i < this.files.length; i++) {
				formData.append("file_"+i, this.files[i]);
			}

			var modelo = Backbone.Model.extend ({urlRoot : URL,});

			model = new modelo({});
			
			$('.alert-box').each(function(index){
				$(this).hide();
			});
			
			model.save(null,{
								data: formData,
								processData: false,
								cache: false,
								contentType: false,
								success: function (model, result) {
									$('.alert-box.success').show();
								},
								error: function () {
									$('.alert-box.error').show();
								}
			});
		}

	});
	
	var v = new Vista($('body'));


});