<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row row-content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div id="editCalendar_div">
			<button id="editCalendar_btn" type="button" class="btn btn-default">
				<spring:message code="calendar.editCalendar" text="default text" />
			</button>
		</div>

		<div id="updateCalendar_div">
			<button id="confirm_btn" type="button" class="btn btn-success">
				<spring:message code="calendar.confirmUpdate" text="default text" />
			</button>
			<button id="revert_btn" type="button" class="btn btn-default">
				<spring:message code="calendar.revert" text="default text" />
			</button>
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
		
		<!-- ALERT -->
		<div id="alert" class="alert alert-danger fade in">
		    <a href="#" class="close" data-dismiss="alert">&times;</a>
		    <strong>Ops!</strong> An error occours, please try again
		</div>
			
		<!-- contains the calendar -->
		<div style="margin-left: 20%; margin-right: 20%;">
			<div id='calendar'></div>
		</div>
	</div>
</div>
<!-- DIV CONTENT -->
<!-- MODAL -->
<div id="createEvent_modal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					<spring:message code="calendar.createEvent" text="default text" />
				</h4>
			</div>
			<div class="modal-body">
			  <form action="javascript:{}">
				<div class="form-group">

					<label for="eventTitle_create"> 
						<spring:message code="calendar.title" text="default text" />:
					</label> 
					<input class="form-control" id="eventTitle_create" type="text" required="required"/>
					
					<label for="eventStart"> <spring:message
							code="calendar.start" text="default text" />:
					</label> <input class="form-control" id="eventStart_create" type="text" />
					<label for="eventEnd"> <spring:message code="calendar.end"
							text="default text" />:
					</label> <input class="form-control" id="eventEnd_create" type="text" /> <label
						for="colorPicker"> <spring:message code="calendar.color"
							text="default text" />:
					</label> <select name="colorpicker-shortlist" id="colorPicker_create">
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
					</select> <br>
					<div class="row" id="checkboxes">
						<div class="col-xs-12">
							<div class="form-group">
								<label for="" class="col-md-4 control-label">repeat
									every:</label>
								<div class="col-md-7" id="dow-group">
									<c:forEach var="i" begin="1" end="7">
										<input name="${i}" type="hidden" value="0" />
									</c:forEach>
									<div class="btn-group-horizontal"
										data-toggle="buttons-checkbox">
										<c:forEach var="i" begin="1" end="7">
											<button type="button" class="btn btn-default" data-checkbox-name="${i}">${i}</button>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button id="modalButton_createEvent" type="submit" class="btn btn-success">
						<spring:message code="calendar.done" text="default text" />
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="calendar.close" text="default text" />
					</button>
				</div>
			   </form>
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
				<h4 class="modal-title">
					<spring:message code="calendar.updateEvent" text="default text" />
				</h4>
			</div>
			<form action="javascript:{}">
				<div class="modal-body">
					<div class="form-group">
						<input class="form-control" id="eventId_update" type="hidden" /> 
						
						<label for="eventTitle_update"> 
							<spring:message code="calendar.title" text="default text" />:
						</label> 
						<input class="form-control" id="eventTitle_update" type="text" required="required"/>
						
						<label for="eventStart"> <spring:message
								code="calendar.start" text="default text" />:
						</label> <input class="form-control" id="eventStart_update" type="text" />
						<label for="eventEnd"> <spring:message code="calendar.end"
								text="default text" />:
						</label> <input class="form-control" id="eventEnd_update" type="text" /> <label
							for="colorPicker"> <spring:message code="calendar.color"
								text="default text" />:
						</label> <select name="colorpicker-shortlist" id="colorPicker_update">
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
						
						<div class="row" id="checkboxes">
							<div class="col-xs-12">
								<div class="form-group">
									<label for="" class="col-md-4 control-label">repeat
										every:</label>
									<div class="col-md-7" id="dow-group">
										<c:forEach var="i" begin="1" end="7">
											<input name="${i}" type="hidden" value="0" />
										</c:forEach>
										<div class="btn-group-horizontal"
											data-toggle="buttons-checkbox">
											<c:forEach var="i" begin="1" end="7">
												<button type="button" class="btn btn-default"
													data-checkbox-name="${i}">${i}</button>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
						
					</div>
				</div>
				<div class="modal-footer">
					<button id="delete_event_btn" type="button" data-dismiss="modal"
						class="btn btn-default">
						<spring:message code="calendar.delete" text="default text" />
					</button>
					<button id="modalButton_updateEvent" type="submit" class="btn btn-success">
						<spring:message code="calendar.update" text="default text" />
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="calendar.close" text="default text" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>