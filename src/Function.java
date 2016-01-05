import java.util.List;

public class Function extends Block {
	private String declaration;
	private List<Block> body;

	public Function(String declaration, List<Block> body) {
		this.declaration = declaration;
		this.body = body;
	}

	@Override
	public void print(int indent) {
		System.out.println(declaration + " {");
		for (Block item : body) {
			item.print(indent + 1);
		}
		System.out.println(getTabs(indent) + "}");
	}

}
