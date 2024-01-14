public class ProductManager {

	void listProductsForAdmin() {
		DataBase.ProductManipulation.listProductFullInof();
	}

	public boolean addNewProduct() {
		Product product = new Product();
		getProductInfo(product);
		return DataBase.ProductManipulation.addNewProduct(product);
	}

	// printing choices
	int printChoicesFor() {
		int choice;
		while (true) {
			System.out.print("""
					Select your choice
					1- Enter product ID.
					2- Return.
					""");
			Main.Main_Sacnner.nextLine();
			choice = MySystem.DataValidation.checkValidIntInput("Enter you choice : ");
			if (choice != 1 && choice != 2) {
				MySystem.PrintColoredText.printRedText("invalid input enter 1 - 2.", "\n");
			}
			Main.Main_Sacnner.nextLine();
			return choice;
		}

	}

	static void printProductInfo(String productID, String whoCallMe) {
		String prodctIDLocal;
		while (true) {
			Product product = new Product();
			boolean isValidProductID = DataBase.ProductOperations.getProductInfo(productID, product);
			if (isValidProductID) {
				System.out.println("Info of The product you want ." +
						"\nID = " + product.ID +
						"\nName = " + product.name +
						"\nprice = " + product.price +
						"\nQuantity = " + product.quantity +
						"\nCategory = " + product.category +
						"\ndiscount = " + product.discount);
				if (whoCallMe == "Admin") {
					System.out.println(
							"\nrevenue = " + product.revenue +
									"\nsold quantity = " + product.sold_quantity);
				}
				MySystem.PrintColoredText.printGreenText("Valid product ID, continue the operation ...", "\n");
				break;
			} else {

				MySystem.PrintColoredText.printRedText("Invalid product ID", "\n");
				break;

			}
		}
	}

	public void updateProductInfo() {

		String productID;
		int choice = 0;

		choice = printChoicesFor();
		if (choice == 2)
			return;

		// validation for provided ID;
		productID = getProductID("edit");
		printProductInfo(productID, "Admin");


		// get modification choice and modification value
		String updateInfo;
		float value;

		while (true) {
			System.out.println("""
					what do you want to change in product ?
					1- Price
					2- Discount.
					3- Quantity.
					4- Return.
					""");
			choice = MySystem.DataValidation.checkValidIntInput("Enter your Choice : ");
			// price
			if (choice == 1) {
				updateInfo = "Price";
				value = MySystem.DataValidation.checkValidFloatInput("Enter new price : ");
				break;
			}
			// Discount
			else if (choice == 2) {
				updateInfo = "discount";
				value = MySystem.DataValidation.checkValidFloatInput("Enter new discount in percent (%): ");
				value = (float) value / 100;
				break;
			}
			// Quantity
			else if (choice == 3) {
				updateInfo = "quantity";
				value = (float) MySystem.DataValidation.checkValidIntInput("Enter new quantity : ");
				break;
			} else if (choice == 4) {
				return;
			} else {
				System.out.println("Invalid input, please enter number between 1 - 4 (included)");
				continue;
			}
		}
		boolean productUpdated = DataBase.ProductManipulation.upadateProductInfo(productID, updateInfo, value);
		System.out.println(productUpdated ? "Product Updated" : "product Not updated.");

	}

	void deleteProduct() {
		String productID;
		int choice = 0;

		choice = printChoicesFor();
		if (choice == 2)
			return;

		// validation for provided ID;
		productID = getProductID("delete ");
		printProductInfo(productID, "Admin");

		System.out.println("Do you really want to delete this product ? ");
		choice = MySystem.DataValidation.checkValidIntInput("""
				1- Yes.
				2- No.
				Enter your choice : """);
		if (choice == 1) {
			boolean deletingResult = DataBase.ProductManipulation.DeleteProduct(productID);
			if (deletingResult)
				MySystem.PrintColoredText.printGreenText("Product Deleted.", "\n");
			else
				MySystem.PrintColoredText.printRedText("Failed to delete product", "\n");
		}
		return;
	}

	private String getProductID(String action) {
		String productID;
		System.out.print("Enter ID for the product you want to " + action + " : ");
		productID = Main.Main_Sacnner.nextLine();
		return productID;
	}

	private void getProductInfo(Product product) {
		int productQuantity, soldQuantity;
		float productRevenue, productPrice, productDiscount;
		String productID, productName, Category;
		System.out.println("start adding new Product operation.");
		while (true) {

			System.out.print("Enter uniqe Product ID : ");
            Main.Main_Sacnner.nextLine();
			productID = Main.Main_Sacnner.nextLine();

			if (DataBase.ProductOperations.getProductInfo(productID, product)) {

				MySystem.PrintColoredText.printRedText("There is already a product with same ID, Enter anothe ID", "\n");
				continue;
			}

			System.out.print("Enter Product name  : ");
			productName = Main.Main_Sacnner.nextLine();

			productPrice = MySystem.DataValidation.checkValidFloatInput("Enter product price : ");

			productQuantity = MySystem.DataValidation.checkValidIntInput("Enter product quantity : ");


			Category = getProductCategory();

			productDiscount = MySystem.DataValidation.checkValidFloatInput("Enter product discount in percent % : ");
			productDiscount = productDiscount / 100;
			productRevenue = MySystem.DataValidation.checkValidFloatInput("Enter prduct revenue : ");
			soldQuantity = MySystem.DataValidation.checkValidIntInput("Ente sold quantity : ");

			product.setValues(productID, productName, productPrice, productQuantity, Category, productDiscount, productRevenue, soldQuantity);
			break;

		}

	}

	String getProductCategory() {

		while (true) {
			System.out.println("""
					---------------------------------------------------------------------------------------------
					Enter Product category
					1-Home.
					2-Health.
					3-Entertainment.
					4-Electronics.
					5-Clothing.
					6-Beauty.
					7-Automotive.
					8-Activity
					""");
			int choice = 0;
			choice = MySystem.DataValidation.checkValidIntInput("Enter category choice : ");
			if (choice == 1)
				return "Home";
			else if (choice == 2)
				return "Health";
			else if (choice == 3)
				return "Entertainment";

			else if (choice == 4)
				return "Electronics";
			else if (choice == 5)
				return "Clothing";

			else if (choice == 6)
				return "Beauty";

			else if (choice == 7)
				return "Automotive";

			else if (choice == 8)
				return "Activity";
			else {
				MySystem.PrintColoredText.printRedText("invalid input, Enter number from 1 - 8", "\n");
				continue;
			}
		}

	}

	static int getProductQuantity(String productID){
		Product product = new Product();
		DataBase.ProductOperations.getProductInfo(productID, product);
		return product.quantity;
	}

}
