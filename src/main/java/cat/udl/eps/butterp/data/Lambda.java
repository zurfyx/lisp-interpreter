package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

import java.util.List;

public class Lambda extends Function {

    private SExpression params;
    private SExpression body;
    private Environment definitionEnv;


    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        if (!(params instanceof ConsCell)) {
            throw new EvaluationError("WrongNumberOfArguments");
        }
        this.params = params;
        this.body = body;
        this.definitionEnv = definitionEnv;
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        if (ListOps.length(evargs) != ListOps.length(this.params)) {
            throw new EvaluationError("WrongNumberOfArguments");
        }
        Environment newEnv = callingEnv;
        ConsCell args = (ConsCell) evargs;
        ConsCell params = (ConsCell) this.params;
        bindParams(args, params, newEnv);
        return this.body.eval(newEnv);
    }

    // maybe it could be improved
    private void bindParams(ConsCell evargs, ConsCell params, Environment env) {
        for (int i = 0; i < ListOps.length(evargs); i++) {
            env.bind(new Symbol(ListOps.nth(params, i).toString()), ListOps.nth(evargs, i));
        }
    }
}
