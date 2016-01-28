
$(document).ready(function() {
	
	questionManager = QuestionManager.getInstance();
	listenersManager = ListenersManager.getInstance();
	
	questionManager.initQuestionArea();
	listenersManager.initListeners();
	
});



var QuestionManager = (function() {
	
	var questionArea;
	var questionAreaName = 'textEditor';
	var alreadyInitialized = false;
	
	var instance;
	
	var QuestionManager = function() {
		
		this.getQuestionArea = getQuestionArea;
		this.initQuestionArea = initQuestionArea
	};
	
	var initQuestionArea = function() {
		
		if(alreadyInitialized === false) {
			alreadyInitialized = true;
			
			
			questionArea = CKEDITOR.replace(questionAreaName);
		}
		else {
			console.log("Operation not permitted!! Init function can be called one time.");
		}
	};
	
	var getQuestionArea = function() {
		return questionArea;
	};
	

	var getInstance = function() {
    	
    	if (!instance) {
            instance = new QuestionManager(); 
        } 
        return instance; 
    };
    
    
    //return singleton obj
    return {
    	getInstance: getInstance
    };
	
})();



var ListenersManager = (function(){
	
	//private fields
    var alreadyInitialized = false;

    var instance;
    
    
    //constructor
    var ListenersManager = function() {
    	
    	this.initListeners = initListeners;
    }
    
    
    var initListeners = function() {
    	
		if(alreadyInitialized === false) {
			alreadyInitialized = true;
			
			
			$('#tagRow').on('keyup keypress', function(e) {
				var code = e.keyCode || e.which;
				if (code == 13) { 
					e.preventDefault();
					return false;
				}
			});
			
			
			$(".removeAttachmentBtn").on("click", function(){
				
				
				var attachment = $(this);
				var parameters = {name: $(attachment).data("aname"), id: $(attachment).data("aid"), type: "remove"};
				
				$.ajax({
						url: "/forum/removeQuestionAttachment",
						method: "POST",
						data:{
							attachedIdRemove: parameters.id
						}
					}).done(function(e, result, data) {
						
							var result = data.responseText;
							if(result === "true") {

								$(attachment).closest(".attachmentContainer").remove();
								
								var attachmentsIDs = $('#attachedFiles').val();
								attachmentsIDs = attachmentsIDs.replace(parameters.id, "");
								attachmentsIDs = attachmentsIDs.replace(";;", ";");
								$('#attachedFiles').val(attachmentsIDs);
							}
							else {
								alert("remove non andata a buon fine")
							}
					});
				
			});
			

			$('#attachedFiles').on("change", function(data, parameters) {
				
				
				parameters = parameters || {name: undefined, id: undefined, type: undefined};
				
						
				var newAttachment = '<div class="attachmentContainer" data-aid="'+ parameters.id +'" class="row">'+
					'<div class="col-sm-10 col-md-10 col-lg-10">'+
  					'	<div class="row">'+
  					'		<div class="col-sm-2 col-md-2 col-lg-2">'+
					'  			<span style="font-size:2em;" class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>'+
  					'		</div>'+
  					'		<div class="col-sm-8 col-md-8 col-lg-8">'+
  					'			<p>'+ parameters.name +'</p>'+
  					'		</div>'+
  					'		<div class="col-sm-2 col-md-2 col-lg-2">'+
  					'			<div class="btn btn-danger removeAttachmentBtn">delete</div>'+
  					'		</div>'+
  					'	</div>'+
  					'</div>'+
  				'</div>'
  					
  				newAttachment = $(newAttachment);
  					
  				$(newAttachment).find(".removeAttachmentBtn").on("click", function(){
  					
  					
  					var attachment = $(this);
  					
  					$.ajax({
  							url: "/forum/removeQuestionAttachment",
  							method: "POST",
  							data:{
  								attachedIdRemove: parameters.id
  							}
  						}).done(function(e, result, data) {
  							
  								var result = data.responseText;
  								if(result === "true") {

  									$(attachment).closest(".attachmentContainer").remove();
  									
  									var attachmentsIDs = $('#attachedFiles').val();
  									attachmentsIDs = attachmentsIDs.replace(parameters.id, "");
  									attachmentsIDs = attachmentsIDs.replace(";;", ";");
  									$('#attachedFiles').val(attachmentsIDs);
  									
  								}
  								else {
  									alert("remove non andata a buon fine")
  								}
  						});
  					
  				});
				
				
				$('#attachmentSection').append(newAttachment);
						
				
			});
			
		}
		else {
			console.log("Operation not permitted!! Init function can be called one time.");
		}
	} 
    
    
    var getInstance = function() {
    	
    	if (!instance) {
    		 instance = new ListenersManager();  
        } 
        return instance; 
    };
    
    
    //return singleton obj
    return {
    	getInstance: getInstance
    };
    
})();