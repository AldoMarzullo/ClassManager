package it.unical.classmanager.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * This singleton class represent a real terminal in a server 
 * and permit to execute all the shell operation on it.
 * 
 * Inside the class has a pool of thread that are used to acces rapidly to an active terminal.
 * 
 * @author sera
 *
 */
public class Terminal {
	
	/**
	 * The number of the default pool thread
	 */
	private final int defaultThreadPoolNumber = 10;
	
	/**
	 * The timeout in minute after that the console invoke a break operation (Ctrl+C)
	 */
	private final long timeoutMinuteConsole = 10;
	
	
	private int threadPullNumber;
	private ExecutorService executorService;
	
	static private Terminal instance;
	
	private Terminal() {
		
		this.threadPullNumber = this.defaultThreadPoolNumber;
		this.executorService = Executors.newFixedThreadPool(this.threadPullNumber);
	
	}
	
	
	/**
	 * This operation permit to get the current singleton terminal 
	 * 
	 * @return
	 */
	static public Terminal getTerminal() {
		
		if(Terminal.instance == null) {
			System.out.println("created terminal singleton");
			Terminal.instance = new Terminal();
		}
		
		return Terminal.instance;
	}
	
	
	public int  getThreadPullNumber() {
		return this.threadPullNumber;
	}
	

	
	/**
	 * The function permit to execute a shell operation on the current instantiated terminal
	 * 
	 * @param directory The scope of the operation, the directory in which the operation is executed
	 * @param command A list of command to do i.e. a command like <i>"ls /home"</i> must be 
	 * 			passed like an array of n position which the command is splitted in ["ls", "/home"]
	 * 
	 * @return An {@link ExecutionResult} that contain the result of the operation
	 */
	public ExecutionResult executeCommand(final String directory, final String... command) {
		
		Execution execution = new Execution(directory, command);
		
		List<Callable<Object>> calls = new ArrayList<Callable<Object>>();
		calls.add(Executors.callable(execution));
		
		try {
			this.executorService.invokeAny(calls, timeoutMinuteConsole, TimeUnit.MINUTES);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		
		return execution.getExecutionResult();
	}
	
	
	
	/**
	 * The function shutdown the terminal and nullify the instance of the singleton
	 */
	public void shutdownTerminalService() {
		
		this.executorService.shutdown();
		Terminal.instance = null;
	}
	
}
