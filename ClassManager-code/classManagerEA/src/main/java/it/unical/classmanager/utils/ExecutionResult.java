package it.unical.classmanager.utils;

import java.util.Arrays;


/**
 * This class represent the result of an execution of an operation an a {@link Terminal} shell
 * 
 * @author sera
 *
 */
public class ExecutionResult {

	/**
	 * Is the directory in which was executed the operation
	 */
	private String directory;
	
	/**
	 * Is the operation performed in the shell
	 */
	private String[] command;
	
	
	/**
	 * Represent the exit code returned by the shell from an operation
	 */
	private int exitCode;
	
	
	/**
	 * Is the standard output returned by the shell after the execution of the command
	 */
	private String consoleResult;
	
	
	public ExecutionResult() {
		this.exitCode = 0;
		this.consoleResult = "";
	}
	
	/**
	 * Create a result for an {@link Execution} and is the constructor that accept all the private parameter of the class
	 * 
	 * @param exitCode Is the directory in which was executed the operation
	 * @param consoleResult Is the operation performed in the shell
	 * @param directory Represent the exit code returned by the shell from an operation
	 * @param command Is the standard output returned by the shell after the execution of the command
	 */
	public ExecutionResult(int exitCode, String consoleResult, String directory, String... command) {
		this.exitCode = exitCode;
		this.consoleResult = consoleResult;
		
		this.directory = directory;
		this.command = command;
	}
	
	
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String[] getCommand() {
		return command;
	}

	public void setCommand(String[] command) {
		this.command = command;
	}

	public int getExitCode() {
		return exitCode;
	}
	
	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}
	
	public String getConsoleResult() {
		return consoleResult;
	}
	
	public void setConsoleResult(String consoleResult) {
		this.consoleResult = consoleResult;
	}

	
	
	@Override
	public String toString() {
		return "ExecutionResult [directory=" + directory + ", command=" + Arrays.toString(command) + ", exitCode="
				+ exitCode + ", consoleResult=" + consoleResult + "]";
	}
	
}
