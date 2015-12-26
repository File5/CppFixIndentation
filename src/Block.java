
public abstract class Block {
	
	public abstract void print(int indent);
	
	protected String getTabs(int indent) {
		StringBuilder tabs = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			tabs.append("    ");
		}
		return tabs.toString();
	}

}
