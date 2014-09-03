/**
 * Holds an abstract syntax tree. This class doesn't check for the correctness
 * of such a tree.
 **/
public abstract class List extends Expression {
    
    public abstract Expression car();
    public abstract List cdr();
    
    public int length() {
        if (this instanceof Nil) {
            return 0;
        } else {
            return 1 + ((Pair)this).cdr().length();
        }
    }
    
    
    /**
     * 
     */
    public static class Nil extends List {
        public Expression car() {
            //throw new Exception();
            return null;
        }
        
        public List cdr() {
            //throw new Exception();
            return new List.Nil();
        }
        
        public String toString() {
            return "()";
        }       
    }
    
    public static class Pair extends List {
        private Expression car;

        private List cdr;

        public Pair(Expression car) {
            this.car = car;
            cdr = new Nil();
        }

        public Pair(Expression car, List cdr) {
            this(car);
            
            if (cdr != null) {
                this.cdr = cdr;
            }
        }

        public Expression car() {
            return car;
        }

        public List cdr() {
            return cdr;
        }

        public Expression append(Expression e) {
            if (cdr instanceof Nil) {
                cdr = new Pair(e);
            } else {
                ((Pair)cdr).append(e);
            }
            return e;
        }

        public String toString() {
            return "(" + toString(this) + ")";
        }

        /**
         * Returns an unwrapped string. For example for a lisp list '(1 2 3 4), this
         * method would give "1 2 3 4".
         * 
         * This is a helper method for toString():String.
         */
        private String toString(List l) {
            if (l instanceof Nil) {
                return "";
            }
           
            String result = l.car().toString();

            if (!(l.cdr() instanceof Nil)) {
                result += " " + toString(l.cdr());
            }

            return result;
        }
    }
}