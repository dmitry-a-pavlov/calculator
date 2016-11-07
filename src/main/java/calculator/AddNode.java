package calculator;

import static calculator.Utils.ADD_TOKEN;

/**
 * Addition expression
 * 
 * @author Dmitriy Pavlov
 */
public class AddNode extends TwoOperandsNode {
														
	public AddNode(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return super.toString(ADD_TOKEN);
	}
	
	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}

}
