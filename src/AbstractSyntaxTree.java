import java.util.LinkedList;

/**
 *  Holds an abstract syntax tree. This class doesn't check for the correctness
 *  of such a tree.
 **/
public class AbstractSyntaxTree {
    private String element = "";
    
    private LinkedList<AbstractSyntaxTree> childNodes;
    
    public AbstractSyntaxTree(){
        childNodes = new LinkedList<AbstractSyntaxTree>();
    }
    
    public AbstractSyntaxTree(String element){
        this();
        this.element = element;
    }
    
    public String getElement() {
        return element;
    }
    
    public String setElement(String element) {
        return (this.element = element);
    }
    
    public LinkedList<AbstractSyntaxTree> getChildNodes() {
        return childNodes;
    }
    
    public AbstractSyntaxTree appendChild(String childElement) {
        AbstractSyntaxTree child = new AbstractSyntaxTree(childElement);
        childNodes.offer(child);
        return child;
    }
    
    public AbstractSyntaxTree appendChild(AbstractSyntaxTree node) {
        childNodes.offer(node);
        return node;
    }
    
    // public String toLispSyntax()

}