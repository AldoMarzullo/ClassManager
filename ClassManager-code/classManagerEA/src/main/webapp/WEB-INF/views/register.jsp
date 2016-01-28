<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- <html> -->
<!-- 	<head> -->
<!-- 		<title>Register</title> -->
		
		
		<script type="text/javascript" src="resources/script/register.js"></script>
<!-- 	</head> -->
<!-- 	<body> -->
		<form:form id="register-form" role="form" action="register" method="POST" commandName="userRegisterForm">
		
			<div class="form-group">
				<label for="username">Username:</label> 
				<form:input type="text" name="username" path="username" class="form-control"/> 
				<div class="errors"></div>
			</div>
			
			<div class="form-group">
				<label for="serial-number"><spring:message code="message.serialNumber" text="default text" />:</label> 
				<form:input type="text" name="serial-number" path="serialNumber" text="default text" class="form-control" />  
				<div class="errors"></div>
			</div>		
				
			<div class="form-group">
				<label for="password">Password:</label> 
				<form:input type="password" name="password" path="password" class="form-control"/>  			
				<div class="errors"></div>
			</div>			
			
			
			<div class="form-group">
				<label for="password_confirm"><spring:message code="message.passwordConfirm" text="default text"/>:</label> 
				<form:input type="password" name="confirmPassword" path="confirmPassword" class="form-control"/>  
				<div class="errors"></div>
			</div>
			
			<div class="form-group">
				<label for="firstname"><spring:message code="message.firstName" text="default text"/>:</label> 
				<form:input type="text" name="firstname" path="firstName" class="form-control"/>  
				<div class="errors"></div>
			</div>			
			
			<div class="form-group">
				<label for="lastname"><spring:message code="message.lastName" text="default text"/>:</label> 
				<form:input type="text" name="lastname" path="lastName" class="form-control" text="default text"/>  
				<div class="errors"></div>
			</div>			
		
			
			<div class="form-group">
				<label for="email">Email:</label> 
				<form:input type="text" name="email" path="email" class="form-control" text="default text"/> 
				<div class="errors"></div>
			</div>			
			
			<div class="form-group">
				<label for="birthdate"><spring:message code="message.birthDate" text="default text"/>:</label> 
				<form:input id="datepicker" type="text" name="birthdate" path="birthDate" readonly='true' class="form-control" />  
				<div class="errors"></div>
			</div>			
			
			
			<spring:message code="message.register" text="default text" var="registerLabel"/>
			<button class="btn btn-default" type="submit" >${registerLabel}</button> 
		
		
		</form:form>

<!-- 	</body> -->
<!-- </html> -->