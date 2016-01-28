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
import it.unical.classmanager.statistics.cart.ColumnStackedPercentCart;

/**
 * Query: "Negli anni il numero medio di lezioni per ogni giorno della settimana"
 * 
 * @author Aloisius92
 */
public class Professor_AvgLectureByWeekDayAllProfessor extends AbstractQueryCart {
    
    public Professor_AvgLectureByWeekDayAllProfessor() {
	super();
    }
    
    public Professor_AvgLectureByWeekDayAllProfessor(User user){
	super(user);
    }
    
    /* (non-Javadoc)
     * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
     */
    @Override
    protected AbstractCart buildCartFromQuery() {
	return buildCartFromQuery(new ColumnStackedPercentCart());
    }
    
    @Override
    protected AbstractCart buildCartFromQuery(AbstractCart cart) {
	// Query
	CartQueryDAO cartQueryDAO = DaoHelper.getCartQueryDAO();
	List<Professor> allProfessors = DaoHelper.getUserDAO().getAllProfessors();	
	List<Object[]> avg = cartQueryDAO.getAvgLectureByWeekDayAllProfessor(allProfessors);
	
	/*
	 * Professor, Monday, NumberLecture
	 * Professor, Tuesday, NumberLecture
	 * Professor, Wednesday, NumberLecture
	 * Professor, Thursday, NumberLecture
	 * Professor, Friday, NumberLecture
	 * Professor, Saturday, NumberLecture
	 * Professor, Sunday, NumberLecture
	 * 
	 */

	cart.setProperty("#container", idContainer);
	cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDayAllProfessor_Title",null,locale));
	cart.setProperty("#yAxisTitle", messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDayAllProfessor_yAxisTitle",null,locale));
	cart.setProperty("#xAxisCategories", messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDayAllProfessor_xAxisCategories",null,locale));
	cart.setProperty("#xAxisTitle", "");
	cart.setProperty("#xAxisMax", "0");	
	cart.setProperty("#yAxisMax", "0");
	cart.setProperty("#tooltipValueSuffix", "");
	
	StringBuilder seriesContent = new StringBuilder("");	
	for(int i=0; i<avg.size(); i+=7){
	    if(i==0){
		seriesContent.append("{");		
	    } else {
		seriesContent.append(", {");
	    }
	    
	    {
		seriesContent.append("\n");
		seriesContent.append("name: \'"+avg.get(i)[0].toString()+"\',\n");
		seriesContent.append("data: [");
		
		for(int j=0; j<7; j++){
		    float currentData = Float.parseFloat(avg.get(i+j)[2].toString());
		    if(j==0){
			seriesContent.append(currentData);			
		    } else {
			seriesContent.append(", "+currentData);			
		    }
		}
		
		seriesContent.append("]\n");
		seriesContent.append("}");
	    }
	}
	cart.setProperty("#series", seriesContent.toString());
	StringBuilder drilldownContent = new StringBuilder("");
	cart.setProperty("#drilldownSeries", drilldownContent.toString());
	
	return cart;
    }
    
}




