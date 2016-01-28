package it.unical.classmanager.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class which represent a GenericContainerBean's list.
 * 
 * @see it.unical.classmanager.utils.GenericContainerBean
 * 
 * @author Aloisius92
 */
public class GenericContainerBeanList {
	private List<GenericContainerBean> list;

	public GenericContainerBeanList() {
		setList(new ArrayList<GenericContainerBean>());
	}

	public GenericContainerBeanList(List<Object[]> objects) {
		setList(new ArrayList<GenericContainerBean>());

		for(int i=0; i<objects.size(); i++){
			this.addToList(new GenericContainerBean(objects.get(i)));	    
		}	
	}

	public List<GenericContainerBean> getList() {
		return list;
	}

	public void setList(List<GenericContainerBean> list) {
		this.list = list;
	}

	public void addToList(GenericContainerBean invitationBean){
		list.add(invitationBean);
	}   

	public int size(){
		return list.size();
	}

	public GenericContainerBean get(int index){
		return list.get(index);
	}
}
