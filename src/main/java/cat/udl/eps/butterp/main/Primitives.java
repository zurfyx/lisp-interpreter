package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;

public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.NIL, Symbol.NIL);
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);

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
