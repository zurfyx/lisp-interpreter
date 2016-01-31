package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public SExpression eval(Environment env) {
        return env.find(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        return name != null ? name.equals(symbol.name) : symbol.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
