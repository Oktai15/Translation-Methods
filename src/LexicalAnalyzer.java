import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    private final String exp;
    private int curPos;
    private Token curToken;
    private Matcher mName;

    public LexicalAnalyzer(String exp) {
        this.exp = exp.trim(); //!
        this.curPos = 0;
        Pattern pName = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");
        mName = pName.matcher(this.exp);
    }

    private void nextPos() {
        ++curPos;
    }

    private void nextPos(int length) {
        curPos += length;
    }

    public void nextToken() throws ParseException {
        if (curPos + 1 > exp.length()) {
            curToken = new Token(Lexeme.END, "");
            return;
        }

        while (isBlank(exp.charAt(curPos))) {
            nextPos();
        }

        if (Character.isLetter(exp.charAt(curPos))) {
            if (exp.charAt(curPos) == 'v') { //var
                if (curPos + 2 < exp.length() && exp.substring(curPos, curPos + 3).equals("var")) {
                    nextPos(3);
                    curToken = new Token(Lexeme.VAR, "");
                    return;
                }
            }

            if (exp.charAt(curPos) == 'p') { //procedure
                if (curPos + 8 < exp.length() && exp.substring(curPos, curPos + 9).equals("procedure")) {
                    nextPos(9);
                    curToken = new Token(Lexeme.PROCEDURE, "");
                    return;
                }
            }

            if (exp.charAt(curPos) == 'f') { //function
                if (curPos + 7 < exp.length() && exp.substring(curPos, curPos + 8).equals("function")) {
                    nextPos(8);
                    curToken = new Token(Lexeme.FUNCTION, "");
                    return;
                }
            }

            if (mName.find(curPos)) {
                String curName = exp.substring(mName.start(), mName.end());
                curToken = new Token(Lexeme.NAME, curName);
                nextPos(curName.length());
                return;
            }
        }

        switch (exp.charAt(curPos)) {
            case (':'): {
                curToken = new Token(Lexeme.COLON, "");
                nextPos();
                return;
            }
            case (';'): {
                curToken = new Token(Lexeme.SEMICOLON, "");
                nextPos();
                return;
            }
            case ('('): {
                curToken = new Token(Lexeme.OPENBRACKET, "");
                nextPos();
                return;
            }
            case (')'): {
                curToken = new Token(Lexeme.CLOSEBRACKET, "");
                nextPos();
                return;
            }
            case (','): {
                curToken = new Token(Lexeme.COMMA, "");
                nextPos();
                return;
            }
            default:
                throw new ParseException("Illegal character " + exp.charAt(curPos) +
                        " pos: " + (Integer.toString(curPos)), curPos);
        }
    }

    private boolean isBlank(int curChar) {
        return curChar == ' ' || curChar == '\r' || curChar == '\n' || curChar == '\t';
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }


}
