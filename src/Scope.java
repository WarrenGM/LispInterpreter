import java.util.*;

public class Scope {
    
    private Scope outerScope;
    
    private Map<String, String> definitions;
    
    public Scope() {
        definitions = new HashMap<String, String>();
    }
    
    public Scope(Scope outerScope) {
        this();
        this.outerScope = outerScope;
    }
    
    public String bind(String var, String value) {
        definitions.put(var, value);
        return value;
    }
    
    public String lookUp(String var) {
        if (definitions.containsKey(var)) {
            return definitions.get(var);
        } else if (outerScope != null) {
           return outerScope.lookUp(var);
        } else {
            return var + " not found"; // TODO Throw exception 
        }
    }
    
    public boolean inScope(String var) {  
        return definitions.containsKey(var) 
            || (outerScope != null && outerScope.inScope(var));
    }
    
}