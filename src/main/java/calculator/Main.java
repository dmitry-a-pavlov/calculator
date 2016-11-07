package calculator;

import static calculator.Utils.EXPRESSION;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * Entry point to the calculator
 * 
 * @author Dmitriy Pavlov
 */
public class Main {
	private static final Logger rootLogger = Logger.getLogger("");	
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	
	private static final String HELP =
			"Usage:\n"
			+ "\tjava -cp=\"calc.jar\" calculator.Main \"<expression>\" [<verbose level>]\n"
			+ "\t<expression> := the calculator language expression\n"
			+ "\t<verbose level> :=  INFO | ERROR | DEBUG\n"
			+ "\tERROR level will be set by default\n"
			+ "\tExample: java calculator.Main \"add(2, 2)\" INFO";

	
	private static final String DEBUG_LEVEL = "DEBUG";
	private static final String INFO_LEVEL = "INFO";
	private static final String ERROR_LEVEL = "ERROR";
	
	
	public static void main(String[] args) {
		if(!checkArguments(args)) {
			System.out.println(HELP);
			return;
		}
		
		String vLevel = setLevel(args);
		String src = args[0];
		
		logger.log(Level.INFO, "input: {0}, verbosity: {1}", new Object[]{src, vLevel});
		
		Calculator calc = Calculator.parse(src);
		System.out.println(String.valueOf(calc.eval()));
	}

	/**
	 * Set log verbosity level   
	 * @param args
	 */
	private static String setLevel(String[] args) {
		String vLevel = ERROR_LEVEL;
		if(args.length >= 2 && args[1] != null) {
			vLevel = args[1];
		}
		switch (vLevel.toUpperCase()) {
		case DEBUG_LEVEL:
			setLevel(Level.FINE);
			break;			
		case INFO_LEVEL:
			setLevel(Level.INFO);
			break;			
		case ERROR_LEVEL:
			setLevel(Level.SEVERE);
			break;
		}
		return vLevel;
	}

	private static void setLevel(Level level) {
		rootLogger.setLevel(level);
		// Handler for console (reuse it if it already exists)
		for (Handler handler : rootLogger.getHandlers()) {
		    if (handler instanceof ConsoleHandler) {
		    	handler.setLevel(level);
		        return;
		    }
		}
		//there was no console handler found, create a new one
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(level);
	    rootLogger.addHandler(consoleHandler);
		
	}

	/**
	 *	Simple arguments validation 
	 */
	private static boolean checkArguments(String[] args) {
		if(args.length < 1) {
			return false;
		}
		String src = args[0];
		Matcher m = EXPRESSION.matcher(src);
		if (!m.matches()) {
			logger.log(Level.SEVERE, "Wrong expression: {0}", src);
			return false;
		}

		if(args.length > 1) {
			String vLevel = args[1];
			switch (vLevel.toUpperCase()) {
			case DEBUG_LEVEL:
			case INFO_LEVEL:	
			case ERROR_LEVEL:
				break;
			default:
				logger.log(Level.SEVERE, "Unsupported verbosity level: {0}", vLevel);				
				return false;
			}
		}
		return true;
	}

}
