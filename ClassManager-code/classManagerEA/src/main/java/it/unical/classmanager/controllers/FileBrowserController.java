package it.unical.classmanager.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.unical.classmanager.model.AbstractFileBean;
import it.unical.classmanager.utils.FileManager;

/**
 * Is called when is necessary retrieve files starting from a root
 */
@Controller
public class FileBrowserController {

	@Autowired
	ApplicationContext appContext;
	private static final Logger logger = LoggerFactory.getLogger(FileBrowserController.class);

	public static final String LECTURES_ROOT_TYPE = "lectures";
	public static final String STUDENTS_HOMEWORKS_ROOT_TYPE = "students";

	
	public List<AbstractFileBean> getLectureContent(String path){

		String root = path;
		List<AbstractFileBean> files = new ArrayList<AbstractFileBean>();
		
		new FileManager().addTree(root, files);

		logger.info("getFiles");

		return files;
	}

	/**
	 * This method allows the user to download the file contained in the parameter "path"
	 * @param path : is the parameter of the GET request, that is the path of the file to dowload
	 */ 
	@RequestMapping(value="download", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @RequestParam("path") String path) throws IOException {

		//TODO Security check
		File file = null;

		String workingDir = System.getProperty("user.dir");
		Resource resource = appContext.getResource("file:" + workingDir + "/" + path);
		file = resource.getFile();

		if(!file.exists()){
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType==null){
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : "+mimeType);

		response.setContentType(mimeType);

		/* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() +"\""));


		/* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
		//response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

		response.setContentLength((int)file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		//Copy bytes from source to destination(outputstream in this example), closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

}
