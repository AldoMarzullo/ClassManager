package it.unical.classmanager.editorData.concrete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.classmanager.editorData.Environment;
import it.unical.classmanager.utils.ExecutionResult;
import it.unical.classmanager.utils.enumative.ExitCode;
import it.unical.classmanager.utils.general.Paths;

public class JavaEnvironment extends Environment{
	
	/**
	 * The name of the java class
	 */
	private String className;
	
	public JavaEnvironment() {
		
		this.directoryPath = Paths.javaEnvironment;
		this.fileName = "main";
		this.extension = ".java";
		
		createEnvironment();
	}
	
	
	@Override
	protected void createSandbox(String fileName, String content) {

		this.className = getClassNameFromString(content);
		
		String tmpDir = this.directoryPath + fileName + "/";
		new File(tmpDir).mkdir();
		File file = new File(tmpDir + className + this.extension);
		
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
	
	
	@Override
	public String compile(String content) {

		String tmpfileName = generateRandomFileName();
		try{
			String tmpDir = this.directoryPath + tmpfileName + "/";
			createSandbox(tmpfileName, content);
			
			
			ExecutionResult result = this.terminal.executeCommand(tmpDir, "javac", this.className+this.extension);
	
			if(result.getExitCode() != ExitCode.EXIT_OK.ordinal()) {
				
				System.out.println("error during the compilation");
				return result.getConsoleResult();
			}
			
			result = this.terminal.executeCommand(tmpDir, "docker", "run", "-v",
					tmpDir + ":/home/docker"+ tmpDir +":ro", "-m", "4m",
					"ubuntu_editor/sera", "timeout", "--signal=5", "20", "/bin/sh", "-c", "cd /home/docker" + tmpDir + "; java " + this.className );
			
			//remove warning message
			String tmpExitConsole = result.getConsoleResult();
			tmpExitConsole = tmpExitConsole.replace("WARNING: Your kernel does not support swap limit capabilities, memory limited without swap.\n", "");
			result.setConsoleResult(tmpExitConsole);
			
			
			//result = this.terminal.executeCommand(tmpDir, "java", this.className);
			
			if(result.getExitCode() != ExitCode.EXIT_OK.ordinal()) {
				
				System.out.println("error during the execution");
				return result.getConsoleResult();
			}
	
			return result.getConsoleResult();
		}
		finally{
			destroySandBox(tmpfileName);
		}
		
	}
	
	/**
	 * Get the regex result
	 * 
	 * @param text Text to analyze
	 * @param regex regex to search 
	 * @return the string if it is present, "" in the other case
	 */
	private String printMatches(String text, String regex) {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(text);
	    
	    if(matcher.find())
	    	return matcher.group();
	    return "";
	}
	
	
	/**
	 * Get the name of the java class from the text
	 * 
	 * @param content the content of a Java file
	 * @return the name of the class if it is present, "" in the other case
	 */
	private String getClassNameFromString(String content) {
		
		String tmpName = printMatches(content, "\\s*(public|private)\\s+class\\s+(\\w+)\\s+((extends\\s+\\w+)|(implements\\s+\\w+( ,\\w+)*))?\\s*\\{");
		
		if(tmpName.length() != 0) {
			
			tmpName = tmpName.replaceAll("\\s+", "");
			return tmpName.substring(11, tmpName.length()-1);
		}
		
		return "";
	}
	
	
}
