package it.unical.classmanager;

import it.unical.classmanager.editorData.Environment;
import it.unical.classmanager.editorData.concrete.CPPEnvironment;
import it.unical.classmanager.utils.Terminal;

public class Main {

	public static void main(String[] args) {
		
		Environment env = new CPPEnvironment();
		
		for (int i = 0; i < 1; i++) {
			String fileContent = "#include <iostream>\n"
					+ "using namespace std;\n"
					+ "\n"
					+ "int main(){\n"
					+ "int* hello = NULL;\n"
					+ "cout << *hello << endl;\n\n"
					+ "cout<<\"hello!!!\"<<endl;\n"
					+ "cout << \"ahahhahahah\" << endl; \n"
					+ "return 0;\n"
					+ "}";
			System.out.println(env.compile(fileContent));
		}

		
		env.destroyEnvironment();
		Terminal.getTerminal().shutdownTerminalService();
	}

	
}
