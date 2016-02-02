package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Lambda extends Function {
    private SExpression params;
    private SExpression body;
    private Environment definitionEnv;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        this.params = params;
        this.body = body;
        this.definitionEnv = definitionEnv;
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
