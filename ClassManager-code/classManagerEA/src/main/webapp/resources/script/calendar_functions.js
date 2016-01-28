//used to store temporary new events
var EventTmp = (function(){

	var tmpIds;
	var toDelete;
	
	var EventTmp = function(){

		tmpIds = -1;
		this.getNextId = getNextId;
		this.toDelete = [];
		this.deleteEvent = deleteEvent;
		
	}

	var getNextId = function(){
		return tmpIds--;
	}
	
	var deleteEvent = function(id){
		this.toDelete.push(id);
	}
	
	return EventTmp;

})();

//init functions
$(document).ready(function() {

	createCalendar(false);
	$("#updateCalendar_div").hide();
	$("#delete_event_form").hide();
	$("#update_event_div").hide();

	eventTmp = new EventTmp();

	$('select[name="colorpicker-shortlist"]').simplecolorpicker();
});


function createCalendar(editable, currentDate){

	/*
	 * The javascript script "fullCalendar" creates and initializes the calendar.
	 * the parameters of the calendar ar defined using a JSON.
	 */
	$('#calendar').fullCalendar({

		/*
		 *defines the option to show in the header of the graphics component. Here are defined tree buttons to change
		 *the time of the visulization (prev day or month, next day or month, today) and the granularity of the visualization
		 *(month-week-day)
		 */ 
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		lang: 'it',
		defaultDate: '2015-12-12',

		//allows to modify the event shown
		editable: editable,
		// allows "more" link when too many events
		eventLimit: true,
		
		eventRender: function (event, element) {
		    element.find('.fc-event-title').html(event.title);
		},
		
		views:{
			titleFormat: 'MMM D YYYY'
		},

		/*
		 * loads the events from the server. The event should be represented in JSON format
		 */
		events: "/calendar/events",

		/*
		 * Defines what happens when an event is clicked.
		 */
		eventClick: function(calEvent, jsEvent, view) {

			if(editable && calEvent.editable){
				
				$("#updateEvent_modal").modal('show');
				$("#eventStart_update").val(calEvent.start.format());
				$("#eventEnd_update").val(calEvent.end.format());
				$("#eventId_update").val(calEvent.id)
				$("#eventTitle_update").val(calEvent.title)
				$('#colorPicker_update').simplecolorpicker('selectColor', calEvent.color);
			}

		},

		/*
		 * eventDrop and eventResize defines what happens when the graphical component representative of the event is moved,
		 * stretched or compressed
		 */
		eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc){

			//updateEvent(event,dayDelta,minuteDelta,allDay,revertFunc);
		},
		eventResize: function(event,dayDelta,minuteDelta,allDay,revertFunc){

			//updateEvent(event,dayDelta,minuteDelta,allDay,revertFunc);
		},
		/*
		 * these allows to select graphically a specific range of time. In this application, when a range is selected
		 * a new event is created using the corresponding paramenters
		 */
		selectable: editable,
		selectHelper: true,
		select: 		
			function(start, end, allDay) 
			{ 
				$("#createEvent_modal").modal('show');
				$("#eventTitle_create").val("");
				$("#eventStart_create").val(start.format());
				$("#eventEnd_create").val(end.format());
				$('select[name="colorpicker"]').simplecolorpicker('selectColor', '#7bd148');
	
				$('#calendar').fullCalendar('unselect');
			}
	});
}

/**
 * called when the "edit" button is clicked
 */
$(document).ready(function(){	

	$("#modalButton_createEvent").click(function(event){

		var title = $("#eventTitle_create").val();
		if (title) 
		{
			var id = eventTmp.getNextId();
			var start = $("#eventStart_create").val();
			var end = $("#eventEnd_create").val();
			var color = $('#colorPicker_create').val();
			var dow = [];
			$("#dow-group input[value=1]").each(function(index){
				var val = $(this).attr("name");
				dow.push(val);
			})
			
			var eventJson;
			
			if(dow.length != 0)
				eventJson = {"id" : id,"title":title,"start":start,"end":end,"color":color, "dow":dow, "editable":true};
			else
				eventJson = {"id" : id,"title":title,"start":start,"end":end,"color":color, "editable":true};
			
			$('#calendar').fullCalendar('renderEvent',eventJson,true);
			$("#createEvent_modal").modal('hide');
		}
		//$('#calendar').fullCalendar('unselect');
	});



	$("#modalButton_updateEvent").click(function(event)
			{
				
				if($("#eventTitle_update").val()){
					var old = $('#calendar').fullCalendar( 'clientEvents', $("#eventId_update").val());
					$('#calendar').fullCalendar('removeEvents', $("#eventId_update").val());
					$('#calendar').fullCalendar('renderEvent',
					{
						id: $("#eventId_update").val(),
						title: $("#eventTitle_update").val(),
						start: $("#eventStart_update").val(),
						end: $("#eventEnd_update").val(),
						color: $('#colorPicker_update').val(),
						pow: old[0].pow,
						editable: old[0].editable
					},
					true); // make the event "stick"
					$("#updateEvent_modal").modal('hide');
				}

	});

	$("#editCalendar_btn").click(function(event){

		$("#editCalendar_btn").hide();
		$("#updateCalendar_div").show();
		
		var moment = $('#calendar').fullCalendar('getDate');
		var view = $('#calendar').fullCalendar('getView');
		
		$('#calendar').fullCalendar('destroy');
		createCalendar(true);
		
		//in order to restore the date where the user was
		$('#calendar').fullCalendar('gotoDate', moment);
		$('#calendar').fullCalendar( 'changeView', view.type);
		
	});

	$("#revert_btn").click(function(event){
		revertFunc();
	});
	

});


/**
 * called when the "confirm update" button is clicked
 */
$(document).ready(function(){

	$("#confirm_btn").click(function(event){

		if (!confirm("Are you sure about this change?")) {
			window.redirect("/calendar");
		}

		var toSend = [];
		var calendar = $('#calendar').fullCalendar('clientEvents');
		$.each(calendar, function( index, value ) {

			var event = new Object();

			event.id = value.id;
			event.title = value.title;
			event.startDate = value.start.format();
			if(value.end != null)
			event.endDate = value.end.format();
			event.color = value.color;
			event.dow = value.dow;

			var check = false;
			for (i = 0; i < toSend.length; i++) { 
			    if(toSend[i].id == event.id){
			    	check = true;
			    	break;
			    }
			}
			
			if(!check)
				toSend.push(event);
		});

		/* deletes the event removed */
		var toDelete = JSON.stringify(eventTmp.toDelete);
		
		calendar = JSON.stringify(toSend);
		var success = false;
		
		$.ajax({ 
			headers: {
				Accept : "text/plain; charset=utf-8"
			},
			url: "/calendar/update_calendar", 
			type: 'POST', 
			dataType: 'json', 
			data: calendar, 
			contentType: 'application/json',
			mimeType: 'application/json',
			success: function(data) { 
				
				sendDelete(toDelete);
			},
			error:function(data,status,er) {   
				
				 $("#alert").alert().show();
			}
		});
		
		
	});
});


function sendDelete(data){
	
	$.ajax({ 
		headers: {
			Accept : "text/plain; charset=utf-8"
		},
		url: "calendar/delete_events", 
		type: 'POST', 
		dataType: 'json', 
		data: data,
		contentType: 'application/json',
		mimeType: 'application/json',
		success: function(data) { 
			window.location.replace("/calendar");
		},
		error:function(data,status,er) {   
			
			 $("#alert").alert().show();
		}
	});
}

/**
 * called when the "delete" button is clicked
 */
$(document).ready(function(){

	$("#delete_event_btn").click(function(event){

		var eventToRemove = $("#eventId_update").val();
		$('#calendar').fullCalendar('removeEvents', eventToRemove);
		eventTmp.deleteEvent(eventToRemove);
		
	});
	

	$('.btn[data-checkbox-name]').click(function() {
	    $('input[name="'+$(this).data('checkboxName')+'"]').val(
	        $(this).hasClass('active') ? 0 : 1
	    );
	});
	
	$("#alert").alert().hide();
});

function revertFunc(){
	window.location.replace("/calendar");
}

/* TODO USE DATE AND HOURS
 * TODO CHECK INPUT AND DATE VALIDITY
 */


/*events: [
{
	 title: 'All Day Event',
	 start: '2015-12-01',
	 editable: false,
	 dow: [1, 4]
},
{
	 title: 'Long Event',
	 start: '2015-12-07',
	 end: '2015-12-10'
},
{
	 id: 999,
	 title: 'Repeating Event',
	 start: '2015-12-09T16:00:00'
},
{
	 id: 999,
	 title: 'Repeating Event',
	 start: '2015-12-16T16:00:00'
},
{
	 title: 'Conference',
	 start: '2015-12-11',
	 end: '2015-12-13'
},
{
	 title: 'Meeting',
	 start: '2015-12-12T10:30:00',
	 end: '2015-12-12T12:30:00'
},
{
	 title: 'Lunch',
	 start: '2015-12-12T12:00:00'
},
{
	 title: 'Meeting',
	 start: '2015-12-12T14:30:00'
},
{
	 title: 'Happy Hour',
	 start: '2015-12-12T17:30:00'
},
{
	 title: 'Dinner',
	 start: '2015-12-12T20:00:00'
},
{
	 title: 'Birthday Party',
	 start: '2015-12-13T07:00:00'
},
{
	 title: 'Click for Google',
	 url: 'http://google.com/',
	 start: '2015-12-28'
}
]

/*
 * sends an Ajax request to the server when an event is modify in order to store the new information
 */
/*function updateEvent(event,dayDelta,minuteDelta,allDay,revertFunc){

	alert(event.title + " was dropped on " + event.start.format());

	if (!confirm("Are you sure about this change?")) {
		revertFunc();
	}
	else{

		var end = event.end.format();//.split("-");
		//var endDate = new Date(end[2], end[1] - 1, end[0]);

		var start = event.start.format();//.split("-");
		//var startDate = new Date(start[2], start[1] - 1, start[0]);

		var event = {'endDate': start, 'id': event.id, 'startDate': end, 'title': event.title};

		$.ajax({ 
			headers: {
				Accept : "text/plain; charset=utf-8"
			},
			url: "/update_event", 
			type: 'POST', 
			dataType: 'json', 
			data: JSON.stringify(event), 
			contentType: 'application/json',
			mimeType: 'application/json',
			success: function(data) { 
				window.redirect("/calendar");
			},
			error:function(data,status,er) { 
				alert("error: "+data+" status: "+status+" er:"+er);
				revertFunc();

			}
		});


	}
}

/*
 * sends an Ajax request to the server when an event is created in order to store the new information
 */
/*function createEvent(title,start,end){


	var end = end.format();//.split("-");
	//var endDate = JSON.stringify(new Date(end[2], end[1] - 1, end[0]));

	var start = start.format();//.split("-");
	//var startDate = new Date(start[2], start[1] - 1, start[0]).toString();

	var event = {'endDate': start, 'id': tmpIds, 'startDate': end, 'title': event.title};

	$.ajax({ 
		headers: {
			Accept : "text/plain; charset=utf-8"
		},
		url: "/create_event", 
		type: 'POST', 
		dataType: 'json', 
		data: JSON.stringify(event), 
		contentType: 'application/json',
		mimeType: 'application/json',
		success: function(data) { 
			window.redirect("/calendar");
		},
		error:function(data,status,er) { 
			alert("error: "+data+" status: "+status+" er:"+er);
			revertFunc();

		}
	});

}

/**
 * called when the "delete" button is clicked
 */
/*$(document).ready(function(){

	$("#delete_event_submit").click(function(event){

		if (!confirm("Are you sure about this change?")) {
			event.preventDefault();
		}
	});
});
 */