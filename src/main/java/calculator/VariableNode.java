package calculator;

import static calculator.Utils.VARIABLES;

import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Variable expression
 * 
 * @author Dmitriy Pavlov
 */
public class VariableNode implements Expression {

	/*package*/ final String name;

	public VariableNode(String varName) {
		Objects.requireNonNull(varName);
		Matcher m = VARIABLES.matcher(varName);
		if (!m.matches()) {
			throw new SytaxErrorException("Variable name must consist from letters a-zA-Z: " + varName); 
		}
		name = varName;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
	}

}
