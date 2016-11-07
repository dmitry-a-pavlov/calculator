package calculator;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Static utilities and constant 
 * 
 * @author Dmitriy Pavlov
 */
public class Utils {

    private Utils() {
        throw new AssertionError("Must never be called");
    }
    
	public static final Pattern WHITESPACE = Pattern
			.compile("\\p{javaWhitespace}+");

	public static final Pattern PUNCTUATION = Pattern
			.compile("[\\(\\),]");
	
	public static final Pattern EXPRESSION = Pattern
			.compile("^([a-zA-Z]+)\\((.*)\\)$");

	//terminal leafs of AST (abstract syntax tree)
	/** 
	 * Variable name starts with letter 
	 */
	public static final Pattern VARIABLES = Pattern.compile("[a-zA-Z]+"); // [a-zA-Z]\\w+
	public static final Pattern NUMBERS = Pattern.compile("[-+]?\\d+");

	
	//Tokens
	public static final String ADD_TOKEN = "add";
	public static final String SUB_TOKEN = "sub";
	public static final String DIV_TOKEN = "div";
	public static final String MULT_TOKEN = "mult";
	public static final String LET_TOKEN = "let";
	
	public static boolean isEmpty(String expression) {
		return expression == null || expression.isEmpty();
	}

	public static Level getLevel(Logger logger) {
		while(logger != null) {
			Level level = logger.getLevel();
			if(level != null) {
				return level;
			}
			logger = logger.getParent();
			
		}
		return null;
	}

	
	
}
