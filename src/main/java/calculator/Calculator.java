package calculator;

import static calculator.Utils.ADD_TOKEN;
import static calculator.Utils.DIV_TOKEN;
import static calculator.Utils.EXPRESSION;
import static calculator.Utils.LET_TOKEN;
import static calculator.Utils.MULT_TOKEN;
import static calculator.Utils.NUMBERS;
import static calculator.Utils.PUNCTUATION;
import static calculator.Utils.SUB_TOKEN;
import static calculator.Utils.VARIABLES;
import static calculator.Utils.WHITESPACE;
import static calculator.Utils.getLevel;
import static calculator.Utils.isEmpty;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;


/**
 * Calculator evaluates expressions in a very simple integer expression
 * language. The program takes an input on the command line, computes the
 * result, and prints it to the console
 * 
 * <code>
 * Input												Output<br>
 * -----------------------------------------------------------<br>
 * add(1, 2)											3<br>
 * add(1, mult(2, 3))									7<br>
 * mult(add(2, 2), div(9, 3))							12<br>
 * let(a, 5, add(a, a))									10<br>
 * let(a, 5, let(b, mult(a, 10), add(b, a)))			55<br>
 * let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))	40<br>
 * -----------------------------------------------------------<br>
 * </code>
 * 
 * An expression is one of the following: 
 * 
 * 1. Numbers: integers between, Integer.MIN_VALUE and Integer.MAX_VALUE. 
 *    Variables: strings of characters, where each character is one of a-z, A-Z. 
 * 2. Arithmetic functions: add, sub, mult, div, each taking two arbitrary expressions as arguments. 
 *    In other words, each argument may be any of the expressions on this list. 
 * 3. "let" operator for assigning values to variables: 
 * 		let(<variable name>, <value expression>, <expression where variable is used>) 
 *    As with arithmetic functions, the <value expression> and 
 *    the <expression where the variable is used> may be an arbitrary expression from this list.
 * 
 * Additional assumptions:
 * 1. Case sensitive<br>
 * 2. Variables have local visibility: inside 'let' and its subexpressions
 * 3  Variables can be overwritten by internal 'let' expression
 * 4. Lazy evaluation - 'let(a, mult(100, 3), add(10,10))' will not evaluate 'mult(100, 3)' 
 * 
 * @author Dmitriy Pavlov
 */
public class Calculator {
	private static final Logger logger = Logger.getLogger(Calculator.class.getName());
	private static final String ERROR_MESSAGE = "Wrong sytax: ";

	/**
	 * AST for the the given expression
	 */
	private final Expression syntaxTree;

	private Calculator(Expression ast) {
		syntaxTree = ast;
	}

	public static Calculator parse(String expression) {
		if(isEmpty(expression)) {
			throw new IllegalArgumentException("Expression code must be provided");
		}
		
		String src = expression;
		logger.log(Level.FINE, "parser input :" + src);
		
		//we don't need extra spaces
		expression = WHITESPACE.matcher(expression).replaceAll("");
		
		//the language doesn't have a syntax similar to the LISP
		//we have prefix notation out of the box -> we don't need punctuation
		expression = PUNCTUATION.matcher(expression).replaceAll(" ");
		String[] tokens = WHITESPACE.split(expression); 
		
		Deque<Expression> stack = new ArrayDeque<Expression>();
		for (int i = tokens.length - 1; i >= 0; i--) {
			
			//assume our language is case sensitive
			switch (tokens[i]) {
			case ADD_TOKEN:
				checkSyntax(src, stack, 2);
				stack.push(new AddNode(stack.pop(), stack.pop()));
				continue;
			case SUB_TOKEN:
				checkSyntax(src, stack, 2);
				stack.push(new SubNode(stack.pop(), stack.pop()));
				continue;
			case MULT_TOKEN:
				checkSyntax(src, stack, 2);
				stack.push(new MultNode(stack.pop(), stack.pop()));
				continue;
			case DIV_TOKEN:
				checkSyntax(src, stack, 2);
				stack.push(new DivNode(stack.pop(), stack.pop()));
				continue;
			case LET_TOKEN:
				checkSyntax(src, stack, 3);
				stack.push(new LetNode(stack.pop(), stack.pop(), stack.pop()));
				continue;
			}
			if(NUMBERS.matcher(tokens[i]).matches()) {
				stack.push(new NumberNode(tokens[i]));
				continue;
			} 
			if(VARIABLES.matcher(tokens[i]).matches()) {
				stack.push(new VariableNode(tokens[i]));
				continue;
			}
			
			throw new SytaxErrorException("Token is not recognized: " + tokens[i]);
		}
		//the invariant must be hold
		if(stack.size() != 1) {
			throw new SytaxErrorException(ERROR_MESSAGE + src);
		}
		if(getLevel(logger) == Level.FINE) {
			logger.log(Level.FINE, "parser output:" + stack.peek().toString());
		}
		return new Calculator(stack.pop()); 
	}

	private static void checkSyntax(String src, Deque<Expression> stack, int stackSize) {
		if(stack.size() < stackSize) {
			throw new SytaxErrorException(ERROR_MESSAGE + src);
		}
	}
	
	@Override
	public String toString() {
		return syntaxTree.toString();
	}
	
	public int eval() {
		EvaluatorVisitot evaluator = new EvaluatorVisitot();
		syntaxTree.accept(evaluator);
		return evaluator.getResult();
	}

	//sanity tests
	public static void main(String[] args) {
		String source = null;
		source = "let(a,let\t(b, 10, add(b, b)), let(b, 20, add	(a, b)))\n\r\f";//40
		System.out.println(source);
		String expression = WHITESPACE.matcher(source).replaceAll("");
		System.out.println(expression);
		Matcher m = EXPRESSION.matcher(expression);
		if (m.matches()) {
			for (int i = 1; i <= m.groupCount(); i++) {
				System.out.println(m.group(i));
			}
		}
		expression = PUNCTUATION.matcher(expression).replaceAll(" ");
		System.out.println(expression);
		String[] leaves = WHITESPACE.split(expression); 
		
		for (int i = leaves.length - 1; i >= 0; i--) {
			System.out.print(leaves[i] + " ");
		}
		Calculator calc = Calculator.parse(source); 
		System.out.println();
		System.out.println(calc);
		
		expression = WHITESPACE.matcher(source).replaceAll("");
		assert expression.equals(calc.toString());
		
		System.out.println("result:"  + calc.eval());
	}
}
