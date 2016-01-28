package it.unical.classmanager.editorData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import it.unical.classmanager.utils.Terminal;

/**
 * Abstract class that represent an environment
 * 
 * @author sera
 *
 */
public abstract class Environment {

	/**
	 * Is the terminal on wich execute the operation
	 */
	protected Terminal terminal;
	
	/**
	 * Is the directory path of the root folder of the environment
	 */
	protected String directoryPath;
	
	/**
	 * The prefix of the name of the temporary file used by the environment 
	 */
	protected String fileName;
	
	/**
	 * The extension of the temporary file used by the environment
	 */
	protected String extension;
	
	
	public Environment() {

		this.terminal = Terminal.getTerminal();
	}
	
	
	/**
	 * Init function of the environment, it initialize the filesystem creating necessary folders
	 */
	protected void createEnvironment() {
		
		if(!new File(this.directoryPath).exists()) {
			new File(this.directoryPath).mkdirs();
		}
		
		cleanMainDirectory();
	}
	
	
	/**
	 * Destroies the environment and erase all the file contained in it
	 */
	public void destroyEnvironment() {

		cleanMainDirectory();
	}
	
	
	/**
	 * Clean the root directory of the environment from all the temporary files
	 */
	public void cleanMainDirectory() {
		
		if(new File(this.directoryPath).list().length != 0) {
			
			for(String tmpFileName : new File(this.directoryPath).list()) {
				new File(this.directoryPath+tmpFileName).delete();
			}
		}
	}
	
	/**
	 * Method for generate random name of a temporary file
	 * 
	 * @return a random generated String 
	 */
	protected String generateRandomFileName() {
		
		//TODO aggiungere session value per il valore random
		int tmpIndex = new Random().nextInt(10000);
		return this.fileName+tmpIndex;
	}
	
	
	/**
	 * Create the folder in which will be created and executed the file
	 * 
	 * @param fileName the name of the folder and of the file
	 * @param content the content of the file
	 */
	protected void createSandbox(String fileName, String content) {
		
		String tmpDir = this.directoryPath + fileName + "/";
		new File(tmpDir).mkdir();
		File file = new File(tmpDir + fileName + this.extension);
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			
			writer.print(content);
			writer.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			writer.close();
		}
	}
	
	
	/**
	 * Simple utility function for force deleting a directory
	 * 
	 * @param dir the string path of the directory to delete
	 */
	protected void removeDirectory(File dir) {
	    if (dir.isDirectory()) {
	        File[] files = dir.listFiles();
	        if (files != null && files.length > 0) {
	            for (File aFile : files) {
	                removeDirectory(aFile);
	            }
	        }
	        dir.delete();
	    } else {
	        dir.delete();
	    }
	}
	
	
	/**
	 * Destroy the temporary folder of a sandbox
	 * 
	 * @param fileName
	 */
	protected void destroySandBox(String fileName) {
		
		String tmpDir = this.directoryPath + fileName + "/";
		removeDirectory(new File(tmpDir));
	}
	
	
	/**
	 * Execute on a terminal the operation of compile a file for a specific environment
	 * 
	 * @param content is the content of the temporary file created for compiling
	 * @return the result of the {@link Terminal} console
	 */
	public abstract String compile(String content);
	
}
