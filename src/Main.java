import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileReader file = new FileReader("file4.cpp");
		Scanner scanner = new Scanner(file);
		//LinkedList<String> code = new LinkedList<String>();
		StringBuilder code = new StringBuilder();
		while (scanner.hasNextLine()) {
			code.append(scanner.nextLine() + '\n');
		}
		// System.out.println(code);
		Interpreter icpp = new Interpreter(code.toString());
		icpp.analyze();
//		System.out.println(Interpreter.toIOBlockString("int i = StrToInt(Edit1   ->		Text);"));
//		System.out.println(Interpreter.toIOBlockString("char d = asdasdf ->	Value;"));
//		System.out.println(Interpreter.toIOBlockString("bool b = asdfasd->\nChecked;"));
//		System.out.println(Interpreter.toIOBlockString("int a[2][2][8] = SG->Cells[0][0];"));
//		System.out.println(Interpreter.toIOBlockString("Label -> Caption = \"Suka\";"));
//		System.out.println(Interpreter.toIOBlockString("Label3->d Caption = \"Govno\""));
		scanner.close();
	}

}
