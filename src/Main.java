import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			FileReader file = new FileReader(args[0]);
			Scanner scanner = new Scanner(file);
			StringBuilder code = new StringBuilder();
			while (scanner.hasNextLine()) {
				code.append(scanner.nextLine() + '\n');
			}
			Interpreter icpp = new Interpreter(code.toString());
			icpp.deleteComments();
			icpp.analyze();
			scanner.close();
		}
	}

}
