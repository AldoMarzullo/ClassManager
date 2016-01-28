package it.unical.classmanager.utils.enumative;


/**
 * An enumerative class that represent the language available for the editor
 * 
 * 
 * @author sera
 *
 */
public enum EnvironmentEnum {

	C_CPP("c_cpp", "C/C++"), JAVA("java", "Java"), PYTHON("python", "Python"), PERL("perl", "Perl"), DLV("dlv", "DLV");

	private String label;
	private String value;
	
	
	private EnvironmentEnum(String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static EnvironmentEnum getEnumFromString(String name) {
		return valueOf(name.toUpperCase());
	}
}
