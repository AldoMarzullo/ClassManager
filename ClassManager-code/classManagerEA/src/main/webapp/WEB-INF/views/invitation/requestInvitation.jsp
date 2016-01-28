<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-12 col-md-12 col-lg-12">
	<br>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">
				<spring:message code="message.invitation.studentOperation1" />
			</h3>
		</div>
		<div class="panel-body">
			<div class="row col-sm-12 col-md-12 col-lg-12"></div>
			<div class="row col-sm-12 col-md-12 col-lg-12">
				<div class="panel panel-default">
					<!----------------------->
					<!--Selectable Courses -->
					<!----------------------->
					<c:if test="${empty selectableCourse}">
						<br>
						<div class="alert alert-danger" role="alert">
							<strong>Ops!</strong>
							<spring:message code="message.invitation.requestHelpInfo3" />
						</div>
					</c:if>
					<c:if test="${not empty selectableCourse}">
						<table class="table">
							<caption>
								<spring:message code="message.invitation.requestHelpInfo1" />
							</caption>
							<!-- Table Header -->
							<thead>
								<tr>
									<th>#</th>
									<!-- Course Name -->
									<th><spring:message
											code="message.invitation.requestTableHeadField1" /></th>
									<!-- Cfu -->
									<th><spring:message
											code="message.invitation.requestTableHeadField4" /></th>
									<!-- Reference Year -->
									<th><spring:message
											code="message.invitation.requestTableHeadField5" /></th>
									<!-- Professor Name -->
									<th><spring:message
											code="message.invitation.requestTableHeadField2" /></th>
									<th><form:form id="requestAll"
											action="requestInvitation_All" method="POST">
											<button
												class="btn btn-default invitationActionButton pull-right"
												type="submit">
												<spring:message code="message.invitation.requestAll" />
											</button>
											<input type="hidden" name="RequestAll" value="RequestAll">
										</form:form></th>
								</tr>
							</thead>
							<!-- Generated content -->
							<!-- Table Body -->
							<tbody>
								<c:set var="k" value="0" />
								<c:forEach items="${selectableCourse.list}" var="singleCourse">
									<tr>
										<c:set var="k" value="${k+1}" />
										<th scope="row">${k}</th>
										<!-- Course Name -->
										<td>${singleCourse.field1}</td>
										<!-- Cfu -->
										<td>${singleCourse.field2}</td>
										<!-- Reference Year -->
										<td>${singleCourse.field3}</td>
										<!-- Professor Name -->
										<td>${singleCourse.field4}</td>
										<td><form:form id="request${k}"
												action="requestInvitation_Single" method="POST">
												<button
													class="btn btn-default invitationActionButton pull-right"
													type="submit">
													<spring:message code="message.invitation.requestSingle" />
												</button>
												<input type="hidden" name="CourseName"
													value="${singleCourse.field1}">
												<input type="hidden" name="ProfessorName"
													value="${singleCourse.field2}">
											</form:form></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<!---------------------------->
					<!-- End Selectable Courses -->
					<!---------------------------->
				</div>
			</div>
			<br>
			<hr>
			<br>
			<div class="row col-sm-12 col-md-12 col-lg-12"></div>
			<div class="row col-sm-12 col-md-12 col-lg-12">
				<div class="panel panel-default">
					<!------------------------>
					<!--Cancellable Courses -->
					<!------------------------>
					<c:if test="${empty cancellableCourse}">
						<div class="alert alert-danger" role="alert">
							<strong>Ops!</strong>
							<spring:message code="message.invitation.requestHelpInfo4" />
						</div>
					</c:if>
					<c:if test="${not empty cancellableCourse}">
						<table class="table">
							<caption>
								<spring:message code="message.invitation.requestHelpInfo2" />
							</caption>
							<!-- Table Header -->
							<thead>
								<tr>
									<th>#</th>
									<!-- Course Name -->
									<th><spring:message
											code="message.invitation.requestTableHeadField1" /></th>
									<!-- Cfu -->
									<th><spring:message
											code="message.invitation.requestTableHeadField4" /></th>
									<!-- Reference Year -->
									<th><spring:message
											code="message.invitation.requestTableHeadField5" /></th>
									<!-- Professor Name -->
									<th><spring:message
											code="message.invitation.requestTableHeadField2" /></th>
									<th><form:form id="cancelAll"
											action="requestInvitation_CancelAll" method="POST">
											<button
												class="btn btn-default invitationCancelButton pull-right"
												type="submit">
												<spring:message code="message.invitation.cancelAll" />
											</button>
											<input type="hidden" name="CancelAll" value="CancelAll">
										</form:form></th>
								</tr>
							</thead>
							<!-- Generated content -->
							<!-- Table Body -->
							<tbody>
								<c:set var="k" value="0" />
								<c:forEach items="${cancellableCourse.list}" var="singleCourse">
									<tr>
										<c:set var="k" value="${k+1}" />
										<th scope="row">${k}</th>
										<!-- Course Name -->
										<td>${singleCourse.field1}</td>
										<!-- Cfu -->
										<td>${singleCourse.field2}</td>
										<!-- Reference Year -->
										<td>${singleCourse.field3}</td>
										<!-- Professor Name -->
										<td>${singleCourse.field4}</td>
										<td><form:form id="cancel${k}"
												action="requestInvitation_CancelSingle" method="POST">
												<button
													class="btn btn-default invitationCancelButton pull-right"
													type="submit">
													<spring:message code="message.invitation.cancelSingle" />
												</button>
												<input type="hidden" name="CourseName"
													value="${singleCourse.field1}">
												<input type="hidden" name="ProfessorName"
													value="${singleCourse.field2}">
											</form:form></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<!----------------------------->
					<!-- End Cancellable Courses -->
					<!----------------------------->
				</div>
			</div>
		</div>
	</div>
</div>
