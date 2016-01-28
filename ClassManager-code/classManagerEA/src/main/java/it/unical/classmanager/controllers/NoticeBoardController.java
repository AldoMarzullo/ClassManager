package it.unical.classmanager.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.unical.classmanager.model.dao.CommunicationsDAO;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.dao.UserDAO;
import it.unical.classmanager.model.data.Communications;
import it.unical.classmanager.model.data.Professor;


@Controller
public class NoticeBoardController {

	@Autowired
	private ApplicationContext context;
	private static final Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);
	
	private final static String HEADER = "noticeboard/noticeboardHeader.jsp";
	private final static String BODY = "noticeboard/noticeboardBody.jsp";
	
	/**
	 * returns the notice board
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/noticeboard", method = RequestMethod.GET)
	public String noticeBoard(Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		
		
		PagedListHolder<Communications> communicationsPage = (PagedListHolder<Communications>) request.getSession().getAttribute("noticesList");
		CommunicationsDAO communicationsDao = (CommunicationsDAO) context.getBean("communicationsDAO");
		if ( communicationsPage == null ) {
			communicationsPage = new PagedListHolder<Communications>(communicationsDao.getAllCommunications());
		} else {
			communicationsPage.setSource(communicationsDao.getAllCommunications());
		}

		
		int noticesPerPage = ( request.getParameter("notices") != null ) ? Integer.parseInt(request.getParameter("notices")) : -1;
		if ( noticesPerPage != -1 ) {
			if ( noticesPerPage < 10 )
				noticesPerPage = 10;
			if ( noticesPerPage > 100 ) {
				noticesPerPage = 100;
			}
		} else {
			noticesPerPage = communicationsPage.getPageSize();
		}
			
		
		
		int pageToGet = ( request.getParameter("page") != null ) ? Integer.parseInt(request.getParameter("page")) : -1;
		if ( pageToGet != -1 ) {
			communicationsPage.setPage(pageToGet -1 );
		}
		
		communicationsPage.setPageSize(noticesPerPage);
		request.getSession().setAttribute("noticesList", communicationsPage);
		model.addAttribute("pageSize", noticesPerPage);
		model.addAttribute("customHeader", NoticeBoardController.HEADER);
		model.addAttribute("customBody", NoticeBoardController.BODY);
		
		int pageNumber = communicationsPage.getPage() + 1;
		int pageCount = communicationsPage.getPageCount();
		
		model.addAttribute("pageNumber",pageNumber);
		model.addAttribute("pageCount",pageCount);
		
		model.addAttribute("new-notice", new Communications());
		model.addAttribute("edit-notice", new Communications());
		

		return "layout";
			
	}
	
	/**
	 * saves a new notice or updates a previous one.
	 */
	@RequestMapping(value = "/newnotice", method = RequestMethod.POST)
	public String newNotice(@Valid @ModelAttribute("new-notice") Communications communication,BindingResult result,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		
		/**
		 * handle errors like this, add the RedirectAttributes to the method and add the parameter to pass
		 * this methods redirect passing parameters
		 */
		String username = (String) request.getSession().getAttribute("loggedIn");
		String role = (String) request.getSession().getAttribute("role");
		if ( username == null || role.equals("Student") ) {
			redirectAttributes.addAttribute("error", "session");
			return "redirect:/sessionerror";
		}
		
		UserDAO userDao = DaoHelper.getUserDAO();
		
		if ( result.hasErrors() ) {
			model.addAttribute("customHeader", NoticeBoardController.HEADER);
			model.addAttribute("customBody", NoticeBoardController.BODY);
			model.addAttribute("display",true);
			return "layout";
		}
		
			
		//check if this is a fake auth 
		Professor professor = (Professor) userDao.get(username);
		if ( professor == null ) {
			return handleFakeAuth(model);
		}
		
		CommunicationsDAO communicationsDAO = DaoHelper.getCommunicationsDAO();
		
		if ( communication.getId() == -1 ) {
			communication.setProfessor(professor);
			
			if ( role.equals("admin") ) {
				communication.setServiceMessage(true);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy-HH:mm");
			String dateString = sdf.format(Calendar.getInstance().getTime());
			
			try {
				communication.setDate(sdf.parse(dateString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			communicationsDAO.create(communication);
		} else {
			//I am updating
			Communications toEdit = communicationsDAO.get(communication.getId());
			
			if ( toEdit == null || ( !role.equals("admin") && !toEdit.getProfessor().getUsername().equals(username) ) ) {
				return handleFakeAuth(model);
			}
			
			
			toEdit.setName(communication.getName());
			toEdit.setDescription(communication.getDescription());
			
			communicationsDAO.update(toEdit);
		}
		
		
//		logger.info(communication.toString());

		
		return "redirect:/noticeboard";
	}
	
	
	
	private String handleFakeAuth(Model model) {
		model.addAttribute("customHeader", NoticeBoardController.HEADER);
		model.addAttribute("customBody", NoticeBoardController.BODY);
		return "layout";
	}

	/**
	 * deletes a notice
	 */
	@RequestMapping(value = "/deletenotice", method = RequestMethod.POST)
	public String deleteNotice (@RequestParam(value="post-id", required=true) Integer postId,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		
		/**
		 * handle errors like this, add the RedirectAttributes to the method and add the parameter to pass
		 * this methods redirect passing parameters
		 */
		String username = (String) request.getSession().getAttribute("loggedIn");
		String role = (String) request.getSession().getAttribute("role");
		if ( username == null || role.equals("Student") ) {
			redirectAttributes.addAttribute("error", "session");
			return "redirect:/sessionerror";
		}
		
		CommunicationsDAO communicationsDAO = DaoHelper.getCommunicationsDAO();
		
		logger.info("deleteing " + postId);
		
		if ( role.equals("admin") ) {
			
			communicationsDAO.delete(communicationsDAO.get(postId));
			
		} else if ( role.equals("Professor") ) {
			
			Communications communication = communicationsDAO.get(postId);
			//I am the owner and I can delete my post
			if ( communication.getProfessor().getUsername().equals(username) ) {
				communicationsDAO.delete(communication);
			}
			
		}
		
		return "redirect:/noticeboard";
	}
}