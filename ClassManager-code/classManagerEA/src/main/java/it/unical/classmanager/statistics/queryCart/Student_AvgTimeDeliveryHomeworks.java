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
 * Query: "Media tempi consegna compiti per corso"
 * 
 * @author Aloisius92
 */
public class Student_AvgTimeDeliveryHomeworks extends AbstractQueryCart {

	public Student_AvgTimeDeliveryHomeworks() {
		super();
	}

	public Student_AvgTimeDeliveryHomeworks(User user){
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
		List<Object[]> avgTimeDeliveryHomeworksByStudent = DaoHelper.getCartQueryDAO().getAvgTimeDeliveryHomeworksByStudent((Student)this.getUser());

		/* ResultSet content
		 * 
		 * Course, AvgTime
		 * Course, AvgTime
		 * Course, AvgTime
		 * ...
		 * Course, AvgTime
		 */

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Student_AvgTimeDeliveryHomeworks_Title",null,locale));

		StringBuilder categories = new StringBuilder("");
		for(int i=0; i<avgTimeDeliveryHomeworksByStudent.size(); i++){
			if(i==0){
				categories.append("\'"+avgTimeDeliveryHomeworksByStudent.get(i)[0].toString()+"\'");
			} else {
				categories.append(", \'"+avgTimeDeliveryHomeworksByStudent.get(i)[0].toString()+"\'");		
			}
		}
		cart.setProperty("#xAxisCategories", categories.toString());
		cart.setProperty("#yAxisMin", "0");

		StringBuilder seriesContent = new StringBuilder("");
		seriesContent.append("{name: \'"+messageSource.getMessage("message.statistics.Student_AvgTimeDeliveryHomeworks_SeriesContentName",null,locale)+"\', data: [");
		for(int i=0; i<avgTimeDeliveryHomeworksByStudent.size(); i++){
			float avgValue = Float.parseFloat(avgTimeDeliveryHomeworksByStudent.get(i)[1].toString());
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
