package calculator;

import static calculator.Utils.SUB_TOKEN;

/**
 * Subtraction expression
 * 
 * @author Dmitriy Pavlov
 */
public class SubNode extends TwoOperandsNode implements Expression {

	public SubNode(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return super.toString(SUB_TOKEN);
	}
	
	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}

}
