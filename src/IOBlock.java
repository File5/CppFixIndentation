
public class IOBlock extends Block {
	private String expression;
	
	public IOBlock(String expression) {
		this.expression = expression;
	}

	@Override
	public void print(int indent) {
		System.out.println(getTabs(indent) + "/* I/O Block */ " + expression);
	}

}
