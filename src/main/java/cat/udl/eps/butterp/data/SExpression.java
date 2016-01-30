package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public interface SExpression {

    SExpression eval(Environment env);
}
