package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public abstract class Function implements SExpression {

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public abstract SExpression apply(SExpression evargs, Environment env);

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
