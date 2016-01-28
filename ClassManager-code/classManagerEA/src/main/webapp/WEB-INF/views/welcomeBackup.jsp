<jsp:include page="pageCommons/include.jsp" flush="true" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Class Manager</title>
<jsp:include page="pageCommons/head.jsp" flush="true" />
<link rel="stylesheet" type="text/css"
	href="resources/style/welcome.css" />
</head>
<body>
	<div class="container-fluid page-content">
		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<img class="mainTitle" alt="" src="/resources/logo.png">
				<p class="text-center subTitle fontType">
					<spring:message code="message.welcomeSubTitle1" />
				</p>
				<p class="text-center subTitle fontType">
					<spring:message code="message.welcomeSubTitle2" />
				</p>
				<p class="text-center subTitle fontType">
					<spring:message code="message.welcomeSubTitle3" />
				</p>
				<br>
				<p class="text-center fontType">
					<c:if test="${empty loggedIn}">
						<a id="login-button" class="welcomeLogin login" href="#"><span
							class="glyphicon glyphicon-upload"></span>Log-in</a>
					</c:if>
				</p>
			</div>
		</div>
	</div>

	<!-- Modal For Login-->
	<jsp:include page="pageCommons/loginModal.jsp" flush="true" />

	<!-- Modal For Register-->
	<jsp:include page="pageCommons/registerModal.jsp" flush="true" />
</body>
</html>
