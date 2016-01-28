<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h2><spring:message code="message.noticeboard" text="default text"/></h2>
<form class="form-inline">
	<label for="select"><spring:message code="message.noticesperpage" text="default text"/></label>
	<select id="page-size" class="form-control" name="users">
	  <option value="10">10</option>
	  <option value="25">25</option>
	  <option value="50">50</option>
	  <option value="100">100</option>
	</select>
	<c:if test="${not empty pageSize}">
		<input id="selected-value" type="hidden" value="${pageSize}"/>
	</c:if>
	<c:if test="${not empty loggedIn && role == 'Professor' || role == 'admin' }">
		<button id="new-post" type="button" class="btn btn-success"><spring:message code="message.noticeboard.newpost" text="default text"/></button> 
	</c:if>
	
</form>
<!-- 	<div class="col-sm-9 col-md-9 col-lg-10">		 -->
		<div id="form-div" style="display : ${ display ? 'block' : 'none'}" class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
 				<form:form commandName="new-notice" action="newnotice" role="form">
				  	<spring:bind path="name">
						<div class="form-group ${status.error ? 'has-error' : ''}">
						   <label for="title"><spring:message code="message.noticeboard.title" text="default text" /></label>
				  		   <form:input path="name" name="title" type="text" class="form-control" id="notice-title" placeholder="Title"/>
						   <span id="helpBlock" class="help-block">${status.errorMessages[0]}</span>
						</div>
   					</spring:bind>
				    
				  	<spring:bind path="description">
						<div class="form-group ${status.error ? 'has-error' : ''}">
						    <label for="text"><spring:message code="message.noticeboard.text" text="default text" /></label>
						    <form:textarea path="description" id="textEditor"></form:textarea>
							<span id="helpBlock" class="help-block">${status.errorMessages[0]}</span>
						</div>
   					 </spring:bind>
   					<form:input id="notice-id" path="id" name="id" type="hidden" class="form-control"/>
				  <button type="submit" class="btn btn-default">Submit</button>
				</form:form>
			</div>
		</div>
		<c:forEach var="notice" items="${noticesList.getPageList()}">
	  			<div class="row">
	  				<div class="col-sm-7 col-md-7 col-lg-7">
			  			<h4>${notice.name}</h4>
							<c:choose>
						    <c:when test="${notice.isServiceMessage()}">
						       <h4 style="color:red;text-transform:uppercase"><spring:message code="message.noticeboard.service" text="default text"/></h4>
						    </c:when>    
						    <c:otherwise>
					       		<h4>${ notice.professor.getLastName()}</h4>
					    	</c:otherwise>
							</c:choose>
  			 			${notice.description}
	  				</div>
	  				<div class="col-sm-5 col-md-5 col-lg-5">
	  				 	${notice.date}
	  				<c:if test="${ loggedIn.equals(notice.professor.getUsername()) && role == 'Professor' || not empty loggedIn && role == 'admin' }">
						<form style="display:inline-block" action="deletenotice" id="${notice.id}" method="POST">
							<input type="hidden" name="post-id" value="${notice.id}"/>
							<button type="submit" class="btn btn-danger" form="${notice.id}"><spring:message code="message.noticeboard.deletepost" text="default text"/></button>
						</form>
						<button class="btn btn-primary edit"><spring:message code="message.noticeboard.editpost" text="default text"/></button>
						
					</c:if>				
	  				</div>
	  				
	  				

	  			
	  			
	  			</div>
		</c:forEach>
		
		<form id="options">
			<input name="total-pages" type="hidden" value="${pageCount}"/> 
			<input name="page-number" type="hidden" value="${pageNumber}"/> 
		</form>
		<div id="paginator">
		</div>
		
		<div id="prompt-modal" class="modal fade" tabindex="-1" role="dialog">
			<div class="modal-dialog">
			  <div class="modal-content">
			    <div class="modal-header">
			      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			      <h4 class="modal-title"><spring:message text="default text" code="message.noticeboard.warning"/></h4>
			    </div>
			    <div class="modal-body">
			      <p><spring:message text="default text" code="message.noticeboard.prompt"/></p>
			    </div>
			    <div class="modal-footer">
			      <button id="do-delete" type="button" class="btn btn-success" data-dismiss="modal"><spring:message text="default text" code="message.noticeboard.confirm"/></button>
			      <button type="button" class="btn btn-primary" data-dismiss="modal">No</button>
			    </div>
			  </div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
