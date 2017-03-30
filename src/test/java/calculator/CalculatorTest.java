package calculator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class CalculatorTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] { 
						{"5", 5},
						{"add(1, 2)", 3}, 
						{"add(1, mult(2, 3))", 7}, 
						{"mult(add(2, 2), div(9, 3))", 12},
						{"let(a, 5, add(a, a))", 10},
						{"let(a,let\t(b, 10, add(b, b)), let(b, 20, add	(a, b)))\n\r\f", 40},
						{"let(a, 5, let(b, mult(a, 10), add(b, a)))", 55}						
				});
	}
	
	private String source;
	private int result;
    
	public CalculatorTest(String src, int res) {
		this.source = src;
		this.result = res;
	}
    
    
    
	@Test
	public void test() {
		Calculator calc = Calculator.parse(source); 
		assertTrue("Incorrect result: " + this.source, this.result == calc.eval());
	}

}
