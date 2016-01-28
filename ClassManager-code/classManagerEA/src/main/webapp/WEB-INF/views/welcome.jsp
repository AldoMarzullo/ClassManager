<jsp:include page="pageCommons/include.jsp" flush="true" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Class Manager</title>
<jsp:include page="pageCommons/head.jsp" flush="true" />

<!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="/resources/css/freelancer.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/resources/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css">
<link
	href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css">
</head>
<body>
	<!-- Header -->
	<header>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<img height="10%" width="64%" class="img-responsive"
						src="/resources/logo.png" alt="">
					<div class="intro-text">
						<span class="name blackShadow">Be less busy</span>
						<hr>
						<span class="skills blackShadow">Web Project 2016</span> <br>
						<c:if test="${empty loggedIn}">
							<a id="login-button" href="#"
								class="btn btn-lg btn-outline login blackShadow"> <i
								class="glyphicon glyphicon-upload"></i> Log-in
							</a>
						</c:if>

					</div>
				</div>
			</div>
		</div>
	</header>

	<!-- About Section -->
	<section class="success" id="about">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2>About</h2>
					<hr class="star-light">
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4 col-lg-offset-2">
					<p>
						<spring:message code="message.welcomeSubTitle1" />
					</p>
				</div>
				<div class="col-lg-4">
					<p>
						<spring:message code="message.welcomeSubTitle2" />
					</p>
				</div>
			</div>
		</div>
	</section>

	<!-- Portfolio Grid Section -->
	<section id="portfolio">
		<div class="container">
			<div class="row">
				<div class="footer-col col-sm-2 col-md-2 col-lg-2"></div>
				<div class="footer-col col-sm-8 col-md-8 col-lg-8">
					<img class="img-responsive" src="/resources/siteMap.jpg" alt="">
				</div>
				<div class="footer-col col-sm-2 col-md-2 col-lg-2"></div>
			</div>
		</div>
	</section>
	<div class="colorSeparator">
		<br>
	</div>
	<section id="portfolio">
		<div class="container">
			<div class="row">
				<img class="img-responsive" src="/resources/trelloTasks.png" alt="">
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer class="text-center">
		<div class="footer-above">
			<div class="container">
				<div class="row">
					<div class="footer-col col-sm-6 col-md-6 col-lg-6">
						<!-- 						<h3>Image1</h3> -->
					</div>
					<div class="footer-col col-sm-6 col-md-6 col-lg-6">
						<!-- 						<h3>Image2</h3> -->

					</div>
				</div>
			</div>
		</div>
		<div class="footer-below">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">Copyright &copy; OrologiRolex 2016</div>
				</div>
			</div>
		</div>
	</footer>

	<!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
	<div class="scroll-top page-scroll visible-xs visible-sm">
		<a class="btn btn-primary" href="#page-top"> <i
			class="fa fa-chevron-up"></i>
		</a>
	</div>

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

	<!-- Plugin JavaScript -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script src="js/classie.js"></script>
	<script src="js/cbpAnimatedHeader.js"></script>

	<!-- Contact Form JavaScript -->
	<script src="js/jqBootstrapValidation.js"></script>
	<script src="js/contact_me.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="js/freelancer.js"></script>

	<!-- Modal For Login-->
	<jsp:include page="pageCommons/loginModal.jsp" flush="true" />

	<!-- Modal For Register-->
	<jsp:include page="pageCommons/registerModal.jsp" flush="true" />
</body>
</html>
