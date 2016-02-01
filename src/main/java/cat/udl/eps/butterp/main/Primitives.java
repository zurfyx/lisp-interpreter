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

        env.bindGlobal(new Symbol("list"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return evargs;
            }
        });

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
