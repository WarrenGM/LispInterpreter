import static java.lang.Integer.*;

public enum BuiltIn {
    
    ////
    //
    //  VALUES
    //
    ////
    
    CAR (new BuiltInProc() {
        public String keyword() { return "car"; }
        
        public Expression evaluate(List args) {
            return ((List)args.car()).car();
        }
    }),
    
    CDR (new BuiltInProc() {
        public String keyword() { return "cdr"; }
        
        public Expression evaluate(List args) {
            return args.cdr();
        }
    }),
    
    CONS (new BuiltInProc() {
        public String keyword() { return "cons"; }
        
        public Expression evaluate(List args) {
            //List result = new 
            return null;//args.car().toString();
        }
    }),
    
    EVAL (new BuiltInProc() {
        public String keyword() { return "eval"; }
        
        public Expression evaluate(List args) {
            return args.car();
        }
    }),
    
    PLUS (new BuiltInProc() {
        public String keyword() { return "+"; }
        
        public Expression evaluate(List args) {
            Num result = ((Num)args.car());
            
            while (!(args.cdr() instanceof List.Nil)) {
                args = args.cdr();
                result = result.plus((Num)args.car());
            }
            
            return result;
        }
    }),
    
    MINUS (new BuiltInProc() {
        public String keyword() { return "-"; }
        
        public Expression evaluate(List args) {
            Num result = ((Num)args.car());
            while (!(args.cdr() instanceof List.Nil)) {
                args = args.cdr();
                result = result.minus((Num)args.car());
            }
            return result;
        }
    }),
    
    MULTIPLY (new BuiltInProc() {
        public String keyword() { return "*"; }
        
        public Expression evaluate(List args) {
            Num result = ((Num)args.car());
            while (!(args.cdr() instanceof List.Nil)) {
                args = args.cdr();
                result = result.times((Num)args.car());
            }
            return result;
        }
    }),
    
    DIVIDE (new BuiltInProc() {
        public String keyword() { return "/"; }
        
        public Expression evaluate(List args) {
            
            Num result = ((Num)args.car());
            while (!(args.cdr() instanceof List.Nil)) {
                args = args.cdr();
                result = result.divide((Num)args.car());
            }
            return result;
        }
    }),
    
    LESS_THAN (new BuiltInProc() {
        public String keyword() { return "<"; }
        
        public Expression evaluate(List args) {
            boolean result = true;
            
            while (!(args.cdr() instanceof List.Nil)) {
                result = result 
                        && ((Num)args.car()).lessThan((Num)args.cdr().car());
                args = args.cdr();
            }
            
            return new Atom.Bool(result);
        }
    }),
    
    LESS_THAN_OR_EQUAL (new BuiltInProc() {
        public String keyword() { return "<="; }
        
        public Expression evaluate(List args) {
            int leftOperand = parseInt(args.car().toString()),
                rightOperand = parseInt(args.cdr().car().toString());
                    
            return new Atom.Bool(leftOperand < rightOperand);
        }
    }),
    
    GREATER_THAN (new BuiltInProc() {
        public String keyword() { return ">"; }
        
        public Expression evaluate(List args) {
            int leftOperand = parseInt(args.car().toString()),
                rightOperand = parseInt(args.cdr().car().toString());
                    
            return new Atom.Bool(leftOperand > rightOperand);
        }
    }),
    
    EQUALS (new BuiltInProc() {
        public String keyword() { return "="; }
        
        public Expression evaluate(List args) {
            int leftOperand = parseInt(args.car().toString()),
                rightOperand = parseInt(args.cdr().car().toString());
                    
            return new Atom.Bool(leftOperand == rightOperand);
        }
    });
    
    /////
    //
    //  LOGIC
    //
    /////
        
    private final BuiltInProc p;
    BuiltIn(BuiltInProc p) {
        this.p = p;
    }
    
    public BuiltInProc procedure() {
        return p;
    }
    
    public static void loadBuiltIns(Scope scope) {
        for (BuiltIn proc : BuiltIn.values()) {
            scope.bind(proc.procedure().keyword(), proc.procedure());
        }
    }
        
}
