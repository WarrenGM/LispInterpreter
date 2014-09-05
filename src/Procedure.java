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

        if (((List)body).car().toString().equals("if")) {
            // ifBody := (cdr `(if condition then else)) == (condition then else)
            List ifBody = ((List)body).cdr();
            boolean cond = ((Atom.Bool)Evaluator.eval(ifBody.car(), callScope)).getValue();

            ifBody = ifBody.cdr(); // (then else)
            Expression result = cond ? ifBody.car() : ifBody.cdr().car(); 

            return substitute(result, callScope);
        }

        return Evaluator.eval(body, callScope);
    }

    @Override
    public String toString() {
        return "<procedure>";
    }

    private Expression substitute(Expression expr, Scope scope) {    
        Expression result;
        
        if (expr instanceof Atom.Symbol) {
            result = scope.lookUp(expr.toString());
            if (result == null || result instanceof Procedure) {
                result = expr;
            }
            return result;
        }
        
        if (expr instanceof Atom 
                || expr instanceof Procedure || expr instanceof List.Nil) {
            return expr;
        }

        List list = (List)expr;
        
        Expression car = list.car();
        if (car instanceof List) {
            result = substitute(car, scope);
        } else if (car instanceof Atom.Symbol) {
            result = scope.lookUp(car.toString());
            if (result == null || result instanceof Procedure) {
                result = car;
            }
        } else {
            result = car;
        }
        return new List.Pair(result, (List)substitute(list.cdr(), scope));
        

    }

}