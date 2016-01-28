package it.unical.classmanager.utils;

import org.springframework.ui.Model;

/**
 * Helper class for adding custom head and body contents to layout page.
 * 
 * @author Aloisius92
 */
public class CustomHeaderAndBody {

	/**
	 * This function set the custom head.jsp.
	 * 
	 * @param model The Model
	 * @param head The path to head.jsp
	 *  
	 */
	public static void setCustomHead(Model model, String head){
		model.addAttribute("customHeader", head);
	}
	
	/**
	 * This function set the custom body.jsp.
	 * 
	 * @param model The Model
	 * @param head The path to body.jsp
	 *  
	 */
	public static void setCustomBody(Model model, String body){
		model.addAttribute("customBody", body);
	}

	/**
	 * This function set the custom head.jsp and body.jsp.
	 * 
	 * @param model The Model
	 * @param head The path to head.jsp
	 * @param head The path to body.jsp
	 *  
	 */
	public static void setCustomHeadAndBody(Model model, String head, String body){
		setCustomHead(model, head);
		setCustomBody(model, body);
	}
}
