$(function() {
	
	var user;
	
	$(".promote").click(function(event) {
		event.preventDefault();
		user = $(this).closest("tr").find(".user").html();
		doAction("promote");
	});
	
	$(".demote").click(function(event) {
		event.preventDefault();
		user = $(this).closest("tr").find(".user").html();
		doAction("demote");
	});
	
	
	
	
	$(".btn-danger").click(function(event) {
		event.preventDefault();
		user = $(this).closest("tr").find(".user").html();
		$('#prompt-modal').modal('toggle');
//		$('#result-modal').on('hidden.bs.modal', function (e) {
//			location.href="userslist";
//		});
		
//		$.post( "edituser", { user: user,action :"delete" }, function(data){
//			if ( data.status === "SUCCESS" ) {
//				
//				
//				
//				
//				
//				location.href="userslist";
//			}
//		});
	});
	
	$("#do-delete").click(function() {
		doAction("delete");
	});
	
	
	
	
	var totalPages = parseInt($("#options :input[name='total-pages']").val());
	var currentPage = parseInt($("#options :input[name='page-number']").val());
	
	$('#paginator').bootpag({
	    total: totalPages,
	    page: currentPage,
	    maxVisible: 5,
//	    href: "searchusers?page={{number}}",
	    leaps: false
	}).on("page", function(event, num){
	    window.location.href = "searchusers?page="+num;
	});
    
	var value = $("#selected-value").val();
	$("#page-size").val(value);
	
	function doAction(action) {
		console.log(user);
		$.post( "edituser", { user: user,action : action }, function(data){
			if ( data.status === "SUCCESS" ) {
				
				$('#result-modal').modal('toggle');
				$('#result-modal').on('hidden.bs.modal', function (e) {
					location.href="userslist";
				});
			}
			//location.href=data;	
			console.log(data);
		});
	}
		
	
})