<jsp:include page="pageCommons/include.jsp" flush="true" />
<html>
<head>
<title>Class Manager</title>
<jsp:include page="pageCommons/head.jsp" flush="true" />
<jsp:include page="${customHeader}" flush="true" />
	<script src="/resources/script/notifications.js"></script>
</head>
<body>
	<!-- NAVBAR -->
	<jsp:include page="pageCommons/navBar.jsp" flush="true" />

	<div class="container-fluid page-content">
		<div class="row">
			<!-- SIDEBAR -->
			<div class="col-sm-3 col-md-3 col-lg-2">
				<div id="sidebar-wrapper" class="sidebar-desktop">
					<ul class="sidebar-nav">
						<jsp:include page="pageCommons/sideBar.jsp" flush="true" />
					</ul>
				</div>
			</div>

			<!-- CUSTOM BODY -->
			<div class="col-sm-9 col-md-9 col-lg-10">
				<jsp:include page="${customBody}" flush="true" />
			</div>

		</div>
	</div>
	
	<!-- Modal For Login-->
	<jsp:include page="pageCommons/loginModal.jsp" flush="true" />

	<!-- Modal For Register-->
	<jsp:include page="pageCommons/registerModal.jsp" flush="true" />

</body>
</html>