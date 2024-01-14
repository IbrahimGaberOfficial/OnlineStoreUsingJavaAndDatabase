import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DataBase {
	static Connection connnection;
	static Statement statement;
	static ResultSet resultSet;

	static String loclahost = "",
			schemeName = "",
			username = "",
			password = "";

	public DataBase() {

	}

	private static void setDatabaseConnectionInfo() {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/DataBaseInfo/DatabaseInfo.txt"))) {

			if ((loclahost = reader.readLine()) == null) {
				MySystem.PrintColoredText.printRedText("Invalid local host. check you DatabaseInfo.txt file.", "\n");
			}
			if ((schemeName = reader.readLine()) == null) {
				MySystem.PrintColoredText.printRedText("Invalid local host. check you DatabaseInfo.txt file.", "\n");
			}
			if ((username = reader.readLine()) == null) {
				MySystem.PrintColoredText.printRedText("Invalid local host. check you DatabaseInfo.txt file.", "\n");
			}
			if ((password = reader.readLine()) == null) {
				MySystem.PrintColoredText.printRedText("Invalid local host. check you DatabaseInfo.txt file.", "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void executeQuerry(String sql_statement) {
		try {
			setDatabaseConnectionInfo();
			//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
			// to see how IntelliJ IDEA suggests fixing it.
			connnection = DriverManager.getConnection("jdbc:mysql://localhost:" + loclahost + "/" + schemeName, username, password);

			statement = connnection.createStatement();
			resultSet = statement.executeQuery(sql_statement);
//            connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static int upaateQueerry(String sql_update_statement) {
		int affected_columns = 0;
		try {
			//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
			// to see how IntelliJ IDEA suggests fixing it.
			setDatabaseConnectionInfo();
			connnection = DriverManager.getConnection("jdbc:mysql://localhost:" + loclahost + "/" + schemeName, username, password);

			statement = connnection.createStatement();
			affected_columns = statement.executeUpdate(sql_update_statement);
//            connnection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return affected_columns;
	}

	// payment operation section
	public static class Payment {
		// update products info after success payment
		static boolean updateProductTable(ArrayList<String> productsIDs) {

			int affectedColumns = 0;
			String sql_update_statement;

			for (var productID : productsIDs) {
				sql_update_statement = " UPDATE products SET quantity = quantity - 1 WHERE ID = '" + productID + "';";
				affectedColumns = upaateQueerry(sql_update_statement);
				if (affectedColumns <= 0) {
					String errorStr = "[Error] Failed to update product with ID = " + productID + ", from products table in DB";
					MySystem.PrintColoredText.printRedText(errorStr, "\n");
					return false;
				}
			}
			MySystem.PrintColoredText.printGreenText("all products updated in DB", "\n");
			return true;
		}

		// check if the carde is valid
		static boolean checkValidCard(String cardNumber, String cardCCV, String cardDate, DoubleWarapper cardBalance) {
			try {
				executeQuerry("select * from card_info where card_number = '" + cardNumber + "' and card_ccv = '" + cardCCV + "' and card_date = '" + cardDate + "';");
				if (resultSet.next()) {
					cardBalance.setDoubleVar(Float.parseFloat(resultSet.getString(5)));
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		// check if the provided card have enough balance
		static boolean checkEnoughBalance(DoubleWarapper balance, DoubleWarapper amountToPay) {
			return balance.getDoubleVar() >= amountToPay.getDoubleVar();
		}

		// starting the withdrawal operation and update the card with the new balance
		static boolean withDrawFromCard(String cardNumber, String cardCCV, String cardDate, DoubleWarapper cardBalance, DoubleWarapper amountToPay) {
			double newBalance = cardBalance.getDoubleVar() - amountToPay.getDoubleVar();
			System.out.format("Your balance = %.0f\n", cardBalance.getDoubleVar());
			System.out.format("needed to be paid =  %.0f\n", amountToPay.getDoubleVar());
			System.out.format("new balance must =  %.0f\n", newBalance);
			// update the card balnce in DB with newBalnce


			String sql_update_statement = "UPDATE card_info SET card_balance = card_balance - " + amountToPay.getDoubleVar() + " WHERE card_number = '" + cardNumber + "';";
			int afected_columns = upaateQueerry(sql_update_statement);
			return afected_columns > 0;


		}

		// start the payment operation in DB
		static boolean doPayment(String cardNumber, String cardCCV, String cardDate, DoubleWarapper amountToPay, StrWrapper paymentResult) {
			boolean isValidCard, haveEnoughBalance, isSuccessPayment;
			DoubleWarapper cardBalance = new DoubleWarapper();
			isValidCard = checkValidCard(cardNumber, cardCCV, cardDate, cardBalance);
			if (!isValidCard) {
				paymentResult.setStr("Invalid card info.");
				return false;
			}
			haveEnoughBalance = checkEnoughBalance(cardBalance, amountToPay);
			if (!haveEnoughBalance) {
				paymentResult.setStr("Do not have enough balance.");
				return false;
			}
			isSuccessPayment = withDrawFromCard(cardNumber, cardCCV, cardDate, cardBalance, amountToPay);
			if (!isSuccessPayment) {
				paymentResult.setStr("Can not withdraw from your card.");
				return false;
			}
			return true;

		}
	}

	public static class SignUp {
		static boolean checkIFAvilablePlace(String email) {
			try {
				executeQuerry("select * from customers  where email = '" + email + "';");
				if (resultSet.next())
					return false;
				else
					return true;
			} catch (Exception e) {

			}
			return false;
		}

		static boolean addNewCustomer(String name, String email, String phone, String country, String city, String password) {
			String sqlStatementAddNewCustomer = "INSERT INTO `onlinestore`.`customers` " +
					"(`name`, `email`, `phone`, `country`, `city`, `password`)  values " +
					"( '" + name + "', " + "'" + email + "', '" + phone + "', " + "'" + country + "', " + "'" + city + "', " + "'" + password + "');";
			int affectedColums = upaateQueerry(sqlStatementAddNewCustomer);
			if (affectedColums > 0)
				return true;
			else
				return false;
		}
	}

	// customer and admin login checking class
	public static class Login {
		static boolean checkLogin(String login_type, String email, String password) {
			try {
				executeQuerry("select * from " + login_type + " where email = '" + email + "' and password = '" + password + "';");
				if (resultSet.next()) {
					if (login_type.equals("customers")) {
						MySystem.CustomerOperations.setCustomerValues(resultSet.getString(1),
								resultSet.getString(2),
								resultSet.getString(3),
								resultSet.getString(4),
								resultSet.getString(5),
								resultSet.getString(6),
								resultSet.getString(7));
						MySystem.MainSystemOperations.setWhoMadeLogin("customer");
						return true;
					} else if (login_type.equals("admins")) {
						MySystem.MainSystemOperations.setWhoMadeLogin("admin");
						MySystem.AdminOperations.setAdminInfo(resultSet.getString(1),
								resultSet.getString(2),
								resultSet.getString(3),
								resultSet.getString(4));
						return true;
					}
				} else {
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	// product operation
	public class ProductOperations {
		static boolean getProductInfo(String product_id, Product product) {
			try {
				executeQuerry("select * from products  where ID = '" + product_id + "';");
				if (resultSet.next()) {
					product.setValues(resultSet.getString(1),
							resultSet.getString(2),
							Float.parseFloat(resultSet.getString(3)),
							Integer.parseInt(resultSet.getString(4)),
							resultSet.getString(5),
							Float.parseFloat(resultSet.getString(6)),
							Float.parseFloat(resultSet.getString(7)),
							Integer.parseInt(resultSet.getString(8))

					);
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		static void print_products(int columns_to_display) {
			try {
				// meta data
				{
					var meta_data = resultSet.getMetaData();
					for (int i = 1; i <= columns_to_display; i++) {
						String column_name = meta_data.getColumnName(i);
						if (i == 1) {
							System.out.format("%-10s", column_name);
							continue;
						}
						if (i == 2) {
							System.out.format("%-30s", column_name);
							continue;
						}
						System.out.format("%-20s", column_name);

					}
					System.out.println("\n\n");
				}
				while (resultSet.next()) {

					for (int i = 1; i <= columns_to_display; i++) {
						if (i == 1) {
							System.out.format("%-10s ", resultSet.getString(i));
							continue;
						}
						if (i == 2) {
							System.out.format("%-30s ", resultSet.getString(i));
							continue;
						}
						if (i == 6) {
							System.out.format("%-20s ", Float.parseFloat(resultSet.getString(i)) * 100 + "%");
							continue;
						}
						System.out.format("%-20s ", resultSet.getString(i));

					}
					//System.out.print( + ", ");

					System.out.println();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		static void listProducts(int col_n_display) {

			boolean continue_loop = true;
			int choice = 0, try_number = 3;


			while ((try_number--) > 0 && continue_loop) {
				System.out.println("""
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
				choice = MySystem.DataValidation.checkValidIntInput("Enter category number : ");
				if (choice == 1) {
					executeQuerry("select * from products where Category like 'Home'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 2) {
					executeQuerry("select * from products where Category like 'Health'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 3) {
					executeQuerry("select * from products where Category like 'Entertainment'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 4) {
					executeQuerry("select * from products where Category like 'Electronics'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 5) {
					executeQuerry("select * from products where Category like 'Clothing'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 6) {
					executeQuerry("select * from products where Category like 'Beauty'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 7) {
					executeQuerry("select * from products where Category like 'Automotive'");
					continue_loop = false;
					print_products(col_n_display);
				} else if (choice == 8) {
					executeQuerry("select * from products where Category like 'Activity'");
					continue_loop = false;
					print_products(col_n_display);
				} else {
					System.out.println("Invalid input.\n");
					continue_loop = true;
				}


			}

		}
	}


	public class ProductManipulation {
		static void listProductFullInof() {
			ProductOperations.listProducts(8);

		}

		static boolean addNewProduct(Product product) {
			String sqlAddingNewProductStatement = "INSERT INTO products (ID, name, price, quantity, category, discount, revenue, sold_quantity) " +
					" values ( '" + product.ID + "', '" + product.name + "', " +
					product.price + ", " + product.quantity + ", '" + product.category + "', " + product.discount +
					", " + product.revenue + "," + product.sold_quantity + "); ";

			System.out.println(sqlAddingNewProductStatement);
			int affectedColumns;
			affectedColumns = upaateQueerry(sqlAddingNewProductStatement);

			return affectedColumns > 0;
		}

		static boolean upadateProductInfo(String prodcutID, String updateInfo, float value) {
			String sqlStatementUpdateProductInfo = "update products set " + updateInfo + " = " + value + " where ID = '" + prodcutID + "';";
			try {
				int affectedRows = upaateQueerry(sqlStatementUpdateProductInfo);
				if (affectedRows > 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		static boolean DeleteProduct(String productID) {
			String sqlStatementToDeleteProduct = "delete from products where ID = '" + productID + "';";
			try {
				int affectedColumns = upaateQueerry(sqlStatementToDeleteProduct);
				if (affectedColumns > 0)
					return true;
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}


	}

}
