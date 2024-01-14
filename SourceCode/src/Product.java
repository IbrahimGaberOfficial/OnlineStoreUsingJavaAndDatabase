public class Product {
	String ID, name, category;
	float price, discount, revenue;
	int quantity, sold_quantity;

	public Product() {
	}

	public Product(String ID, String name, float price, int quantity, String category, float discount, float revenue, int sold_quantity) {
		this.ID = ID;
		this.name = name;
		this.category = category;
		this.price = price;
		this.discount = discount;
		this.revenue = revenue;
		this.quantity = quantity;
		this.sold_quantity = sold_quantity;
	}

	public void setValues(String ID, String name, float price, int quantity, String category, float discount, float revenue, int sold_quantity) {
		this.ID = ID;
		this.name = name;
		this.category = category;
		this.price = price;
		this.discount = discount;
		this.revenue = revenue;
		this.quantity = quantity;
		this.sold_quantity = sold_quantity;
	}

	public String printProductToCustomer() {
		return "Product info:\n" +
				"ID = '" + ID + '\'' + "\n" +
				"name = '" + name + '\'' + "\n" +
				"price = '" + price + '\'' + "\n" +
				"quantity = '" + quantity + '\'' + "\n" +
				"category = '" + category + '\'' + "\n" +
				"discount = '" + discount * 100 + "%" + '\'' + "\n";
	}

	public String printProductToAdmin() {
		return "Product info:\n" +
				"ID = '" + ID + '\'' + "\n" +
				"name = '" + name + '\'' + "\n" +
				"price = '" + price + '\'' + "\n" +
				"quantity = '" + quantity + '\'' + "\n" +
				"category = '" + category + '\'' + "\n" +
				"discount = '" + discount * 100 + "%" + '\'' + "\n" +
				"revenue = '" + revenue + '\'' + "\n" +
				"sold quantity = '" + sold_quantity + '\'' + "\n";

	}

	public float getFianlPrice() {
		return (price - (price * discount));
	}


}
