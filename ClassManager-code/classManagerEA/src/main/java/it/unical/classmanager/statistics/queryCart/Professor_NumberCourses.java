/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.List;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.PieGradientCart;

/**
 * Query: "Professori/numero corsi che sostengono"
 * 
 * @author Aloisius92
 */
public class Professor_NumberCourses extends AbstractQueryCart {

	public Professor_NumberCourses() {
		super();
	}

	public Professor_NumberCourses(User user){
		super(user);
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
	 */
	@Override
	protected AbstractCart buildCartFromQuery() {
		return buildCartFromQuery(new PieGradientCart());
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery(AbstractCart)
	 */
	@Override
	protected AbstractCart buildCartFromQuery(AbstractCart cart) {
		List<Object[]> courseByProfessor = DaoHelper.getCartQueryDAO().getCourseByProfessor((Professor)this.getUser());

		/* ResultSet content
		 * 
		 * Professor, NumberCourse
		 * Professor, numberCourse
		 * Professor, numberCourse
		 * Professor, numberCourse
		 */

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Professor_NumberCourses_Title",null,locale));

		StringBuilder seriesContent = new StringBuilder("");
		StringBuilder data = new StringBuilder("");
		for(int i=0; i<courseByProfessor.size(); i++){	    
			String currentData = "{name: \'"+
					courseByProfessor.get(i)[0]+"\', y: "+
					courseByProfessor.get(i)[1]+" }"; 

			if(i==0){
				data.append(currentData);
			} else {
				data.append(", "+currentData);
			}
		}
		seriesContent.append("{");
		seriesContent.append("\n");
		seriesContent.append("name: \'"+messageSource.getMessage("message.statistics.Professor_NumberCourses_SeriesContentName",null,locale)+"\',\n");
		seriesContent.append("data: [\n");
		seriesContent.append(data.toString());
		seriesContent.append("]\n");
		seriesContent.append("}");
		cart.setProperty("#series", seriesContent.toString());

		return cart;
	}

}	
