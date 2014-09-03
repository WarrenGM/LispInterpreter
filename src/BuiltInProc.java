
public abstract class BuiltInProc extends Procedure {
    abstract public String keyword();
    
    abstract public Expression evaluate(List args);
}
