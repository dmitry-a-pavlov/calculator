package calculator;

import static calculator.Utils.DIV_TOKEN;

/**
 * Div expression
 * 
 * @author Dmitriy Pavlov
 */
public class DivNode extends TwoOperandsNode implements Expression {

	public DivNode(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return super.toString(DIV_TOKEN);
	}
	
	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}

}
