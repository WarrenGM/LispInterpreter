/**
 *  Scheme grammar found at:
 *      http://www.cs.indiana.edu/scheme-repository/R4RS/r4rs_9.html
 **/
public class Parser {
    public Expression parse(String input) {
        input = format(input);
        
        switch (input.charAt(0)) {
            case '\'':
                return parse("(quote " + input.substring(1) + ")");
            case '(':
                return parseList(input);
            default:
                return parseAtom(input);
        }
    }
    
    // TODO
    /**
     * Returns the input with superfluous whitespace removed.
     * @param input A lisp expression.
     */
    private String format(String input) {
        return input;
    }
    
    public List parseList(String input) {
       
        if (input.equals("()")) {
            return new List.Nil();
        }
        
        String currentToken = "";
        int i = 0, lParens = 0, rParens = 0;
        
        while (++i < input.length() - 1) {
            char c = input.charAt(i);
            
            if (c == ' ') {
                if (!currentToken.equals("") && lParens == rParens) {
                    break;
                } else if (rParens < lParens) {
                    currentToken += c;
                }
            } else {
                currentToken += c;
                
                if (c == ')') {
                    rParens++;
                    
                    if (lParens == rParens) {
                        i++;
                        break;
                    }
                } else if (c== '(') {
                    lParens++;
                }
            }
        }
        
        List cdr = (i < input.length() - 1) ? parseList("(" + input.substring(i + 1))
                                            : new List.Nil();
        return new List.Pair(parse(currentToken), cdr);
    }
    
    /**
     *  TODO: syntax errors
     **/
    public Atom parseAtom(String input) {
        
        // INTEGER
        try {
            int i = Integer.parseInt(input);
            return new Num(i);
        } catch (Exception e) {}
        
        if (input.toLowerCase().equals("#t") || input.toLowerCase().equals("#f")) {
            return new Atom.Bool(input);
        }
        
        return new Atom.Symbol(input);
    }
    
    public static boolean hasBalancedParens(String str) {
        int lParens = 0, rParens = 0;
        
        for (char c : str.toCharArray()) {
            if (c == '(') {
                lParens++;
            } else if (c == ')') {
                rParens++;
            }
        }
        return lParens == rParens;
    }
}