package cat.udl.eps.butterp;

import cat.udl.eps.butterp.reader.Lexer;
import cat.udl.eps.butterp.reader.LexerError;
import cat.udl.eps.butterp.reader.StringLexer;
import cat.udl.eps.butterp.reader.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cat.udl.eps.butterp.reader.Token.*;
import static org.junit.Assert.assertEquals;

public class LexerTest {

    @Test public void empty_string() {
        Lexer lexer = new StringLexer("");
        assertEquals(EOF, lexer.nextToken());
    }

    @Test public void one_number() {
        assertTokens("42", INTEGER("42"));
    }

    @Test public void one_atom() {
        assertTokens("lala12", ATOM("lala12"));
    }

    @Test(expected = LexerError.class)
    public void two_signs() {
        assertTokens("+12+12");
    }

    @Test public void simple_list() {
        assertTokens("(a 12 b)", LPAREN, ATOM("a"), INTEGER("12"), ATOM("b"), RPAREN);
    }

    @Test(expected = LexerError.class)
    public void bad_number() {
        assertTokens("12a");
    }

    @Test public void simple_syntax_quote() {
        assertTokens("'12", QUOTE, INTEGER("12"));
    }

    @Test(expected = LexerError.class)
    public void syntax_quote_space_number() {
        assertTokens("' 1234");
    }

    private static List<Token> lexerize(String input) {
        Lexer lexer = new StringLexer(input);
        List<Token> tokens = new ArrayList<>();
        Token token = lexer.nextToken();
        while (token != EOF) {
            tokens.add(token);
            token = lexer.nextToken();
        }
        return tokens;
    }

    private void assertTokens(String input, Token... tokens) {
        List<Token> actual   = lexerize(input);
        List<Token> expected = Arrays.asList(tokens);
        assertEquals(expected, actual);
    }
}
