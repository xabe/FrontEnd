var URL = 'http://localhost:9080/test/prueba/formData/';


function usuarioController($scope, $http) {
    
    //Add Curso
    
    
    $scope.fileNameChanged = function(element) {
	    $scope.$apply(function(scope) {
		    console.log('files:', element.files);
			$("#uploadFile").val(element.value);
	        for (var i = 0; i < element.files.length; i++) {
	          scope.data.files.push(element.files[i]);
	        }
	    });
    };
    
	$scope.data = {
		nombre: "",
		files: []
    };
	
	$scope.disableMessage = function(){
		$('.alert-box').each(function(index){
			$(this).hide();
		});
	};
	
	$scope.resetForm = function(form){
		$('form')[0].reset();
		$scope.disableMessage();
	};
 
	$scope.resetForm();
	
	$scope.submitForm = function(isValid) {
		$scope.disableMessage();
		if(!isValid)
		{
			$('.alert-box.required').show();
		}
		else
		{
			console.log("Enviando datos....");		
			
			$http({
	            method: 'POST',
	            url: URL,
	            headers: { 'Content-Type': undefined },
	            transformRequest: function (data) {
	                var formData = new FormData();
	                formData.append("model", angular.toJson(data.model));
	                for (var i = 0; i < data.files.length; i++) {
	                    formData.append("file_" + i, data.files[i]);
	                }
	                return formData;
	            },
	            data: { model: $scope.data, files: $scope.data.files }
	        }).
	        success(function (data, status, headers, config) {	        	
				$('.alert-box.success').show();
	        }).
	        error(function (data, status, headers, config) {
				$('.alert-box.error').show();
	        });
		} 
		       
    };
}