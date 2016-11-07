package calculator;

/**
 * Number expression
 * 
 * @author Dmitriy Pavlov
 */
public class NumberNode implements Expression {

	/*package*/ final int number;
	
	public NumberNode(String integer) {
		try {
			number = Integer.parseInt(integer);
		} catch (NumberFormatException e) {
			throw new SytaxErrorException("Integer value expected: " + integer, e);
		}
	}
	
	public NumberNode(int value) {
		number = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(number);
	}

	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}

}
