package cat.udl.eps.butterp;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;
import cat.udl.eps.butterp.environment.NestedMap;
import cat.udl.eps.butterp.main.Primitives;
import cat.udl.eps.butterp.reader.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimitivesTest {

    private final Environment env = new NestedMap();

    private void assertEvalTo(String input, String output) {
        SExpression sexpr = Parser.parse(input);
        SExpression actual = sexpr.eval(env);
        SExpression expected = Parser.parse(output);
        assertEquals(expected, actual);
    }

    private void assertEvalFails(String input) {
        assertEvalTo(input, null);
    }

    @Before
    public void loadPrimitives() {
        Primitives.loadPrimitives(env);
    }

    @Test(expected = EvaluationError.class)
    public void unbound_symbol() {
        assertEvalFails("NotExists");
    }

    @Test public void true_() {
        SExpression sexpr = Parser.parse("t");
        assertEquals(Symbol.TRUE, sexpr.eval(env));
    }

    @Test public void nil() {
        SExpression sexpr = Parser.parse("nil");
        assertEquals(Symbol.NIL, sexpr.eval(env));
    }

    @Test
    public void number() {
        assertEvalTo("1", "1");
    }

    @Test public void quoted_symbol() {
        assertEvalTo("(quote A)", "A");
    }

    @Test public void quoted_number() {
        assertEvalTo("(quote 1)", "1");
    }

    @Test public void quoted_list() {
        assertEvalTo("(quote (1 2 3 4))", "(1 2 3 4)");
    }

    @Test public void quoted_empty_list() {
        assertEvalTo("(quote ())", "nil");
    }

    @Test(expected = EvaluationError.class)
    public void quoted_no_arg() {
        assertEvalFails("(quote)");
    }

    @Test(expected = EvaluationError.class)
    public void quoted_too_many_args() {
        assertEvalFails("(quote 1 2 3)");
    }

    @Test public void car() {
        assertEvalTo("(car (quote (1 2 3)))", "1");
    }

    @Test(expected = EvaluationError.class)
    public void car_no_args() {
        assertEvalFails("(car)");
    }

    @Test(expected = EvaluationError.class)
    public void car_too_many_args() {
        assertEvalFails("(car 1 2 3)");
    }

    @Test(expected = EvaluationError.class)
    public void car_number() {
        assertEvalFails("(car 1)");
    }

    @Test public void cdr() {
        assertEvalTo("(cdr (quote (1 2 3)))", "(2 3)");
    }

    @Test(expected = EvaluationError.class)
    public void cdr_no_args() {
        assertEvalFails("(cdr)");
    }

    @Test(expected = EvaluationError.class)
    public void cdr_too_many_args() {
        assertEvalFails("(cdr 1 2 3)");
    }

    @Test(expected = EvaluationError.class)
    public void cdr_number() {
        assertEvalFails("(cdr 1)");
    }

    @Test(expected = EvaluationError.class)
    public void cons_no_args() {
        assertEvalFails("(cons)");
    }

    @Test(expected = EvaluationError.class)
    public void cons_one_args() {
        assertEvalFails("(cons 1)");
    }

    @Test(expected = EvaluationError.class)
    public void cons_too_many_args() {
        SExpression sexpr = Parser.parse("(cons 1 2 3 4)");
        sexpr.eval(env);
    }

    @Test(expected = EvaluationError.class)
    public void cons_second_num_args() {
        assertEvalFails("(cons nil 2)");
    }

    @Test public void cons_to_nil() {
        SExpression sexpr = Parser.parse(("(cons 1 nil)"));
        SExpression expected = Parser.parse("(1)");
        assertEquals(expected, sexpr.eval(env));
    }

    @Test public void cons_list() {
        assertEvalTo("(cons 1 (quote (2 3 4)))", "(1 2 3 4)");
    }

    @Test(expected = EvaluationError.class)
    public void lambda_no_arg() {
        assertEvalFails("(lambda)");
    }

    @Test(expected = EvaluationError.class)
    public void lambda_no_params_list() {
        assertEvalFails("(lambda 1)");
    }

    @Test(expected = EvaluationError.class)
    public void lambda_params_list_no_symbol() {
        assertEvalFails("(lambda (A 1 B))");
    }

    @Test
    public void lambda_constantly_one() {
        assertEvalTo("((lambda () 1))", "1");
    }

    @Test
    public void lambda_snoc() {
        assertEvalTo("((lambda (D A) (cons A D)) nil 1)", "(1)");
    }

    @Test
    public void nested_lambdas() {
        assertEvalTo("(((lambda (A) (lambda (D) (cons A D))) 1) nil)", "(1)");
    }

    @Test
    public void lambda_func_param() {
        assertEvalTo("((lambda (F A) (F A)) car (quote (1 2)))", "1");
    }

    @Test(expected = EvaluationError.class)
    public void eq_no_args() {
        assertEvalFails("(eq)");
    }
    
    @Test(expected = EvaluationError.class)
    public void eq_one_arg() {
        assertEvalFails("(eq 1)");
    }

    @Test(expected = EvaluationError.class)
    public void eq_too_many_args() {
        assertEvalFails("(eq 1 2 3 4)");
    }

    @Test
    public void eq_numbers_true() {
        assertEvalTo("(eq 1 1)", "t");
    }

    @Test
    public void eq_numbers_nil() {
        assertEvalTo("(eq 1 2)", "nil");
    }

    @Test
    public void eq_complex_true() {
        assertEvalTo("(eq (quote (1 2 (3 4) 5 (6))) (quote (1 2 (3 4) 5 (6))))", "t");
    }

    @Test
    public void eq_complex_false() {
        assertEvalTo("(eq (quote (1 2 (3) 5 (6))) (quote (1 2 (3 4) 5 (6))))", "nil");
    }

    @Test(expected = EvaluationError.class)
    public void if_no_args() {
        assertEvalFails("(if)");
    }

    @Test(expected = EvaluationError.class)
    public void if_one_arg() {
        assertEvalFails("(if 1)");
    }

    @Test(expected = EvaluationError.class)
    public void if_two_args() {
        assertEvalFails("(if 1 2)");
    }

    @Test(expected = EvaluationError.class)
    public void if_too_many_args() {
        assertEvalFails("(if)");
    }

    @Test
    public void if_then() {
        assertEvalTo("(if (eq 1 1) (quote 1) (quote 2))", "1");
    }

    @Test
    public void if_else() {
        assertEvalTo("(if (eq 1 2) (quote 1) (quote 2))", "2");
    }

    @Test
    public void add_no_arg() {
        assertEvalTo("(add)", "0");
    }

    @Test
    public void add_many_args() {
        assertEvalTo("(add 1 2 3 4)", "10");
    }

    @Test(expected = EvaluationError.class)
    public void add_not_a_number() {
        assertEvalFails("(add 1 t 2)");
    }

    @Test(expected = EvaluationError.class)
    public void define_no_args() {
        assertEvalFails("(define)");
    }

    @Test(expected = EvaluationError.class)
    public void define_one_args() {
        assertEvalFails("(define 1)");
    }

    @Test(expected = EvaluationError.class)
    public void define_two_many_args() {
        assertEvalFails("(define 1 2 3 4)");
    }

    @Test(expected = EvaluationError.class)
    public void define_no_symbol() {
        assertEvalFails("(define 1 2)");
    }

    @Test
    public void define_symbol() {
        assertEvalTo("(define N (add 1 2))", "nil");
        assertEvalTo("N", "3");
    }

    @Test
    public void mult_no_arg() {
        assertEvalTo("(mult)", "1");
    }

    @Test
    public void mult_many_args() {
        assertEvalTo("(mult 1 2 3 4)", "24");
    }

    @Test(expected = EvaluationError.class)
    public void mult_not_a_number() {
        assertEvalFails("(mult 1 t 2)");
    }

    @Test
    public void define_factorial() {
        assertEvalTo("(define factorial " +
                "       (lambda (n)" +
                "         (if (eq n 0) " +
                "             1 " +
                "             (mult n " +
                "                   (factorial (add n -1))))))", "nil");
        assertEvalTo("(factorial 6)", "720");
    }

    @Test public void define_y_combinator() {
        assertEvalTo("(define y" +
                "       (lambda (x)" +
                "         ((lambda (proc)" +
                "            (x (lambda (arg) ((proc proc) arg))))" +
                "          (lambda (proc)" +
                "            (x (lambda (arg) ((proc proc) arg)))))))", "nil");
        assertEvalTo("(define f" +
                "       (lambda (func)" +
                "         (lambda (n)" +
                "           (if (eq n 0)" +
                "              1" +
                "              (mult n (func (add n -1)))))))", "nil");
        assertEvalTo("(define factorial (y f))", "nil");
        assertEvalTo("(factorial 6)", "720");
    }

    @Test public void syntax_quote() {
        assertEvalTo("(car '((1 2) cons))", "(1 2)");
    }

    @Test(expected = EvaluationError.class)
    public void eval_no_arg() {
        assertEvalFails("(eval)");
    }

    @Test(expected = EvaluationError.class)
    public void eval_too_many_args() {
        assertEvalFails("(eval '(1 2) 3)");
    }

    @Test public void eval_ok() {
        assertEvalTo("(eval '(add 1 2))", "3");
    }

    @Test(expected = EvaluationError.class)
    public void apply_no_arg() {
        assertEvalFails("(apply)");
    }

    @Test(expected = EvaluationError.class)
    public void apply_one_arg() {
        assertEvalFails("(apply car)");
    }

    @Test(expected = EvaluationError.class)
    public void apply_too_many_arg() {
        assertEvalFails("(apply car '(1 2) '(3 4))");
    }

    @Test public void apply_ok() {
        assertEvalTo("(apply add '(1 2 3 4))", "10");
    }

    @Test public void list_no_arg() {
        assertEvalTo("(list)", "nil");
    }

    @Test public void list_some_args() {
        assertEvalTo("(list 1 (add 2 3) '(1 2))", "(1 5 (1 2))");
    }
}
