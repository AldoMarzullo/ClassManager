$(document).ready(function() {

	listenersManager = ListenersManager.getInstance();
	listenersManager.initListeners();

	uploadFile_index = 0;
	createClass_index = 1;
	addHomework_index = 2;
	addMaterials_index = 3;

	buttons = ["#uploadFile_btn", "#createNewClass_btn", "#addHomework_btn","#addMaterials_btn"];

	$("#uploadFile_div").hide();

	/*$( ".datepicker" ).datepicker({
    	changeYear: true,
    	dateFormat: "dd/mm/yy",
    	yearRange: "-100:+100",	
    });*/

	$( ".datepicker" ).datetimepicker({
		format: 'DD/MM/YYYY'
	});

	$( ".timepicker" ).datetimepicker({
		format: 'HH:mm:ss'
	});

});



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


			$("#createNewClass_btn").on("click", function() {

				$("#lecture-form").attr("action", "/lectures");
				$("#createNewClass_modal").modal().show();

			});


			$("#addHomework_btn").on("click", function() {

				//$("#homework-form").attr("action", "/lectures/create_homeworks");
				$("#addHomework_modal").modal().show();
			});

			/*
			 * shows and hide the upload div
			 */
			$("#uploadFile_btn").on("click", function() {

				if($("#uploadFile_div").is(":hidden")){
					$("#uploadFile_div").show();
				}
				else
					$("#uploadFile_div").hide();
			});

			$("div.file").click(function(event){

				var path = $(this).siblings("input[name=path]").val();
				$("#visualizer").attr("href", path);

				$("#visualizer_modal").modal().show();
				$("#visualizer").gdocsViewer({ width: '100%', height: '100%' });

				event.preventDefault();
			});

			$("#download_btn_modal").click(function(){

				var url = $("#visualizer").attr("href");
				window.open("/download?path=" + url);

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


function download(event){

	event.stopPropagation();

	var url = $(event.currentTarget).closest("form").find("input[name=path]").val();
	window.open("/download?path=" + url);
}

function delete_lectures(event){

	event.stopPropagation();
	var lectureId = $(event.currentTarget).closest("form").find("input[name=parentId]").val();

	submit("/lectures/delete_lecture","lectureId",lectureId);
}

function delete_homeworks(event){

	event.stopPropagation();
	var homeworkId = $(event.currentTarget).closest("form").find("input[name=id]").val();

	submit("/lectures/delete_homeworks","homeworkId",homeworkId);
}

function delete_homeworkAttached(event){

	event.stopPropagation();
	var homeworkAId = $(event.currentTarget).closest("form").find("input[name=id]").val();

	submit("/lectures/delete_homeworkAttached","homeworkAttachedId",homeworkAId);
}

function delete_homeworkStudentSolving(event){

	event.stopPropagation();
	var hssId = $(event.currentTarget).closest("form").find("input[name=id]").val();

	submit("/lectures/delete_homeworkStudentSolving","homeworkStudentSolvingId",hssId);
}

function delete_homeworkStudentSolvingAttachment(event){

	event.stopPropagation();
	var homeworkId = $(event.currentTarget).closest("form").find("input[name=id]").val();

	submit("/lectures/delete_homeworkStudentSolvingAttachment","homeworkStudentSolvingAttachedId",homeworkId);
}

function delete_materials(event){


	event.stopPropagation();
	var materialId = $(event.currentTarget).closest("form").find("input[name=id]").val();

	submit("/lectures/delete_materials","materialId",materialId);
}

function submit(path, name, id){

	$('body')
	.append('<form id="delete_form"></form>');
	$('#delete_form') 
	.attr("action", path) .attr("method","post")
	.append('<input type="hidden" name="' + name + '" value="' + id + '">');

	$('#delete_form').submit();
	$('#delete_form').remove();
}


function update_lectures(event){

	event.stopImmediatePropagation();

	var lectureId = $(event.currentTarget).closest("form").find("input[name=parentId]").val();
	var topic = $(event.currentTarget).closest("form").find("input[name=topic]").val();
	var description = $(event.currentTarget).closest("form").find("input[name=description]").val();
	var classroom = $(event.currentTarget).closest("form").find("input[name=classroom]").val();
	var date = $(event.currentTarget).closest("form").find("input[name=date]").val();
	var bhour = $(event.currentTarget).closest("form").find("input[name=beginHour]").val();
	var ehour = $(event.currentTarget).closest("form").find("input[name=endHour]").val();

	$("#createLectureModal_id").val(lectureId);

	$("#lecture-form").find("input[name=parentId]").val(lectureId);
	$("#lecture-form").find("input[name=topic]").val(topic);
	$("#lecture-form").find("input[name=description]").val(description);
	$("#lecture-form").find("input[name=classroom]").val(classroom);

	date = new Date(date);
	date = String([date.getDate(), date.getMonth()+1, date.getFullYear()].join('/'));
	$("#lecture-form").find("input[name=date]").val(date);
	$("#lecture-form").find("input[name=beginHour]").val(bhour);
	$("#lecture-form").find("input[name=endHour]").val(ehour);

	$("#createNewClass_modal").modal().show();

}


function update_homeworks(event){

	event.stopImmediatePropagation();

	var homeworkId = $(event.currentTarget).closest("form").find("input[name=id]").val();
	var name = $(event.currentTarget).closest("form").find("input[name=name]").val();
	var description = $(event.currentTarget).closest("form").find("input[name=description]").val();

	$("#addHomeworkModal_id").val(homeworkId);

	$("#homework-form").find("input[name=id]").val(homeworkId);
	$("#homework-form").find("input[name=name]").val(name);
	$("#homework-form").find("input[name=description]").val(description);

	//$("#homework-form").attr("action", "/lectures/update_homeworks");
	$("#addHomework_modal").modal().show();

}


function reloadHomework(event){

	var homeworkId = $("#addHomework_modal").find("input[name=parentId]").val();
	window.location.replace("/lectures/homeworks?parentId=" + homeworkId);
}