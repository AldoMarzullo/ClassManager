$(window).resize(function() 
{
	if($(window).width() < 767)
	{				
		$('.sidebar-desktop').removeClass('active');
		$('#sidebar-wrapper').removeClass('sidebar-desktop').addClass('sidebar-mobile');				
	}
	else if ($(window).width() >= 767)
	{		
		$('#sidebar-wrapper').removeClass('sidebar-mobile').addClass('sidebar-desktop');
		$('.sidebar-desktop').addClass('active');
	}
});
$(document).ready(function() 
{
	if($(window).width() < 767)
	{
		$('.sidebar-desktop').removeClass('active');
		$('#sidebar-wrapper').removeClass('sidebar-desktop').addClass('sidebar-mobile');			
	}
	else
	{		
		$('#sidebar-wrapper').removeClass('sidebar-mobile').addClass('sidebar-desktop');   		
		$('.sidebar-desktop').addClass('active');
	}
	$("#menu-toggle").click(function(e) 
	{
		$('.sidebar-mobile').toggleClass('active', 1000);
	});
});