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
		keywords.add("case");
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
	
	private boolean peekWord(String word) {
		skipWhitespaces();
		StringBuilder keyword = new StringBuilder();
		int len = word.length();
		int i = pos;
		char current = getCurrentChar();
		while (!isEnd() && i - pos < len) {
			keyword.append(current);
			String blockType = keyword.toString();
			// if it is keyword with no-letter char next to it
			if (blockType.equals(word) && !Character.isLetter(code.charAt(i + 1))) {
				return true;
			}
			i++;
			current = code.charAt(i);
		}
		return false;
	}
	
	private boolean peekElse() {
		return peekWord("else");
	}
	
	private boolean peekCase() {
		return peekWord("case") || peekWord("default");
	}
	
	private void skipElse() {
		if (peekElse()) {
			for (int i = 0; i < 4; i++) {
				nextChar();
			}
		}
	}
	
	private void skipCase() {
		if (peekWord("case")) {
			for (int i = 0; i < 4; i++) {
				nextChar();
			}
		} else if (peekWord("default")) {
			for (int i = 0; i < 7; i++) {
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
	
	private Case readCase() {
		skipWhitespaces();
		// skip characters before 'case'
		while (!peekCase()) {
			nextChar();
		}
		StringBuilder value = new StringBuilder();
		
		skipCase();
		skipWhitespaces();
		char current = getCurrentChar();
		while (current != ':') {
			value.append(current);
			nextChar();
			skipWhitespaces();
			current = getCurrentChar();
		}
		nextChar(); // skip ':'
		// skipWhitespaces();
		List<Block> bodyCase = readCompoundStatement();
		return new Case(value.toString(), bodyCase);
	}
	
	private List<Case> readCases() {
		skipWhitespaces();
		nextChar(); // skip first '{'
		List<Case> blocks = new LinkedList<Case>();
		while (getCurrentChar() != '}') {
			blocks.add(readCase());
			skipWhitespaces();
		}
		nextChar(); // skip }
		return blocks;
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
		} else if (blockType.equals("switch")) {
			String condition = readCondition();
			List<Case> body = new LinkedList<Case>();
			body.addAll(readCases());
			blocks.add(new Switch(condition, body));
		} else if (blockType.equals("case")) {
			Case block = readCase();
			blocks.add(block);
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
			if (peekCase()) {
				List<Block> blocks = new LinkedList<Block>();
				blocks.add(new EmptyBlock());
				return blocks;
			}
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
		nextChar(); // skip first '{'
		List<Block> blocks = readCompoundStatement();
		nextChar(); // skip }
		return blocks;
	}
	
	private List<Block> readCompoundStatement() {
		skipWhitespaces();
		List<Block> blocks = new LinkedList<Block>();
		while (getCurrentChar() != '}') {
			blocks.addAll(readStatement());
			if (blocks.get(blocks.size() - 1) instanceof EmptyBlock) {
				return blocks;
			}
			skipWhitespaces();
		}
		return blocks;
	}
	
}
