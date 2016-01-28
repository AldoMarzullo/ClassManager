package it.unical.classmanager.statistics;

import java.util.ArrayList;
import java.util.List;

import it.unical.classmanager.statistics.cart.AbstractCart;

/**
 * Helper class which represetn a list of AbstractCarts.
 *
 * @see it.unical.classmanager.statistics.cart.AbstractCart
 * 
 * @author Aloisius92
 */
public class CartsList {
	private List<AbstractCart> carts;

	public CartsList(){
		carts = new ArrayList<AbstractCart>();
	}

	public List<AbstractCart> getCarts() {
		return carts;
	}

	public void setCarts(List<AbstractCart> carts) {
		this.carts = carts;
	}
	
	public void add(AbstractCart cart){
		carts.add(cart);
	}
}
