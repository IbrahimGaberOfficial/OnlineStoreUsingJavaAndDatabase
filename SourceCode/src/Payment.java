import java.util.ArrayList;

public class Payment {
//    Card card = new Card();

	static private StrWrapper paymentResult = new StrWrapper();
	static private ArrayList<String> productsIDs = new ArrayList<String>();

	static public boolean startPayment(Cart cart) {
		StrWrapper cardNumber = new StrWrapper();
		StrWrapper cardCCV = new StrWrapper();
		StrWrapper cardDate = new StrWrapper();
		DoubleWarapper amountToPay = new DoubleWarapper();
		amountToPay.setDoubleVar(cart.getTotalPrice());

		getPaymentInfo(cardNumber, cardDate, cardCCV);
		System.out.println("Start payment operation...");
		boolean do_payment = DataBase.Payment.doPayment(cardNumber.getStr(), cardCCV.getStr(), cardDate.getStr(), amountToPay, paymentResult);
		if (do_payment) {
			System.out.println("-success withdraw");

			// update the products database
			// get products IDs to update database
			productsIDs = cart.getProductsIDs();
			boolean isProductsTableUpdated = DataBase.Payment.updateProductTable(productsIDs);
			if (isProductsTableUpdated) {
				MySystem.PrintColoredText.printGreenText("Success updating products. your products in way to your home", "");
				cart.resetCarte();
				MySystem.PrintColoredText.printGreenText("Your carte was rested and is empty now", "");
				return true;
			} else {
				MySystem.PrintColoredText.printRedText("failed to update the products in the database", "\n");
			}
			// reset the Carte

			return true;
		} else {
			String errorStr = "failed payment.\n" + "Error : " + paymentResult.getStr();
			MySystem.PrintColoredText.printRedText(errorStr, "\n");
			return false;
		}


	}

	static private void getPaymentInfo(StrWrapper cardNumber, StrWrapper cardDate, StrWrapper cardCCV) {
//        String tmp;
		System.out.println("Start payment operation : \n");
		Main.Main_Sacnner.nextLine();
		System.out.print("Enter your card number : ");
		String getCarName = Main.Main_Sacnner.nextLine();
		cardNumber.setStr(getCarName);

		System.out.print("Enter your card expire date : ");
		String getCardDate = Main.Main_Sacnner.nextLine();
		cardDate.setStr(getCardDate);

		System.out.print("Enter your card CCV number  : ");
		String getCardCCV = Main.Main_Sacnner.nextLine();
		cardCCV.setStr(getCardCCV);

	}
}
