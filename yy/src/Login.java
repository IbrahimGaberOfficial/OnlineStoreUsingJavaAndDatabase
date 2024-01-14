import com.mysql.cj.log.Log;
import com.sun.security.jgss.GSSUtil;

import java.util.Scanner;
import java.util.*;

public class Login {
	static String email, password;


	static boolean start() {

		int choice = 0;
		int try_times = 3;

		tryChooseLogin:
		while ((try_times--) > 0) {
			System.out.println("""
					--------------------------------------------------------------
					Enter you choice. 
					1- Admin login.
					2- Customer login
					3- Return.""");

			choice = MySystem.DataValidation.checkValidIntInput("Enter your choice : ");

			// admin login
			if (choice == 1) {
				setEmailAndPassword();
				return DataBase.Login.checkLogin("admins", email, password);

			} else if (choice == 2) {
				setEmailAndPassword();

				return DataBase.Login.checkLogin("customers", email, password);

			} else if (choice == 3) {
				return false;
			} else {

				MySystem.PrintColoredText.printRedText("Invalid input try again.", "\n");
			}
		}
		if (try_times <= 0)
			MySystem.PrintColoredText.printRedText("You tried many times try again later.", "\n");
		return false;
	}

	static void setEmailAndPassword() {
		Login.email = "";
		Login.password = "";

		System.out.print("""
				--------------------------------------------------------------
				Enter your info
				""");

		Login.email = MySystem.DataValidation.checkValidEmail();

		System.out.print("Enter your Password : ");
		Login.password = Main.Main_Sacnner.next();

	}


}
