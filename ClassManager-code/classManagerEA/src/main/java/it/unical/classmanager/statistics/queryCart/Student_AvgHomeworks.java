/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.List;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.PolarSpiderCart;

/**
 * Query: "Media compiti per corso"
 * 
 * @author Aloisius92
 */
public class Student_AvgHomeworks extends AbstractQueryCart {

	public Student_AvgHomeworks() {
		super();
	}

	public Student_AvgHomeworks(User user){
		super(user);
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
	 */
	@Override
	protected AbstractCart buildCartFromQuery() {
		return buildCartFromQuery(new PolarSpiderCart());
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery(AbstractCart)
	 */
	@Override
	protected AbstractCart buildCartFromQuery(AbstractCart cart) {
		List<Object[]> avgHomeworksByStudent = DaoHelper.getCartQueryDAO().getAvgHomeworksByStudent((Student)this.getUser());

		/* ResultSet content
		 * 
		 * avgHomeworksByStudent
		 * Course1, AvgScore
		 * Course2, AvgScore
		 * Course3, AvgScore
		 * ...
		 * CourseN, AvgScore
		 */	

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Student_AvgHomeworks_Title",null,locale));

		StringBuilder categories = new StringBuilder("");
		for(int i=0; i<avgHomeworksByStudent.size(); i++){
			if(i==0){
				categories.append("\'"+avgHomeworksByStudent.get(i)[0].toString()+"\'");
			} else {
				categories.append(", \'"+avgHomeworksByStudent.get(i)[0].toString()+"\'");		
			}
		}
		cart.setProperty("#xAxisCategories", categories.toString());
		cart.setProperty("#yAxisMin", "0");

		StringBuilder seriesContent = new StringBuilder("");
		seriesContent.append("{name: \'"+messageSource.getMessage("message.statistics.Student_AvgHomeworks_SeriesContentName",null,locale)+"\', data: [");
		for(int i=0; i<avgHomeworksByStudent.size(); i++){
			float avgValue = Float.parseFloat(avgHomeworksByStudent.get(i)[1].toString());
			if(i==0){
				seriesContent.append(avgValue);
			} else {
				seriesContent.append(", "+avgValue);		
			}
		}
		seriesContent.append("], pointPlacement: \'on\'}");
		cart.setProperty("#series", seriesContent.toString());

		return cart;
	}

}
