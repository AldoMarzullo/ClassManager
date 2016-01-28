package it.unical.classmanager.model;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxDateValidator implements ConstraintValidator<MaxDate,Object>{

	 @Override
	    public void initialize(MaxDate constraintAnnotation) {
	    }
	 
	    @Override
	    public boolean isValid(Object value, ConstraintValidatorContext context) {
	        if (value == null) {
	            return true;
	        }
	       
	        Date dateOfBirth = (Date) value;
	        
	        Calendar dob = Calendar.getInstance();
	        dob.setTime(dateOfBirth);
	        Calendar today = Calendar.getInstance();
	        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
	        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
	            age--;
	        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
	                && today.get(Calendar.DAY_OF_MONTH) < dob
	                        .get(Calendar.DAY_OF_MONTH)) {
	            age--;
	        }

	        if (age >= 18) {
	            return true;
	        } 
	        return false;
	        
	    }
	
}
