package calculator;

public abstract class TwoOperandsNode implements Expression {

	/*package*/ final Expression left;
	/*package*/ final Expression right;
	
	public TwoOperandsNode(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public String toString(String operation) {
		StringBuilder sb = new StringBuilder();
		sb.append(operation).append('(');
		sb.append(left.toString()).append(',');
		sb.append(right.toString()).append(')');
		return sb.toString();
	}
}
