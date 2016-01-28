<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-12 col-md-12 col-lg-12">
	<br>
	<div class="row">
		<div class="col-sm-7 col-md-7 col-lg-7">
			<!----------------------->
			<!--   Research Bar    -->
			<!----------------------->
			<c:if test="${not empty courseSelected}">
				<form class="form-inline" action="/sendInvitation_research"
					method="post">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							</div>
							<c:if test="${empty researchField}">
								<input type="text" class="form-control" name="researchBar"
									placeholder="Reserch...">
							</c:if>
							<c:if test="${not empty researchField}">
								<input type="text" class="form-control" name="researchBar"
									value="${researchField}" placeholder="Reserch...">
							</c:if>
							<input type="hidden" name="courseName" value="${courseSelected}">
						</div>
					</div>
					<button type="submit" class="btn btn-primary">
						GO! <span class="glyphicon glyphicon-arrow-right"
							aria-hidden="true"></span>
					</button>
					<button class="btn invitationActionButton" type="button"
						data-toggle="collapse" data-target="#fileUpload"
						aria-expanded="false" aria-controls="fileUpload">FileUpload</button>
				</form>
			</c:if>
			<!----------------------->
			<!-- End Research Bar  -->
			<!----------------------->
			<!----------------------->
			<!--File Upload Button -->
			<!----------------------->
			<c:if test="${empty courseSelected}">
				<button class="btn invitationActionButton" type="button"
					data-toggle="collapse" data-target="#fileUpload"
					aria-expanded="false" aria-controls="fileUpload">FileUpload</button>
			</c:if>
		</div>
		<div class="col-sm-5 col-md-5 col-lg-5">
			<!----------------------->
			<!--    File Upload    -->
			<!----------------------->
			<div class="collapse" id="fileUpload">
				<div class="panel panel-default">
					<div class="panel-body">
						<form action="/sendInvitation" enctype="multipart/form-data"
							method="post">
							<h4>
								<spring:message code="message.invitation.sendFile" />
							</h4>
							<hr>
							<input class="btn btn-default invitationActionButton pull-right"
								type="file" name="sendFile" value="Input"> <br> <br>
							<button class="btn btn-default invitationActionButton pull-right"
								type="submit">
								<span class="glyphicon glyphicon-cloud-upload"
									aria-hidden="true"></span>
								<spring:message code="message.sendFileUpload" />
							</button>
							<br> <br>
						</form>
						<c:if test="${not empty fileFormatError}">
							<hr>
							<div class="alert alert-danger" role="alert">
								<spring:message code="message.invitation.sendFileFormatError" />
							</div>
						</c:if>
						<div class="alert alert-warning" role="alert">
							<spring:message code="message.invitation.sendFileFormatHeader" />
							<br>
							<spring:message code="message.invitation.sendFileFormatContent" />
							<br>
							<spring:message code="message.invitation.sendFileFormatContent" />
							<br> ...<br>
							<spring:message code="message.invitation.sendFileFormatContent" />
							<br>
						</div>
					</div>
				</div>
			</div>
			<!----------------------->
			<!--  End File Upload  -->
			<!----------------------->
		</div>
	</div>
</div>
<div class="col-sm-12 col-md-12 col-lg-12">
	<br>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<spring:message code="message.invitation.professorOperation1" />
			</h3>
		</div>
		<div class="panel-body">
			<div class="row col-sm-12 col-md-12 col-lg-12">
				<br> <span class="glyphicon glyphicon-info-sign pull-left"
					aria-hidden="true"> </span> <strong class="pull-left"> <spring:message
						code="message.invitation.sendHelpInfo7" />
				</strong>
				<div class="dropdown pull-left ">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="courseSelector" data-toggle="dropdown" aria-haspopup="false"
						aria-expanded="true">
						<spring:message code="message.invitation.sendHelpInfo8" />
						<span class="caret"></span>
					</button>
					<!----------------------->
					<!--    Course List    -->
					<!----------------------->
					<ul class="dropdown-menu" aria-labelledby="courseSelector">
						<c:forEach items="${courseList.list}" var="singleCourse">
							<li><a
								href="/sendInvitation?courseName='${singleCourse.field1}'">${singleCourse.field1}</a></li>
						</c:forEach>
					</ul>
					<!----------------------->
					<!--  End Course List  -->
					<!----------------------->
				</div>
				<c:if test="${not empty courseSelected}">
					<button type="button" class="btn btn-success pull-right">${courseSelected}</button>
				</c:if>
			</div>
			<br> <br> <br>
			<c:if test="${not empty courseSelected}">
				<div class="row col-sm-12 col-md-12 col-lg-12">
					<div class="panel panel-default">
						<!----------------------->
						<!--Selectable Student -->
						<!----------------------->
						<c:if test="${empty studentList}">
							<br>
							<div class="alert alert-danger" role="alert">
								<strong>Ops!</strong>
								<spring:message code="message.invitation.sendHelpInfo2" />
							</div>
						</c:if>
						<c:if test="${not empty studentList}">
							<table class="table">
								<caption>
									<spring:message code="message.invitation.sendHelpInfo1" />
								</caption>
								<!-- Table Header -->
								<thead>
									<tr>
										<th>#</th>
										<!-- Username -->
										<th><spring:message
												code="message.invitation.sendTableHeadField1" /></th>
										<!-- Serial Number -->
										<th><spring:message
												code="message.invitation.sendTableHeadField3" /></th>
										<!-- Subscription Date -->
										<th><spring:message
												code="message.invitation.sendTableHeadField4" /></th>
										<!-- First Name -->
										<th><spring:message
												code="message.invitation.sendTableHeadField5" /></th>
										<!-- Second Name -->
										<th><spring:message
												code="message.invitation.sendTableHeadField6" /></th>
										<th><form:form id="inviteAll"
												action="sendInvitation_InviteAll" method="POST">
												<button
													class="btn btn-default invitationActionButton pull-right"
													type="submit">
													<spring:message code="message.invitation.inviteAll" />
												</button>
												<input type="hidden" name="courseSelected"
													value="${courseSelected}">
												<input type="hidden" name="InviteAll" value="InviteAll">
											</form:form></th>
									</tr>
								</thead>
								<!-- Generated content -->
								<!-- Table Body -->
								<tbody>
									<c:set var="k" value="0" />
									<c:forEach items="${studentList.list}" var="singleStudent">
										<!-- Table Row -->
										<tr>
											<c:set var="k" value="${k+1}" />
											<th scope="row">${k}</th>
											<!-- Student Name -->
											<td>${singleStudent.field1}</td>
											<!-- Serial Number -->
											<td>${singleStudent.field2}</td>
											<!-- Subscription Date -->
											<td>${singleStudent.field3}</td>
											<!-- First Name -->
											<td>${singleStudent.field4}</td>
											<!-- Second Name -->
											<td>${singleStudent.field5}</td>
											<td><form:form id="invite${k}"
													action="sendInvitation_InviteSingle" method="POST">
													<button
														class="btn btn-default invitationActionButton pull-right"
														type="submit">
														<spring:message code="message.invitation.inviteSingle" />
													</button>
													<input type="hidden" name="courseSelected"
														value="${courseSelected}">
													<input type="hidden" name="StudentName"
														value="${singleStudent.field1}">
												</form:form></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<!---------------------------->
						<!-- End Selectable Student -->
						<!---------------------------->
					</div>
				</div>

				<br>
				<hr>
				<br>

				<div class="row col-sm-12 col-md-12 col-lg-12">
					<div class="panel panel-default">
						<!------------------------>
						<!--Cancellable Student -->
						<!------------------------>
						<c:if test="${empty invitedStudent}">
							<br>
							<div class="alert alert-danger" role="alert">
								<strong>Ops!</strong>
								<spring:message code="message.invitation.sendHelpInfo3" />
							</div>
						</c:if>
						<c:if test="${not empty invitedStudent}">
							<table class="table">
								<caption>
									<spring:message code="message.invitation.sendHelpInfo4" />
								</caption>
								<!-- Table Header -->
								<thead>
									<tr>
										<th>#</th>
										<!-- Username -->
										<th><spring:message
												code="message.invitation.sendTableHeadField1" /></th>
										<!-- Serial Number -->
										<th><spring:message
												code="message.invitation.sendTableHeadField3" /></th>
										<!-- Subscription Date -->
										<th><spring:message
												code="message.invitation.sendTableHeadField4" /></th>
										<!-- First Name -->
										<th><spring:message
												code="message.invitation.sendTableHeadField5" /></th>
										<!-- Second Name -->
										<th><spring:message
												code="message.invitation.sendTableHeadField6" /></th>
										<th><form:form id="cancelAll"
												action="sendInvitation_CancelAll" method="POST">
												<button
													class="btn btn-default invitationCancelButton pull-right"
													type="submit">
													<spring:message code="message.invitation.cancelAll" />
												</button>
												<input type="hidden" name="courseSelected"
													value="${courseSelected}">
												<input type="hidden" name="CancelAll" value="CancelAll">
											</form:form></th>
									</tr>
								</thead>
								<!-- Generated content -->
								<!-- Table Body -->
								<tbody>
									<c:set var="k" value="0" />
									<c:forEach items="${invitedStudent.list}" var="singleStudent">
										<tr>
											<c:set var="k" value="${k+1}" />
											<th scope="row">${k}</th>
											<!-- Username -->
											<td>${singleStudent.field1}</td>
											<!-- Serial Number -->
											<td>${singleStudent.field2}</td>
											<!-- Subscription Date -->
											<td>${singleStudent.field3}</td>
											<!-- First Name -->
											<td>${singleStudent.field4}</td>
											<!-- Second Name -->
											<td>${singleStudent.field5}</td>
											<td><form:form id="cancel${k}"
													action="sendInvitation_CancelSingle" method="POST">
													<button
														class="btn btn-default invitationCancelButton pull-right"
														type="submit">
														<spring:message code="message.invitation.cancelSingle" />
													</button>
													<input type="hidden" name="courseSelected"
														value="${courseSelected}">
													<input type="hidden" name="StudentName"
														value="${singleStudent.field1}">
												</form:form></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
						<!----------------------------->
						<!-- End Cancellable Student -->
						<!----------------------------->
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>