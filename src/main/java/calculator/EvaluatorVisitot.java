package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Evaluate expression
 * 
 * @author Dmitriy Pavlov
 */
public class EvaluatorVisitot implements TreeNodeVisitor{
	
	private static final Logger logger = Logger.getLogger(EvaluatorVisitot.class.getName());
	
	//Variable stack
	private Deque<Context> ctxStack = new ArrayDeque<Context>();
	
	//Evaluation result stack
	private Deque<NumberNode> resultStack = new ArrayDeque<NumberNode>();
	
	//due to lazy evaluation we can easily make a loop wile tree traversing
	//and get StackOverflowExeption 
	//any expression except Number must be evaluated only once
	private Set<Expression> evaluated = new HashSet<Expression>();

	@Override
	public void visit(AddNode add) {
		canEvaluate(add);
		add.right.accept(this);
		add.left.accept(this);
		int res = resultStack.pop().number + resultStack.pop().number;
		resultStack.push(new NumberNode(res));
	}

	@Override
	public void visit(SubNode sub) {
		canEvaluate(sub);
		sub.right.accept(this);
		sub.left.accept(this);
		int res = resultStack.pop().number - resultStack.pop().number;
		resultStack.push(new NumberNode(res));
	}

	@Override
	public void visit(DivNode div) {
		canEvaluate(div);
		div.right.accept(this);
		div.left.accept(this);
		int res = resultStack.pop().number / resultStack.pop().number;
		resultStack.push(new NumberNode(res));
	}

	@Override
	public void visit(MultNode mult) {
		canEvaluate(mult);
		mult.right.accept(this);
		mult.left.accept(this);
		int res = resultStack.pop().number * resultStack.pop().number;
		resultStack.push(new NumberNode(res));
	}

	@Override
	public void visit(LetNode let) {
		canEvaluate(let);
		Context ctx = new Context(ctxStack.peek());
		ctx.put(let.getVarName(), let.value);
		ctxStack.push(ctx);
		let.expression.accept(this); //must put result into result's stack
		ctxStack.pop();//kill context
	}

	@Override
	public void visit(VariableNode var) {
		canEvaluate(var);
		Context ctx = ctxStack.peek();
		Expression expression = ctx != null? ctx.find(var.name): null;
		if(expression == null) {
			throw new SytaxErrorException("No varable found: " + var.name);
		}
		
		expression.accept(this); //lazy evaluation
		
		NumberNode varValue = resultStack.peek(); //result of the evaluation in the stack
		
		//update context to avoid the repeating the same evaluation
		Context varContext = ctx.findContext(var.name);
		varContext.put(var.name, varValue); //variable assignment 
	}

	@Override
	public void visit(NumberNode num) {
		resultStack.push(num);
	}

	public int getResult() {
		if(resultStack.size() != 1) {
			logger.log(Level.SEVERE, "Incorrect evaluation");
		}
		return resultStack.peek().number;
	}
	
	private void canEvaluate(Expression exp) {
		if(evaluated.contains(exp)) {
			throw new SytaxErrorException("There is a self-reference in a variable defenition expression");
		}
		evaluated.add(exp);		
	}

}
