var ws = new WebSocket("ws://localhost:8080/wsexample");
var numberOfNewNotifications;
takeOldNotifications();
numberOfNewNotifications = 0;
$( document ).ready(function() 
{
});

ws.onopen = function() 
{
	ws.send("helo:" + $("#websocketUser").val());
	console.log("Connect");
};

ws.onmessage = function(evt) 
{
	console.log("Message received");
	takeNewNotifications();
};

ws.onclose = function() 
{
	console.log("Close");
};

ws.onerror = function(err) 
{
	console.log("Close");
};

function takeNewNotifications() 
{
	$('#panelNotificationsNew').html("");
	$.ajax(
	{
		url : '/getNewNotifications',
		datatype : 'text',
		success : function(data) 
		{
			var notifications = eval("(" + data + ")");
			console.log(notifications);
			if(notifications.length > 0)
			{
				numberOfNewNotifications = notifications.length;
				$('#badge').html('' + numberOfNewNotifications);
			}
			for (var i = 0; i < notifications.length; i++) 
			{
				var notification = notifications[i];
				drawNotification(notification, true);
			}
		}
	});
}

function takeOldNotifications() 
{
	$('#panelNotificationsOld').html("");
	$.ajax(
	{
		url : '/getOldNotifications',
		datatype : 'text',
		success : function(data) 
		{
			var notifications = eval("(" + data + ")");
			console.log(notifications);
			for (var i = 0; i < notifications.length; i++) 
			{
				var notification = notifications[i];
				drawNotification(notification, false);
			}
		}
	});
}

function drawNotification(notification, isNew) 
{
	console.log(notification);
	var label = '';
	if(isNew)
		label = '<span id="notification'+notification[0]+'" class="label label-success label-notifications">New</span> ';
	var source = label + '<span class="text-muted">' + notification[3] + '</span>, '
			+ '<span class="text-danger">' + notification[4] + '</span>'
			+ '<p class="text-info">' + notification[1] + '</p>';
	var div = '<div class="notificationRow">' + source + '</div>';
	var a;
	if(isNew)
		a = '<a style="text-decoration:none;" href="' + notification[2] + '" onclick="setThisRead('+notification[0]+')">' + div + '<a>';
	else 
		a = '<a style="text-decoration:none;" href="' + notification[2] + '">' + div + '<a>';
	var li = '<li>' + a + '</li>'
	if(isNew)
		$('#panelNotificationsNew').append(li);
	else
		$('#panelNotificationsOld').append(li);
}

function setThisRead(id)
{
	$.ajax(
	{
		url : '/setNoficationRead?id='+id,
		method: "get",
		datatype : 'text',
		success : function(data) 
		{}
	});
	numberOfNewNotifications--;
	if(numberOfNewNotifications <= 0)
	{
		$('#badge').html('');
		numberOfNewNotifications = 0;
	}
	else
		$('#badge').html(''+numberOfNewNotifications);

	console.log(id+ " " + numberOfNewNotifications);
	$('#notification'+id).remove();
}
	
function resetBadge()
{
	//numberOfNewNotifications = 0;
	//$('#badge').html('');
	/*$.ajax(
	{
		url : '/setNoficationsRead',
		datatype : 'text',
		success : function(data) 
		{}
	});*/
}


	$('.dropdown.keep-open').on({
	    "shown.bs.dropdown": function() { this.closable = false; },
	    "click":             function() { this.closable = true; },
	    "hide.bs.dropdown":  function() { console.log("asdsd"); }
	});