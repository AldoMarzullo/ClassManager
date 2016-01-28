package it.unical.classmanager.utils;

/**
 * Helper class for contains generic values.
 * This class is utilized in several jsp pages.
 * 
 * @author Aloisius92
 */
public class GenericContainerBean {
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	private int maxFields = 6;
	private String fields[];

	public GenericContainerBean() {
		resetFields();
		setField1("");
		setField2("");
		setField3("");
		setField4("");
		setField5("");
		setField6("");
	}

	public GenericContainerBean(String field1) {
		super();
		resetFields();
		setField1(field1);
		setField2("");
		setField3("");
		setField4("");
		setField5("");
		setField6("");
	}

	public GenericContainerBean(String field1, String field2) {
		super();
		resetFields();
		setField1(field1);
		setField2(field2);
		setField3("");
		setField4("");
		setField5("");
		setField6("");
	}

	public GenericContainerBean(String field1, String field2, String field3) {
		super();
		resetFields();
		setField1(field1);
		setField2(field2);
		setField3(field3);
		setField4("");
		setField5("");
		setField6("");
	}

	public GenericContainerBean(String field1, String field2, String field3, String field4) {
		super();
		resetFields();
		setField1(field1);
		setField2(field2);
		setField3(field3);
		setField4(field4);
		setField5("");
		setField6("");
	}

	public GenericContainerBean(String field1, String field2, String field3, String field4, String field5) {
		super();
		resetFields();
		setField1(field1);
		setField2(field2);
		setField3(field3);
		setField4(field4);
		setField5(field5);
		setField6("");
	}

	public GenericContainerBean(String field1, String field2, String field3, String field4, String field5, String field6) {
		super();
		resetFields();
		setField1(field1);
		setField2(field2);
		setField3(field3);
		setField4(field4);
		setField5(field5);
		setField6(field6);
	}

	public GenericContainerBean(Object[] objects) {
		resetFields();
		for(int i=0; i<objects.length && i<maxFields; i++){
			fields[i] = objects[i].toString();		    
		}
	}    

	private void resetFields(){
		fields = new String[maxFields];
		for(int i=0; i<maxFields; i++){
			fields[i] = "";	    
		}
	}

	public String getField1() {
		return this.fields[0];
	}

	public void setField1( String field1) {
		this.field1 = field1;
		this.fields[0] = this.field1;
	}

	public String getField2() {
		return this.fields[1];
	}

	public void setField2( String field2) {
		this.field2 = field2;
		this.fields[1] = this.field2;
	}

	public String getField3() {
		return this.fields[2];
	}

	public void setField3( String field3) {
		this.field3 = field3;
		this.fields[2] = this.field3;
	}

	public String getField4() {
		return this.fields[3];
	}

	public void setField4( String field4) {
		this.field4 = field4;
		this.fields[3] = this.field4;
	}

	public String getField5() {
		return this.fields[4];
	}

	public void setField5( String field5) {
		this.field5 = field5;
		this.fields[4] = this.field5;
	}

	public String getField6() {
		return this.fields[5];
	}

	public void setField6( String field6) {
		this.field6 = field6;
		this.fields[5] = this.field6;
	}

}
