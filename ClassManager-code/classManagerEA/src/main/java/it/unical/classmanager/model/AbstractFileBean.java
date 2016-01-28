package it.unical.classmanager.model;

public abstract class AbstractFileBean {
	
	public static final String FOLDER_TYPE = "folder";
	public static final String FILE_TYPE = "file";
	
	protected int parentId;
	private int id;
	protected String name;
	protected String type;
	protected String path;
	private String action;
	protected boolean evaluable;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean isEvaluable() {
		return evaluable;
	}

	public void setEvaluable(boolean evaluable) {
		this.evaluable = evaluable;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
