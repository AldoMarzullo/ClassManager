package it.unical.classmanager.editorData.concrete;


import it.unical.classmanager.editorData.Environment;
import it.unical.classmanager.utils.ExecutionResult;
import it.unical.classmanager.utils.enumative.ExitCode;
import it.unical.classmanager.utils.enumative.TerminalErrorSignal;
import it.unical.classmanager.utils.general.Paths;


/**
 * Represent a concrete c++ {@link Environment} that contain a terminal to execute operation
 * 
 * @author sera
 *
 */
public class CPPEnvironment extends Environment{

	/**
	 * Default constructor that create the environment
	 */
	public CPPEnvironment() {
		this.directoryPath = Paths.cppEnvironment;
		this.fileName = "main";
		this.extension = ".cpp";
		
		createEnvironment();
	}
	
	
	@Override
	public String compile(String content) {

		String tmpfileName = generateRandomFileName();
		try{
			String tmpDir = this.directoryPath + tmpfileName + "/";
			createSandbox(tmpfileName, content);
			
			
			ExecutionResult result = this.terminal.executeCommand(tmpDir, "g++", tmpfileName+this.extension, "-o", tmpfileName);
	
			if(result.getExitCode() != ExitCode.EXIT_OK.ordinal()) {
				
				System.out.println("error during the compilation");
				return result.getConsoleResult();
			}
			
			
			result = this.terminal.executeCommand(tmpDir, "docker", "run", "-v",
					tmpDir + ":/home/docker"+ tmpDir +":ro", "-m", "4m",
					"ubuntu_editor/sera", "timeout", "--signal=5", "20", "/home/docker" + tmpDir + tmpfileName);
			
			
			//remove warning message
			String tmpExitConsole = result.getConsoleResult();
			tmpExitConsole = tmpExitConsole.replace("WARNING: Your kernel does not support swap limit capabilities, memory limited without swap.\n", "");
			result.setConsoleResult(tmpExitConsole);
			
			//result = this.terminal.executeCommand(tmpDir, "./"+tmpfileName);
			
			if(result.getExitCode() != ExitCode.EXIT_OK.ordinal()) {
				
				System.out.println("error during the execution");
				
				//if the error is > 128 is a signal of the system
				if(result.getExitCode() > 128) {
					return TerminalErrorSignal.getEnumFromCode(result.getExitCode()-128).toString();
				}
				
				return result.getConsoleResult();
			}
	
			return result.getConsoleResult();
		}
		finally{
			destroySandBox(tmpfileName);
		}
		
	}
	

}
