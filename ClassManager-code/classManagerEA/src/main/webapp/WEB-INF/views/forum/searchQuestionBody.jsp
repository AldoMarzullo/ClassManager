<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

	<spring:htmlEscape defaultHtmlEscape="true" /> 
	<div class="row row-content">
		<div class="col-sm-12 col-md-12 col-lg-12">

			<div class="row">
				<div class="col-sm-12 col-md-12 col-lg-12">

					<div class="row">
						<div class="col-sm-12 col-md-12 col-lg-12">
							<div class="panel-group" id="accordion" role="tablist">
								<div class="panel panel-default">
									<div class="panel-heading" role="tab" id="headingOne">
										<div class="panel-title">
											<div>
												<div role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"> 
												 <h4><b><spring:message code="message.forum.searchQuestionForm" /></b></h4>
												</div>
											</div>
										</div>
									</div>
									<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
										<div class="panel-body">
			
											<form:form commandName="searchSettings" action="searchQuestion" method="POST" acceptCharset="utf-8">

												<div class="form-group">
													<label for="questionNameInp"><spring:message code="message.forum.questionName" /></label>
													<spring:message code='message.forum.questionNamePlace' var="questionNamePlace"/> 
													<form:input path="questionName" type="text" class="form-control" id="questionNameInp" placeholder="${questionNamePlace }" ></form:input>
												</div>

												<div class="form-group">
													<label for="descriptionInp"><spring:message code="message.forum.questionDescription" /></label>
													<spring:message code='message.forum.questionDescriptionPlace'  var="descriptionPlace"/>
													<form:input path="questionDescription" type="text" class="form-control" id="descriptionInp" placeholder="${descriptionPlace }" ></form:input>
												</div>

												<div class="form-group">
													<label for="authorInp"><spring:message code="message.forum.questionAuthor" /></label>
													<spring:message code='message.forum.questionAuthorPlace' var="authorPlace" />
													<form:input path="username" type="text" class="form-control" id="authorInp" placeholder="${authorPlace }"></form:input>
												</div>
												
												<div class="form-group">
													<label for="authorInp"><spring:message code="message.forum.tags" /></label>
													<spring:message code='message.forum.insertTags' var="tagsPlace" />
													<div id="tagRow">
														<form:input path="tags" class="form-control" id="tagsInp" data-role="tagsinput" placeholder="${tagsPlace }"></form:input>
													</div>
												</div>

												<button type="submit" class="btn btn-primary"><spring:message code='message.forum.submit' /></button>
											</form:form>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12 col-md-12 col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-md-12">
											<h4>
												<b><spring:message code="message.forum.foundedQuestions" /></b>
											</h4>
										</div>
									</div>

								</div>
								<div class="panel-body">
								
									
									<c:if test="${elemNum != 0}">
										<div class="row">
											<div class="col-sm-12 col-md-12 col-lg-12">
												<div id="paginatorTop">
												</div>
											</div>
										</div>
									</c:if>
									
								
									<div class="row">
										<div class="col-md-12">
											
											<c:forEach var="question" items="${questionsList}">
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
																<div class="col-md-12">answer</div>
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
									
									<form id="pageSearchPag" accept-charset="utf-8" method="post" action="/forum/questionsSearchedPage" style="display: none;">
										<input type="text" id="qname" name="qname" />
										<input type="text" id="qdescription" name="qdescription" />
										<input type="text" id="qauthor" name="qauthor" />
										<input type="text" id="qtags" name="qtags" />
										<input type="text" id="pagenum" name="pagenum" />
									</form>
									
									<input type="hidden" value="${currPage }" id="currPage" />
									<input type="hidden" value="${pageCount }" id="pageCount" />
									<input type="hidden" value="${pageSize }" id="pageSize" />
									

								</div>
							</div>
						</div>
					</div>
					
					

				</div>

			</div>

		</div>
	</div>

