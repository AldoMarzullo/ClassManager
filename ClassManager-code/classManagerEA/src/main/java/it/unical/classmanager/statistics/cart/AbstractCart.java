package it.unical.classmanager.statistics.cart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which represent a generic cart type.
 * 
 * @author Aloisius92 
 */
public abstract class AbstractCart  {
	/**
	 * Path to folder which contains html carts scripts.
	 */
	protected static final String pathToCartFiles = "src/main/webapp/resources/statistics/cartsHtml";
	/**
	 * Scripts extension.
	 */
	protected static final String extensionFiles = ".html";
	/**
	 * Cart's name.
	 */
	private String cartName;
	/**
	 * The first content of the Cart script once readed from  the file.
	 * This variables contains all placeholder that will be removed
	 * when the cart is completely builded. After the query.
	 */
	private List<String> dataCart;
	/**
	 * String that contains the entire cart script once builded.
	 */
	private StringBuilder cartScript = null;
	/**
	 * Map that contains the reference to place holders and content of the placeholders.
	 */
	private Map<String, String> dataMap;    
	/**
	 * List of all possible placeholders.
	 */
	private static String[] properties = {
			"#container",
			"#titleText",
			"#subTitleText",	
			"#tooltipPointFormat",
			"#tooltipValueSuffix",	
			"#xAxisCategories",
			"#xAxisLabelsFormat",
			"#xAxisPlotLinesLabelText",
			"#xAxisTitle",
			"#xAxisMin",
			"#xAxisMax",
			"#yAxisLabelsFormat",
			"#yAxisPlotLinesLabelText",
			"#yAxisTitle",
			"#yAxisMin",
			"#yAxisMax",
			"#series", 
	"#drilldownSeries"}; 
	/**
	 * List of all possible default value for placeholders.
	 */
	private static String[] propertiesDefaultValue = {
			"#container",
			"titleText",
			"subTitleText",	
			"",
			"",	
			"",
			"",
			"",
			"xAxisTitle",
			"0",
			"0",
			"",
			"",
			"yAxisTitle",
			"0",
			"0",
			"", 
	""}; 


	public AbstractCart(String cartName){
		setAllDefaultValue();	
		this.cartName = cartName;	
		readDataCart();
	}

	/**
	 * Functions that set all default values in dataMap
	 * 
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#dataMap
	 */
	private void setAllDefaultValue(){
		cartName = "";
		dataCart = new ArrayList<String>();
		cartScript = null;
		dataMap = new HashMap<String, String>();

		for(int i=0; i<propertiesDefaultValue.length; i++){
			setProperty(properties[i], propertiesDefaultValue[i]);
		}
	}

	/**
	 * Functions that read the cart html script in the path folder.
	 * It writes each line of the html script in an element of dataCart
	 * 
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#dataCart
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#pathToCartFiles
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#extensionFiles
	 */
	private void readDataCart() {	
		BufferedReader reader;
		try {
			String pathToFile = pathToCartFiles+"/"+getCartName()+extensionFiles;

			reader=new BufferedReader(new FileReader(pathToFile));
			String line;
			boolean exit = false;
			while(!exit && ((line=reader.readLine())!=null)){
				dataCart.add(line+"\n");
			} 

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}	
	}

	/**
	 * Functions that replace the specified placeholder in dataCat
	 * with the value specified in dataMap.
	 * It writes each line of the html script in an element of dataCart
	 * 
	 * @param toReplace the string placeholder to replace
	 * @param replacement the string replacement for placeholder
	 * 
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#dataCart
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#dataMap
	 */
	private boolean replaceInData(String toReplace, String replacement){
		boolean find = false;
		for(int i=0; i<dataCart.size(); i++){
			if(dataCart.get(i).contains(toReplace)){
				String replace = dataCart.get(i).replace(toReplace, replacement);
				dataCart.set(i, replace);
				find = true;
			}
		}
		return find;
	}

	/**
	 * Functions that read the dataMap and replace
	 * all placeholder with the correspondents values.
	 * 
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#dataMap
	 */
	private void replaceAllPlaceholders(){	
		for(int i=0; i<properties.length; i++){
			this.replaceInData(properties[i], getProperty(properties[i]));	
		}
	}

	/**
	 * Functions that build the final cart.
	 * 
	 * Once all util data is setted the call to this 
	 * function replace all placeholders with real data.
	 * If no data is setted, default values will be used.
	 * 
	 */
	private String buildFinalCart(){
		replaceAllPlaceholders();

		cartScript = new StringBuilder("");
		for(int i=0; i<dataCart.size(); i++){
			cartScript.append(dataCart.get(i));
		}

		return cartScript.toString();
	}

	/* ******************* */
	/*	  SET AND GET	   */
	/* ******************* */
	public StringBuilder getCartScript() {
		if(cartScript==null){
			buildFinalCart();
		}
		return cartScript;
	}

	public void setCartScript(StringBuilder cartScript) {
		this.cartScript = cartScript;
	}

	public String getCartName() {
		return cartName;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	} 

	/**
	 * Functions that set the value to specified key in dataMap.
	 * 
	 * @param key the key value
	 * @param value the value for the key
	 */
	public void setProperty(String key, String value){
		dataMap.put(key, value);
	}

	/**
	 * Functions that return the value of the specified key in dataMap.
	 * 
	 * @param key the key value
	 * 
	 * @see it.unical.classmanager.statistics.cart.AbstractCart#dataMap
	 * 
	 * @return the value of the key
	 */
	public String getProperty(String key){
		if(dataMap.containsKey(key)){
			return dataMap.get(key);
		}
		return null;
	}
}
