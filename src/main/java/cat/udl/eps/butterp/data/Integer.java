package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Integer implements SExpression {

    public final int value; // Si el definiu privat caldrà un getter

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Integer integer = (Integer) o;

        return value == integer.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}
