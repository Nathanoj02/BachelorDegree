import value.*;

import java.util.Scanner;

public class IntImp extends ImpBaseVisitor<Value> {

    private final Conf conf;

    public IntImp(Conf conf) {
        this.conf = conf;
    }

    private ComValue visitCom(ImpParser.ComContext ctx) {
        return (ComValue) visit(ctx);
    }

    private int visitIntExp(ImpParser.ExpContext ctx) {
        try {
            return ((IntValue) visit(ctx)).toJavaValue();
        }
        catch (ClassCastException e) {
            System.err.println("Type mismatch exception!");
            System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println(ctx.getText());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println("> Integer or macro expected.");
            System.exit(1);
        }

        return 0;   // unreachable code
    }

    @Override
    public ComValue visitProg(ImpParser.ProgContext ctx) {
        for (ImpParser.FunContext f : ctx.fun())
            visitFun(f);

        return visitCom(ctx.com());
    }

    // ----------------------- Start Fun -----------------------
    @Override
    public ComValue visitFun(ImpParser.FunContext ctx) {
        String id = ctx.ID(0).getText();
        // Per la 1a esecuzione, salva il contesto in memoria
        if (!conf.fun_contains(id))
        {
            conf.fun_update(id, ctx);
            return ComValue.INSTANCE;
        }

        conf.start_fun();

        // Controlla se c'è return
        boolean has_ret = false;

        if (ctx.NON_VOID() == null && ctx.RETURN() != null)
        {
            System.err.println("Missing non-void declaration!");
            System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println(ctx.getText());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.exit(1);
        }

        if (ctx.NON_VOID() != null)
        {
            if (ctx.RETURN() == null)
            {
                System.err.println("Missing return statement!");
                System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
                System.err.println(ctx.getText());
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
                System.exit(1);
            }
            has_ret = true;
        }

        // Copio gli argomenti
        for (int i = 1; i < ctx.ID().size(); i++)
            conf.fetch_argument(ctx.ID(i).getText(), i - 1, ctx);

        // Eseguo il corpo della funzione
        visitCom(ctx.com());

        // Return del valore
        if (has_ret)
        {
            int v = visitIntExp(ctx.exp());
            conf.add_return(new IntValue(v));
        }

        conf.end_fun();

        return ComValue.INSTANCE;
    }

    // ----------------------- End Fun -----------------------

    // ----------------------- Start Com -----------------------
    @Override
    public ComValue visitPrint(ImpParser.PrintContext ctx) {
        System.out.println(visit(ctx.exp()));

        return visitCom(ctx.com());
    }

    @Override
    public ComValue visitVariable(ImpParser.VariableContext ctx) {
        String id = ctx.ID().getText();
        IntValue value = new IntValue(visitIntExp(ctx.exp()));

        conf.update(id, value);

        return visitCom(ctx.com());
    }

    @Override
    public ComValue visitReadInt(ImpParser.ReadIntContext ctx) {
        String id = ctx.ID().getText();

        if (!conf.contains(id)){
            System.err.println("Variable " + id + " used but never instantiated");
            System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());

            System.exit(1);
        }

        System.out.print("Insert value for " + id + ": ");

        Scanner sc = new Scanner(System.in);
        IntValue value = new IntValue(sc.nextInt());

        conf.update(id, value);

        return visitCom(ctx.com());
    }

    @Override
    public ComValue visitAssign(ImpParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visitIntExp(ctx.exp(0));

        for(int i = 1; i < ctx.exp().size(); i++)
        {
            switch (ctx.op.getType())
            {
                case ImpParser.PLUS -> value += visitIntExp(ctx.exp(i));
                case ImpParser.MINUS -> value -= visitIntExp(ctx.exp(i));
                case ImpParser.MUL -> value *= visitIntExp(ctx.exp(i));
                case ImpParser.DIV -> {
                    try {
                        value /= visitIntExp(ctx.exp(i));
                    }
                    catch (ArithmeticException e) {
                        System.err.println("Division by zero exception!");
                        System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
                        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
                        System.err.println(ctx.getText());
                        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
                        System.exit(1);
                    }
                }
                case ImpParser.MOD -> {
                    try {
                        value %= visitIntExp(ctx.exp(i));
                    }
                    catch (ArithmeticException e) {
                        System.err.println("Division by zero in mod exception!");
                        System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
                        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
                        System.err.println(ctx.getText());
                        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
                        System.exit(1);
                    }
                }
                case ImpParser.EQ -> value = value == visitIntExp(ctx.exp(i)) ? 1 : 0;
                case ImpParser.GT -> value = value > visitIntExp(ctx.exp(i)) ? 1 : 0;
                case ImpParser.AND -> value = (value > 0) && (visitIntExp(ctx.exp(i)) > 0) ? 1 : 0;
                case ImpParser.OR -> value = (value > 0) || (visitIntExp(ctx.exp(i)) > 0) ? 1 : 0;
            }
        }

        conf.update(id, new IntValue(value));

        return visitCom(ctx.com());
    }

    @Override
    public ComValue visitIf(ImpParser.IfContext ctx) {
        if (visitIntExp(ctx.exp()) == 1)
            return visitCom(ctx.com(0));
        else {
            try {
                visitCom(ctx.com(1));
                return visitCom(ctx.com(2));
            }
            catch (NullPointerException e) {
                return visitCom(ctx.com(1));
            }
        }
    }

    public ComValue visitWhile(ImpParser.WhileContext ctx) {
        while(visitIntExp(ctx.exp()) == 1)
            visitCom(ctx.com(0));

        return visitCom(ctx.com(1));
    }

    @Override
    public ComValue visitCall(ImpParser.CallContext ctx) {
        // Controllo se vuole un valore di ritorno
        boolean has_ret = ctx.FUN_ASGN() != null;

        // Nome della funzione da eseguire
        String fun_id = ctx.ID(has_ret ? 1 : 0).getText();

        // Carica gli argomenti in memoria
        for (int i = 0; i < ctx.exp().size(); i++)
        {
            String arg_name = "@arg" + i;
            IntValue arg_value = new IntValue(visitIntExp(ctx.exp(i)));
            conf.update(arg_name, arg_value);
        }

        // Cerca la funzione e la esegue
        ImpParser.FunContext f = conf.fun_get(fun_id);
        visitFun(f);

        // Si salva il valore di ritorno
        if (has_ret)
            conf.fetch_return(ctx.ID(0).getText(), ctx);

        return visitCom(ctx.com());
    }

    @Override
    public ComValue visitEmpty(ImpParser.EmptyContext ctx) {
        return ComValue.INSTANCE;
    }

    // ----------------------- End Com -----------------------


    // ----------------------- Start Exp -----------------------
    @Override
    public IntValue visitInt(ImpParser.IntContext ctx) {
        return new IntValue(Integer.parseInt(ctx.INT().getText()));
    }

    @Override
    public IntValue visitMacro0(ImpParser.Macro0Context ctx) {
        return new IntValue(0);
    }

    @Override
    public IntValue visitMacro1(ImpParser.Macro1Context ctx) {
        return new IntValue(1);
    }

    @Override
    public StringValue visitString(ImpParser.StringContext ctx) {
        String s = ctx.STRING().getText()
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
        return new StringValue(s.substring(1, s.length() - 1));
    }

    @Override
    public IntValue visitId(ImpParser.IdContext ctx) {
        String id = ctx.ID().getText();

        if (!conf.contains(id)){
            System.err.println("Variable " + id + " used but never instantiated");
            System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());

            System.exit(1);
        }

        return conf.get(id);
    }
}
