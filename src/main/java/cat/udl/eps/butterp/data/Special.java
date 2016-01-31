package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public abstract class Special implements SExpression {
    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    public abstract SExpression applySpecial(SExpression args, Environment env);

    @Override
    public String toString() {
        return String.format("<special-%x>", hashCode());
    }
}
