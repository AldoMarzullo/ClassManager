package it.unical.classmanager.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.TimeUnit;


/**
 * This class represent a single operation, a single execution of an operation in a shell {@link Terminal}
 * 
 * @author sera
 *
 */
public class Execution implements Runnable{

	/**
	 * Is the directory in which will be executed the operation
	 */
	private String directory;
	
	/**
	 * Is the operation performed in the shell
	 */
	private String[] command;
	
	/**
	 * Is the result of the console operation
	 */
	private ExecutionResult executionResult;
	
	
	/**
	 * The timeout in second after that the execution invoke a break operation (Ctrl+C)
	 */
	private final long timeoutSecondExecution = 10;
	
	
	public Execution(String directory, String... command) {
		
		this.directory = directory;
		this.command = command;
	}
	
	
	public ExecutionResult getExecutionResult() {
		return executionResult;
	}


	@Override
	public void run() {

		String[] tmpResult = executeCommand(this.directory, this.command);
		this.executionResult = 
				new ExecutionResult(Integer.parseInt(tmpResult[0]), tmpResult[1],
						this.directory, this.command);
		
	}
	
	
	
	/**
	 * This function perform an operation on the physical terminal of the server.
	 * It create a {@link ProcessBuilder} and execute the request operation 
	 * 
	 * @param directory Is the directory in which will be executed the operation
	 * @param commands Is the operation performed in the shell
	 * @return A string containing in the position 0 the exit code of the operation on the terminal 
	 * 			and in the position 1 the message returned from the shell
	 */
	private String[] executeCommand(String directory, String... commands) {

		StringBuilder sb = new StringBuilder();
		ProcessBuilder pb = new ProcessBuilder(commands);

		int exitVal = 0;
		
		try{
			pb.directory(new File(directory));
			pb.redirectErrorStream(true);
			Process proc = pb.start();

			boolean timeElapsed = !proc.waitFor(this.timeoutSecondExecution, TimeUnit.SECONDS);
			if(timeElapsed) {
				proc.destroy();
				
				String[] result = {"1", "ERROR: Time for the running is out..."};
				return result;
			}
			
			
			Reader readerInput = new InputStreamReader(proc.getInputStream());
			int ch;
			while ((ch = readerInput.read()) != -1)
				sb.append((char) ch);
			readerInput.close();

			Reader readerErr = new InputStreamReader(proc.getErrorStream());
			int chErr;
			while ((chErr = readerErr.read()) != -1)
				sb.append((char) chErr);
			readerErr.close();
			
			exitVal = proc.exitValue();
			
		}
		catch(IOException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] result = {Integer.toString(exitVal), sb.toString()};
		return result;
	}

	
}
