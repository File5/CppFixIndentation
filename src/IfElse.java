import java.util.List;

public class IfElse extends Block {
	private List<Block> body;
	private List<Block> bodyElse;
	private String condition;
	
	public IfElse(String condition, List<Block> body, List<Block> bodyElse) {
		this.condition = condition;
		this.body = body;
		this.bodyElse = bodyElse;
	}

	@Override
	public void print(int indent) {
		System.out.println(getTabs(indent) + "if (" + condition + ") {");
		for (Block item : body) {
			item.print(indent + 1);
		}
		System.out.println(getTabs(indent) + "} else {");
		for (Block item : bodyElse) {
			item.print(indent + 1);
		}
		System.out.println(getTabs(indent) + "}");
	}

}
