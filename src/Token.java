public class Token {

    private final Lexeme lexeme;
    private final String value;

    public Token(Lexeme lexeme, String value) {
        this.lexeme = lexeme;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

}
