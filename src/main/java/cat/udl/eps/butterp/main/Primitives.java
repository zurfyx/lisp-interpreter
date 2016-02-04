package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.*;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.environment.Environment;

public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.NIL, Symbol.NIL);
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);

        /* FUNCTIONS */

        /* add: returns the sum of all integers that are passed by argument */
        env.bindGlobal(new Symbol("add"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                //check arguments
                if (!ListOps.isListOf(evargs, Integer.class)) {
                    throw new EvaluationError("ADD should get only integer arguments.");
                }

                return applyAdd(evargs);
            }

            private SExpression applyAdd(SExpression evargs) {
                if (evargs.equals(Symbol.NIL)) {
                    return new Integer(0);
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression value = consCell.car;
                return sum(value, applyAdd(consCell.cdr));
            }

            private SExpression sum(SExpression x, SExpression y) {
                int xInt = ((Integer) x).value;
                int yInt = ((Integer) y).value;
                return new Integer(xInt + yInt);
            }
        });

        /* apply: returns the result of applying the function passed
                  on first argument to the list passed on the second */
        env.bindGlobal(new Symbol("apply"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                // check arguments
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("APPLY should get two arguments.");
                }
                if (!(ListOps.nth(evargs, 0) instanceof Function)) {
                    throw new EvaluationError("First arg of APPLY should be a function.");
                }
                if (!ListOps.nth(evargs, 1).equals(Symbol.NIL) && (!(ListOps.nth(evargs, 1) instanceof ConsCell))) {
                    throw new EvaluationError("Second arg of APPLY should be a list.");
                }

                Function fun = (Function) ListOps.nth(evargs, 0);
                SExpression args = ListOps.nth(evargs, 1);
                return fun.apply(args, env);
            }
        });

        /* car: returns the first elements of the list that is passed by argument */
        env.bindGlobal(new Symbol("car"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                // check arguments
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("CAR needs an argument.");
                }
                if (!(ListOps.nth(evargs, 0) instanceof ConsCell)) {
                    throw new EvaluationError("CAR needs a list argument.");
                }

                return ListOps.car(ListOps.car(evargs));
            }
        });

        /* cdr: returns a list formed by all elements but first of
                the list passed by argument */
        env.bindGlobal(new Symbol("cdr"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                // check arguments
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("CDR needs an argument.");
                }
                if (!(ListOps.nth(evargs, 0) instanceof ConsCell)) {
                    throw new EvaluationError("CDR needs a list argument.");
                }

                return ListOps.cdr(ListOps.car(evargs));
            }
        });

        /* cons: returns a list which its first element is the first argument
         *       and the rest of elements is the second argument */
        env.bindGlobal(new Symbol("cons"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                // check arguments
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("CONS needs two arguments.");
                }
                if (!ListOps.nth(evargs, 1).equals(Symbol.NIL) && (!(ListOps.nth(evargs, 1) instanceof ConsCell))) {
                    throw new EvaluationError("CONS second argument should be list.");
                }

                SExpression newElement = ListOps.car(evargs);
                SExpression list = ListOps.car(ListOps.cdr(evargs));
                return ListOps.cons(newElement, list);
            }
        });

        /* eq: returns t if the two arguments are equals, else returns nil */
        env.bindGlobal(new Symbol("eq"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                //check arguments
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("EQ needs two arguments.");
                }

                SExpression firstVal = ListOps.car(evargs);
                SExpression secondVal = ListOps.car(ListOps.cdr(evargs));
                return firstVal.equals(secondVal) ? Symbol.TRUE : Symbol.NIL;
            }
        });

        /* eval: returns the evaluation of the expression that is passed by argument */
        env.bindGlobal(new Symbol("eval"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                // check arguments
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("EVAL should get only one argument.");
                }

                return ListOps.car(evargs).eval(env);
            }
        });

        /* list: returns a list formed by all passed arguments */
        env.bindGlobal(new Symbol("list"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return evargs;
            }
        });

        /* mult: returns the multiplication of all integers that are passed by argument */
        env.bindGlobal(new Symbol("mult"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                //check arguments
                if (!ListOps.isListOf(evargs, Integer.class)) {
                    throw new EvaluationError("MULT should get only integer arguments.");
                }

                return applyMult(evargs);
            }

            private SExpression applyMult(SExpression evargs) {
                if (evargs.equals(Symbol.NIL)) {
                    return new Integer(1);
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression value = consCell.car;
                return mult(value, applyMult(consCell.cdr));
            }

            private SExpression mult(SExpression x, SExpression y) {
                int xInt = ((Integer) x).value;
                int yInt = ((Integer) y).value;
                return new Integer(xInt * yInt);
            }
        });

        /* SPECIALS */

        /* define: binds globally the symbol passed on the first argument to the
         *         expression passed on the second */
        env.bindGlobal(new Symbol("define"), new Special() {
            @Override
            public SExpression applySpecial(SExpression evargs, Environment env) {
                //check arguments
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("DEFINE should have two arguments.");
                }
                if (!(ListOps.nth(evargs, 0) instanceof Symbol)) {
                    throw new EvaluationError("DEFINE's first argument should be a symbol.");
                }

                Symbol key = (Symbol) ListOps.nth(evargs, 0);
                SExpression value = ListOps.car(ListOps.cdr(evargs)).eval(env);
                return saveDefinition(key, value, env);
            }

            private SExpression saveDefinition(Symbol key, SExpression value, Environment env) {
                env.bindGlobal(key, value);
                return Symbol.NIL;
            }
        });

        /* if: implementation of if operand */
        env.bindGlobal(new Symbol("if"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                // check arguments
                if (ListOps.length(args) != 3) {
                    throw new EvaluationError("APPLY should get two arguments.");
                }


                SExpression condition = ListOps.nth(args, 0);
                SExpression conditionTrue = ListOps.nth(args, 1);
                SExpression conditionFalse = ListOps.nth(args, 2);
                return applyIf(condition, conditionTrue, conditionFalse, env);
            }

            private SExpression applyIf(SExpression condition, SExpression conditionTrue, SExpression conditionFalse,
                                        Environment env) {
                return (condition.eval(env).equals(Symbol.NIL)) ? conditionFalse.eval(env) : conditionTrue.eval(env);
            }
        });

        /* lambda: returns an anonymous function composed by a list of parameters
         *         and an expression body */
        env.bindGlobal(new Symbol("lambda"), new Special() {
            @Override
            public SExpression applySpecial(SExpression evargs, Environment env) {
                // check arguments
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("LAMBDA needs two args.");
                }
                if (!ListOps.nth(evargs, 0).equals(Symbol.NIL) && (!(ListOps.nth(evargs, 0) instanceof ConsCell)) &&
                        (!ListOps.isListOf(ListOps.car(evargs), Symbol.class))) {
                    throw new EvaluationError("LAMBDA params should be a list of symbols.");
                }

                SExpression params = ListOps.nth(evargs, 0);
                SExpression body = ListOps.nth(evargs, 1);
                return createLambda(params, body, env);
            }

            private Lambda createLambda(SExpression params, SExpression body, Environment env) {
                return new Lambda(params, body, env);
            }
        });

        /* quote: returns arg without evaluating it */
        env.bindGlobal(new Symbol("quote"), new Special() {
            @Override
            public SExpression applySpecial(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("QUOTE needs an argument.");
                }

                return ListOps.car(evargs);
            }
        });
    }
}
