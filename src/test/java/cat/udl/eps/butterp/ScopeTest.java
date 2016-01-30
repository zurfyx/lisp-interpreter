package cat.udl.eps.butterp;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScopeTest {

    private static final Symbol A = new Symbol("A");
    private static final SExpression V1 = new Integer(1);
    private static final SExpression V2 = new Integer(2);

    private final Environment environment = new NestedMap();

    @Test(expected = EvaluationError.class)
    public void empty_environment() {
        environment.find(A);
    }

    @Test
    public void one_binding() {
        environment.bind(A, V1);
        assertEquals(V1, environment.find(A));
    }

    @Test
    public void change_binding() {
        environment.bind(A, V1);
        environment.bind(A, V2);
        assertEquals(V2, environment.find(A));
    }

    @Test
    public void one_environment_deep() {
        environment.bind(A, V1);
        Environment newEnvironment = environment.extend();
        assertEquals(V1, newEnvironment.find(A));
    }

    @Test
    public void push_change_pop_environment() {
        environment.bind(A, V1);
        Environment newEnvironment = environment.extend();
        newEnvironment.bind(A, V2);
        assertEquals(V2, newEnvironment.find(A));
        assertEquals(V1, environment.find(A));
    }

    @Test
    public void bind_in_global() {
        Environment newEnvironment = environment.extend();
        newEnvironment.bind(A, V1);
        newEnvironment.bindGlobal(A, V2);
        assertEquals(V1, newEnvironment.find(A));
        assertEquals(V2, environment.find(A));
    }
}
