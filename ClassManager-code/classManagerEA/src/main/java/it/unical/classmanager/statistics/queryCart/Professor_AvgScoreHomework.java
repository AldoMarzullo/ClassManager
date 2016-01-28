/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Professor;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.ColumnStackedAndGroupedCart;

/**
 * Query: "Chi prende i voti migliori? 
 * Per ogni corso tra gli studenti iscritti 
 * calcola la media tra le valutazioni dei compiti, 
 * (se uno studente non svolge il compito valutazione 0)."
 * 
 * @author Aloisius92
 */
public class Professor_AvgScoreHomework extends AbstractQueryCart {
	private String research;

	public Professor_AvgScoreHomework() {
		super();
	}

	public Professor_AvgScoreHomework(User user, String research){
		super(user);
		this.research = research;
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
	 */
	@Override
	protected AbstractCart buildCartFromQuery() {
		return buildCartFromQuery(new ColumnStackedAndGroupedCart());
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery(AbstractCart)
	 */
	@Override
	protected AbstractCart buildCartFromQuery(AbstractCart cart) {
		List<Object[]> avgScoreHomework = DaoHelper.getCartQueryDAO().getAvgScoreHomework((Professor)this.getUser(), research);

		/* ResultSet content
		 * 
		 * CourseClass, Student, AvgScore
		 * CourseClass, Student, AvgScore
		 * CourseClass, Student, AvgScore
		 */

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Professor_AvgScoreHomework_Title",null,locale));
		cart.setProperty("#yAxisTitle", messageSource.getMessage("message.statistics.Professor_AvgScoreHomework_yAxisTitle",null,locale));
		cart.setProperty("#xAxisMax", "0");

		StringBuilder categories = new StringBuilder("");
		String lastCourse = "";
		for(int i=0; i<avgScoreHomework.size(); i++){
			String currentData = "\'"+avgScoreHomework.get(i)[0].toString()+"\'";
			if(!lastCourse.equals(currentData)){
				lastCourse = currentData;
				if(i==0){
					categories.append(currentData);
				} else {
					categories.append(", "+currentData);		
				}
			}
		}	
		cart.setProperty("#xAxisCategories", categories.toString());
		cart.setProperty("#yAxisMax", "30");
		cart.setProperty("#tooltipValueSuffix", "");

		StringBuilder seriesContent = new StringBuilder("");
		HashMap<String, List<Float>> studentScores = new HashMap<String, List<Float>>();
		for(int i=0; i<avgScoreHomework.size(); i++){
			String student = avgScoreHomework.get(i)[1].toString();
			Float value = Float.parseFloat(avgScoreHomework.get(i)[2].toString());

			List<Float> list = studentScores.get(student);
			if(list==null){
				studentScores.put(student, new ArrayList<Float>());
			}
			studentScores.get(student).add(value);
		}

		Set<String> keySet = studentScores.keySet();
		int k =0;
		for(String key : keySet){
			if(k==0){
				seriesContent.append("{");			
			} else {
				seriesContent.append(", {");		
			}

			seriesContent.append("\n");
			seriesContent.append("name: \'"+key+"\',\n");
			seriesContent.append("data: [");

			List<Float> list = studentScores.get(key);
			for(int i=0; i<list.size(); i++){
				float currentValue = list.get(i);
				if(i==0){
					seriesContent.append(currentValue);			
				} else {
					seriesContent.append(", "+currentValue);		
				}
			}	    
			seriesContent.append("],\n");
			seriesContent.append(""+messageSource.getMessage("message.statistics.Professor_AvgScoreHomework_StackPrefix",null,locale)+": \'student\'\n");
			seriesContent.append("}");

			k++;
		}
		cart.setProperty("#series", seriesContent.toString());
		StringBuilder drilldownContent = new StringBuilder("");
		cart.setProperty("#drilldownSeries", drilldownContent.toString());

		return cart;
	}

}



