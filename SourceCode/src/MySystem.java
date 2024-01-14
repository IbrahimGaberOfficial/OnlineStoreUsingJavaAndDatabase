public class MySystem {
	static String whoMadeLogin = "not set";
	static Customer myCustomer = new Customer();
	static Admin admin = new Admin();


	static class MainSystemOperations {
		static public void runSystem() {
			boolean keepLoopWork = true;
			while (keepLoopWork) {

				keepLoopWork = listProductsOrLoginOrSignup();
				if (whoMadeLogin.equals("customer") && keepLoopWork) {

					CustomerOperations.customerOperations();
				} else if (whoMadeLogin.equals("admin") && keepLoopWork) {
					AdminOperations.adminOperations();
				} else {
					System.out.print("[MySystem]<-(runSystem) Data base connection closed.\n");

					try {
						DataBase.connnection.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.print("[MySystem]<-(runSystem) program ended.\n ");
				}
			}
		}

		public static boolean listProductsOrLoginOrSignup() {
			int choice = 0;
			afterListProductOrLogin:
			while (true) {

				System.out.println("""
						-------------------------------------------------------
						1- Login.
						2- Signup.
						3- List customer products.
						4- Exit.""");
				choice = DataValidation.checkValidIntInput("Enter your choice : ");
				if (choice == 1) {
					boolean isValidLogin = Login.start();

					if (!isValidLogin) {
						MySystem.PrintColoredText.printRedText("User not found. Invalid email or password, try again.", "\n");
						continue;
					}
					return true;

				} else if (choice == 2) {
					SignUp.startSignUpOperation();
				} else if (choice == 3) {
					DataBase.ProductOperations.listProducts(6);
				} else if (choice == 4) {
					return false;
				} else {
					System.out.println("Invalid input.");
				}

			}


		}

		static void setWhoMadeLogin(String set_who_made_login) {
			whoMadeLogin = set_who_made_login;
		}
	}

	class PrintColoredText {
		static void printGreenText(String str, String newlineOrSpace) {
			System.out.println("\u001B[32m" + str + newlineOrSpace + "\u001B[37m");
		}

		static void printRedText(String str, String newlineOrSpace) {
			System.out.println("\u001B[31m" + str + newlineOrSpace + "\u001B[37m");
		}

	}

	// check int input
	static class DataValidation {

		public static int checkValidIntInput(String message) {
			System.out.print(message);
			int returnValueAfterSuccessInput;
			while (!Main.Main_Sacnner.hasNextInt()) {
				MySystem.PrintColoredText.printRedText("Invalid input Please enter an intger ", "\n");
				Main.Main_Sacnner.nextLine();
				System.out.print(message);
			}
			returnValueAfterSuccessInput = Main.Main_Sacnner.nextInt();
			return returnValueAfterSuccessInput;
		}

		public static float checkValidFloatInput(String message) {
			System.out.print(message);
			float returnValueAfterSuccessInput;

			while (!Main.Main_Sacnner.hasNextFloat()) {
				MySystem.PrintColoredText.printRedText("Invalid input Please enter an float value.", "\n");
				Main.Main_Sacnner.nextLine();
				System.out.print(message);
			}
			returnValueAfterSuccessInput = Main.Main_Sacnner.nextFloat();
			return returnValueAfterSuccessInput;
		}

		public static String checkValidEmail() {

			String mailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";
			String returnMail;

			Main.Main_Sacnner.nextLine(); // empty the string stream

			while (true) {
				System.out.print("Enter you mail : ");
				returnMail = Main.Main_Sacnner.nextLine();
				if (returnMail.matches(mailPattern)) {
					break;
				}
				System.out.println();
				MySystem.PrintColoredText.printRedText("inValid email pattern, input valid email pattern", "\n");


			}
			return returnMail;
		}

		public static String checkValidPhoneNumber() {
			String validPhoneNumber;
			while (true) {
				System.out.print("Enter your phone : ");
				validPhoneNumber = Main.Main_Sacnner.nextLine();
				if (isValidPhoneNumber(validPhoneNumber))
					return validPhoneNumber;
				else {
					System.out.print("""
							--------------------------------------------------------------
							Invalid phone number.
							--------------------------------------------------------------
							""");
					continue;
				}
			}

		}

		private static boolean isValidPhoneNumber(String phoneNumber) {
			// Define a simple pattern for a valid international phone number
			// Modify this pattern based on your specific requirements
			String phonePattern = "^\\+[0-9]+$";

			// Compile the pattern
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(phonePattern);

			// Use the pattern to match the input phone number
			java.util.regex.Matcher matcher = pattern.matcher(phoneNumber);

			// Return true if the phone number matches the pattern
			return matcher.matches();
		}
	}


	static class CustomerOperations {
		static void setCustomerValues(String ID, String name, String email, String phone, String country, String city, String password) {
			myCustomer.setValues(ID, name, email, phone, country, city, password);
		}


		static void customerOperations() {

			while (true) {
				System.out.println("""
						----------------------------------------------------------------
						Enter number of wanted operation:
						1- List products.
						2- List Information of Product by product ID.
						3- Add product to carte.
						4- Remove product from carte.
						5- List my carte 's products.
						6- Make payment .
						7- List my information.
						10- Logout.
						""");

				int choice = DataValidation.checkValidIntInput("Enter you choice : ");

				// list products
				if (choice == 1) {
					myCustomer.listProducts();
				}
				// 2 add product to carte
				else if (choice == 2) {
					System.out.print("Enter product ID : ");
					Main.Main_Sacnner.nextLine();
					String productID = Main.Main_Sacnner.nextLine();
					ProductManager.printProductInfo(productID, "customer");

				} else if (choice == 3) {
					System.out.print("to add product to carte, please enter product ID : ");
					Main.Main_Sacnner.nextLine();
					String product_id = Main.Main_Sacnner.nextLine();
					myCustomer.addProductToCart(product_id);
				}
				// 3 remove product from carte
				else if (choice == 4) {
					if (myCustomer.my_cart.isEmptyCarte()) {
						System.out.print("""
								--------------------------------------------------------------
								Your carte is Empty. there is no products to remove.
								--------------------------------------------------------------
								""");
						continue;
					}

					System.out.print("to remove product to carte, please enter product ID : ");
					Main.Main_Sacnner.nextLine();
					String product_id = Main.Main_Sacnner.nextLine();
					myCustomer.removeProductFromCart(product_id);
				} else if (choice == 5) {
					if (myCustomer.my_cart.isEmptyCarte()) {
						System.out.print("""
								--------------------------------------------------------------
								Your carte is Empty. there is no products to view.
								--------------------------------------------------------------
								""");
						continue;
					}
					myCustomer.printMyCart();
				} else if (choice == 6) {
					if (myCustomer.my_cart.isEmptyCarte()) {
						System.out.print("""
								--------------------------------------------------------------
								your care is empty add some products to could pay for them.
								--------------------------------------------------------------
								""");
						continue;
					}
					myCustomer.doPayment();
				}
				// 6- print customer info
				else if (choice == 7) {
					System.out.println(myCustomer.toString());
				} else if (choice == 10) {
					return;
				}
			}

		}
	}

	public static class AdminOperations {
		static void setAdminInfo(String ID, String name, String mail, String password) {
			admin.setAdminInfo(ID, name, mail, password);
		}

		static void adminOperations() {
			while (true) {

				System.out.println("""
						------------------------------------------------------
						Admin operations.
						1- List product full info.  
						2- Add new product to database.
						3- Modify product info into database.
						4- Delete product from database.
						5- Print my account info.
						10- Logout.""");

				int choice = DataValidation.checkValidIntInput("Enter you choice : ");
				// 1- list product full info
				if (choice == 1) {
					admin.listProductsFullInfo();
				}
				// 2 - add new product to database
				else if (choice == 2) {
					admin.addNewProduct();
				}
				// 3 - modify product info into database
				else if (choice == 3) {
					admin.updateProductInfo();
				}
				// 4 - Delete product from database.
				else if (choice == 4) {
					admin.deleteProduct();
				} else if (choice == 5) {
					System.out.println(admin.toString());
				}
				// 10 Logout.
				else if (choice == 10) {
					return;
				}
			}
		}
	}


}
