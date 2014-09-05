public class Evaluator {

    private Scope globalScope;

    public Evaluator() {
        this(new Scope());
    }

    public Evaluator(Scope scope) {
        globalScope = scope;
    }

    public Expression eval(Expression ast) {
        return eval(ast, globalScope);
    }

    // Evaluates an abstract syntax tree.
    public static Expression eval(Expression exp, Scope scope) {
       
        while (true) {            
            if (exp instanceof Atom.Symbol) {
                return scope.lookUp(exp.toString());
            } else if (exp instanceof Atom) {
                return exp;
            } 

            List l = (List)exp;

            if(l instanceof List.Nil){
                return l;
            } else if (l.car().toString().equals("quote")) {
                return l.cdr().car();

            } else if (l.car().toString().equals("lambda")) {
                l = l.cdr();

                Expression params = l.car();
                if (params instanceof Atom.Symbol) {
                    params = new List.Pair(params);
                    // TODO Raise error ?
                }

                Expression body = l.cdr().car();

                return new Procedure((List)params, body, scope);

            } else if (l.car().toString().equals("if")) {
                List cdr = l.cdr();
                boolean condition = ((Atom.Bool)eval(cdr.car(), scope)).getValue();

                cdr = cdr.cdr();
                exp = condition ? cdr.car() : cdr.cdr().car();
            } else if (l.car().toString().equals("define")) {
                l = l.cdr();

                String reference = "";

                if (l.car() instanceof List) {
                    List car = (List)l.car();
                    reference = car.car().toString();
                    Procedure p = new Procedure(car.cdr(), l.cdr().car(), scope);
                    scope.bind(reference, p);
                } else {
                    Expression rValue = eval(l.cdr().car(), scope);
                    reference = l.car().toString();
                    scope.bind(reference, rValue);
                }          

                return new Atom.Symbol("Defined " + reference);

            } else if (l.cdr() instanceof List.Nil) {
                return scope.lookUp(l.car().toString());
            } else if (l.car().toString().equals("let")) {
                List declarations = (List)l.cdr().car();
                Scope local = new Scope(scope);

                while (!(declarations instanceof List.Nil)) {
                    List car = (List)declarations.car();
                    local.bind(car.car().toString(), eval(car.cdr().car(), scope));
                    declarations = declarations.cdr();
                }

                return eval(l.cdr().cdr().car(), local);
            }

            Procedure proc = (Procedure)scope.lookUp(l.car().toString());

            l = l.cdr();

            List.Pair args = null, argsHead = null;
            while (!(l instanceof List.Nil)) {
                if (args == null) {
                    args = new List.Pair(eval(l.car(), scope));
                    argsHead = args;
                    l = l.cdr();
                } else {
                    args.append(eval(l.car(), scope));
                    l = l.cdr();
                    args = (List.Pair)args.cdr();
                }

            }        
            exp = proc.evaluate(argsHead);
        }
    }
}