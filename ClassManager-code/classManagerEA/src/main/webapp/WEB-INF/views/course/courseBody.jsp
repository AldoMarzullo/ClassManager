<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row row-content">
	<div class="col-sm-6 col-md-6 col-lg-6">
		<h3 class="text-muted">
			<spring:message code="message.course.courses" text="default text"/>
		</h3>
	</div>
	<div class="col-sm-6 col-md-6 col-lg-6">
		<c:if test="${not empty loggedIn && role != 'Student' }">
			<div style="float:right;">
				<a href="#" class="btn btn-success" data-toggle="modal" data-target="#${state}">
			      	<span class="glyphicon glyphicon-plus"></span>
			      	<spring:message code="message.course.addButton" text="default text"/>
			    </a>
		    </div>
		</c:if>
	</div>
</div>
<div class="row row-content">
	<c:forEach items="${courses}" var="course">
		<div class="col-md-4">			
			<form:form action="selectCourse" method="post" modelAttribute="selectCourseForm" role="form" id="selectForm${course.id}">	
				<form:input type="hidden" path="id" class="form-control" value="${course.id}" />													
				<a href="javascript:{}" onclick="document.getElementById('selectForm${course.id}').submit();" class="no-text-decoration">
					<div class="panel panel-default course">
						<div class="panel-body">
							<form:input type="hidden" path="name" class="form-control" value="${course.name}" />
							<h4 class="text-info">${course.name}</h4>
							<p class="text-muted">
								<spring:message code="message.course.courseCFU" text="default text"/>: ${course.cfu}
								<form:input type="hidden" path="cfu" class="form-control" value="${course.cfu}" />			
							</p>
							<p class="text-muted">
								<spring:message code="message.course.courseActivationDate" text="default text"/>: ${course.activationDate}
								<form:input type="hidden" path="activationDate" class="form-control" value="${course.activationDate}" />	
							</p>
							<p class="text-muted">
								<spring:message code="message.course.courseEndDate" text="default text"/>: ${course.endDate}
								<form:input type="hidden" path="endDate" class="form-control" value="${course.endDate}" />
							</p>
							<p class="text-muted">
								<spring:message code="message.course.courseReferenceYear" text="default text"/>: ${course.referenceYear}
								<form:input type="hidden" path="referenceYear" class="form-control" value="${course.referenceYear}" />
							</p>
							<p class="text-primary">
								<spring:message code="message.course.degreeCourse" text="default text"/>: ${course.degreeCourse.name} (${course.degreeCourse.courseCode})
								<form:input type="hidden" path="degreeCourse" class="form-control" value="${course.degreeCourse.id}" />	
							</p>	
							<p class="text-danger">
								<form:input type="hidden" path="professor" class="form-control" value="${course.professor.username}" />	
								<spring:message code="message.course.professor" text="default text"/>: ${course.professor.username}
							</p>
						</div>
					</div>
				</a>
				<input type="hidden" name="mess"/>
			</form:form>
		</div>
	</c:forEach>
</div>

<!-- Modal -->
<div id="${state}" class="modal fade" role="dialog">
  	<div class="modal-dialog">
	    <!-- Modal content-->
	    <div class="modal-content">
	      	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal">&times;</button>
	        	<h4 class="modal-title"><spring:message code="message.course.createCourse" text="default text"/></h4>
	      	</div>
	      	<div class="modal-body">
	      		<form:form action="courses" method="post" modelAttribute="courseForm" role="form">
		        	<div class="form-group">
	   					<label for="name"><spring:message code="message.course.courseName" text="default text"/></label>
					    <form:input type="text" path="name" class="form-control" id="name" placeholder="Name" />										    
						<form:errors path="name" cssClass="error" />
					</div>
					<div class="form-group">
						<label for="cfu"><spring:message code="message.course.courseCFU" text="default text"/></label>
					    <form:input type="number" path="cfu" class="form-control" id="cfu" placeholder="CFU" min="1" max="12" />
					    <form:errors path="cfu" cssClass="error" />
					</div>
	 					<div class="form-group"> 
	 						<label for="activationDate"><spring:message code="message.course.courseActivationDate" text="default text"/></label>
					    	<form:input type="date" path="activationDate" class="form-control" id="fakeActivationDate" />
					    	<form:errors path="activationDate" cssClass="error" />
 						</div>					 
						<div class="form-group"> 
							<label for="endDate"><spring:message code="message.course.courseEndDate" text="default text"/></label>
					    	<form:input type="date" path="endDate" class="form-control" id="endDate" />
					    	<form:errors path="endDate" cssClass="error" />
						</div>
					<div class="form-group">
						<label for="referenceYear"><spring:message code="message.course.courseReferenceYear" text="default text"/></label>
					    <form:input type="number" path="referenceYear" class="form-control" id="referenceYear" placeholder="Reference Year"  min="1" max="3" />
					    <form:errors path="referenceYear" cssClass="error" />
					</div>
					<div class="form-group">
						<label for="sel1"><spring:message code="message.course.degreeCourses" text="default text"/></label>
						<form:select path="degreeCourse" class="form-control" id="degreeCourses">
							<c:forEach items="${degreeCourses}" var="degreeCourse">
								<option value="${degreeCourse.id}">${degreeCourse.name} (${degreeCourse.courseCode})</option>
							</c:forEach>
						</form:select>
					</div>
					<div class="form-group">
						<label for="sel1"><spring:message code="message.course.professor" text="default text"/></label>
						<form:select path="professor" class="form-control" id="progessor">
							<option value="${professor.username}">${professor.username}</option>
						</form:select>
					</div>
	      	 		<button type="submit" class="btn btn-success"><spring:message code="message.course.submit" text="default text"/></button>
	      	 	</form:form>
	      	</div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code="message.course.closeModal" text="default text"/>
				</button>
	      	</div>
	    </div>	
	 </div>
</div>

<script>
$(document).ready(function() 
{
	$('#modalOpened').modal('show');
});
</script>