import java.text.ParseException;
import java.util.Arrays;

public class Parser {

    private LexicalAnalyzer lex;

    private Tree title() throws ParseException {
        Token curToken = lex.curToken();
        lex.nextToken();
        Tree nameAndArgs = nameAndArgs();
        switch (curToken.getLexeme()) {
            case PROCEDURE: {
                isRightLexemeNow(Lexeme.SEMICOLON);
                return new Tree("title",
                        Arrays.asList(new Tree("procedure"), nameAndArgs, new Tree(";")));
            }
            case FUNCTION: {
                isRightLexemeNow(Lexeme.COLON);
                lex.nextToken();
                isRightLexemeNow(Lexeme.NAME);
                String curType = lex.curToken().getValue();
                lex.nextToken();
                isRightLexemeNow(Lexeme.SEMICOLON);
                return new Tree("title",
                        Arrays.asList(new Tree("function"), nameAndArgs, new Tree(":"),
                                new Tree(curType), new Tree(";")));
            }
            default:
                throw new AssertionError();
        }
    }

    private Tree nameAndArgs() throws ParseException {
        String curValue = lex.curToken().getValue();
        switch (lex.curToken().getLexeme()) {
            case NAME:
                lex.nextToken();
                Tree args = globalAndLocalArgs();
                return new Tree("nameAndArgs", Arrays.asList(new Tree(curValue), args));
            default:
                throw new AssertionError();
        }
    }

    private Tree globalAndLocalArgs() throws ParseException {
        switch (lex.curToken().getLexeme()) {
            case OPENBRACKET: {
                lex.nextToken();
                Tree oneType = oneType();
                Tree otherTypes = moreOneType();
                isRightLexemeNow(Lexeme.CLOSEBRACKET);
                lex.nextToken();
                return new Tree("args", Arrays.asList(new Tree("("), oneType, otherTypes, new Tree(")")));
            }
            case SEMICOLON:
            case COLON: {
                //eps
                return new Tree("args");
            }
            default:
                throw new AssertionError();
        }
    }

    private Tree oneType() throws ParseException {
        Token curToken = lex.curToken();
        switch (lex.curToken().getLexeme()) {
            case VAR:
            case NAME: {
                if (curToken.getLexeme() == Lexeme.VAR) {
                    lex.nextToken();
                }
                //NAME
                String curVar = lex.curToken().getValue();
                lex.nextToken();
                Tree manyVars = moreOneVar();
                isRightLexemeNow(Lexeme.COLON);
                lex.nextToken();
                isRightLexemeNow(Lexeme.NAME);
                String curType = lex.curToken().getValue();
                lex.nextToken();
                if (curToken.getLexeme() == Lexeme.VAR) {
                    return new Tree("onetype", Arrays.asList(new Tree("var"),
                            new Tree(curVar), manyVars, new Tree(":"), new Tree(curType)));
                }
                return new Tree("onetype", Arrays.asList(new Tree(curVar), manyVars,
                        new Tree(":"), new Tree(curType)));
            }
            case CLOSEBRACKET:{
                return new Tree("oneType");
            }
            default:
                throw new AssertionError();
        }
    }


    private Tree moreOneType() throws ParseException {
        switch (lex.curToken().getLexeme()) {
            case COMMA: {
                lex.nextToken();
                Tree oneType = oneType();
                Tree otherTypes = moreOneType();
                return new Tree("more1type", Arrays.asList(new Tree(","), oneType, otherTypes));
            }
            case CLOSEBRACKET: {
                //eps
                return new Tree("more1type");
            }
            default:
                throw new AssertionError();
        }
    }

    private Tree moreOneVar() throws ParseException {
        switch (lex.curToken().getLexeme()) {
            case COMMA: {
                lex.nextToken();
                isRightLexemeNow(Lexeme.NAME);
                String curValue = lex.curToken().getValue();
                lex.nextToken();
                Tree otherNames = moreOneVar();
                return new Tree("more1var",
                        Arrays.asList(new Tree(","), new Tree(curValue), otherNames));
            }
            case COLON: {
                //eps
                return new Tree("more1var");
            }
            default:
                throw new AssertionError();
        }
    }

    private void isRightLexemeNow(Lexeme rightLexeme) throws ParseException {
        Lexeme curLexeme = lex.curToken().getLexeme();
        if (curLexeme != rightLexeme) {
            String rightString = rightLexeme.toString().toLowerCase();
            String curString = curLexeme.toString().toLowerCase();
            throw new ParseException(rightString + " was expected instead of " + curString + " at position " +
                    (Integer.toString(lex.curPos())), lex.curPos());
        }
    }

    Tree parse(String exp) throws ParseException {
        lex = new LexicalAnalyzer(exp);
        lex.nextToken();
        return title();
    }
}
