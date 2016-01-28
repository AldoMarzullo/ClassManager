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
				
					<div class="row">
						<div class="col-sm-12 col-md-12 col-lg-12">
							<div class="panel panel-default">
							  <div class="panel-heading">
							  	<div class="row">
							  		<div class="col-md-12">
							  			<h4><b><spring:message code="message.forum.questionsTitle"/></b></h4>
							  		</div>
							  	</div>
							    
							  </div>
							</div>	
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-offset-8 col-md-2">
							<a class="btn btn-primary btn-sm" href="/forum/searchQuestion"><spring:message code="message.forum.searchQuestion"/></a>
						</div>
						<div class="col-md-2">
							<form action="insertQuestion">
								<input class="btn btn-primary btn-sm" type="submit" value="<spring:message code="message.forum.insertQuestion"/>"/>
							</form>
						</div>
					</div>
					
					<c:if test="${elemNum != 0}">
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div id="paginatorTop">
								</div>
							</div>
						</div>
					</c:if>
					
					
					<div class="row">
						<div class="col-sm-12 col-md-12 col-lg-12">
							<ul id="questionsList" class="list-group">
								<c:forEach var="question" items="${questions}">
									<li class="list-group-item answerRow" data-qid="${question.getId() }">
									  <div class="row text-center">
										<div class="col-md-2">
											<div class="col-sm-12 col-md-12 col-lg-12" style="margin-top: 10px">
									  			<div class="pull-right" style="border-radius: 5px; background-color: #8096A5; padding: 10 10 10 10">
										  			<div class="row">
														<div class="col-md-12">
															${question.getAnswers().size()}
														</div>
													</div>
													<div class="row">
														<div class="col-md-12"><spring:message code="message.forum.answers"/></div>
													</div>
									  			</div>
								  			</div>
										</div>
										<div class="col-md-10">
											<div class="row">
												<div class="col-md-12">
													<h4><a class="questionLink" href="/forum/detailedQuestion?qid=${question.getId()}">${question.getTitle()}</a></h4>
												</div>
											</div>
											<div class="row">
										    	<div class="col-md-12">
										    		<c:forEach var="tag" items="${question.getTags().split(',')}">
										    			<span class="label label-info">${tag}</span>
										    		</c:forEach>
										    	</div>
										    </div>
											<div class="row">
												<div class="col-sm-12 col-md-12 col-lg-12">
										  			<p class="pull-right" style="border-radius: 3px; background-color: #E0EAF1; padding: 5 5 5 5">
											  		<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
										  			${question.getUser().getUsername() }
										  			</p>
										  		</div>
											</div>
										</div>
									  </div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					
					<c:if test="${elemNum != 0}">
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<div id="paginatorBottom">
								</div>
							</div>
						</div>
					</c:if>
					
					<input type="hidden" value="${currPage }" id="currPage" />
					<input type="hidden" value="${pageCount }" id="pageCount" />
					<input type="hidden" value="${pageSize }" id="pageSize" />
				</div>

			</div>
			
		</div>
	</div>
