package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name; // Si el definiu privat caldrà un getter

    public Symbol(String name) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
