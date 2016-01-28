package it.unical.classmanager.editorTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import it.unical.classmanager.editorData.Environment;
import it.unical.classmanager.managers.EnvironmentManger;
import it.unical.classmanager.utils.enumative.EnvironmentEnum;

public class EditorTestCase {

	
	/*
	@BeforeClass
	static public void init() {
		
	}
	*/
	
	
	@Test
	public void cppHelloWorld() {
		
		String code = "#include <iostream>\n"
				+ "using namespace std;\n"
				+ "\n"
				+ "int main(){\n\n"
				+ "    cout<<\"hello!!!\";\n"
				+ "    return 0;\n"
				+ "}";
		
		Environment cppEnv = EnvironmentManger.getInstance().getEnvironment(EnvironmentEnum.getEnumFromString("c_cpp"));
		String result = cppEnv.compile(code);
		
		assertEquals("hello!!!", result);
		
	}
	
	
	@Test
	public void cppCompilingError() {
		
		String code = "#include <iostream>\n"
				+ "using namespace std;\n"
				+ "\n"
				+ "int main(){\n\n"
				+ "    cout<<\"hello!!!\"l;\n"
				+ "    return 0;\n"
				+ "}";
		
		Environment cppEnv = EnvironmentManger.getInstance().getEnvironment(EnvironmentEnum.getEnumFromString("c_cpp"));
		String result = cppEnv.compile(code);
		
		assertTrue(result.matches("main\\d+\\.cpp\\: In function \\‘int main\\(\\)\\’\\:\nmain\\d+\\.cpp\\:6\\:21\\: error\\: expected \\‘\\;\\’ before \\‘l\\’\n     cout\\<\\<\"hello\\!\\!\\!\"l\\;\n                     \\^\n"));
	}
	
	
	@Test
	public void cppWhileTrue() {
		
		String code = "#include <iostream>\n"
				+ "using namespace std;\n"
				+ "\n"
				+ "int main(){\n\n"
				+ "    while(true){};\n"
				+ "    return 0;\n"
				+ "}";
		
		Environment cppEnv = EnvironmentManger.getInstance().getEnvironment(EnvironmentEnum.getEnumFromString("c_cpp"));
		String result = cppEnv.compile(code);
		
		assertEquals("ERROR: Time for the running is out...", result);
		
	}
	
	
	
	@Test
	public void cppNullPointerException() {
		
		String code = "#include <iostream>\n"
				+ "using namespace std;\n"
				+ "\n"
				+ "int main(){\n\n"
				+ "    int *n = NULL;\n"
				+ "    cout << *n;\n"
				+ "    return 0;\n"
				+ "}";
		
		Environment cppEnv = EnvironmentManger.getInstance().getEnvironment(EnvironmentEnum.getEnumFromString("c_cpp"));
		String result = cppEnv.compile(code);
		
		assertEquals("signal(11): Invalid memory reference", result);
		
	}

}
