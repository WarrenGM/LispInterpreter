import java.util.*;

public class Scope {
    
    private Scope outerScope;
    
    private Map<String, Expression> definitions;
    
    public Scope() {
        definitions = new HashMap<String, Expression>();
    }
    
    public Scope(Scope outerScope) {
        this();
        this.outerScope = outerScope;
    }
    
    public Expression bind(String var, Expression value) {
        if (definitions.containsKey(var)) {
            return null;//var + " has already been declared in this scope.";
        }
    
        definitions.put(var, value);
        return value;
    }
    
    public Expression lookUp(String var) {
        if (definitions.containsKey(var)) {
            return definitions.get(var);
        } else if (outerScope != null) {
           return outerScope.lookUp(var);
        } else {
            return null;// var + " not found"; // TODO Throw exception 
        }
    }
    
    public boolean inScope(String var) {  
        return definitions.containsKey(var) 
            || (outerScope != null && outerScope.inScope(var));
    }
    
}