import static java.lang.Integer.*;
import java.lang.Boolean;
import java.util.Scanner;

/**
 *  TODO: error detection, not static?
 **/

public class SchemeREPL implements REPL {

    private boolean running = false;
    
    private Scope globalScope;
    
    public SchemeREPL() {   
        globalScope = new Scope();
    }

    /**
     *  Reads and parses one line of input to an abstract syntax tree.
     **/
    public AbstractSyntaxTree read(String input) {
        AbstractSyntaxTree ast = new AbstractSyntaxTree();
        char[] chars = input.toCharArray();
        
        int i = 0; // Then index for the next two loops.
        
        String prefix = "",
               currentToken = "";
        
        // Search for the prefix;
        for (; i < chars.length; i++) {
            if (chars[i] == ' ' || chars[i] == '(' || chars[i] == ')') {
                if (prefix.equals("")) {
                    continue;
                } else {
                    break;
                }
            } else {
                prefix += chars[i];
            }
        }
        
        // The number of left and right parenthesis encountered
        int lParens = 0, rParens = 0;
        
        // Search for child elements
        for (; i < chars.length; i++) {
            if (chars[i] == ' ') {
                if (currentToken.equals("")) {
                    continue;
                }
                
                if (lParens == rParens) {
                    ///System.out.println("appended " + currentToken);
                    ast.appendChild(read(currentToken));
                    currentToken = "";
                } else {
                    currentToken += chars[i];
                }
            } else {
                if(chars[i] == ')') { // Could be the end of the token
                    rParens++;
                    if(lParens >= rParens) {
                        currentToken += chars[i];
                    }
                    if(lParens <= rParens) { // Then it is the end of the token;
                    
                        if(!currentToken.equals("")) {
                            ///System.out.println("appended " + currentToken);
                            ast.appendChild(read(currentToken));
                        }
                        currentToken = "";
                    }
                
                } else {
                    currentToken += chars[i];
                    
                    if(chars[i] == '(') {
                        lParens++;
                    }
                }           
            }
        }
    
        ast.setElement(prefix);
        return ast;
    }

    public String eval(AbstractSyntaxTree ast) {
        return eval(ast, globalScope);
    }
    
    // Evaluates an abstract syntax tree.
    public String eval(AbstractSyntaxTree ast, Scope scope) {
        switch (ast.getChildNodes().size()) {
            case 0:
                return eval0Args(ast, scope);
            case 1:
                return eval1Arg(ast, scope);
            case 2: 
                return eval2Args(ast, scope);
            case 3:
                return eval3Args(ast);
            default:
                return "";
        }
    }
    
    
    private String eval0Args(AbstractSyntaxTree ast, Scope scope) {
        String element = ast.getElement();
        
        try {
            parseInt(element);
            return element;
        } catch (Exception e) {}
                
        if (element.equals("#T") || element.equals("#F")) {
            return Boolean.toString(ast.getElement().equals("#T"));
        } else if (scope.inScope(element)) {
            return scope.lookUp(element);
        }
        
        if (element.length() > 1 
            && element.charAt(0) == '\"'
            && element.charAt(element.length() - 1) == '\"') {
        
            return element;            
        }
        
        return "Could not evaluate: " + element;
    }
    
    private String eval1Arg(AbstractSyntaxTree ast, Scope scope) {
        System.out.println("1arg: " + ast.getChildNodes().get(0).getElement());
        return "Undefined";
    }
    
    
    private String eval2Args(AbstractSyntaxTree ast, Scope scope) {
        String element = ast.getElement();
                
        switch (element) {
            case "define":
                String var = ast.getChildNodes().get(0).getElement(),
                       val = eval(ast.getChildNodes().get(1));
                       
                System.out.println("<> " + var + " = " + val);
                scope.bind(var, val);
                return var + " is bound to " + val;
        
        }
        
        return evalOp(ast);
    }
    
    /**
     *  Pre: ast has two Children; 
     **/
    private String evalOp(AbstractSyntaxTree ast) {
        // Left and right child nodes;
        String left = eval(ast.getChildNodes().get(0)),
               right = eval(ast.getChildNodes().get(1));
    
        switch (ast.getElement()) {
            case "+": 
                return Integer.toString(parseInt(left) + parseInt(right));
            case "-": 
                return Integer.toString(parseInt(left) - parseInt(right));
            case "*": 
                return Integer.toString(parseInt(left) * parseInt(right));
            case "/": 
                return Integer.toString(parseInt(left) / parseInt(right));
            case "<":
                return Boolean.toString(parseInt(left) < parseInt(right));
            case "<=":
                return Boolean.toString(parseInt(left) <= parseInt(right));
            case ">":
                return Boolean.toString(parseInt(left) > parseInt(right));
            case ">=":
                return Boolean.toString(parseInt(left) >= parseInt(right));
            case "=":
                return Boolean.toString(parseInt(left) == parseInt(right));
        }
        return "";
    }
    
    private String eval3Args(AbstractSyntaxTree ast) {
        switch (ast.getElement()) {
            case "if": 
                return evalIf(ast);
        }
        
        return "";
    }
    
    
    private String evalIf(AbstractSyntaxTree ast) {
        // Left and right child nodes;
        String middle = eval(ast.getChildNodes().get(1)),
               right = eval(ast.getChildNodes().get(2));
        
        boolean condition = eval(ast.getChildNodes().get(0)).equals("true");
        
        return condition ? middle : right;
    }
    
    // TODO print to other streams.
    public void print(String evaluation) {
        System.out.println(evaluation);
    }
    
    public void loop() {
        running = true;
        
        while (running) {
            String input = "";
        
            System.out.print("---> ");
            Scanner scanner = new Scanner(System.in);
         
            input = scanner.nextLine();
            
            if (input.equals("")) {
                continue;
            }
            
            print(eval(read(input)));
            
        }
    }

}