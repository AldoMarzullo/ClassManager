package it.unical.classmanager.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import it.unical.classmanager.model.AbstractFileBean;
import it.unical.classmanager.model.FileBean;
import it.unical.classmanager.model.FolderBean;

public class FileManager {

	public final static String RESOURCES_PATH = "files";
	public final static String HOMEWORK_PATH = "homeworks";
	public final static String HOMEWORK_ATTACHED_PATH = "homeworkAttached";
	public final static String MATERIALS_PATH = "materials";
	public final static String LECTURES_PATH = "lectures";
	public final static String STUDENTS_PATH = "students";
	public final static String HOMEWORK_STUDENT_SOLVING_PATH = "homeworkStudentSolving";
	public final static String HOMEWORK_STUDENT_SOLVING_ATTACHMENT_PATH = "homeworkStudentSolvingAttachment";

	@Autowired
	ApplicationContext appContext;

	/**
	 * This method creates a directory in the specified path (without the file separator at the end, without RESOURCES_PATH)
	 * @param path : the path where the directory must be created
	 * @param name : the name of the new directory
	 * @return boolean : false if the operation fails
	 */
	public boolean mkDir(String path, String name){
		new File(RESOURCES_PATH + File.separator + path).mkdirs();
		final String completePath = RESOURCES_PATH + File.separator + path + File.separator + name;
		return new File(completePath).mkdir();
	}

	/**
	 * Creates a new file in the specified path
	 * @param path is the path where to create the new file
	 * @param name is the name of the new file
	 * @return false if the operation fails
	 */
	public boolean mkFile(String path, String name){

		String path_ = path + File.separator + name;
		path_ = path_.replaceAll("/", File.separator);
		File f = new File(path); 

		boolean success = false;
		try {

			success = f.createNewFile();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return success;
	}

	/**
	 * Creates a new file in the specified path starting from a multipart
	 * @param path is the path where to create the new file
	 * @param name is the name of the new file
	 * @return false if the operation fails
	 */
	public boolean mkMultipartFile(MultipartFile file, String path, String name){

		new File(FileManager.RESOURCES_PATH + "/" + path).mkdirs();
		
		String path_ = FileManager.RESOURCES_PATH + File.separator + path + File.separator + name;
		path_ = path_.replaceAll("/", File.separator);
		path_ = path_.replaceAll(" ", "\\ ");

		File f = new File(path_); 
		boolean success = false;
		try {

			file.transferTo(f);
			success = true;

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	/**
	 * This method create recursively a tree of files
	 * 
	 * @param folder : is the root folder
	 * @param files	: is the list of the files contained in {@link @param folder}
	 * @param evaluable : indicates if the file is evaluable or not and if it has a score
	 */
	public void addTree(String root, List<AbstractFileBean> files) {

		File folder = new File(root);
		File[] listOfFiles = folder.listFiles();

		if(listOfFiles != null)
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && !listOfFiles[i].isHidden()) {

					FileBean file = FileBean.toFileBean(listOfFiles[i]);
					files.add(file);

				} else if (listOfFiles[i].isDirectory()) {

					FolderBean file = new FolderBean(0,listOfFiles[i].getName(),AbstractFileBean.FOLDER_TYPE, listOfFiles[i].getPath(), listOfFiles[i].listFiles().length);
					files.add(file);
				}
			}
	}

	/**
	 * This method deletes a file from the file system
	 * @param path the file path of the file to delete
	 * @return
	 */
	public boolean deleteFile(String path){

		File file = new File(path);
		return file.delete();
	}

	/**
	 * deletes a non empty directory
	 * @param path the path of the directory to delete
	 * @return
	 */
	public boolean deleteDirectory(String path){

		boolean success = false;
		try {
			FileUtils.deleteDirectory(new File(path));
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}
}
