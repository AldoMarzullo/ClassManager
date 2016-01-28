package it.unical.classmanager.editorData;

public class EditorStatus {

	private String code;
	private String consoleContent;
	private String theme;
	private String language;
	
	public EditorStatus() {

		this.code = "";
		this.consoleContent = "";
		this.theme = "";
		this.language = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConsoleContent() {
		return consoleContent;
	}

	public void setConsoleContent(String consoleContent) {
		this.consoleContent = consoleContent;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "EditorStatus [code=" + code + ", consoleContent=" + consoleContent + ", theme=" + theme + ", language="
				+ language + "]";
	}
	
}
