/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.List;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.PolarSpiderCart;

/**
 * Query: "Negli anni il numero medio di lezioni per ogni giorno della settimana"
 * 
 * @author Aloisius92
 */
public class Professor_AvgLectureByWeekDaySingleProfessor extends AbstractQueryCart {

	public Professor_AvgLectureByWeekDaySingleProfessor() {
		super();
	}

	public Professor_AvgLectureByWeekDaySingleProfessor(User user){
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
		List<Object[]> avg = DaoHelper.getCartQueryDAO().getAvgLectureByWeekDaySingleProfessor((Professor)this.getUser());

		/* ResultSet content
		 * 
		 * Monday, NumberLecture
		 * Tuesday, NumberLecture
		 * Wednesday, NumberLecture
		 * Thursday, NumberLecture
		 * Friday, NumberLecture
		 * Saturday, NumberLecture
		 * Sunday, NumberLecture
		 */

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDaySingleProfessor_Title",null,locale));
		cart.setProperty("#subTitleText", messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDaySingleProfessor_SubTitle",null,locale));
		cart.setProperty("#xAxisCategories", messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDaySingleProfessor_xAxisCategories",null,locale));
		cart.setProperty("#xAxisTitle", "");
		cart.setProperty("#yAxisTitle", "");
		cart.setProperty("#xAxisMax", "360");
		cart.setProperty("#yAxisMax", "0");
		cart.setProperty("#tooltipValueSuffix", "");

		StringBuilder seriesContent = new StringBuilder("");
		seriesContent.append("{name: \'"+messageSource.getMessage("message.statistics.Professor_AvgLectureByWeekDaySingleProfessor_SeriesContentName",null,locale)+"\', data: [");
		for(int i=0; i<avg.size(); i++){
			float avgValue = Float.parseFloat(avg.get(i)[1].toString());
			if(i==0){
				seriesContent.append(avgValue);
			} else {
				seriesContent.append(", "+avgValue);		
			}
		}
		seriesContent.append("], pointPlacement: \'on\'}");
		cart.setProperty("#series", seriesContent.toString());
		StringBuilder drilldownContent = new StringBuilder("");
		cart.setProperty("#drilldownSeries", drilldownContent.toString());

		return cart;
	}

}
