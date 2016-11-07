package calculator;

import java.util.HashMap;

/**
 * Simple context for execution flow.
 * 
 * {<varName>, <Expression>}
 * 
 * @author Dmitriy Pavlov
 */
@SuppressWarnings("serial")
public class Context extends HashMap<String, Expression> {
	
	private final Context parent;
	
	public Context(Context parent) {
		this.parent = parent;
	}
	
	
	/**
	 * Find variable by name. 
	 * @param varName 
	 * @return Expression for a given variable 
	 */
	public Expression find(String varName) {
		for(Context ctx = this; ctx != null; ctx = ctx.parent) {
			Expression exp = ctx.get(varName);
			if(exp != null)
				return exp;
		}
		return null;
	}


	public Context findContext(String varName) {
		for(Context ctx = this; ctx != null; ctx = ctx.parent) {
			Expression exp = ctx.get(varName);
			if(exp != null)
				return ctx;
		}
		return null;
	};
}
