package calculator;

import static calculator.Utils.LET_TOKEN;

/**
 * Assignment expression
 * 
 * @author Dmitriy Pavlov
 */
public class LetNode implements Expression {

	/*package*/ final Expression variable; 
	/*package*/ final Expression value;
	/*package*/ final Expression expression;
	
	public LetNode(Expression first, Expression second, Expression third) {
		variable = first;
		value = second;
		expression = third;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(LET_TOKEN).append('(');
		sb.append(variable.toString()).append(',');
		sb.append(value.toString()).append(',');
		sb.append(expression.toString()).append(')');
		return sb.toString();
	}
	
	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}
	
	public String getVarName() {
		if(!(variable instanceof VariableNode)) {
			throw new SytaxErrorException("Variable expected but " + variable.toString() + " found");
		}
		VariableNode var = VariableNode.class.cast(variable);
		return var.name;
	}

}
