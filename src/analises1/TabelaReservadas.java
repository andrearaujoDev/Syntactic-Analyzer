package analises1;

import java.util.HashMap;
import java.util.Map;

public class TabelaReservadas {
    Map<Integer, String> tabela = new HashMap<Integer, String>();
    
    public TabelaReservadas() {
        tabela.put(1, "program");
        tabela.put(2, "uses");
        tabela.put(3, "begin");
        tabela.put(4, "end");
        tabela.put(5, "const");
        tabela.put(6, "integer");
        tabela.put(7, "real");
        tabela.put(8, "boolean");
        tabela.put(9, "char");
        tabela.put(10, "string");
        tabela.put(11, "var");
        tabela.put(12, "array");
        tabela.put(13, "of");
        tabela.put(14, "for");
        tabela.put(15, "to");
        tabela.put(16, "do");
        tabela.put(17, "while");
        tabela.put(18, "repeat");
        tabela.put(19, "until");
        tabela.put(20, "and");
        tabela.put(21, "or");
        tabela.put(22, "not");
        tabela.put(23, "if");
        tabela.put(24, "then");
        tabela.put(25, "else");
        tabela.put(26, "write");
        tabela.put(27, "writeln");
        tabela.put(28, "read");
        tabela.put(29, "readln");
    }
    
    public Boolean estaContido(String valor) {
        return tabela.containsValue(valor);
    }
    
}
