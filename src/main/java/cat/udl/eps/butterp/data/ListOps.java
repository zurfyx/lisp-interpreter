package cat.udl.eps.butterp.data;

import java.util.Arrays;
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
        return list(Arrays.asList(elems));
    }

    public static SExpression list(List<SExpression> elems) {
        return list(elems, 0);
    }

    private static SExpression list(List<SExpression> elems, int index) {
        SExpression nextSExpression = index == elems.size()-1 ? Symbol.NIL : list(elems, index+1);
        return new ConsCell(elems.get(index), nextSExpression);
    }

    public static int length(SExpression sexpr) {
        if (sexpr.equals(Symbol.NIL)) {
            return 0;
        }

        ConsCell consCell = (ConsCell) sexpr;
        return 1 + length(consCell.cdr);
    }

    public static SExpression nth(SExpression sexpr, int n) {
        ConsCell consCell = (ConsCell) sexpr;
        return n == 0 ? consCell.car : nth(sexpr.cdr, n-1);
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
