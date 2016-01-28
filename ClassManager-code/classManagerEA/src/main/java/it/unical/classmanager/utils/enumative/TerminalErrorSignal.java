package it.unical.classmanager.utils.enumative;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * An enumerative class that represent the signal of the UNIX system
 * 
 * @author sera
 *
 */
public enum TerminalErrorSignal {
	
	SIGHUP(1, "Hangup detected on controlling terminal or death of controlling process"),
	SIGINT(2, "Interrupt from keyboard"),
	SIGQUIT(3, "Quit from keyboard"),
	SIGILL(4, "Illegal Instruction"),
	SIGTRAP(5, "Trace/breakpoint trap"),
	SIGABRT(6, "Abort signal from abort(3)"),
	SIGBUS(7, "Bus error (bad memory access)"),
	SIGFPE(8, "Floating point exception"),
	SIGKILL(9, "Kill signal"),
	SIGUSR1(10, "User-defined signal 1"),
	SIGSEGV(11, "Invalid memory reference"),
	SIGUSR2(12, "User-defined signal 2"),
	SIGPIPE(13, "Broken pipe: write to pipe with no readers"),
	SIGALRM(14, "Timer signal from alarm(2)"),
	SIGTERM(15, "Termination signal"),

	SIGCHLD(17, "Child stopped or terminated"),
	SIGCONT(18, "Continue if stopped"),
	SIGSTOP(19, "Stop process"),
	SIGTSTP(20, "Stop typed at terminal"),
	SIGTTIN(21, "Terminal input for background process"),
	SIGTTOU(22, "Terminal output for background process"),

	SIGURG(23, "Urgent condition on socket (4.2BSD)"),
	SIGXCPU(24, "CPU time limit exceeded (4.2BSD)"),
	SIGXFSZ(25, "File size limit exceeded (4.2BSD)"),
	SIGVTALRM(26, "Virtual alarm clock (4.2BSD)"),
	SIGPROF(27, "Profiling timer expired"),
	SIGSYS(31, "Bad argument to routine (SVr4)");
	
	private final int code;
	private final String description;

	private static final Map<String, String> signalMap = Collections.unmodifiableMap(initializeMapping());
	
	
	
	private static Map<String, String> initializeMapping() {
		
	    Map<String, String> tmpMap = new HashMap<String, String>();
	    for (TerminalErrorSignal tmpSignal : TerminalErrorSignal.values()) {
	        tmpMap.put(Integer.toString(tmpSignal.getCode()), tmpSignal.name());
	    }
	    return tmpMap;
	}
	
	
	private TerminalErrorSignal(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		return "signal("+ this.code +"): " + this.description;
	}
	
	
	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static TerminalErrorSignal getEnumFromCode(int code) {
		return TerminalErrorSignal.valueOf(signalMap.get(Integer.toString(code)));
	}
	
	
	public static void main(String[] args) {
		
		
		//System.out.println(TerminalErrorSignal.SIGSEGV);
		System.out.println(TerminalErrorSignal.getEnumFromCode(11));
	}
	
}
