package cat.udl.eps.butterp.reader;

/**
 * Created by jmgimeno on 15/10/15.
 */
public class StringLexer extends Lexer {


    protected final String input; // input string
    protected int  p = 0;         // index into input of current character

    @Override
    public void consume() {
        p++;
        c = p < input.length() ? input.charAt(p) : EOF;
    }


    public StringLexer(String input) {
        super();
        this.input = input.isEmpty() ? " " : input;
        c = this.input.charAt(p); // prime lookahead
    }
}
