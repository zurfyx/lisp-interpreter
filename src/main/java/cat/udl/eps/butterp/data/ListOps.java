package cat.udl.eps.butterp.data;

import java.util.List;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression car(SExpression sexpr) {
        return ((ConsCell) sexpr).car;
    }

    public static SExpression cdr(SExpression sexpr) {
        return ((ConsCell) sexpr).cdr;
    }

    public static SExpression list(SExpression... elems) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static SExpression list(List<SExpression> elems) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static int length(SExpression sexpr) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static SExpression nth(SExpression sexpr, int n) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
