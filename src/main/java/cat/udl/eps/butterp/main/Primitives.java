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
                if (evargs.equals(Symbol.NIL)) {
                    return new Integer(0);
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression value = consCell.car;
                return sum(value, apply(consCell.cdr, env), env);
            }

            private SExpression sum(SExpression x, SExpression y, Environment env) {
                int xInt = castInteger(x, env);
                int yInt = castInteger(y, env);
                return new Integer(xInt + yInt);
            }

            private int castInteger(SExpression sExpression, Environment env) {
                if (!(sExpression instanceof Integer)) {
                    throw new EvaluationError("NotInteger");
                }
                return ((Integer) sExpression).value;
            }
        });

        /* apply: returns the result of applying the function passed
                  on first argument to the list passed on the second */
        env.bindGlobal(new Symbol("apply"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                Function fun = castFunction(ListOps.nth(evargs, 0));
                SExpression args = castList(ListOps.nth(evargs, 1));

                return fun.apply(args, env);
            }

            private Function castFunction(SExpression sExpression) {
                if (!(sExpression instanceof Function)) {
                    throw new EvaluationError("NotFunction");
                }
                return (Function) sExpression;
            }

            private SExpression castList(SExpression sExpression) {
                if (sExpression != Symbol.NIL && !(sExpression instanceof ConsCell)) {
                    throw new EvaluationError("NotList");
                }
                return sExpression;
            }
        });

        /* car: returns the first elements of the list that is passed by argument */
        env.bindGlobal(new Symbol("car"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression list = consCell.car;
                return getFirstElementOfList(list);
            }

            private SExpression getFirstElementOfList(SExpression list) {
                if (!(list instanceof ConsCell)) {
                    throw new EvaluationError("NotList");
                }
                return ((ConsCell)list).car;
            }
        });


        /* cdr: returns a list formed by all elements but first of
                the list passed by argument */
        env.bindGlobal(new Symbol("cdr"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression list = consCell.car;
                return getAllElementsButFirstOfList(list);
            }

            public SExpression getAllElementsButFirstOfList(SExpression list) {
                if (!(list instanceof ConsCell)) {
                    throw new EvaluationError("NotList");
                }
                return ((ConsCell)list).cdr;
            }
        });

        /* cons: returns a list which its first element is the first argument
         *       and the rest of elements is the second argument */
        env.bindGlobal(new Symbol("cons"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression newElement = consCell.car;
                SExpression list = ((ConsCell)consCell.cdr).car;

                return addElementToList(newElement, list);
            }

            private boolean isList(SExpression list) {
                return list.equals(Symbol.NIL) || list instanceof ConsCell;
            }

            private SExpression addElementToList(SExpression element, SExpression list) {
                if (!isList(list)) {
                    throw new EvaluationError("SecondArgumentIsNotList");
                }
                return new ConsCell(element, list);
            }
        });

        /* eq: returns t if the two arguments are equals, else returns nil */
        env.bindGlobal(new Symbol("eq"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression firstVal = consCell.car;
                SExpression secondVal = ((ConsCell)consCell.cdr).car;

                return firstVal.equals(secondVal) ? Symbol.TRUE : Symbol.NIL;
            }
        });

        /* eval: returns the evaluation of the expression that is passed by argument */
        env.bindGlobal(new Symbol("eval"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) evargs;
                return consCell.car.eval(env);
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
                if (evargs.equals(Symbol.NIL)) {
                    return new Integer(1);
                }

                ConsCell consCell = (ConsCell) evargs;
                SExpression value = consCell.car;
                return mult(value, apply(consCell.cdr, env), env);
            }

            private SExpression mult(SExpression x, SExpression y, Environment env) {
                int xInt = castInteger(x, env);
                int yInt = castInteger(y, env);
                return new Integer(xInt * yInt);
            }

            private int castInteger(SExpression sExpression, Environment env) {
                if (!(sExpression instanceof Integer)) {
                    throw new EvaluationError("NotInteger");
                }
                return ((Integer) sExpression).value;
            }
        });

        /* SPECIALS */

        /* define: binds globally the symbol passed on the first argument to the
         *         expression passed on the second */
        env.bindGlobal(new Symbol("define"), new Special() {
            @Override
            public SExpression applySpecial(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) evargs;
                Symbol key = castSymbol(consCell.car);
                SExpression value = ((ConsCell)consCell.cdr).car.eval(env);

                //save definition
                env.bindGlobal(key, value);

                return Symbol.NIL;
            }

            private Symbol castSymbol(SExpression sExpression) {
                if (!(sExpression instanceof Symbol)) {
                    throw new EvaluationError("NotSymbol");
                }

                return (Symbol) sExpression;
            }
        });

        /* if: implementation of if operand */
        env.bindGlobal(new Symbol("if"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                if (ListOps.length(args) != 3) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                ConsCell consCell = (ConsCell) args;
                SExpression condition = consCell.car.eval(env);
                if (!(condition.equals(Symbol.NIL))) {
                    return ListOps.nth(consCell, 1).eval(env);
                } else {
                    return ListOps.nth(consCell, 2).eval(env);
                }
            }
        });

        /* lambda: returns an anonymous function composed by a list of parameters
         *         and an expression body */
        env.bindGlobal(new Symbol("lambda"), new Special() {
            @Override
            public SExpression applySpecial(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                SExpression sExpression = evargs;
                SExpression params = ListOps.nth(sExpression, 0);
                SExpression body = ListOps.nth(sExpression, 1);
                if (!isList(params)) { // || !isNotListOfSymbols(params)
                    throw new EvaluationError("NoList");
                }

                return createLambda(params, body, env);
            }

            private boolean isList(SExpression list) {
                return list.equals(Symbol.NIL) || list instanceof ConsCell;
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
                    throw new EvaluationError("WrongNumberOfArguments");
                }

                return ((ConsCell)evargs).car;
            }
        });
    }
}
