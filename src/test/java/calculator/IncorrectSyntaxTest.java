package calculator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class IncorrectSyntaxTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] { 
						{"add(1, 3000000000)", "Integer value expected"}, 
						{"add(c, 300000000)", "No varable found"}, 
						{"add(1, mul(2, 3))", "Wrong sytax"}, 
						{"add(1, mul(2, ", "Wrong sytax"},
						{"let(a, add(5,a), add(a, a))", "There is a self-reference"},
						{"let(a, 5, add(a_, a))", "Token is not recognized"},
						{"let(5, 5, add(a, a))", "Variable expected but"}						
				});
	}
	
	private String source;
	private String errMsg;
	
    
	public IncorrectSyntaxTest(String src, String msg) {
		this.source = src;
		this.errMsg = msg;
	}
    
    
    
	@Test
	public void test() {
		try {
			Calculator calc = Calculator.parse(source); 
			calc.eval();
			fail();
		} catch (Exception e) {
			String msg = e.getMessage();
			System.out.println(msg);
			assertTrue("Incorrect error: " + msg, msg != null 
					&& msg.startsWith(this.errMsg));
		} 
	}

}
