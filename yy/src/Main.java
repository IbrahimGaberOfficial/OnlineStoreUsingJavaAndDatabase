import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
	public static Scanner Main_Sacnner = new Scanner(System.in);

	public static void main(String[] args) {

		MySystem.PrintColoredText.printGreenText(" -------------------------------------------------------\n" +
				"\n" +
				"Welcome To Story website, we wish you a nice day.\n" +
				"\n" +
				"Start your buying story.\n" +
				"\n" +
				"------------------------------------------------------", "\n");
		MySystem.MainSystemOperations.runSystem();

		Main_Sacnner.close();
	}


}