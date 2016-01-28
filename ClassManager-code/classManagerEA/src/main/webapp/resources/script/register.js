$(function() {
    $( "#datepicker" ).datepicker({
    	changeYear: true,
    	dateFormat: "dd/mm/yy",
    	yearRange: "-100:+100",
    	minDate: "-100y",
    	maxDate: "-18y",
    	
    });
    
    $("#register-form").submit(function(event) {
		event.preventDefault();
		var form = $("#register-form");
		var data = {};

		$.each(this, function(i, v) {
            var input = $(v);
	        data[input.attr("name")] = input.val();
	        delete data["undefined"];
		});
		
		//data["birthdate"] = $("#datepicker").datepicker( 'getDate' );
		
		console.log(data);
		
		$.ajax({
		  contentType : 'application/json; charset=utf-8',
		  url: form.attr("action"),
		  type : form.attr("method"),
		  datatype : 'json',
		  data: JSON.stringify(data)
		}).done(function(data) {
			
			$(".glyphicon").remove();
			$(".errors").empty();
			$(".has-error").removeClass("has-error has-feedback");
			
			if(data.status === "ERROR")	{
                for(var key in data.errorsMap)	{
                	var error = data.errorsMap[key];
                    var formGroup =  $("input[name=" + key + "]").parent();
                    formGroup.append('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
                    formGroup.addClass("has-error has-feedback");
                    var errorDiv = formGroup.find(".errors");
                    errorDiv.append('<span class="help-block">' + error + '</span>');
                }
			} else if ( data.status === "SUCCESS" ) {
				window.location.replace("/");
			}
		});
	});
    
    
    
});