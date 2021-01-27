package business.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCart {

	private static final int SURCHARGE = 500;

	private List<ShoppingCartItem> items;

	public ShoppingCart() {
	}

	public int getSurcharge() {
		return SURCHARGE;
	}

	public List<ShoppingCartItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartItem> items) {
		this.items = items;
	}

	/**
	 * Returns the sum of the item price multiplied by the quantity for all
	 * items in shopping cart list. This is the total cost *in cents*,
	 * not including the surcharge.
	 *
	 * @return total of items in cart, excluding surcharge
	 */
	@JsonIgnore
	public int getComputedSubtotal() {
		return items.stream()
			.mapToInt(item -> item.getQuantity() * item.getBookForm().getPrice())
			.sum();
	}

}
