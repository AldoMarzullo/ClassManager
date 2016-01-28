/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.Date;
import java.util.List;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.SplineIrregularTimeCart;

/**
 * Query: "Voti esami"
 * 
 * @author Aloisius92
 */
public class Student_ExamScoreSeries extends AbstractQueryCart {

	public Student_ExamScoreSeries() {
		super();
	}

	public Student_ExamScoreSeries(User user){
		super(user);
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
	 */
	@Override
	protected AbstractCart buildCartFromQuery() {
		return buildCartFromQuery(new SplineIrregularTimeCart());
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery(AbstractCart)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected AbstractCart buildCartFromQuery(AbstractCart cart) {
		List<Object[]> examScoreSeriesByStudent = DaoHelper.getCartQueryDAO().getExamScoreSeriesByStudent((Student)this.getUser());

		/* ResultSet content
		 * 
		 * Anno, Date, ExamName, Score
		 * Anno, Date, ExamName, Score
		 * Anno, Date, ExamName, Score
		 * Anno, Date, ExamName, Score
		 * ...
		 * Anno, Date, ExamName, Score
		 */

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Student_ExamScoreSeries_Title",null,locale));
		cart.setProperty("#subTitleText", messageSource.getMessage("message.statistics.Student_ExamScoreSeries_SubTitle",null,locale));
		cart.setProperty("#xAxisTitle", messageSource.getMessage("message.statistics.Student_ExamScoreSeries_xAxisTitle",null,locale));
		cart.setProperty("#yAxisTitle", messageSource.getMessage("message.statistics.Student_ExamScoreSeries_yAxisTitle",null,locale));
		cart.setProperty("#yAxisMin", "0");
		cart.setProperty("#yAxisMax", "30");

		StringBuilder seriesContent = new StringBuilder("");
		int currentYear = 0;
		if(examScoreSeriesByStudent.size()>0){
			for(int i=0; i<examScoreSeriesByStudent.size(); i++){
				int year = Integer.parseInt(examScoreSeriesByStudent.get(i)[0].toString());
				Date date = (Date) examScoreSeriesByStudent.get(i)[1];
				//String examName = examScoreSeriesByStudent.get(i)[2].toString();
				float score = Float.parseFloat(examScoreSeriesByStudent.get(i)[3].toString());

				currentYear = year;

				if(i==0){
					seriesContent.append("{name: \'"+currentYear+"\',\n");
				} else {
					seriesContent.append(", {name: \'"+currentYear+"\',\n");
				}		

				StringBuilder data = new StringBuilder("");
				data.append("[Date.UTC("+date.getYear()+", "+(date.getMonth()-1)+", "+date.getDate()+"), "+score+"]\n");		

				i++;
				if(i<examScoreSeriesByStudent.size()){
					do {
						year = Integer.parseInt(examScoreSeriesByStudent.get(i)[0].toString());
						date = (Date) examScoreSeriesByStudent.get(i)[1];
						//examName = examScoreSeriesByStudent.get(i)[2].toString();
						score = Float.parseFloat(examScoreSeriesByStudent.get(i)[3].toString());

						if(currentYear == year){
							data.append(",[Date.UTC("+date.getYear()+", "+(date.getMonth()-1)+", "+date.getDate()+"), "+score+"]\n");		
							i++;
						} else if(!(currentYear == year) && i<examScoreSeriesByStudent.size()){
							i--;
						}
					}
					while((currentYear == year) && i<examScoreSeriesByStudent.size());
				}

				seriesContent.append("data: ["+data+"]}\n");
			}
		}
		cart.setProperty("#series", seriesContent.toString());

		return cart;
	}

}
