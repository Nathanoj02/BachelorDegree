import value.IntValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Conf {

    // Lista per gestire i vari ambienti delle funzioni
    private final ArrayList<FunConf> confs = new ArrayList<>();

    // Indica in quale ambiente stiamo lavorando
    private int conf_num = -1;

    public Conf() {
        this.confs.add(new FunConf());
        conf_num++;
    }

    public boolean contains(String id) {
        return confs.get(conf_num).contains(id);
    }

    public IntValue get(String id) {
        return confs.get(conf_num).get(id);
        /* Per variabili non locali
        for (int i = conf_num; i >= 0; i--)
        {
            IntValue v = confs.get(i).get(id);
            if (v != null)  return v;
        }
        return null;*/
    }

    public void update(String id, IntValue v) {
        confs.get(conf_num).update(id, v);
    }

    public void remove(String id) {
        confs.get(conf_num).remove(id);
    }

    public boolean fun_contains(String id) {
        return confs.get(0).fun_contains(id);
    }

    public ImpParser.FunContext fun_get(String id) {
        return confs.get(0).fun_get(id);
    }

    public void fun_update(String id, ImpParser.FunContext fctx) {
        confs.get(conf_num).fun_update(id, fctx);
    }

    // Ottiene il valore dell'argomento id attraverso il numero dell'arogmento, poi lo rimuove dalla memoria
    public void fetch_argument(String id, int arg_num, ImpParser.FunContext ctx)
    {
        String arg_id = "@arg" + arg_num;

        IntValue arg_value = confs.get(conf_num - 1).get(arg_id);

        if (arg_value == null)
        {
            System.err.println("Not enough arguments!");
            System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println(ctx.getText());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.exit(1);
        }

        confs.get(conf_num - 1).remove(arg_id);

        this.update(id, arg_value);
    }

    // Imposta il valore del return
    public void add_return(IntValue v) {
        confs.get(conf_num - 1).update("@ret", v);
    }

    // Ottiene valore del return e lo mette dentro id (ctx serve solo per stampa errore)
    public void fetch_return(String id, ImpParser.CallContext ctx) {
        IntValue v = this.get("@ret");

        if (v == null)
        {
            System.err.println("Missing return value in called function!");
            System.err.println("@ " + ctx.start.getLine() + " : " + ctx.start.getCharPositionInLine());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.err.println(ctx.getText());
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>");
            System.exit(1);
        }

        this.remove("@ret");
        this.update(id, v);
    }

    // Quando inizia una funzione, crea un nuovo ambiente
    public void start_fun()
    {
        confs.add(new FunConf());
        conf_num++;
    }

    // Quando termina una funzione, rimuove l'ambiente
    public void end_fun()
    {
        confs.remove(conf_num);
        conf_num--;
    }

    private class FunConf {

        // Memoria variabili
        private final Map<String, IntValue> map = new HashMap<>();

        // Memoria delle funzioni (salva i contesti)
        private final Map<String, ImpParser.FunContext> fun_map = new HashMap<>();

        public boolean contains(String id) {
            return map.containsKey(id);
        }

        public IntValue get(String id) {
            return map.get(id);
        }

        public void update(String id, IntValue v) {
            map.put(id, v);
        }

        public void remove(String id) {
            map.remove(id);
        }

        public boolean fun_contains(String id) {
            return fun_map.containsKey(id);
        }

        public ImpParser.FunContext fun_get(String id) {
            return fun_map.get(id);
        }

        public void fun_update(String id, ImpParser.FunContext fctx) {
            fun_map.put(id, fctx);
        }
    }
}
