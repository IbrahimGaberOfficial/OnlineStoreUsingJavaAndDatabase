public class SignUp {

	static String name, email, phone, country, city, password;

	static void startSignUpOperation() {
		System.out.println("Start signup operation...");
		setSignupInfo();
		printInfoThatWasSet();
		if (DataBase.SignUp.checkIFAvilablePlace(email)) {
			System.out.println("------vailable email-----------");
			if (DataBase.SignUp.addNewCustomer(name, email, phone, country, city, password))
				System.out.println("New User Added. \n You can now login.");
			else
				MySystem.PrintColoredText.printRedText("Failed to add new user tyr again later", "\n");
			return;
		}

	}


	static void setSignupInfo() {

		Main.Main_Sacnner.nextLine();
		System.out.print("Enter your name : ");
		name = Main.Main_Sacnner.nextLine();

		email = MySystem.DataValidation.checkValidEmail();


		phone = MySystem.DataValidation.checkValidPhoneNumber();

		System.out.print("Enter your country : ");
		country = Main.Main_Sacnner.nextLine();

		System.out.print("Enter your city : ");
		city = Main.Main_Sacnner.nextLine();

		while (true) {
			System.out.print("Enter your password : ");
			password = Main.Main_Sacnner.nextLine();
			if (password.length() < 4) {
				MySystem.PrintColoredText.printRedText("Your password is too short make you password longer than 4 characters.", "\n");
				continue;
			} else
				break;
		}

	}

	static void printInfoThatWasSet() {
		System.out.println(
				"Name = " + name +
						"\nEmail = " + email +
						"\nPhone = " + phone +
						"\nCountyr = " + country +
						"\nCity = " + city +
						"\nPassword = " + password
		);
	}


}
