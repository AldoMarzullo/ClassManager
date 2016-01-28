package it.unical.classmanager.model;


public class FolderBean extends AbstractFileBean{

	private int childs = 0;
	
	public FolderBean() {
		
	}
	
	public FolderBean(int lectureId, String name, String type, String path, int childs){
		this.parentId = lectureId;
		this.name = name;
		this.type = type;
		this.path = path;
		this.childs = childs;
	}

	public int getChilds() {
		return childs;
	}

	public void setChilds(int childs) {
		this.childs = childs;
	}
	
	
}

