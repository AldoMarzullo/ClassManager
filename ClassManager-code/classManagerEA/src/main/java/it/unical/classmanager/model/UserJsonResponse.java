package it.unical.classmanager.model;

import java.util.Map;

public class UserJsonResponse{
   
	private String status;
    private Map<String,String> errorsMap;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Map<String,String> getErrorsMap() {
        return errorsMap;
    }
    public void setErrorsMap(Map<String,String> errorsMap) {
        this.errorsMap = errorsMap;
    }
}