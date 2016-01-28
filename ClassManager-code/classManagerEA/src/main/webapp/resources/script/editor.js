
$(document).ready(function() {
	
	editorManager = EditorManager.getInstance();
	listenersManager = ListenersManager.getInstance();

	editorManager.initEditor();
	listenersManager.initListeners();
});


//class
var Status = function(){
	return {
		
		'code': '',
		'consoleContent': '',
		'theme': '',
		'language': ''
	};
};


//class
var EditorManager = (function(){
	
	//private variables
	var ace_editor;
	var ace_console;
	var alreadyInitialized = false;
	
	var status = new Status();
	
	var instance;
	
	//constructor
	var EditorManager = function(){
		
		this.getEditorStatusInfo = getEditorStatusInfo;
		this.getAceEditor = getAceEditor;
		this.getAceConsole = getAceConsole;
		
		this.setLanguage = setLanguage;
		this.setTheme = setTheme;

		this.initEditor = initEditor;
		
		
		status.code = $('#codeTextarea').val();
		status.consoleContent = $('#consoleTextarea').val();
		
		status.theme = $('#themeInput').val() || $('#editorTheme').val();
		status.language = $('#languageInput').val() || $('#editorLang').val();
		
		$('#editorTheme').val(status.theme);
		$('#editorLang').val(status.language)
	};

	
	//private functions
	var getAceEditor = function() {
		return ace_editor;
	}
	
	var getAceConsole = function() {
		return ace_console;
	}
	
	var initEditor = function() {
		
		if(alreadyInitialized === false) {
			alreadyInitialized = true;
		
			var editorStatusInfo = getEditorStatusInfo();
			
			//editor init
			ace_editor = ace.edit("editor");
			ace_editor.setOptions({
			    enableBasicAutocompletion: true,
			    theme: 'ace/theme/' + editorStatusInfo.theme,
			    mode: 'ace/mode/' + editorStatusInfo.language
			});
			ace_editor.$blockScrolling = Infinity;
	
			//console init
			ace_console = ace.edit("console");
			ace_console.setOptions({
				readOnly: true,
				theme: "ace/theme/cobalt",
				mode: "ace/mode/text",
				highlightActiveLine: false,
				showGutter: false,
				showPrintMargin: false
			});
			ace_console.$blockScrolling = Infinity;
			
			updateEditorContent();
		}
		else {
			console.log("Operation not permitted!! Init function can be called one time.");
		}
	};
	
	var updateEditorContent = function() {
		
		var editorStatusInfo = getEditorStatusInfo();
		
		ace_editor.session.insert(ace_editor.getCursorPosition(), editorStatusInfo.code);
		ace_console.session.insert(ace_console.getCursorPosition(), editorStatusInfo.consoleContent);
	};
	
	var getEditorStatusInfo = function() {
		return status;
	};
	
	var setLanguage = function(language) {
		status.language = language;
		ace_editor.getSession().setMode("ace/mode/" + language);
	};

	var setTheme = function(theme) {
		status.theme = theme;
		ace_editor.setTheme("ace/theme/" + theme);
	}
	
	
	var getInstance = function() {
    	
    	if (!instance) {
            instance = new EditorManager(); 
        } 
        return instance; 
    };
    
    
    //return singleton obj
    return {
    	getInstance: getInstance
    };	
})();





//class
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
			
			$("#editorLang").on("click", function() {

				var language = $("#editorLang").val();
				EditorManager.getInstance().setLanguage(language);
			});

			$("#editorTheme").on("click", function() {

				var theme = $("#editorTheme").val();
				EditorManager.getInstance().setTheme(theme);
			});

			
			$("#toggleConsole").on("click", function(){

				$("#console").toggle();

				if($("#console").is(":visible")) {
					$("#editor").css("height", "70%")			
				}
				else {
					$("#editor").css("height", "90%")
				}
			
			});


			$("#clearConsole").on("click", function() {
				EditorManager.getInstance().getAceConsole().setValue("");
			});


			$('#tryCodeBtn').on("click", function() {
				
				var selectVal = $("#editorLang").val();
				
				if(selectVal === "javascript") {
					
					var editorContent = EditorManager.getInstance().getAceEditor().getValue();
					var evalResult = eval(editorContent.toString()) || "";
					
					var console = EditorManager.getInstance().getAceConsole();
					
					console.setValue("");
					console.session.insert(console.getCursorPosition(), JSON.stringify(evalResult, null, 4));
				}
				else {
					
					$('#codeTextarea').val(EditorManager.getInstance().getAceEditor().getValue());
					$('#themeInput').val(EditorManager.getInstance().getEditorStatusInfo().theme);
					$('#languageInput').val(EditorManager.getInstance().getEditorStatusInfo().language);
					$('#editorForm').submit();
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




/*
var editor;
var consl;

var language;
var theme;



$(document).ready(function() {

	language = $("#editorLang").val();
	theme = $("#editorTheme").val();
	initEditor();

	$("#editorLang").on("click", function() {

		language = $("#editorLang").val();
		setLanguage(language);
	});

	$("#editorTheme").on("click", function() {

		theme = $("#editorTheme").val();
		setTheme(theme);
	});

	$("#toggleConsole").on("click", function(){

		$("#console").toggle();

		if($("#console").is(":visible")) {

			$("#editor").css("height", "80%")			
		}
		else {
			$("#editor").css("height", "100%")
		}
	
	});


	$("#clearConsole").on("click", function() {

		consl.setValue("");
	});


	$("#addConsoleLine").on("click", function() {

		consl.session.insert(consl.getCursorPosition(), consoleInitialText);
	});	

	
	$('#tryCodeBtn').on("click", function() {
		
		$('#codeTextarea').val(editor.getValue());
		$('#codeForm').submit();
	});
	
});


function initEditor() {

	editor = ace.edit("editor");
	editor.setOptions({
	    enableBasicAutocompletion: true,
	});

	consl = ace.edit("console");
	consl.setOptions({
		readOnly: true,
		theme: "ace/theme/cobalt",
		mode: "ace/mode/text",
		highlightActiveLine: false,
		showGutter: false,
		showPrintMargin: false
	});


	
	setTheme(theme);
	setLanguage(language);

	insertText();
};


function setLanguage(language) {
	editor.getSession().setMode("ace/mode/" + language);
};

function setTheme(theme) {
	editor.setTheme("ace/theme/" + theme);
}


function insertText() {

	editor.session.insert(editor.getCursorPosition(), $('#codeTextarea').val());
	consl.session.insert(consl.getCursorPosition(), $("#consoleTextarea").val());
}
*/

