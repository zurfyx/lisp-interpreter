package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

import java.util.HashMap;

public class NestedMap implements Environment {

    private HashMap<Symbol, SExpression> global = new HashMap<>();
    private HashMap<Symbol, SExpression> local = new HashMap<>();
    private NestedMap parent = null;

    public NestedMap() {}

    private NestedMap(NestedMap parent) {
        this.parent = parent;
        this.global = parent.global;
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        global.put(symbol, value);
    }

    private SExpression getGlobalValue(Symbol symbol) {
        if (!global.containsKey(symbol))
            throw new EvaluationError("NotExists");
        return global.get(symbol);
    }

    private SExpression findParent(NestedMap parent, Symbol symbol) {
        SExpression localSymbol = parent.local.get(symbol);
        if (localSymbol != null) {
            return localSymbol;
        } else if (parent.parent == null) { // no more local HashMaps to check
            return parent.getGlobalValue(symbol);
        } else {
            return findParent(parent.parent, symbol);
        }
    }

    @Override
    public SExpression find(Symbol symbol) {
        return findParent(this, symbol);
    }

    @Override
    public Environment extend() {
        return new NestedMap(this);
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        local.put(symbol, value);
    }

}
