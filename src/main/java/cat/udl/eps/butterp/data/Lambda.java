package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Lambda extends Function {

    private SExpression params;
    private SExpression body;
    private Environment definitionEnv;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        this.params = params;
        this.body = body;
        this.definitionEnv = definitionEnv; // this should be extended of definitionEnv
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        if (ListOps.length(evargs) != ListOps.length(params)) {
            throw new EvaluationError("WrongNumberOfArguments");
        }

        Environment newEnv = this.definitionEnv.extend();

        if (!params.equals(Symbol.NIL)) { // has params
            ConsCell evargsList = (ConsCell) evargs;
            bindParams(evargsList, newEnv);
        }

        return body.eval(newEnv);
    }

    private void bindParams(ConsCell evargs, Environment env) {
        for (int i = 0; i < ListOps.length(evargs); i++) {
            Symbol param = (Symbol) ListOps.nth(params, i);
            System.out.println("-> "+ListOps.nth(evargs, i));
            SExpression value = ListOps.nth(evargs, i);
            env.bind(param, value);
        }
    }
}
