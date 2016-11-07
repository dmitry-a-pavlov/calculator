copy ".\target\calculator-0.0.1-SNAPSHOT.jar" "calc.jar"
java -cp "calc.jar" calculator.Main "let(a, let(b, 10, add(b, b)), let(b, 20, add(a,b))"