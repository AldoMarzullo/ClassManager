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
<!-- <link rel="stylesheet" type="text/css" href="resources/style/style.css" />-->
<link rel="stylesheet" type="text/css"
	href="resources/style/nav-bar.css" />
<script src="resources/script/nav-bar.js"></script>
<link href="resources/style/fileBrowser.css" rel="stylesheet" />

<script src="resources/script/fileBrowserScript.js"></script>
<script
	src="resources/lib/jquery-circle-progress-1.1.3/dist/circle-progress.js"></script>
<script src="resources/lib/docsViewer/jquery.gdocsviewer.js"></script>

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

						<!-- SET SCORE MODAL -->
						<div id="scoreUpdate_modal" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<h4 class="modal-title">Update Score</h4>
									</div>
									<div class="modal-body">
										<input id="oldScore_modal" type="number" min="0" max="30" required>
									</div>
									<div class="modal-footer">
										<button id="score_confirm" type="button"
											class="btn btn-success">Update Score</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Cancel</button>
									</div>
								</div>
							</div>
						</div>

						<!-- MODAL -->
						<div id="visualizer_modal" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<h4 id="fileTitle_model" class="modal-title"></h4>
									</div>
									<div class="modal-body">
										<a id="visualizer" href="#"></a>
									</div>
									<div class="modal-footer">
										<button id="download_btn_modal" type="button"
											class="btn btn-default" data-dismiss="modal">Download</button>
										<button id="delete_btn_modal" type="button"
											class="btn btn-default" data-dismiss="modal">Delete</button>
									</div>
								</div>
							</div>
						</div>


						<!-- browser -->
						<div class="filemanager">
							<div class="search">
								<input type="search" placeholder="Find a file.." />
							</div>
							<div class="breadcrumbs"></div>
							<ul class="data"></ul>
							<div class="nothingfound">
								<div class="nofiles"></div>
								<span>No files here.</span>
							</div>
						</div>
						<!-- browser -->

					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>