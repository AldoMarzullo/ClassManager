<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Class Manager</title>
<script src='resources/lib/jquery/jquery.min.js'></script>
<script src="resources/lib/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/lib/bootstrap-3.3.5-dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="resources/style/nav-bar.css" />
<script src="resources/script/nav-bar.js"></script>

<!-- Calendar -->
<link href='resources/lib/jquery-ui/jquery-ui.css' rel='stylesheet' />
<link href='resources/lib/fullcalendar-2.5.0/fullcalendar.css'
	rel='stylesheet' />
<link href='resources/lib/fullcalendar-2.5.0/fullcalendar.print.css'
	rel='stylesheet' media='print' />

<script src='resources/lib/jquery-ui/jquery-ui.js'></script>
<script src='resources/lib/fullcalendar-2.5.0/lib/moment.min.js'></script>
<script src='resources/lib/fullcalendar-2.5.0/fullcalendar.min.js'></script>
<script src='resources/lib/fullcalendar-2.5.0/lang-all.js'></script>

<script src='resources/script/calendar_functions.js'></script>

<!-- colorpicker -->
<script src="resources/lib/simplecolorpicker/jquery.simplecolorpicker.js"></script>
<link rel="stylesheet" href="resources/lib/simplecolorpicker/jquery.simplecolorpicker.css">
<!--  -->

</head>

<body>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header pull-left">
				<a id="menu-toggle" href="#" class="navbar-toggle"> <span
					class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="navbar-brand" href="#"> Class Manager </a>
			</div>
			<div class="navbar-header pull-right">
				<a class="nav-bar-button logout" href="#"><span
					class="glyphicon glyphicon-log-out"></span> Logout</a>
			</div>
		</div>
	</nav>

	<div class="container-fluid page-content">
		<div class="row">
			<!-- SIDEBAR -->
			<div class="col-sm-3 col-md-3 col-lg-2">
				<div id="sidebar-wrapper" class="sidebar-desktop">
					<!-- collapse width -->
					<ul class="sidebar-nav">
						<li><a href="#"><span class="glyphicon glyphicon-home"></span>
								Dashboard</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-book"></span>
								Classroom</a></li>
						<li><a href="#"><span
								class="glyphicon glyphicon-folder-open"></span> Materials</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-list"></span>
								Discussions</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-stats"></span>
								Statistics</a></li>
					</ul>
				</div>
			</div>
			<div class="col-sm-9 col-md-9 col-lg-10">
				<div class="row row-content">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<div id="editCalendar_div">
							<button id="editCalendar_btn">Edit Calendar</button>
						</div>
		
						<div id="updateCalendar_div">
							<button id="confirm_btn">Confirm Update</button>
							<button id="revert_btn">Revert</button>
						</div>
					</div>
				</div>

				<div class="row row-content">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<!-- When an event is clicked the "event" field of this form is filled. If the user
		will click on the "update" button a delete request will be send to the server -->

					</div>
				</div>
				<div class="row row-content">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<!-- contains the calendar -->
						<div style="margin-left: 20%; margin-right: 20%;">
							<div id='calendar'></div>
						</div>
					</div>
				</div>
			</div>
			<!-- DIV CONTENT -->
		</div>
	</div>
	<!-- MODAL -->
	<div id="createEvent_modal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Create Event</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="eventTitle_create">Title: </label>
						<input class="form-control" id="eventTitle_create" type="text" />
						<label for="eventStart">Start: </label>
						<input class="form-control" id="eventStart_create" type="text" />
						<label for="eventEnd">End:</label>
						<input class="form-control" id="eventEnd_create" type="text" />
						<label for="colorPicker">Color:</label>
						<select name="colorpicker-shortlist" id="colorPicker_create">
							<option value="#7bd148">Green</option>
							<option value="#5484ed">Bold blue</option>
							<option value="#a4bdfc">Blue</option>
							<option value="#46d6db">Turquoise</option>
							<option value="#7ae7bf">Light green</option>
							<option value="#51b749">Bold green</option>
							<option value="#fbd75b">Yellow</option>
							<option value="#ffb878">Orange</option>
							<option value="#ff887c">Red</option>
							<option value="#dc2127">Bold red</option>
							<option value="#dbadff">Purple</option>
							<option value="#e1e1e1">Gray</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button id="modalButton_createEvent" type="button"
						class="btn btn-success">Create</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- MODAL UPDATE -->
		<div id="updateEvent_modal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Create Event</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input class="form-control" id="eventId_update" type="hidden" />
						<label for="eventTitle_update">Title: </label>
						<input class="form-control" id="eventTitle_update" type="text" />
						<label for="eventStart">Start: </label>
						<input class="form-control" id="eventStart_update" type="text" />
						<label for="eventEnd">End:</label>
						<input class="form-control" id="eventEnd_update" type="text" />
						<label for="colorPicker">Color:</label>
						<select name="colorpicker-shortlist" id="colorPicker_update">
							<option value="#7bd148">Green</option>
							<option value="#5484ed">Bold blue</option>
							<option value="#a4bdfc">Blue</option>
							<option value="#46d6db">Turquoise</option>
							<option value="#7ae7bf">Light green</option>
							<option value="#51b749">Bold green</option>
							<option value="#fbd75b">Yellow</option>
							<option value="#ffb878">Orange</option>
							<option value="#ff887c">Red</option>
							<option value="#dc2127">Bold red</option>
							<option value="#dbadff">Purple</option>
							<option value="#e1e1e1">Gray</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button id="delete_event_btn" type="button"
						data-dismiss="modal" class="btn btn-default">Delete</button>
					<button id="modalButton_updateEvent" type="button"
						class="btn btn-success">Update</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>

</html>

<!-- When an event is clicked the "event" field of this form is filled. If the user
	will click on the "delete" button a delete request will be send to the server -->
<!--<form:form id="delete_event_form" action="/delete_event"
							method="post" commandName="FullCalendarEventBean">
							<input type="hidden" path="id" />
							<input id="delete_event_submit" type="submit" value="Delete">
						</form:form>-->
