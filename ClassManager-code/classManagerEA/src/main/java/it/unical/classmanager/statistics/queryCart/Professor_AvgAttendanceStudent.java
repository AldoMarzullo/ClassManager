/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.List;

import it.unical.classmanager.model.dao.CartQueryDAO;
import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.ColumnBasicCart;

/**
 * Query: "Per ogni corso la media delle presenze di tutti gli studenti"
 * 
 * @author Aloisius92
 */
public class Professor_AvgAttendanceStudent extends AbstractQueryCart {
    
    public Professor_AvgAttendanceStudent() {
	super();
    }
    
    public Professor_AvgAttendanceStudent(User user){
	super(user);
    }
    
    /* (non-Javadoc)
     * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
     */
    @Override
    protected AbstractCart buildCartFromQuery() {
	return buildCartFromQuery(new ColumnBasicCart());
    }
    
    @Override
    protected AbstractCart buildCartFromQuery(AbstractCart cart) {
	// Query
	CartQueryDAO cartQueryDAO = DaoHelper.getCartQueryDAO();
	List<Object[]> avgAttendanceStudent = cartQueryDAO.getAvgAttendanceStudent((Professor)this.getUser());
	
	/*
	 * CourseClass, AvgAttendance
	 * CourseClass, AvgAttendance
	 * CourseClass, AvgAttendance
	 */
	
	cart.setProperty("#container", idContainer);
	cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Professor_AvgAttendanceStudent_Title",null,locale));
	cart.setProperty("#subTitleText", messageSource.getMessage("message.statistics.Professor_AvgAttendanceStudent_SubTitle",null,locale));
	cart.setProperty("#yAxisTitle", messageSource.getMessage("message.statistics.Professor_AvgAttendanceStudent_yAxisTitle",null,locale));
	cart.setProperty("#xAxisTitle", "");
	
	StringBuilder categories = new StringBuilder("");	
	for(int i=0; i<avgAttendanceStudent.size(); i++){
	    String currentData = "\'"+avgAttendanceStudent.get(i)[0]+"\'";
	    if(i==0){
		categories.append(currentData);
	    } else {
		categories.append(", "+currentData);		
	    }
	}	
	cart.setProperty("#xAxisCategories", categories.toString());
	cart.setProperty("#yAxisCategories", "");
	cart.setProperty("#tooltipValueSuffix", "");
	
	StringBuilder seriesContent = new StringBuilder("");	
	seriesContent.append("{name: \'"+messageSource.getMessage("message.statistics.Professor_AvgAttendanceStudent_SeriesContentName",null,locale)+"\', data: [");
	for(int i=0; i<avgAttendanceStudent.size(); i++){
	    float currentData = Float.parseFloat(avgAttendanceStudent.get(i)[1].toString());
	    if(i==0){
		seriesContent.append(currentData+"\n");
	    } else {
		seriesContent.append(", "+currentData+"\n");		
	    }
	}
	seriesContent.append("]}");	
	cart.setProperty("#series", seriesContent.toString());
	
	StringBuilder drilldownContent = new StringBuilder("");
	cart.setProperty("#drilldownSeries", drilldownContent.toString());
	
	return cart;
    }
    
}