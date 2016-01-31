package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.*;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.environment.Environment;

public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.NIL, Symbol.NIL);
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);
        env.bindGlobal(new Symbol("add"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return new Integer(applyNext(evargs, env));
            }

            private int applyNext(SExpression evargs, Environment env) {
                if (evargs == Symbol.NIL) return 0;
                ConsCell consCell = (ConsCell) evargs;
                if (!(consCell.car instanceof ConsCell))
                    throw new EvaluationError("NotInteger");
                Integer value = (Integer) consCell.car;
                return value + applyNext(evargs.cdr, env);
            }
        });

        /*

        An example of a predefined Function:

        env.bindGlobal(new Symbol("function"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });

        */

        /*

        An example of a predefined Special:

        env.bindGlobal(new Symbol("special"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });

        */

    }
}
