<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-12 col-md-12 col-lg-12">
	<!------------------------------>
	<!-- Tab Component JavaScript -->
	<!------------------------------>
	<script type="text/javascript">
		$('#totalAvgLectureProfessor').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		})
		$('#totalAvgLectureProfessors').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		})
		$('#noLectureProfessor').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		})
		$('#noLectureProfessors').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		})
		$('#avgLectureProfessor').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		})
		$('#avgLectureProfessors').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		})
	</script>
	<!---------------------------------->
	<!-- End Tab Component JavaScript -->
	<!---------------------------------->

	<div class="col-sm-12 col-md-12 col-lg-12">
		<br>
		<!------------------------------>
		<!-- Professor Number Courses -->
		<!------------------------------>
		<div class="col-sm-5 col-md-5 col-lg-5">
			<div id="idCartContainer1"></div>
		</div>
		<div class="panel panel-default col-sm-7 col-md-7 col-lg-7">
			<div class="panel-body col-sm-12 col-md-12 col-lg-12">
				<div>
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a
							href="#totalAvgLectureProfessor"
							aria-controls="totalAvgLectureProfessor" role="tab"
							data-toggle="tab"><spring:message
									code="message.statistics.Professor_AvgLectureByWeekDaySingleProfessor_TabHead" /></a></li>
						<li role="presentation"><a href="#totalAvgLectureProfessors"
							aria-controls="totalAvgLectureProfessors" role="tab"
							data-toggle="tab"><spring:message
									code="message.statistics.Professor_AvgLectureByWeekDayAllProfessor_TabHead" /></a></li>
					</ul>
					<!-- Tab panes -->
					<div class="tab-content">
						<!--------------------------------------------------->
						<!-- Average Lectures By Week Day Single Professor -->
						<!--------------------------------------------------->
						<div role="tabpanel" class="tab-pane active"
							id="totalAvgLectureProfessor">
							<div id="idCartContainer3"></div>
						</div>
						<!--------------------------------------------------->
						<!-- Average Lectures By Week Day All Professor -->
						<!--------------------------------------------------->
						<div role="tabpanel" class="tab-pane"
							id="totalAvgLectureProfessors">
							<div id="idCartContainer4"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-sm-12 col-md-12 col-lg-12">
		<!---------------------------------------------------->
		<!-- For Year Lectures By Week Day Single Professor -->
		<!---------------------------------------------------->
		<div class="panel panel-default col-sm-12 col-md-12 col-lg-12">
			<div class="panel-body">
				<div id="idCartContainer2"></div>
			</div>
		</div>
	</div>

	<div class="col-sm-12 col-md-12 col-lg-12">
		<!----------------------->
		<!-- Homework Analysis -->
		<!----------------------->
		<div class="panel panel-default col-sm-12 col-md-12 col-lg-12">
			<div class="panel-heading">
				<h3 class="panel-title">
					<spring:message code="message.statistics.Professor_Homework_Head" />
				</h3>
			</div>
			<div class="panel-body">
				<!------------------------------------>
				<!-- Average Time Delivery Homework -->
				<!------------------------------------>
				<div class="col-sm-6 col-md-6 col-lg-6">
					<div id="idCartContainer5"></div>
				</div>
				<!---------------------------->
				<!-- Average Score Homework -->
				<!---------------------------->
				<div class="col-sm-6 col-md-6 col-lg-6">
					<div id="idCartContainer6"></div>
				</div>

				<!----------------------->
				<!--   Research Bar    -->
				<!----------------------->
				<form class="form-inline" action="/statistics" method="post">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
							</div>
							<c:if test="${empty researchField}">
								<input type="text" class="form-control" name="researchBar"
									placeholder="Type a name...">
							</c:if>
							<c:if test="${not empty researchField}">
								<input type="text" class="form-control" name="researchBar"
									value="${researchField}" placeholder="Type a name...">
							</c:if>
						</div>
					</div>
					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
					</button>
				</form>
				<!----------------------->
				<!-- End Research Bar  -->
				<!----------------------->
			</div>
		</div>

		<!------------------------->
		<!-- Attendance Analysis -->
		<!------------------------->
		<div class="panel panel-default col-sm-12 col-md-12 col-lg-12">
			<div class="panel-heading">
				<h3 class="panel-title">
					<spring:message code="message.statistics.Professor_Attendance_Head" />
				</h3>
			</div>
			<div class="panel-body">
				<!------------------------->
				<!-- Average Attendances -->
				<!------------------------->
				<div class="col-sm-12 col-md-12 col-lg-12">
					<div id="idCartContainer7"></div>
				</div>
			</div>
		</div>
	</div>
</div>