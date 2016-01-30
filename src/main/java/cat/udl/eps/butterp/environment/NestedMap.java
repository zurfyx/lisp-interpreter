package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

public class NestedMap implements Environment {

    public NestedMap() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public SExpression find(Symbol symbol) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Environment extend() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
