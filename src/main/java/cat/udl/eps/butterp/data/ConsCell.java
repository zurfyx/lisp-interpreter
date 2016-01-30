package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class ConsCell implements SExpression {

    public final SExpression car;
    public final SExpression cdr;

    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsCell consCell = (ConsCell) o;

        if (car != null ? !car.equals(consCell.car) : consCell.car != null) return false;
        return cdr != null ? cdr.equals(consCell.cdr) : consCell.cdr == null;

    }

    @Override
    public int hashCode() {
        int result = car != null ? car.hashCode() : 0;
        result = 31 * result + (cdr != null ? cdr.hashCode() : 0);
        return result;
    }

    // NEEDS TO CHECK
    @Override
    public String toString() {
        String str = "(" + toStringRec(this) + ")";
        return str;
    }

    private String toStringRec(SExpression sExpr) {
        ConsCell consCell = (ConsCell) sExpr;
        if(consCell.car == Symbol.NIL)
            return "";
        return consCell.car.toString() + " " + toStringRec(consCell.cdr);
    }
}
