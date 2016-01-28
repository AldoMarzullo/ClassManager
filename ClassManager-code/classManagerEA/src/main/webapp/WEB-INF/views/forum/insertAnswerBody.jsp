<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

	<div class="row row-content">
		<div class="col-sm-12 col-md-12 col-lg-12">
			
			<div class="row">
				<div id="previewQuestionArea" class="col-md-12">
					<div class="panel panel-warning">
					  <div class="panel-heading"><spring:message code="message.forum.questionPreview"/></div>
					  <div class="panel-body">
					    <div class="row">
					  		<div class="col-sm-12 col-md-12 col-lg-12">
						  		<h3><b>${question.getTitle()}</b></h3>
					  			<hr />
					  		</div>
					  	</div>
					  	<div class="row">
					  		<div class="col-sm-12 col-md-12 col-lg-12">
					  			<p>${question.getDescription()}</p>
					  		</div>
					  	</div>
					  	<div class="row">
					  		<div class="col-sm-12 col-md-12 col-lg-12">
					  			<p class="pull-right" style="background-color: #E0EAF1; padding: 10 10 10 10">
						  		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					  			${question.getUser().getUsername()}
					  			</p>
					  		</div>
					  	</div>
					  </div>
					</div>
				</div>
				
				<div id="answerArea" class="col-md-12">
					<form:form commandName="answer" action="insertAnswer" accept-charset="utf-8">
						<hr />
						<h3 style="margin-left: 10px;">
							<spring:message code="message.forum.yourAnswer"/>
							<input class="btn btn-primary" type="submit" value="<spring:message code="message.forum.sendAnswer"/>" style="float: right; margin-right: 5px;">
						</h3>
			        	<div id="area" style="margin-top: 15px;">
				            <form:textarea path="description" id="textEditor"></form:textarea>
				            <div class="form-group has-error">
								<label class="control-label"><form:errors path="description"/></label>
							</div>
			        	</div>
			        	<input name="qid" value="${question.getId() }" style="display:none;">
			        	
			        	<div class="row" style="margin-top: 20px;">
					  		<div class="col-sm-12 col-md-12 col-lg-12">
					  		
				  				<div class="panel-group" id="accordion" role="tablist">
									<div class="panel panel-default">
										<div class="panel-heading" role="tab" id="headingOne">
											<div class="panel-title">
												<div>
													<div role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
													 <b><spring:message code="message.forum.loadedAttachment" /></b>
													</div>
												</div>
											</div>
										</div>
										<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
											<div class="panel-body">
											
									  				<div class="row">
									  					<div id="attachmentSection" class="col-sm-12 col-md-12 col-lg-12">
									  						<c:forEach var="attachment" items="${attachments }">
									  							<div class="attachmentContainer" data-aid="${attachment.getId() }" class="row">
																	<div class="col-sm-10 col-md-10 col-lg-10">
																		<div class="row">
																			<div class="col-sm-2 col-md-2 col-lg-2">
																  			<span style="font-size:2em;" class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
																			</div>
																			<div class="col-sm-8 col-md-8 col-lg-8">
																				<p>${attachment.getName()}</p>
																			</div>
																			<div class="col-sm-2 col-md-2 col-lg-2">
																				<div data-aname="${attachment.getName()}" data-aid="${attachment.getId()}" class="btn btn-danger removeAttachmentBtn">delete</div>
																			</div>
																		</div>
																	</div>
																</div>
									  						</c:forEach>
									  					</div>
									  				</div>
											
											</div>
										</div>
									</div>
								</div>	
					  		</div>
					  	</div>									
						<input id="attachedFiles" value="${attachmentsID }" style="display: none;" name="attachedFiles"/>
						<input value="${question.getId() }" style="display: none;" name="qid" />
			        </form:form>
			        
			        <div class="row" style="margin-top: 20px">
						<div class="col-sm-12 col-md-12 col-lg-12">
							<div class="panel-group" id="accordion2" role="tablist">
								<div class="panel panel-default">
									<div class="panel-heading" role="tab" id="headingTwo">
										<div class="panel-title">
											<div>
												<div class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo" aria-expanded="false" aria-controls="collapseOne"> 
												 <h4><b><spring:message code="message.forum.loadAttachment" /></b></h4>
												</div>
											</div>
										</div>
									</div>
									<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
											<div id="uploadFile_div">
												<form id="upload" method="POST" action="/forum/uploadAnswerAttachment">
													<div id="drop">
														<spring:message code="lectureManager.drophere" text="default text" /> 
														<a> <spring:message code="lectureManager.browse" text="default text" /> </a>
														<input type="file" name="file" />
													</div>
													<ul>
														<!-- The file uploads will be shown here -->
													</ul>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
			        
			        
			        
				</div>						
			</div>
			
		</div>
	</div>
