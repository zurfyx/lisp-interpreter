package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Integer implements SExpression {

    public final int value; // Si el definiu privat caldrà un getter

    public Integer(int value) {
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
