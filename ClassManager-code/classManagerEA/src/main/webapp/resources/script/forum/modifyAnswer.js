
$(document).ready(function() {
	
	answerManager = AnswerManager.getInstance();
	listenersManager = ListenersManager.getInstance();
	
	answerManager.initAnswerArea();
	listenersManager.initListeners();
	
});



var AnswerManager = (function() {
	
	var answerArea;
	var answerAreaName = 'textEditor';
	var alreadyInitialized = false;
	
	var instance;
	
	var AnswerManager = function() {
		
		this.getAnswerArea = getAnswerArea;
		this.initAnswerArea = initAnswerArea
	};
	
	var initAnswerArea = function() {
		
		if(alreadyInitialized === false) {
			alreadyInitialized = true;
			
			answerArea = CKEDITOR.replace(answerAreaName);
		}
		else {
			console.log("Operation not permitted!! Init function can be called one time.");
		}
	};
	
	var getAnswerArea = function() {
		return answerArea;
	};
	

	var getInstance = function() {
    	
    	if (!instance) {
            instance = new AnswerManager(); 
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
			
			$(".removeAttachmentBtn").on("click", function(){
					
				
				var attachment = $(this);
				var parameters = {name: $(attachment).data("aname"), id: $(attachment).data("aid"), type: "remove"};
				
				$.ajax({
						url: "/forum/removeAnswerAttachment",
						method: "POST",
						data:{
							attachedIdRemove: parameters.id
						}
					}).done(function(e, result, data) {
						
							var result = data.responseText;
							if(result === "true") {

								$(attachment).closest(".attachmentContainer").remove();
								
								var preAttachmentsIDs = $('#preAttachedFilesID').val();
								preAttachmentsIDs = preAttachmentsIDs.replace(parameters.id, "");
								preAttachmentsIDs = preAttachmentsIDs.replace(";;", ";");
								$('#preAttachedFilesID').val(preAttachmentsIDs);
							}
							else {
								alert("remove non andata a buon fine")
							}
					});
				
			});
			
			
			$(".removeNewAttachmentBtn").on("click", function(){
					
				var attachment = $(this);
				var parameters = {name: $(attachment).data("aname"), id: $(attachment).data("aid"), type: "remove"};
				
				$.ajax({
						url: "/forum/removeAnswerAttachment",
						method: "POST",
						data:{
							attachedIdRemove: parameters.id
						}
					}).done(function(e, result, data) {
						
							var result = data.responseText;
							if(result === "true") {
								
								$(attachment).closest(".newAttachmentContainer").remove();
								
								var newAttachmentsIDs = $('#newAttachedFilesID').val();
								newAttachmentsIDs = newAttachmentsIDs.replace(parameters.id, "");
								newAttachmentsIDs = newAttachmentsIDs.replace(";;", ";");
								$('#newAttachedFilesID').val(newAttachmentsIDs);
							}
							else {
								alert("remove non andata a buon fine")
							}
					});
				
			});
			
			
			$('#newAttachedFilesID').on("change", function(data, parameters) {
				
				
				parameters = parameters || {name: undefined, id: undefined, type: undefined};
				
				var newAttachment = '<div class="newAttachmentContainer" data-aid="'+ parameters.id +'" class="row">'+
					'<div class="col-sm-10 col-md-10 col-lg-10">'+
  					'	<div class="row">'+
  					'		<div class="col-sm-2 col-md-2 col-lg-2">'+
					'  			<span style="font-size:2em;" class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>'+
  					'		</div>'+
  					'		<div class="col-sm-8 col-md-8 col-lg-8">'+
  					'			<p>'+ parameters.name +'</p>'+
  					'		</div>'+
  					'		<div class="col-sm-2 col-md-2 col-lg-2">'+
  					'			<div class="btn btn-danger removeNewAttachmentBtn">delete</div>'+
  					'		</div>'+
  					'	</div>'+
  					'</div>'+
  				'</div>'
  					
  				newAttachment = $(newAttachment);
  					
  				$(newAttachment).find(".removeNewAttachmentBtn").on("click", function(){
  					
  					var attachment = $(this);
  					
  					$.ajax({
  							url: "/forum/removeAnswerAttachment",
  							method: "POST",
  							data:{
  								attachedIdRemove: parameters.id
  							}
  						}).done(function(e, result, data) {
  							
  								var result = data.responseText;
  								if(result === "true") {

  									$(attachment).closest(".newAttachmentContainer").remove();
  									
  									var newAttachmentsIDs = $('#newAttachedFilesID').val();
  									newAttachmentsIDs = newAttachmentsIDs.replace(parameters.id, "");
  									newAttachmentsIDs = newAttachmentsIDs.replace(";;", ";");
  									$('#newAttachedFilesID').val(newAttachmentsIDs);

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