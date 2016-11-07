package calculator;

/**
 * Define expression of the language 
 * 
 * @author Dmitriy Pavlov
 */
public interface Expression {
	void accept(TreeNodeVisitor visitor);
}
