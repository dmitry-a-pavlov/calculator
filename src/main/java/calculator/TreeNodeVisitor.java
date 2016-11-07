package calculator;

/**
 * Define syntax tree processing interface
 * 
 * Visitor pattern
 */
public interface TreeNodeVisitor {
    void visit(AddNode add);
    void visit(SubNode sub);
    void visit(DivNode div);
    void visit(MultNode mult);
    void visit(LetNode let);
    void visit(VariableNode var);
    void visit(NumberNode num);
}
