public interface REPL {
    
    /**
     *  Reads and parses one line of input to an abstract syntax tree.
     **/
    public AbstractSyntaxTree read(String str);

    // Evaluates an abstract syntax tree.
    public String eval(AbstractSyntaxTree ast);
    
    public void print(String evaluation);
    
    public void loop();
}