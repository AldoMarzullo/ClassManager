package it.unical.classmanager.editorData.concrete;


import it.unical.classmanager.editorData.Environment;
import it.unical.classmanager.utils.ExecutionResult;
import it.unical.classmanager.utils.enumative.ExitCode;
import it.unical.classmanager.utils.general.Paths;


/**
 * Represent a concrete c++ {@link Environment} that contain a terminal to execute operation
 * 
 * @author sera
 *
 */
public class PythonEnvironment extends Environment{

	/**
	 * Default constructor that create the environment
	 */
	public PythonEnvironment() {
		this.directoryPath = Paths.pythonEnvironment;
		this.fileName = "main";
		this.extension = ".py";
		
		createEnvironment();
	}
	
	
	@Override
	public String compile(String content) {

		String tmpfileName = generateRandomFileName();
		try{
			String tmpDir = this.directoryPath + tmpfileName + "/";
			createSandbox(tmpfileName, content);
			
			ExecutionResult result = this.terminal.executeCommand(tmpDir, "docker", "run", "-v",
					tmpDir + ":/home/docker"+ tmpDir +":ro", "-m", "4m",
					"ubuntu_editor/sera", "timeout", "--signal=5", "20", "python", "/home/docker" + tmpDir + tmpfileName + this.extension);
			
			//remove warning message
			String tmpExitConsole = result.getConsoleResult();
			tmpExitConsole = tmpExitConsole.replace("WARNING: Your kernel does not support swap limit capabilities, memory limited without swap.\n", "");
			result.setConsoleResult(tmpExitConsole);
			
			//ExecutionResult result = this.terminal.executeCommand(tmpDir, "python", "./"+tmpfileName+this.extension);
			
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
	

}
