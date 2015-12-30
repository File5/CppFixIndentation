import java.util.List;

public class Case extends Block {
	private String value;
	private List<Block> body;

	public Case(String value, List<Block> bodyCase) {
		this.value = value;
		this.body = bodyCase;
	}

	@Override
	public void print(int indent) {
		String caseWord;
		if (value.length() == 0) {
			caseWord = "default";
		} else {
			caseWord = "case ";
		}
		System.out.println(getTabs(indent) + caseWord + value + ":");
		for (Block item : body) {
			item.print(indent + 1);
		}
	}

}
