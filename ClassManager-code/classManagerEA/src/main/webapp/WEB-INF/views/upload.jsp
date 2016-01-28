<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Class Manager</title>
<script src="resources/lib/jquery/jquery.min.js"></script>
<script src="resources/lib/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/lib/bootstrap-3.3.5-dist/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="resources/style/style.css" />
<link href="resources/style/uploadFile_css/style.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="resources/style/nav-bar.css" />
<script src="resources/script/nav-bar.js"></script>


<script src="resources/lib/uploadFile-lib/jquery.knob.js"></script>

<!-- jQuery File Upload Dependencies -->
<script src="resources/lib/uploadFile-lib/jquery.ui.widget.js"></script>
<script src="resources/lib/uploadFile-lib/jquery.iframe-transport.js"></script>
<script src="resources/lib/uploadFile-lib/jquery.fileupload.js"></script>

<!-- Our main JS file -->
<script src="resources/lib/uploadFile-lib/script.js"></script>

</head>

<body>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header pull-left">
				<a id="menu-toggle" href="#" class="navbar-toggle"> <span
					class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="navbar-brand" href="#"> Class Manager </a>
			</div>
			<div class="navbar-header pull-right">
				<a class="nav-bar-button logout" href="aldo"><span
					class="glyphicon glyphicon-log-out"></span> Aldo Login</a> <a
					class="nav-bar-button logout" href="#"><span
					class="glyphicon glyphicon-log-out"></span> Logout</a>
			</div>
		</div>
	</nav>

	<div class="container-fluid page-content">
		<div class="row">
			<!-- SIDEBAR -->
			<div class="col-sm-3 col-md-3 col-lg-2">
				<div id="sidebar-wrapper" class="sidebar-desktop">
					<!-- collapse width -->
					<ul class="sidebar-nav">
						<li><a href="#"><span class="glyphicon glyphicon-home"></span>
								Dashboard</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-book"></span>
								Classroom</a></li>
						<li><a href="#"><span
								class="glyphicon glyphicon-folder-open"></span> Materials</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-list"></span>
								Discussions</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-stats"></span>
								Statistics</a></li>
					</ul>
				</div>
			</div>
			<div class="col-sm-9 col-md-9 col-lg-10">
				<div class="row row-content">
					<div class="col-sm-12 col-md-12 col-lg-12">


						<form id="upload" method="post" action="/file" enctype="multipart/form-data">
							<div id="drop">
								Drop Here <a>Browse</a> <input type="file" name="file" multiple />
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
</body>

</html>