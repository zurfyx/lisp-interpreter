package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Lambda extends Function {

    private SExpression params;
    private SExpression body;
    private Environment definitionEnv;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        this.params = params;
        this.body = body;
        this.definitionEnv = definitionEnv.extend();
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        if (ListOps.length(evargs) != ListOps.length(params)) {
            throw new EvaluationError("Incorrect number of args in the call.");
        }

        if (!params.equals(Symbol.NIL)) { // has params
            ConsCell evargsList = (ConsCell) evargs;
            bindParams(evargsList);
        }

        return body.eval(definitionEnv);
    }

    private void bindParams(ConsCell evargs) {
        for (int i = 0; i < ListOps.length(evargs); i++) {
            Symbol param = (Symbol) ListOps.nth(params, i);
            SExpression value = ListOps.nth(evargs, i);
            definitionEnv.bind(param, value);
        }
    }
}
