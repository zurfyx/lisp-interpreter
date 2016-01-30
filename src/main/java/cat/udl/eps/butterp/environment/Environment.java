package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

/**
 * Created by jmgimeno on 10/10/15.
 */
public interface Environment {

    void bind(Symbol symbol, SExpression value);

    void bindGlobal(Symbol symbol, SExpression value);

    SExpression find(Symbol symbol);

    Environment extend();
}
