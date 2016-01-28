<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

	<div class="row row-content">
		<div class="col-sm-12 col-md-12 col-lg-12">
			
			<div class="row">
				
				<div class="col-sm-12 col-md-12 col-lg-12">
				
					<div class="panel panel-default">
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
					  	
					  	<div class="row" style="margin-top:20px">
					    	<div class="col-md-12">
					    		<c:forEach var="tag" items="${question.getTags().split(',')}">
					    			<span class="label label-info">${tag}</span>
					    		</c:forEach>
					    	</div>
					    </div>
					  	
						<c:if test="${question.getQuestionAttachedContents().size() != 0 }">
							<div class="row" style="margin-top: 20px;">
						  		<div class="col-sm-8 col-md-8 col-lg-8">
						  		
					  				<div class="panel-group" id="accordion" role="tablist">
										<div class="panel panel-default">
											<div class="panel-heading" role="tab" id="headingOne">
												<div class="panel-title">
													<div>
														<div class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
														 <b><spring:message code="message.forum.attachments" /></b>
														</div>
													</div>
												</div>
											</div>
											<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
												<div class="panel-body">
												
													<c:forEach var="attachment" items="${question.getQuestionAttachedContents() }">
										  				<div class="row">
										  					<div class="col-sm-6 col-md-6 col-lg-6">
										  						<div class="row">
										  							<div class="col-sm-2 col-md-2 col-lg-2">
															  			<span style="font-size:2em;" class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
										  							</div>
										  							<div class="col-sm-10 col-md-10 col-lg-10">
										  								<a href="/download?path=${attachment.getFilePath() }">${attachment.getName() }</a>
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
						</c:if>								  	
					  	
					  	
					  	
					  	<div class="row">
					  		<div class="col-sm-12 col-md-12 col-lg-12">
					  			<p class="pull-right" style="border-radius: 3px; background-color: #E0EAF1; padding: 10 10 10 10">
						  		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					  			${question.getUser().getUsername()}
					  			</p>
					  		</div>
					  	</div>
					  	
					  	<c:if test="${owner == true}">
							<div class="row">
						  		<div class="col-sm-12 col-md-12 col-lg-12">
						  			<form action="/forum/modifyQuestion" method="POST" style="float: left;">
						  				<input type="submit" class="btn btn-sm btn-warning" value="<spring:message code="message.forum.modifyQuestionBtn"/>"/>
						  				<input name="qid" value="${question.getId() }" style="display:none;"/>
						  			</form>
						  			<form action="/forum/deleteQuestion" method="POST">
						  				<input type="submit" class="btn btn-sm btn-danger" value="<spring:message code="message.forum.deleteQuestion"/>"  style="margin-left: 10px"/>
						  				<input name="qid" value="${question.getId() }" style="display:none;"/>
						  			</form>
						  		</div>
						  	</div>
					  	</c:if>
					  	
					  </div>
					</div>	
					
					
					
					<div class="row">
						<div class="col-xs-6 col-sm-10 col-md-10 col-lg-10">
							<h4 style="margin-left: 10px;"><span class="badge">${question.getAnswers().size()}</span> <spring:message code="message.forum.answers"/></h4>
						</div>
						<div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
							<form action="createAnswer" method="POST">
								<input class="btn btn-primary btn-sm" type="submit" value="<spring:message code="message.forum.insertAnswer"/>" style="float:right; margin-right: 10px"/>
								<input name="qid" value="${question.getId() }" style="display:none;"/>
							</form>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12 col-md-12 col-lg-12">
							<c:forEach var="answer" items="${sortedAnswers}">
								<div class="well">
									<div class="row">
								  		<div class="col-sm-12 col-md-12 col-lg-12">
								  			<p>${answer.getDescription() }</p>
								  		</div>
								  	</div>
								  	
								  	<c:if test="${answer.getAnswerAttachedContents().size() != 0 }">
										<div class="row" style="margin-top: 20px;">
									  		<div class="col-sm-8 col-md-8 col-lg-8">
									  		
								  				<div class="panel-group" id="accordion" role="tablist">
													<div class="panel panel-default">
														<div class="panel-heading" role="tab" id="heading${answer.getId() }">
															<div class="panel-title">
																<div>
																	<div class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${answer.getId() }" aria-expanded="false" aria-controls="collapse${answer.getId() }">
																	 <b><spring:message code="message.forum.attachments" /></b>
																	</div>
																</div>
															</div>
														</div>
														<div id="collapse${answer.getId() }" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${answer.getId() }">
															<div class="panel-body">
															
																<c:forEach var="attachment" items="${answer.getAnswerAttachedContents() }">
													  				<div class="row">
													  					<div class="col-sm-6 col-md-6 col-lg-6">
													  						<div class="row">
													  							<div class="col-sm-2 col-md-2 col-lg-2">
																		  			<span style="font-size:2em;" class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
													  							</div>
													  							<div class="col-sm-10 col-md-10 col-lg-10">
													  								<a href="/download?path=${attachment.getFilePath() }">${attachment.getName() }</a>
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
									</c:if>								  	
								  	
								  	
								  	<div class="row">
								  		<c:choose>
										  <c:when test="${answer.getUser().getUsername() eq loggedUser}">
										    <div class="col-sm-6 col-md-6 col-lg-6">
									  			<form action="/forum/modifyAnswer" method="POST" style="float:left">
									  				<input type="submit" class="btn btn-sm btn-warning" value="<spring:message code="message.forum.modifyAnswer"/>"/>
									  				<input name="qid" value="${question.getId() }" style="display:none;"/>
									  				<input name="aid" value="${answer.getId() }" style="display:none;"/>
									  			</form>
									  			<form action="/forum/deleteAnswer" method="POST" >
									  				<input type="submit" class="btn btn-sm btn-danger" value="<spring:message code="message.forum.deleteAnswer"/>" style="margin-left: 10px"/>
									  				<input name="qid" value="${question.getId() }" style="display:none;"/>
									  				<input name="aid" value="${answer.getId() }" style="display:none;"/>
									  			</form>
									  		</div>
									  		<div class="col-sm-6 col-md-6 col-lg-6">
									  			<p class="pull-right" style="background-color: #E0EAF1; padding: 10 10 10 10">
										  		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
									  			${answer.getUser().getUsername()}
									  			</p>
									  		</div>
										  </c:when>
										  <c:otherwise>
										    <div class="col-sm-12 col-md-12 col-lg-12">
									  			<p class="pull-right" style="background-color: #E0EAF1; padding: 10 10 10 10">
										  		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
									  			${answer.getUser().getUsername()}
									  			</p>
									  		</div>
										  </c:otherwise>
										</c:choose>
								  	
								  	</div>
								</div>
							</c:forEach>
							
						</div>
					</div>
					
					<c:if test="${question.getAnswers().size() > 0}">
						<form action="createAnswer" method="POST">
							<input class="btn btn-primary btn-sm" type="submit" value="<spring:message code="message.forum.insertAnswer"/>"/>
							<input name="qid" value="${question.getId() }" style="display:none;"/>
						</form>
				  	</c:if>

			</div>
			
		</div>
	</div>
	
</div>
