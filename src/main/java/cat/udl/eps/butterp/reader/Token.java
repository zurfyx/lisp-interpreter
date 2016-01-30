package cat.udl.eps.butterp.reader;

// Based on an example from Language Implementation Patterns by Terrence Parr


public class Token {

    public enum Type {
        EOF, ATOM, INTEGER, LPAREN, RPAREN, QUOTE
    }

    public static final Token EOF    = new Token(Type.EOF, null);
    public static final Token LPAREN = new Token(Type.LPAREN, null);
    public static final Token RPAREN = new Token(Type.RPAREN, null);
    public static final Token QUOTE  = new Token(Type.QUOTE, null);

    public static Token INTEGER(String text) {
        return new Token(Type.INTEGER, text);
    }

    public static Token ATOM(String text) {
        return new Token(Type.ATOM, text);
    }

    public final Type   type;
    public final String text;

    private Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return type + (text != null ? String.format("(%s)", text) : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        return !(text != null ? !text.equals(token.text) : token.text != null);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}