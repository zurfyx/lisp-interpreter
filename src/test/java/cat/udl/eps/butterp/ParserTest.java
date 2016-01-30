package cat.udl.eps.butterp;

import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.reader.Parser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    public static final SExpression SYMBOL = new Symbol("SYMBOL");
    public static final SExpression INTEGER = new Integer(1234);
    public static final SExpression QUOTE = new Symbol("quote");

    @Test
    public void read_one_integer() {
        SExpression sexpr = Parser.parse("1234");
        assertEquals(INTEGER, sexpr);
    }

    @Test
    public void read_one_integer_with_spaces() {
        SExpression sexpr = Parser.parse("   1234");
        assertEquals(INTEGER, sexpr);
    }

    @Test
    public void read_one_symbol() {
        SExpression sexpr = Parser.parse("SYMBOL");
        assertEquals(SYMBOL, sexpr);
    }

    @Test
    public void read_one_symbol_with_spaces() {
        SExpression sexpr = Parser.parse("   SYMBOL");
        assertEquals(SYMBOL, sexpr);
    }

    @Test
    public void read_simple_list() {
        SExpression sexpr = Parser.parse("   (1234 SYMBOL)");
        assertEquals(ListOps.list(INTEGER, SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_left() {
        SExpression sexpr = Parser.parse("((1234) SYMBOL   )");
        assertEquals(ListOps.list(ListOps.list(INTEGER), SYMBOL), sexpr);
    }

    @Test
    public void read_multilevel_list_right() {
        SExpression sexpr = Parser.parse("(  1234 (SYMBOL))");
        assertEquals(ListOps.list(INTEGER, ListOps.list(SYMBOL)), sexpr);
    }

    @Test
    public void read_multilevel_list_both() {
        SExpression sexpr = Parser.parse("   (  (1234  ) (  SYMBOL)  )");
        assertEquals(ListOps.list(ListOps.list(INTEGER), ListOps.list(SYMBOL)), sexpr);
    }

    @Test
    public void syntax_quote_number() {
        SExpression sexpr = Parser.parse("'1234");
        assertEquals(ListOps.list(QUOTE, INTEGER), sexpr);
    }

    @Test
    public void syntax_quote_quote_number() {
        SExpression sexpr = Parser.parse("''1234");
        assertEquals(ListOps.list(QUOTE, ListOps.list(QUOTE, INTEGER)), sexpr);
    }
}