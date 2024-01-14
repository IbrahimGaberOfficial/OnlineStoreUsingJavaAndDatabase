import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Cart {
	private int productsCount;
	private float totalPrice = 0f;
	private ArrayList<Product> products;
	private HashMap<String, Integer> productsQuantity ;

	Cart() {

		products = new ArrayList<Product>();
		productsQuantity = new HashMap<>();
	}

	boolean addProduct(String product_id) {
		Product product = new Product();
		// first check from product_id
		boolean product_exsist = DataBase.ProductOperations.getProductInfo(product_id, product);
		// if right_proudct_id
		if (product_exsist  ) {
			if(productsQuantity.get(product_id) == null)
				productsQuantity.put(product_id, 1);

			// check if avilable quantity
			// >= because if prvious if statement we make value of product_id = 1, then in the next it will be increased
			// by one to become 2, so we need extra one to make the logic works well.
			if ( product.quantity > 0 && product.quantity >= productsQuantity.get(product_id)) {

				productsQuantity.put(product_id, productsQuantity.get(product_id) + 1);

				totalPrice += product.getFianlPrice();
				productsCount++;
				products.add(product);
				MySystem.PrintColoredText.printGreenText("you product " + "'" + product.name + "'" + " has been added to you carte.", "\n");
			} else {
				MySystem.PrintColoredText.printRedText("sorry there is no enough quantity right now, check it later", "\n");
			}
			return true;

		} else {
			MySystem.PrintColoredText.printGreenText("you entered invalid product ID. try again.", "\n");
			return false;
		}

	}

	boolean isEmptyCarte() {
		return products.size() <= 0;
	}

	boolean removeProdcutFromCarte(String product_id) {
		if (products.size() <= 0) {
			System.out.println("yor carte is empty.");
			return false;
		}
		int i = 0;
		for (i = 0; i < products.size(); i++) {
			if (Objects.equals(products.get(i).ID, product_id)) {
				String product_name = products.get(i).name;
				totalPrice -= products.get(i).getFianlPrice();
				productsCount--;
				products.remove(i);

				String successRemoveStr = ("product " + product_name + " was removed from you carte.");
				MySystem.PrintColoredText.printGreenText(successRemoveStr, "\n");

				return true;
			}
		}
		MySystem.PrintColoredText.printRedText("you entered invalid product ID. try again.", "\n");
		return false;
	}

	void printMyProducts() {
		for (var carteProduct : products) {
			System.out.println(carteProduct.printProductToCustomer());
		}
		System.out.println("--------------------------------");
		System.out.println("total price after discount = " + totalPrice);
		System.out.println("--------------------------------");

	}

	boolean resetCarte() {
		if (isEmptyCarte()) {
			System.out.println("\u001B[31m" + "carte is empty, can not reset carte." + "\u001B[37m");
			return false;
		} else {
			// empty the carte
			products.clear();
			totalPrice = 0;
			productsQuantity.clear();
			return true;
		}

	}

	ArrayList<String> getProductsIDs() {
		if (isEmptyCarte()) {
			System.out.println("\u001B[31m" + "Can not get you the products IDs, the carte is Empty" + "\u001B[37m");
			ArrayList<String> o = null;
			return o;

		}
		ArrayList<String> IDs = new ArrayList<String>();
		for (var product : this.products) {
			IDs.add(product.ID);
		}
		return IDs;
	}

	public float getTotalPrice() {
		return totalPrice;
	}
}
