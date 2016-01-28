/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.List;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.LineBasicCart;

/**
 * Query: "Voti compiti per corso"
 * 
 * @author Aloisius92
 */
public class Student_HomeworkScoreSeries extends AbstractQueryCart {

	public Student_HomeworkScoreSeries() {
		super();
	}

	public Student_HomeworkScoreSeries(User user){
		super(user);
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
	 */
	@Override
	protected AbstractCart buildCartFromQuery() {
		return buildCartFromQuery(new LineBasicCart());
	}
	
	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery(AbstractCart)
	 */
	@Override
	protected AbstractCart buildCartFromQuery(AbstractCart cart) {
		List<Object[]> homeworkScoreSeriesByStudent = DaoHelper.getCartQueryDAO().getHomeworkScoreSeriesByStudent((Student)this.getUser());

		/* ResultSet content
		 * 
		 * Course, Homework, Score
		 * Course, Homework, Score
		 * Course, Homework, Score
		 * ...
		 * Course, Homework, Score
		 */	

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Student_HomeworkScoreSeries_Title",null,locale));
		cart.setProperty("#subTitleText", "");

		StringBuilder categories = new StringBuilder("");
		String lastCategorie = "";
		for(int i=0; i<homeworkScoreSeriesByStudent.size(); i++){
			String courseName = homeworkScoreSeriesByStudent.get(i)[0].toString();
			if(i==0){
				lastCategorie = courseName;
				//categories.append("\'"+courseName+"\'");
				categories.append("\' \'");
			} else {
				if(!courseName.equals(lastCategorie)){
					lastCategorie = courseName;
					//categories.append(", \'"+courseName+"\'");
					categories.append(", \' \'");
				}
			}
		}

		cart.setProperty("#xAxisCategories", "");
		cart.setProperty("#yAxisTitle", messageSource.getMessage("message.statistics.Student_HomeworkScoreSeries_yAxisTitle",null,locale));
		cart.setProperty("#yAxisMax", "30");
		cart.setProperty("#tooltipValueSuffix", "");	

		StringBuilder seriesContent = new StringBuilder("");
		String currentCourse = "";	
		if(homeworkScoreSeriesByStudent.size()>0){
			for(int i=0; i<homeworkScoreSeriesByStudent.size(); i++){
				String courseName = homeworkScoreSeriesByStudent.get(i)[0].toString();
				//String homeWorkName = avgHomeworksByStudent.get(i)[1].toString();
				float score = Float.parseFloat(homeworkScoreSeriesByStudent.get(i)[2].toString());

				currentCourse = courseName;

				if(i==0){
					seriesContent.append("{name: \'"+courseName+"\',\n");
				} else {
					seriesContent.append(", {name: \'"+courseName+"\',\n");
				}		

				StringBuilder data = new StringBuilder("");
				data.append(score);

				i++;
				do {
					if(i<homeworkScoreSeriesByStudent.size()){
						courseName = homeworkScoreSeriesByStudent.get(i)[0].toString();
						//homeWorkName = avgHomeworksByStudent.get(i)[1].toString();
						score = Float.parseFloat(homeworkScoreSeriesByStudent.get(i)[2].toString());
						if(courseName.equals(currentCourse)){
							data.append(", "+score);
							i++;
						} else if(!courseName.equals(currentCourse) && i<homeworkScoreSeriesByStudent.size()){
							i--;
						}
					}
				}
				while(currentCourse.equals(courseName) && i<homeworkScoreSeriesByStudent.size());

				seriesContent.append("data: ["+data+"]}\n");
			}
		}
		cart.setProperty("#series", seriesContent.toString());

		return cart;
	}

}
