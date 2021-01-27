package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;

	Random random = new Random();

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			LocalDate date = getLocalDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, LocalDate date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber(){return random.nextInt(999999999);}

	private LocalDate getLocalDate(String monthString,
								   String yearString) {
		int month = Integer.parseInt(monthString);
		int year = Integer.parseInt(yearString);
		LocalDate init = LocalDate.of(year, month, 1);
		return init.withDayOfMonth(init.lengthOfMonth());
	}

	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();

		if (name == null || name.equals("") || name.length() > 45 || name.length()<4) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		String address = customerForm.getAddress();

		if(address == null || address.equals("") || address.length()>45 || address.length()<4){
			throw new ApiException.InvalidParameter("Invalid address field");
		}

		String phone = customerForm.getPhone();
		String phoneNum = phone.replaceAll("[()\\s-]+","");

		if(phone == null || phone.equals("") || phoneNum.length() != 10){
			throw new ApiException.InvalidParameter("Invalid phone field");
		}

		String email = customerForm.getEmail();

		if(email == null || email.equals("") ||!email.contains("@") || email.charAt(email.length() - 1) == '.'){
			throw new ApiException.InvalidParameter("Invalid email field");
		}

		String ccNumber = customerForm.getCcNumber();
		String ccNum = ccNumber.replaceAll("[\\s-]+", "");

		if(ccNumber == null || ccNum.length()< 14 || ccNum.length()>16){
			throw new ApiException.InvalidParameter("Invalid credit cart number field");
		}

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");

		}
	}


	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {
		try {
			if(YearMonth.of(Integer.parseInt(ccExpiryYear),Integer.parseInt(ccExpiryMonth)).compareTo(YearMonth.now())<0){
				return true;
			}
			else{
				return false;
			}
		}
		catch(NumberFormatException e){
			throw new NumberFormatException();
		}
	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}

			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if(databaseBook.getPrice() != item.getBookForm().getPrice()){
				String s = Integer.toString(databaseBook.getPrice()) +"Invalid price"+ Integer.toString(item.getBookForm().getPrice());
				throw new ApiException.InvalidParameter("Invalid price");
			}

			if(databaseBook.getCategoryId() != item.getBookForm().getCategoryId()){
				throw new ApiException.InvalidParameter("Invalid category");
			}
		});
	}

}
