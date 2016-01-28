/*
 * The html table stored information is handled using a bootstrap plug-in: "x-editable"
 * When an element of the table is clicked a pop up is shown and the changments are sent to path specified in the "url" property
 */
$(document).ready(function() {

	listenersManager = ListenersManager.getInstance();
	listenersManager.initListeners();

	//toggle `popup` / `inline` mode
	$.fn.editable.defaults.mode = 'popup';     

	/*
	 * This is used to edit the table
	 */
	$('#scores_table td.homework').editable({
		type: 'text',
		placement: 'right',
		url: function(params){

			var type = 'update';
			updateData(params, type);
		},
		validate: function(data){

			if(!data.score.match("^[0-9]+?"))
				return "invalid text";
			if(data.score < 0 || data.score > 30 || (data.score < 30 && data.praise))
				return "invalid range";
		},
		title: 'Change score'
	});

//	$('#scores_table td.newHomework').editable({
//	type: 'text',
//	placement: 'right',
//	url: function(params){

//	var type = 'new';
//	},
//	validate: function(data){

//	if(!data.score.match("^[0-9]+?"))
//	return "invalid text";
//	if(data.score < 0 || data.score > 30 || (data.score < 30 && data.praise))
//	return "invalid range";
//	},
//	title: 'Change score'
//	});

	$('#scores_table td.exam').editable({
		type: 'text',
		placement: 'right',
		url: function(params){

			var type = 'update';
			updateExam(params, type);
		},
		validate: function(data){

			if(!data.score.match("^[0-9]+?"))
				return "invalid text";
			if(data.score < 0 || data.score > 30 || (data.score < 30 && data.praise))
				return "invalid range";
		},
		title: 'Change score'
	});

	$('#scores_table td.newExam').editable({
		type: 'text',
		placement: 'right',
		url: function(params){

			var type = 'new';
			updateExam(params, type);
		},
		validate: function(data){

			if(!data.score.match("^[0-9]+?"))
				return "invalid text";
			if(data.score < 0 || data.score > 30 || (data.score < 30 && data.praise))
				return "invalid range";
		},
		title: 'Change score',
	});


	$("#page-size").change(function() {
		var url = "noticeboard?notices=" + $( this ).find("option:selected").text();
		console.log(url);
		window.location.href=url;
	});

	var value = $("#selected-value").val();
	$("#page-size").val(value);


	var totalPages = parseInt($("#options :input[name='total-pages']").val());
	var currentPage = parseInt($("#options :input[name='page-number']").val());

	$('#paginator').bootpag({
		total: totalPages,
		page: currentPage,
		maxVisible: 5,
//		href: "searchusers?page={{number}}",
		leaps: false
	}).on("page", function(event, num){
		window.location.href = "noticeboard?page="+num;
	});

	var value = $("#selected-value").val();
	$("#page-size").val(value);


	/*
	 * This is used to download the table
	 */
	$("#scores_table").tableExport({
		headings: true,                    // (Boolean), display table headings (th elements) in the first row
		formats: ["xlsx", "csv", "txt"],    // (String[]), filetypes for the export
		fileName: "id",                    // (id, String), filename for the downloaded file
		bootstrap: true,                   // (Boolean), style buttons using bootstrap
		position: "bottom"                 // (top, bottom), position of the caption element relative to table
			//exclude: ".noExl",
	});


});


/* 
* This is used to upload an xlsx file, in order to update the table
*/
function uploadAndReplace(files){
	

	var i,f;
	for (i = 0, f = files[i]; i != files.length; ++i) {
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			var data = e.target.result;

			/* if binary string, read with type 'binary' */
			var workbook = XLSX.read(data, {type: 'binary'});

			var sheet_name_list = workbook.SheetNames;
			var table = $("#scores_table").find("th,td");
			sheet_name_list.forEach(function(y) { /* iterate through sheets */
				var worksheet = workbook.Sheets[y];

				var cont = 0;

				var firstCell = worksheet["!ref"].split(':')[0];
				var lastCell = worksheet["!ref"].split(':')[1];

				var firstL = firstCell.split('')[0];
				var firstN = firstCell.split('')[1];

				var lastL = lastCell.split('')[0];
				var lastN = lastCell.split('')[1];

				$.each(genNumArray(firstN, lastN), function( index, value ) {	       
					$.each(genCharArray(firstL, lastL), function( index2, value2 ) {

						var hash = value2 + "" + value;
						var elem = table[cont];

						if(!$(elem).hasClass("empty"))
							$(elem).html(worksheet[hash].v);

						cont++;
					});
				});

			});

			$("#drop").collapse('hide');
			$("#colapse_upload").hide();
			$("#updateTable_btn").show();
			$("#updateTableDismiss_btn").show();
		};
		reader.readAsBinaryString(f);
	}
}

function genCharArray(charA, charZ) {
	var a = [], i = charA.charCodeAt(0), j = charZ.charCodeAt(0);
	for (; i <= j; ++i) {
		a.push(String.fromCharCode(i));
	}
	return a;
}

function genNumArray(min, max){	
	var list = [];
	for (var i = min; i <= max; i++) {
		list.push(i);
	}

	return list;
}



function updateExam(data, type){

	var part;
	var url;

	if(type === 'new'){

		url = "/scores/create_Partecipation";
		parentId = data.pk.split(" ")[0];
		studentId = data.pk.split(" ")[1];
		part = {'parentId':parentId, 'studentId' : studentId,'score':data.value.score, 'praise':data.value.praise};
	}
	else{
		url = "/scores/update_Partecipation";
		part = {'id':data.pk, 'score': data.value.score, 'praise': data.value.praise};
	}

	/*$.ajax({ 
		url: url, 
		type: 'POST',  
		data: JSON.stringify(part), 
		dataType: 'json',
		contentType: 'application/json',
	});*/
}

function updateData(data, type){

	var part;
	var url;

	url = "/scores/update_score";
	part = {'id':data.pk, 'score': data.value.score, 'praise': data.value.praise};


	/*$.ajax({ 
		url: url, 
		type: 'POST',  
		data: JSON.stringify(part), 
		dataType: 'json',
		contentType: 'application/json',
	});*/
}




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

			$('#filter').keyup(function () {

				var rex = new RegExp($(this).val(), 'i');
				$('.searchable tr').hide();
				$('.searchable tr').filter(function () {
					return rex.test($(this).text());
				}).show();

			});

			$('#exams_checkbox').change(function(){

				if($(this).is(':checked')){
					$(".exam-td").show();
				}
				else{
					$(".exam-td").hide();
				}    
			});

			$('#homeworks_checkbox').change(function(){

				if($(this).is(':checked')){
					$(".homework-td").show();
				}
				else{
					$(".homework-td").hide();
				}    
			});


			$("#drop").on("dragover", function(event) {
				event.preventDefault();  
				event.stopPropagation();
				$(this).addClass('dragging');
			});

			$("#drop").on("dragleave", function(event) {
				event.preventDefault();  
				event.stopPropagation();
				$(this).removeClass('dragging');
			});


			$("#drop").on('drop dragdrop',function(e) {

				e.stopPropagation();
				e.preventDefault();
				
				uploadAndReplace(e.originalEvent.dataTransfer.files);
			});
			
			
			$('#updateTable_btn').click(function(){

				var homeworksTd = $("#scores_table td.homework");
				var homeworks = [];
				$(homeworksTd).each(function(index,data){

					var id = $(data).attr("data-pk");
					var score = $(data).html().split(" ")[0];
					var praise = $(data).html().split(" ")[1] ? true : false;
					var res = {'id':id, 'score': score, 'praise': praise};
					homeworks.push(res);
				});

				var examsTd = $("#scores_table td.exam");
				var oldExams = [];

				$(examsTd).each(function(index,data){

					var id = $(data).attr("data-pk");
					var score = $(data).html().split(" ")[0];
					var praise = $(data).html().split(" ")[1] ? true : false;
					var res = {'id':id, 'score': score, 'praise': praise};
					oldExams.push(res);
				});

				var newexamsTd = $("#scores_table td.newExam");
				var newExams = [];

				$(newexamsTd).each(function(index,data){

					var parentId = data.pk.split(" ")[0];
					var studentId = data.pk.split(" ")[1];
					var score = $(data).html().split(" ")[0];
					var praise = $(data).html().split(" ")[1] ? true : false;
					var res = {'parentId':parentId, 'studentId' : studentId,'score':score, 'praise':praise};
					newExams.push(res);
				});

				//var data = [{'homeworkStudentSolvings': homeworks}, {'oldExams': oldExams}, {'newExams': newExams}];
				var data = {'hss': homeworks};
				
				$.ajax({ 
					url: "/scores/update_all", 
					type: 'POST',  
					data: JSON.stringify(data), 
					dataType: 'json',
					contentType: 'application/json',
					error: function(){
						console.log("error");
					},
					success: function(){
						window.location.replace("/scores");					
					}
				});
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


//	return singleton obj
	return {
		getInstance: getInstance
	};

})();