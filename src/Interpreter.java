import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Interpreter {
	private static Set<String> keywords;
	private String code;
	private int pos;
	private int len;
	
	static {
		keywords = new HashSet<String>();
		keywords.add("for");
		keywords.add("switch");
		keywords.add("if");
		keywords.add("while");
		keywords.add("do");
	}
	
	private static boolean isKeyword(String str) {
		return keywords.contains(str);
	}
	
	public Interpreter(String code) {
		this.code = code;
		this.pos = 0;
		this.len = code.length();
	}
	
	private char getCurrentChar() {
		return code.charAt(pos);
	}
	
	private void nextChar() {
		pos++;
	}
	
	private char peekNextChar() {
		return code.charAt(pos + 1);
	}
	
	private boolean peekElse() {
		skipWhitespaces();
		StringBuilder keyword = new StringBuilder();
		int i = pos;
		char current = getCurrentChar();
		while (!isEnd() && i - pos < 4) {
			keyword.append(current);
			String blockType = keyword.toString();
			// if it is keyword with no-letter char next to it
			if (blockType.equals("else") && !Character.isLetter(code.charAt(i + 1))) {
				return true;
			}
			i++;
			current = code.charAt(i);
		}
		return false;
	}
	
	private void skipElse() {
		if (peekElse()) {
			for (int i = 0; i < 4; i++) {
				nextChar();
			}
		}
	}
	
	private void skipSemicolon() {
		skipWhitespaces();
		if (getCurrentChar() == ';') {
			nextChar();
		}
	}
	
	private boolean isEnd() {
		return pos >= len - 1;
	}
	
	private void skipWhitespaces() {
		while (Character.isWhitespace(getCurrentChar()) && !isEnd()) {
			nextChar();
		}
	}
	
	public void analyze() {
		while (!isEnd()) {
			List<Block> b = readStatement();
			for (Block item : b) {
				item.print(0);
			}
		}
	}
	
	private List<Block> readBlock(String blockType) {
		List<Block> blocks = new LinkedList<Block>();
		if (blockType.equals("do")) {
			List<Block> body = new LinkedList<Block>();
			body.addAll(readStatement());
			String condition = readCondition();
			skipSemicolon();
			blocks.add(new BodyCondition("do", condition, body));
		} else {
			String condition = readCondition();
			List<Block> body = new LinkedList<Block>();
			body.addAll(readStatement());
			if (blockType.equals("if") && peekElse()) {
				skipElse();
				List<Block> bodyElse = readStatement();
				blocks.add(new IfElse(condition, body, bodyElse));
			} else {
				blocks.add(new ConditionBody(blockType, condition, body));
			}
		}
		return blocks;
	}
	
	private String readCondition() {
		while (getCurrentChar() != '(') {
			nextChar();
		}
		nextChar();
		int parentheses = 1;
		StringBuilder condition = new StringBuilder();
		while (parentheses != 0) {
			char current = getCurrentChar();
			switch (current) {
			case ')':
				parentheses--;
				break;
			case '(':
				parentheses++;
				break;
			}
			condition.append(current);
			nextChar();
		}
		condition.deleteCharAt(condition.length() - 1);
		return condition.toString().trim();
	}
	
	private List<Block> readStatement() {
		// skip space chars
		skipWhitespaces();
		
		if (isEnd()) {
			List<Block> blocks = new LinkedList<Block>();
			blocks.add(new EmptyBlock());
			return blocks;
		}
		
		char current = getCurrentChar();
		
		// if it is only 1 'expression;' or keyword
		if (current != '{') {
			StringBuilder statement = new StringBuilder();
			// try to read keyword-block
			while (Character.isLetter(current)) {
				statement.append(current);
				String blockType = statement.toString();
				// if it is keyword with no-letter char next to it
				if (isKeyword(blockType) && !Character.isLetter(peekNextChar())) {
					nextChar(); // skip last keyword's letter
					return readBlock(blockType);
				}
				nextChar();
				current = getCurrentChar();
			}
			// it is 'expression;'
			while (current != ';') {
				statement.append(current);
				nextChar();
				current = getCurrentChar();
			}
			nextChar(); // skip ';'
			List<Block> blocks = new LinkedList<Block>();
			blocks.add(new Statement(statement.toString().trim() + ";"));
			return blocks;
		}
		
		// if it is {expression1; ...}
		return readCompoundStatement();
	}
	
	private List<Block> readCompoundStatement() {
		nextChar(); // skip first '{'
		skipWhitespaces();
		List<Block> blocks = new LinkedList<Block>();
		while (getCurrentChar() != '}') {
			blocks.addAll(readStatement());
			skipWhitespaces();
		}
		nextChar(); // skip }
		return blocks;
	}
	
}
