package it.unical.classmanager.model;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class FileBean extends AbstractFileBean{

	private long size;
	private String extension;
	public static final String FILE_TYPE = "file";
	
	public FileBean() {
		
	}
	
	public FileBean(int fileId, int lectureId, String name, String type, String path, long l, String extension){
		this.name = name;
		this.type = type;
		this.path = path;
		this.size = l;	
		this.extension = extension;
		this.parentId = lectureId;
		this.setId(fileId);
		
	}
	
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public static FileBean toFileBean(File file){
		String extension = FilenameUtils.getExtension(file.getPath());
		return new FileBean(0,0,file.getName(),AbstractFileBean.FILE_TYPE, file.getPath(), file.length(), extension);
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
