package it.unical.classmanager.controllers.editor;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.unical.classmanager.editorData.EditorStatus;
import it.unical.classmanager.editorData.Environment;
import it.unical.classmanager.managers.EnvironmentManger;
import it.unical.classmanager.utils.enumative.EnvironmentEnum;

/**
 * Handles requests for the editor page.
 */
@Controller
public class EditorController {
	
	@SuppressWarnings("unused")
	@Autowired
	private ApplicationContext appContext;
	
	private final static String HEADER = "editor/editorHeader.jsp";
	private final static String BODY = "editor/editorBody.jsp";
	
	
	@RequestMapping(value = "/editor", method = RequestMethod.GET)
	public String redirecting(Locale locale, Model model) {
		return "redirect:/editor/editor";
	}
	
	
	@RequestMapping(value = "/editor/editor", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		EditorStatus status = initEditorStatus();
		List<EnvironmentEnum> envLangs = EnvironmentManger.getInstance().getAviableLanguage();
		
		model.addAttribute("status", status);
		model.addAttribute("aviableLangs", envLangs);
			
		model.addAttribute("customHeader",EditorController.HEADER);
		model.addAttribute("customBody",EditorController.BODY);
		
		return "layout";
	}
	
	@RequestMapping(value = "/editor/editor", method = RequestMethod.POST)
	public String compileSnippet(Locale locale, Model model, @ModelAttribute("status") EditorStatus status) {

		String lang = status.getLanguage();

		Environment env = EnvironmentManger.getInstance().getEnvironment(EnvironmentEnum.getEnumFromString(lang));
		List<EnvironmentEnum> envLangs = EnvironmentManger.getInstance().getAviableLanguage();

		String tmpResult = env.compile(status.getCode());
		status.setConsoleContent(tmpResult);
		
		model.addAttribute("status", status);
		model.addAttribute("aviableLangs", envLangs);

		model.addAttribute("customHeader",EditorController.HEADER);
		model.addAttribute("customBody",EditorController.BODY);
		
		return "layout";
	}
	
	
	
	
	private EditorStatus initEditorStatus() {
		
		EditorStatus status = new EditorStatus();
		String fileContent = "#include <iostream>\n"
				+ "using namespace std;\n"
				+ "\n"
				+ "int main(){\n\n"
				+ "    cout<<\"hello!!!\"<<endl;\n"
				+ "    cout << \"ahahhahahah\" << endl; \n"
				+ "    return 0;\n"
				+ "}";
		status.setCode(fileContent);
		
		return status;
	}
	
}
