import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        LexicalAnalyzer la = new LexicalAnalyzer("function func(var a, b : integer, c : char) : string;");
        do {
            la.nextToken();
            System.out.println(la.curToken().getLexeme().toString() +
                    ": " + la.curToken().getValue());

        } while (la.curToken().getLexeme() != Lexeme.END);
    }


}

