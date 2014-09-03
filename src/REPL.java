import java.util.Scanner;

/**
 * TODO: error detection, not static?
 **/

public class REPL {

    private boolean running = false;

    private Parser parser;

    private Evaluator evaluator;

    public REPL() {
        parser = new Parser();
        
        Scope globalScope = new Scope();
        BuiltIn.loadBuiltIns(globalScope);
        evaluator = new Evaluator(globalScope);
    }

    /**
     * Reads and parses one line of input to an abstract syntax tree.
     **/
    public Expression read(String input) {
        return parser.parse(input);
    }

    public Expression eval(Expression expression) {
        return evaluator.eval(expression);
    }

    public void print(Expression e) {
        System.out.println(e.toString());
    }

    public void loop() {
        running = true;

        Scanner scanner = new Scanner(System.in);
        String currentInput = "";

        while (running) {
            if (currentInput.equals("")) {
                System.out.print("---> ");
            } else {
                System.out.print("   > ");
            }

            if (!currentInput.equals("")) {
                currentInput += " ";
            }
            currentInput += scanner.nextLine();

            if (currentInput.equals("")
                    || !Parser.hasBalancedParens(currentInput)) {

                continue;
            }

            print(eval(read(currentInput)));
            currentInput = "";
        }

        scanner.close();
    }
}