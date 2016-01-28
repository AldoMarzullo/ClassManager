/**
 * 
 */
package it.unical.classmanager.statistics.queryCart;

import java.util.List;

import it.unical.classmanager.model.dao.DaoHelper;
import it.unical.classmanager.model.data.Student;
import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;
import it.unical.classmanager.statistics.cart.ColumnDrilldownCart;

/**
 * Query: "Per ogni corso la media delle presenze"
 * 
 * @author Aloisius92
 */
public class Student_AvgAttendance extends AbstractQueryCart {

	public Student_AvgAttendance() {
		super();
	}

	public Student_AvgAttendance(User user){
		super(user);
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery()
	 */
	@Override
	protected AbstractCart buildCartFromQuery() {
		return buildCartFromQuery(new ColumnDrilldownCart());
	}

	/**
	 * @see it.unical.classmanager.statistics.queryCart.AbstractQueryCart#buildCartFromQuery(AbstractCart)
	 */
	@Override
	protected AbstractCart buildCartFromQuery(AbstractCart cart) {
		List<Object[]> avgAttendanceByStudent = DaoHelper.getCartQueryDAO().getAvgAttendanceByStudent((Student)this.getUser());

		/* ResultSet content
		 * 
		 * CourseName1, Avg1
		 * CourseName2, Avg2
		 * CourseName3, Avg3
		 * ...
		 * CourseNameN, AvgN
		 */	

		cart.setProperty("#container", idContainer);
		cart.setProperty("#titleText", messageSource.getMessage("message.statistics.Student_AvgAttendance_Title",null,locale));
		cart.setProperty("#subTitleText", messageSource.getMessage("message.statistics.Student_AvgAttendance_SubTitle",null,locale));
		cart.setProperty("#xAxisTitle", messageSource.getMessage("message.statistics.Student_AvgAttendance_xAxisTitle",null,locale));

		StringBuilder seriesContent = new StringBuilder("");
		seriesContent.append("{");
		{
			seriesContent.append("\n");
			seriesContent.append("name: \'"+messageSource.getMessage("message.statistics.Student_AvgAttendance_SeriesContentName",null,locale)+"\',\n");
			seriesContent.append("colorByPoint: true,\n");
			seriesContent.append("data: [");

			if(avgAttendanceByStudent.size()>0){
				String name = avgAttendanceByStudent.get(0)[0].toString();
				float value = Float.parseFloat(avgAttendanceByStudent.get(0)[1].toString());
				seriesContent.append("{ name: \'"+name+"\',\n");
				seriesContent.append("y: "+value+",\n");
				seriesContent.append("drilldown: null}\n");

				for(int i=1; i<avgAttendanceByStudent.size(); i++){
					name = avgAttendanceByStudent.get(i)[0].toString();
					value = Float.parseFloat(avgAttendanceByStudent.get(i)[1].toString());
					seriesContent.append(", { name: \'"+name+"\',\n");
					seriesContent.append("y: "+value+",\n");
					seriesContent.append("drilldown: null}\n");
				}
			}
			seriesContent.append("]\n");
			seriesContent.append("}");
		}
		cart.setProperty("#series", seriesContent.toString());
		StringBuilder drilldownContent = new StringBuilder("");
		drilldownContent.append("{}");	
		cart.setProperty("#drilldownSeries", drilldownContent.toString());

		return cart;
	}

}
