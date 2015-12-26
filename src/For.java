import java.util.List;

public class For extends ConditionBody {

	public For(String condition, List<Block> body) {
		super("for", condition, body);
	}

}
