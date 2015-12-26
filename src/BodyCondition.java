import java.util.List;

public class BodyCondition extends Block {
	private List<Block> body;
	private String condition;
	private String blockType;
	
	public BodyCondition(String blockType, String condition, List<Block> body) {
		this.blockType = blockType;
		this.condition = condition;
		this.body = body;
	}

	@Override
	public void print(int indent) {
		System.out.println(getTabs(indent) + blockType + " {");
		for (Block item : body) {
			item.print(indent + 1);
		}
		System.out.println(getTabs(indent) + "} while (" + condition + ");");
	}

}
