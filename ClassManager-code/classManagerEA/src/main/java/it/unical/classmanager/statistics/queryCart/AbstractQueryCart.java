package it.unical.classmanager.statistics.queryCart;

import java.util.Locale;

import org.springframework.context.MessageSource;

import it.unical.classmanager.model.data.User;
import it.unical.classmanager.statistics.cart.AbstractCart;

/**
 * This Class represent a generic AbstractQueryCart.
 * 
 * @author Aloisius92
 */
public abstract class AbstractQueryCart {
	/**
	 * The cart that will contains the result of the query.
	 * 
	 * @see it.unical.classmanager.statistics.cart.AbstractCart
	 */
	protected AbstractCart cart = null;
	/**
	 * The user which query is relative.
	 */
	private User user;
	/**
	 * Reference to messageSource for internazionalizzation
	 */
	protected MessageSource messageSource;
	/**
	 * Reference to locale for internazionalizzation.
	 */
	protected Locale locale;
	/**
	 * The id of the container that will contains the cartScript.
	 */
	protected String idContainer;

	public AbstractQueryCart(){
		cart = null;
		user = null;
	}

	public AbstractQueryCart(User user){
		cart = null;
		this.user = user;
	}

	/**
	 * This is a "Factory Method", the type of cart and query 
	 * that must be performed depends by the subclass.
	 * 
	 * @return AbstractCart
	 */
	protected abstract AbstractCart buildCartFromQuery();
	
	/**
	 * This is a "Factory Method", the type of cart and query 
	 * that must be performed depends by the subclass.
	 * 
	 * @param cart the cart that will display the result of the query
	 * 
	 * @return AbstractCart
	 */
	protected abstract AbstractCart buildCartFromQuery(AbstractCart cart);

	/**
	 * Function that executes query and build the cart.
	 * 
	 * @param messageSource the messageSource
	 * @param idContainer the idContainer
	 * @param locale the locale reference
	 * 
	 * @return AbstractCart
	 */
	public AbstractCart getCart(MessageSource messageSource, String idContainer, Locale locale) {
		this.messageSource = messageSource;
		this.locale = locale;
		this.idContainer = idContainer;
		if(cart==null){
			cart = buildCartFromQuery();
			cart.getCartScript();
		}
		return cart;
	}

	/**
	 * Function that executes query and build the specified cart.
	 * 
	 * @param messageSource the messageSource
	 * @param idContainer the idContainer
	 * @param locale the locale reference
	 * @param cart the specified cart
	 * 
	 * @return AbstractCart
	 */
	public AbstractCart getCart(MessageSource messageSource, String idContainer, Locale locale, AbstractCart cart) {
		this.messageSource = messageSource;
		this.locale = locale;
		this.idContainer = idContainer;
		if(cart==null){
			cart = buildCartFromQuery(cart);
			cart.getCartScript();
		}
		return cart;
	}

	public void setCart(AbstractCart cart) {
		this.cart = cart;
	}

	public User getUser() {
		return user;
	}

	public void setUser( User user) {
		this.user = user;
	}
}
