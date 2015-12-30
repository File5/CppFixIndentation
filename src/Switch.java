import java.util.List;

public class Switch extends Block {
	private String condition;
	private List<Case> cases;

	public Switch(String condition, List<Case> cases) {
		this.condition = condition;
		this.cases = cases;
	}

	@Override
	public void print(int indent) {
		System.out.println(getTabs(indent) + "switch (" + condition + ") {");
		for (Case item : cases) {
			item.print(indent);
		}
		System.out.println(getTabs(indent) + "}");
	}

}
