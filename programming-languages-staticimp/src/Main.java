import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStream;

public class Main
{
    public static void main(String[] args) throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream(args[0]);
        CharStream cs = CharStreams.fromStream(is);

        ImpLexer lexer = new ImpLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ImpParser parser = new ImpParser(tokens);

        ParseTree tree = parser.prog();

        IntImp interpreter = new IntImp(new Conf());
        interpreter.visit(tree);
    }
}
