package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import cat.udl.eps.butterp.reader.LexerError;
import cat.udl.eps.butterp.reader.Parser;
import cat.udl.eps.butterp.reader.ParserError;
import cat.udl.eps.butterp.reader.StringLexer;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Repl {

    public static final String PROMPT = "butterp";

    public static Environment createInitialEnvironment() {
        Environment env = new NestedMap();
        Primitives.loadPrimitives(env);
        return env;
    }

    public static String readInput() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        StringBuilder builder = new StringBuilder();
        String line;
        boolean first = true;
        int parenthesesBalance = 0;
        do {
            String prompt = String.format("%s%s ", PROMPT, first? ">" : "#");
            System.out.print(prompt);
            line = scanner.nextLine();
            builder.append(String.format("%s%n", line));
            first = false;
            parenthesesBalance += parenthesesBalance(line);
        } while (!line.isEmpty() && parenthesesBalance != 0);
        return builder.toString();
    }

    private static int parenthesesBalance(String line) {
        int balance = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '(') balance += 1;
            else if (c == ')') balance -= 1;
        }
        return balance;
    }

    private static void showBanner() {
        System.out.println("Welcome to butterp, your mini-lisp implementation.");
        System.out.println();
        System.out.println("Type the expression to evaluate:");
        System.out.println("\t* interpret waits for parentheses to balance");
        System.out.println("\t* or for an empty line");
        System.out.println("\t* type :exit to exit");
        System.out.println();
    }

    public static void main(String[] args) {
        Environment environment = createInitialEnvironment();
        showBanner();
        while (true) {
            String input = readInput();
            if (":exit\n".equals(input)) break;
            try {
                Parser parser = new Parser(new StringLexer(input));
                SExpression sexpr = parser.sexpr();
                SExpression result = sexpr.eval(environment);
                System.out.printf(">>>>>>> %s%n", result);
            } catch (LexerError | ParserError | EvaluationError ex) {
                System.out.printf("~~~~~~~ %s%n", ex.getMessage());
            }
        }
    }
}
