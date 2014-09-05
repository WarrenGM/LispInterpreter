
public abstract class Atom extends Expression {
    
    public final static Bool TRUE = new Bool("#t");
    
    public final static Bool FALSE = new Bool("#f");
    
    public static class Bool extends Atom {
        private boolean value;
        
        public Bool(String value) {
            this.value = value.equals("#t");
        }
        
        public Bool(boolean value) {
            this.value = value;
        }

        public String toString() {
            return value ? "#t" : "#f";
        }
        
        public boolean getValue() {
            return value;
        }
        
    }
    
    public static class Char extends Atom {

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return null;
        }
        
        
    }
    
    public static class Symbol extends Atom {

        private String value;
        
        public Symbol(String value) {
            this.value = value;
        }
        
        public String toString() {
            return value;
        }
        
    }
    
    public static class Str extends Atom {

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    

}