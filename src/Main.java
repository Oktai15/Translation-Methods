import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static List<String> tests = Arrays.asList(
            "procedure proc();",
            "function func() : char;",
            "function func(a : integer) : string;",
            "function func(a, b : integer) : string;",
            "function func(var a, b : integer) : string;",
            "function func(a, b : integer, var c : char) : string;",
            "function func(var a, b : integer, c : char) : string;",
            "function func(var a, b : integer, c : char, var d : integer, var e : string) : string;");

    public static void main(String[] args) throws ParseException {

        Parser parser = new Parser();

        ArrayList<Tree> trees = new ArrayList<>();

        for (String test : tests) {
            trees.add(parser.parse(test));
        }

        Visualizator visualizator = new Visualizator(trees.get(7));
        visualizator.show(tests.get(7));
    }
}

