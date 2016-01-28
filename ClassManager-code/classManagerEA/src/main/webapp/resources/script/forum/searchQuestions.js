
$(document).ready(function() {
	
	questionsManager = QuestionsManager.getInstance();
	listenersManager = ListenersManager.getInstance();
	
	questionsManager.initManager();
	listenersManager.initListeners();
	
});



var QuestionsManager = (function(){
	
	//private fields
    var alreadyInitialized = false;
    
    var instance;
    
    //constructor
    var QuestionsManager = function() {
    	
    	this.initManager = initManager;
    }
    
    
    var initManager = function() {
    	
		if(alreadyInitialized === false) {
			alreadyInitialized = true;

			
			$('#paginatorTop').bootpag({
			    total: $("#pageCount").val(),
			    page: $("#currPage").val(),
			    maxVisible: $("#pageSize").val(),
			    leaps: false
			}).on("page", function(event, num){
				
				$("#qname").val($("#questionNameInp").val());
				$("#qdescription").val($("#descriptionInp").val());
				$("#qauthor").val($("#authorInp").val());
				$("#qtags").val($("#tagsInp").val());
				$("#pagenum").val(num-1);
				
				$("#pageSearchPag").submit();
				
			});
			
			
			
			$('#paginatorBottom').bootpag({
			    total: $("#pageCount").val(),
			    page: $("#currPage").val(),
			    maxVisible: $("#pageSize").val(),
			    leaps: false
			}).on("page", function(event, num){
			   
				$("#qname").val($("#questionNameInp").val());
				$("#qdescription").val($("#descriptionInp").val());
				$("#qauthor").val($("#authorInp").val());
				$("#qtags").val($("#tagsInp").val());
				$("#pagenum").val(num-1);
				
				$("#pageSearchPag").submit();
				
			});
			
			
		}
		else {
			console.log("Operation not permitted!! Init function can be called one time.");
		}
	} 
    
    
    var getInstance = function() {
    	
    	if (!instance) {
    		 instance = new QuestionsManager();  
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