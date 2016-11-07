package calculator;

import static calculator.Utils.MULT_TOKEN;

/**
 * Multiply expression
 * 
 * @author Dmitriy Pavlov
 */
public class MultNode extends TwoOperandsNode implements Expression {

	public MultNode(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return super.toString(MULT_TOKEN);
	}
	
	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}

}
