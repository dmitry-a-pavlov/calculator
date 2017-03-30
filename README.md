# Calculator
	Simple command-line calculator


# Build:
	mvn install

# Run example:
	run.cmd

# Grammar:
	<expression> := <oper1>(<expression>,<expression>) 
			| <oper2>(<variable>,<expression>,<expression>)
			| <variable>
			| <number>
	<oper1>   := add | sub | mult | div
        <oper2>   := let
	<variable>:=[a-zA-Z]+
	<number>  :=[+|-]?[0-9]+
# Assumption:

	- Numbers: integers between Integer.MIN_VALUE and Integer.MAX_VALUE
	- Variables: strings of characters, where each character is one of a-z, A-Z
	- Arithmetic functions: add, sub, mult, div, each taking two arbitrary expressions as arguments.
	  In other words, each argument may be any of the expressions on this list.
	- A “let” operator for assigning values to variables:
	  	let(<variable name>, <value expression>, <expression where variable is used>)
	  	the <value expression> and the <expression where the variable is used>
		may be an arbitrary expression from this list. 

# Additional assumptions:
	- Case sensitive<br>
	- Variables have local visibility: inside 'let' and its subexpressions
	- Variables can be overwritten by internal 'let' expression
	- Lazy evaluation - 'let(a, mult(100, 3), add(10,10))' will not evaluate 'mult(100, 3)' 
	

# Usage:
	
        java -cp="calc.jar" calculator.Main "<expression>" [<verbose level>]
        <expression> := the calculator language expression
        <verbose level> :=  INFO | ERROR | DEBUG
        ERROR level will be set by default
        Example: java calculator.Main "add(2, 2)" INFO
