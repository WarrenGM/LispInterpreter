
public abstract class Expression {
    
    abstract public String toString();
    
    public List cons(List cdr) {
        return new List.Pair(this, cdr);
    }
}