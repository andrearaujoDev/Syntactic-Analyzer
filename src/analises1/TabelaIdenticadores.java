/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analises1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author valter
 */
public class TabelaIdenticadores {
    private Map<Integer, String> tabela;
    private int key;
     
    public TabelaIdenticadores() {
         this.tabela = new HashMap<Integer, String>();
         this.key = 1;
    }
     
    public void adiciona(String valor) {
        if (!tabela.containsValue(valor)) {
            tabela.put(key, valor);
            key++;
        }
    } 
    
    public Boolean estaContido(String valor) {
        return tabela.containsValue(valor);
    }
}
