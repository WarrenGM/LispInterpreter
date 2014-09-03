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
    public static Expression eval(Expression e, Scope scope) {
        if (e instanceof Atom.Symbol) {
            return scope.lookUp(e.toString());
        } else if (e instanceof Atom) {
            return e;
        } else {
            return evalList((List)e, scope);
        }
    }
    
    private static Expression evalList(List l, Scope scope) {
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
            return condition ? eval(cdr.car(), scope) : eval(cdr.cdr().car(), scope);
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
        return eval(proc.evaluate(argsHead), scope);
    }
    
 }