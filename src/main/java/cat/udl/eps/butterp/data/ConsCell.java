package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class ConsCell implements SExpression {

    public final SExpression car; // Si el definiu privat caldrà un getter
    public final SExpression cdr; // Si el definiu privat caldrà un getter

    public ConsCell(SExpression car, SExpression cdr) {
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
