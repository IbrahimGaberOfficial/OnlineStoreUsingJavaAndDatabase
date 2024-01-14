public class Customer extends User {
	String phone;
	String country, city;
	Cart my_cart = new Cart();

	public Customer() {
	}

	public void setValues(String ID, String name, String email, String phone, String country, String city, String password) {
		this.ID = ID;
		this.name = name;
		this.email = email;
		this.password = password;
//        super(ID, name, email, password);
		this.phone = phone;
		this.country = country;
		this.city = city;
	}

	void addProductToCart(String product_id) {

		my_cart.addProduct(product_id);
	}

	void printMyCart() {
		my_cart.printMyProducts();
	}

	void removeProductFromCart(String product_id) {
		my_cart.removeProdcutFromCarte(product_id);
	}

	boolean doPayment() {
		boolean checkIfSuccessPayment;
//        System.out.println(cardNumber.getStr() + " " + cardCCV.getStr() + " " + cardDate.getStr() + " " + amountToPay.getFlaotVar());
		checkIfSuccessPayment = Payment.startPayment(my_cart);
		return checkIfSuccessPayment;
	}


	@Override
	public String toString() {
		return "-----------------------------------------------------"+
				"\n<<Customer info : >>\n" +
				"ID = '" + ID + "'. \n" +
				"Name = '" + name + "'.\n" +
				"Email = '" + email + "'.\n" +
				"Phone = '" + phone + "'.\n" +
				"Country = '" + country + "'.\n" +
				"City = '" + city + "'.\n" +
				"Password = '" + password + "'.\n";
	}
}
