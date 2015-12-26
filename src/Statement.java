
public class Statement extends Block {
	private String expression;
	
	public Statement(String expression) {
		this.expression = expression;
	}

	@Override
	public void print(int indent) {
		System.out.println(getTabs(indent) + expression);
	}

}
