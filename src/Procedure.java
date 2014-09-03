import java.util.*;

public class Procedure extends Expression {

    private List params;
    
    private Expression body;
    
    private Scope functionScope;
    
    public Procedure() {}
    
    public Procedure(List params, Expression body, Scope scope) {
        this.params = params;
        this.body = body;
        functionScope = scope;
    
    }

    public Type getType() {
        return Type.PROCEDURE;
    }
    
    /**
     *  Pre: each arg in args has been evaluated?
     **/
    public Expression evaluate(List args) {    
        Scope callScope = new Scope(functionScope);
    
        if (params.length() != args.length()) {
            System.out.println("# Argument mismatch:");
            return null;
        }
        
        List paramHeader = params;
        
        while (!(args instanceof List.Nil)) {
            callScope.bind(paramHeader.car().toString(), args.car());
            paramHeader = paramHeader.cdr();
            args = args.cdr();
        }
        
        return Evaluator.eval(body, callScope);
    }

    @Override
    public String toString() {
        return "<procedure>";
    }

}