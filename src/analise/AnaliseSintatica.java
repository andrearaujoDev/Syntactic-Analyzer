package analise;

import javax.swing.JOptionPane;

public class AnaliseSintatica {
    private Token token;
    private AnaliseLexica mt;
    
    public AnaliseSintatica(String codigo) {
        this.token = new Token();
        this.mt = new AnaliseLexica(codigo);
        PROGRAM();
    }
    //PROGRAM
    public void PROGRAM() {
        token = mt.geraToken(); // pega o prox token
        if (token.getStr().toString().equals("program")){ // se for program continua rodando e pega o prox token
            token = mt.geraToken();
            if (token.getTipo() == TiposToken.ID) {
                mt.getTabelaIdenticadores().adiciona(token.getStr().toString()); //Adiciona o token
                token = mt.geraToken();       
                
                if(token.getStr().toString().equals(";")){
                    LIB();
                    CONST();
                    VAR();
                    
                    if(!token.getStr().toString().equals("begin")){
                      token = mt.geraToken();  
                    }
                    
                    if (token.getStr().toString().equals("begin")) {
                        //token = mt.geraToken();
                        LISTA_COM();
                        
                        if(!token.getStr().toString().equals("end")){
                           token = mt.geraToken(); 
                        }
                        
                        if (token.getStr().toString().equals("end")){
                            token = mt.geraToken();
                            if(token.getStr().toString().equals(".")){
                                JOptionPane.showMessageDialog(null, "Arquivo compilado com sucesso!");
                                return;
                            }
                            else ERRO (".", token.getStr().toString());
                        }
                        else ERRO ("end", token.getStr().toString());
                    }
                    else ERRO ("begin", token.getStr().toString());
                }
                else ERRO (";", token.getStr().toString());
            }
            else ERRO ("Identificador", token.getStr().toString());
        }
        else ERRO ("program", token.getStr().toString());
    }
    
    //LIB
    
    public void LIB() {
        token = mt.geraToken();
        
        if(token.getStr().toString().equals("uses")){
            token = mt.geraToken();
            LISTA_ID();
            
            if(token.getStr().toString().equals(";")){
                return;
            }
            else ERRO(";", token.getStr().toString());
        }
        else return;
    }
    
     public void LISTA_ID() {
        if (token.getTipo() == TiposToken.ID){
            LISTA_ID2();
        }
        else ERRO("identificador", token.getStr().toString());
    }
    
    public void LISTA_ID2(){
        token = mt.geraToken();
        if (token.getStr().toString().equals(",")){
            token = mt.geraToken();
            LISTA_ID();
        }
        else return;
    }
    
    //CONST
    
    public void CONST(){
        token = mt.geraToken();
        if (token.getStr().toString().equals("const")){
            token = mt.geraToken();
            DEC_CONST();
        }
        else return;
    }
    
    public void DEC_CONST(){
        
        if (token.getTipo() == TiposToken.ID){
            token = mt.geraToken();
            if (token.getStr().toString().equals("=")){
                token = mt.geraToken();
                EXP_ATRIB();
                
                //token = mt.geraToken();
                if (token.getStr().toString().equals(";")){
                   DEC_CONST2();
                }else{
                    ERRO(";",token.getStr().toString());
                    //Para evitar de dar sucesso depois de gerar erro
                    token = mt.geraToken();
                }
            }
            else ERRO("=", token.getStr().toString());
        }
        else ERRO("identificador", token.getStr().toString());
        
    }
    
    public void DEC_CONST2(){
        token = mt.geraToken();
        if(token.getTipo() == TiposToken.ID){
            DEC_CONST();
        }else{
            return;
        }
    }
    
    //VAR
    
    public void VAR() {
        //token = mt.geraToken();
        if(token.getStr().toString().equals("var")){
            token = mt.geraToken();
            DEC_VAR();
        }else{
            return; //lambda
        }
    }
    
    public void DEC_VAR(){  
       
        if (token.getTipo() == TiposToken.ID){
            LISTA_ID();
            if(token.getStr().toString().equals(":")){
                DEC_VAR2();
            }
        }        
        else if(token.getStr().toString().equals("^")){
            LISTA_PONT();
            
            token = mt.geraToken();
            if(token.getStr().toString().equals(":")){
                DEC_VAR2();
            }
        }
    }
    
    public void DEC_VAR2(){
        token = mt.geraToken();

        if(token.getStr().toString().equals("integer") || token.getStr().toString().equals("real") || 
                token.getStr().toString().equals("boolean") || token.getStr().toString().equals("char") || 
                token.getStr().toString().equals("string")){
            TIPO();        
            token = mt.geraToken();
            if(token.getStr().toString().equals(";")){
                token = mt.geraToken();
                DEC_VAR3();
            }else{
                ERRO (";", token.getStr().toString());
                //Para evitar de dar sucesso depois de gerar erro
                token = mt.geraToken();
            }
        }else if(token.getStr().toString().equals("array")){
            token = mt.geraToken();
            
            if(token.getStr().toString().equals("[")){
                
                INTERVALO();
                //token = mt.geraToken();
                if(token.getStr().toString().equals("]")){
                    
                    token = mt.geraToken();
                    if(token.getStr().toString().equals("of")){
                        token = mt.geraToken();
                        TIPO();
                        token = mt.geraToken();
                        if(token.getStr().toString().equals(";")){
                            token = mt.geraToken();
                            DEC_VAR3();
                        }else{
                            ERRO(";", token.getStr().toString());
                            //Para evitar de dar sucesso depois de gerar erro
                            token = mt.geraToken();
                        }
                    }
                    else ERRO("of", token.getStr().toString());
                }
                else ERRO("]", token.getStr().toString());
            }
            else ERRO("[", token.getStr().toString());
        }
        else ERRO("array", token.getStr().toString());
    }
    
    public void DEC_VAR3(){
        if(token.getTipo() == TiposToken.ID){
           DEC_VAR();  
        }else if(token.getStr().toString().equals("^")){
            token = mt.geraToken();
            DEC_VAR(); 
        }
        else{
           return; 
        }
        
       
    }
    
    public void INTERVALO(){
        token = mt.geraToken();
        
        if(token.getTipo() == TiposToken.CTE){
            token = mt.geraToken();
            if(token.getStr().toString().equals(".")){
                token = mt.geraToken();
                if(token.getStr().toString().equals(".")){
                    token = mt.geraToken();
                    if(token.getTipo() == TiposToken.CTE){
                        INTERVALO2();
                    }
                    else ERRO("constante", token.getStr().toString()); 
                }
                else ERRO(".", token.getStr().toString()); 
                
            }else ERRO(".", token.getStr().toString()); 
        }
        else ERRO("constante", token.getStr().toString()); 
    }
    
    public void INTERVALO2(){
        token = mt.geraToken();
        if(token.getStr().toString().equals(",")){
            token = mt.geraToken();
            if(token.getTipo() == TiposToken.CTE){
                token = mt.geraToken();
                if(token.getStr().toString().equals(".")){
                    token = mt.geraToken();
                    if(token.getStr().toString().equals(".")){
                        token = mt.geraToken();
                        if(token.getTipo() == TiposToken.CTE){
                            token = mt.geraToken();
                            return;
                        }
                        else ERRO("constante", token.getStr().toString()); 
                }
                else ERRO(".", token.getStr().toString()); 
            }
            else ERRO(".", token.getStr().toString()); 
        }
        else ERRO("constante", token.getStr().toString());
        }else{
            return;
        }
    }
    
    public void TIPO(){
        if(token.getStr().toString().equals("integer") || token.getStr().toString().equals("real") || 
                token.getStr().toString().equals("boolean") || token.getStr().toString().equals("char") || 
                token.getStr().toString().equals("string")){
            return;
        }
        else ERRO("tipo reservado", token.getStr().toString());
    }
    
    public void LISTA_PONT(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("^")){
            
            token = mt.geraToken();
            if(token.getTipo() == TiposToken.ID){
                LISTA_PONT2();
            }
        }
    }
    
    public void LISTA_PONT2(){
        token = mt.geraToken();
        if(token.getStr().toString().equals(",")){
            LISTA_PONT3();
        }else{
            return;
        }
    }
    
    public void LISTA_PONT3(){
        token = mt.geraToken();
        if(token.getStr().toString().equals("^")){
            LISTA_PONT();
        }else if(token.getTipo() == TiposToken.ID){
            LISTA_ID();
        }
        else ERRO("^ ou identificador", token.getStr().toString());
        
    }
    
    //COMANDOS
    
    public void LISTA_COM(){
        token = mt.geraToken();
        if((token.getTipo() == TiposToken.ID) || (token.getTipo() == TiposToken.CTE) || (token.getStr().toString().equals("(")) || 
           (token.getStr().toString().equals("read")) || (token.getStr().toString().equals("readln")) || 
           (token.getStr().toString().equals("write")) || (token.getStr().toString().equals("writeln")) ||
           (token.getStr().toString().equals("for")) || (token.getStr().toString().equals("while")) || 
           (token.getStr().toString().equals("repeat")) || (token.getStr().toString().equals("if"))){
            COMANDO();
            LISTA_COM();
        }else{
            return;
        }
    }
    
    public void COMANDO(){
        if((token.getStr().toString().equals("read")) || (token.getStr().toString().equals("readln"))){
            ENTRADA();
            
            if(!token.getStr().toString().equals(";")){
                token = mt.geraToken();
            }
            
            if(token.getStr().toString().equals(";")){
                return;
            }
            else ERRO(";", token.getStr().toString());
        }else if((token.getStr().toString().equals("write")) || (token.getStr().toString().equals("writeln"))){
            SAIDA();
            
            token = mt.geraToken();
            if(token.getStr().toString().equals(";")){
                return;
            }
            else ERRO(";", token.getStr().toString());
            
        }else if(token.getStr().toString().equals("for")){
            FOR();
        }else if(token.getStr().toString().equals("while")){
            WHILE();
        }else if(token.getStr().toString().equals("repeat")){
            REPEAT();
            
            //token = mt.geraToken();
            if(token.getStr().toString().equals(";")){
                return;
            }
            else ERRO(";", token.getStr().toString());
        }else if(token.getStr().toString().equals("if")){
            IF();
            
            token = mt.geraToken();
            if(token.getStr().toString().equals(";")){
                return;
            }
            else ERRO(";", token.getStr().toString());
            
        }else if(token.getTipo() == TiposToken.ID || token.getTipo() == TiposToken.CTE || token.getStr().toString().equals("(")){
            ATRIB();
            
            if (!(token.getStr().toString().equals(";"))){
                token = mt.geraToken();
            }
            if(token.getStr().toString().equals(";")){
                return;
            }
            else ERRO(";", token.getStr().toString());
            
        }
        else ERRO("ID || CTE || ) || read || write || for || while || repeat || if", token.getStr().toString());
    }
    
    public void ESCOPO(){
        //token = mt.geraToken();
        
        if(token.getStr().toString().equals("begin")){
            LISTA_COM();
            
            if(!token.getStr().toString().equals("end")){
                token = mt.geraToken();
            }
            else if(token.getStr().toString().equals("end")){
                
                token = mt.geraToken();
                if(token.getStr().toString().equals(";")){
                    return;
                }
                else ERRO(";", token.getStr().toString());
            }
            else ERRO("end", token.getStr().toString());
        }
        else ERRO("begin", token.getStr().toString());
    }
    
    public void CORPO(){
        token = mt.geraToken();
        if(token.getTipo() == TiposToken.ID || token.getTipo() == TiposToken.CTE || token.getStr().toString().equals("(") ){
           COMANDO(); 
        }else if(token.getStr().toString().equals("begin")){
            ESCOPO();
        }
    }
    
    public void COMANDO2(){
        //token = mt.geraToken();
        if(token.getTipo() == TiposToken.ID || token.getTipo() == TiposToken.CTE || token.getStr().toString().equals("(")){
           ATRIB(); 
        }else if((token.getStr().toString().equals("read")) || (token.getStr().toString().equals("readln"))){
            ENTRADA();
        }else if((token.getStr().toString().equals("write")) || (token.getStr().toString().equals("writeln"))){
            SAIDA();
        }else if(token.getStr().toString().equals("for")){
            FOR2();
        }else if(token.getStr().toString().equals("while")){
            WHILE2();
        }else if(token.getStr().toString().equals("repeat")){
            REPEAT();
        }else if(token.getStr().toString().equals("if")){
            IF();
        }else ERRO(" read || write || for || while || repeat || if ", token.getStr().toString());
    }
    
    public void ESCOPO2(){
        token = mt.geraToken();
        if(token.getStr().toString().equals("begin")){       
            LISTA_COM();
            
            token = mt.geraToken();
            if(token.getStr().toString().equals("end")){
                return;
            }
            else ERRO("end", token.getStr().toString());
        }
        else ERRO("begin", token.getStr().toString());
    }
    
    public void CORPO2(){
        token = mt.geraToken();
        if((token.getTipo() == TiposToken.ID) || (token.getTipo() == TiposToken.CTE) || (token.getStr().toString().equals("(")) || 
           (token.getStr().toString().equals("read")) || (token.getStr().toString().equals("readln")) || 
           (token.getStr().toString().equals("write")) || (token.getStr().toString().equals("writeln")) ||
           (token.getStr().toString().equals("for")) || (token.getStr().toString().equals("while")) || 
           (token.getStr().toString().equals("repeat")) || (token.getStr().toString().equals("if"))){
            COMANDO2();
        }else if(token.getStr().toString().equals("begin")){
            ESCOPO2();
        }
        else ERRO("identificador ou begin" , token.getStr().toString());
    }
    
    public void TERMO(){
        token = mt.geraToken();
        if ((token.getTipo() == TiposToken.ID) || (token.getTipo() == TiposToken.CTE)){
            return;
        }else{
            ERRO("CTE ou identificador", token.getStr().toString());
        }
    }
    
    public void LISTA_TERMO(){
        token = mt.geraToken();
        if (token.getStr().toString().equals((","))){
            TERMO();
            LISTA_TERMO();
        }else{
            return;
        }
    }
    
    //ATRIB
    
    public void ATRIB(){
        //token = mt.geraToken();
        if(token.getTipo() == TiposToken.ID){
            token = mt.geraToken();
            if(token.getStr().toString().equals(":=")){
                token = mt.geraToken();
                EXP_ATRIB();
            }
            else ERRO(":=", token.getStr().toString());
       }
        else ERRO("identificador", token.getStr().toString());
    }
    
    //EXP_ATRIB
    
    public void EXP_ATRIB(){
        MUL();
    }
    
    public void MUL(){
        DIV();
        MUL2();
    }
    
    public void MUL2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("*")){
            token = mt.geraToken();
            MUL();
        }else{
            return;
        }
    }
    
    public void DIV(){
        SOM();
        DIV2();
    }
   
    public void DIV2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("/")){
            token = mt.geraToken();
            DIV();
        }else{
            return;
        }
    }
    
    public void SOM(){
        SUB();
        SOM2();
    }
    
    public void SOM2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("+")){
            token = mt.geraToken();
            SOM();
        }else{
            return;
        }
    }
    
    public void SUB(){
        OPERANDO_ATRIB();
        SUB2();
    }
    
    public void SUB2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("-")){
            token = mt.geraToken();
            SUB();
        }else{
            return;
        }
    }
    
    public void OPERANDO_ATRIB(){
        //token = mt.geraToken();
        if ((token.getTipo() == TiposToken.CTE) || (token.getTipo() == TiposToken.ID)){
            token = mt.geraToken();
            return;
        }
        else if (token.getStr().toString().equals("(")) {
            token = mt.geraToken();
            MUL();
            
            if(token.getStr().toString().equals(")")){
                return;
            }
            else ERRO(")", token.getStr().toString());
            
        }        
        else{
           ERRO("CTE , ID ou ( ", token.getStr().toString());
        }
    }
    
    //Expressões Relacionais
    
    public void EXP_REL(){
        EXP_ATRIB();
        OP_REL();
        EXP_ATRIB();
    }
    
    public void OP_REL(){
        //token = mt.geraToken();
        if((token.getStr().toString().equals("=")) || (token.getStr().toString().equals("<>")) || 
                (token.getStr().toString().equals(">=")) || (token.getStr().toString().equals("<=")) ||
                (token.getStr().toString().equals("<")) || (token.getStr().toString().equals(">"))){
            token = mt.geraToken();
            return;
        }
        else ERRO("Expressão Relacional", token.getStr().toString());
    }
    
    //Expressões Booleanas
    
    public void EXP_BOOL(){
        AND();
    }
    
    public void AND(){
        OR();
        AND2();
    }
    
    public void AND2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("and")){
            AND();
        }else{
            return;
        }
    }
    
    public void OR(){
        OPERANDO_BOOL();
        OR2();
    }
    
    public void OR2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("or")){
            OR();
        }else{
            return;
        }
    }
    
    public void OPERANDO_BOOL(){
        token = mt.geraToken();
        if(token.getTipo() == TiposToken.ID || token.getTipo() == TiposToken.CTE || token.getStr().toString().equals("(") ){
            EXP_REL();
        }else if(token.getStr().toString().equals("not")){
            OPERANDO_BOOL();
        }
        else ERRO("identificador || constante || ( ", token.getStr().toString());

    }
    
    //SAIDA
    
    public void SAIDA(){
        if (token.getStr().toString().equals("write")){
            token = mt.geraToken();
            if (token.getStr().toString().equals("(")){
                TERMO();
                LISTA_TERMO();
                if (token.getStr().toString().equals(")")){
                    return;
                }
                else ERRO(")", token.getStr().toString());
                
            }
        }else if (token.getStr().toString().equals("writeln")){
            WRITELN();
        }
        else ERRO("write || writeln", token.getStr().toString());
    }
    
    public void WRITELN(){
        token = mt.geraToken();
        if (token.getStr().toString().equals("(")){
            TERMO();
            LISTA_TERMO();
            
            //token = mt.geraToken();
            if (token.getStr().toString().equals(")")){
                
                return;
            }
            
        }else{
            return;
        }
    }
    
    //ENTRADA
    
    public void ENTRADA(){
        if(token.getStr().toString().equals("read")){
            token = mt.geraToken();
            if (token.getStr().toString().equals("(")){
                token = mt.geraToken();
                LISTA_ID();
                
                token = mt.geraToken();
                if (token.getStr().toString().equals(")")){
                    return;
                }
                else ERRO(")", token.getStr().toString());
            }
            else ERRO("(", token.getStr().toString());
        }else if(token.getStr().toString().equals("readln")){
            READLN();
        }
        else ERRO("read || readln", token.getStr().toString());

    }
    
    public void READLN(){
        token = mt.geraToken();
        if (token.getStr().toString().equals("(")){ 
            token = mt.geraToken();
            LISTA_ID();
            if (token.getStr().toString().equals(")")){
               return;
            }
            else ERRO(")", token.getStr().toString());
        }else{
            return;
        }
    }
    
    //FOR
    
    public void FOR(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("for")){
            token = mt.geraToken();
            ATRIB();
            
            //token = mt.geraToken();
            if(token.getStr().toString().equals("to")){
                token = mt.geraToken();
                EXP_ATRIB();
                
                //token = mt.geraToken();
                if(token.getStr().toString().equals("do")){                                        
                    CORPO();
                }
                else ERRO("do", token.getStr().toString());
            }
            else ERRO("to", token.getStr().toString());
        }
        else ERRO("for", token.getStr().toString());
    }
    
    public void FOR2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("for")){
            token = mt.geraToken();
            ATRIB();
            
            //token = mt.geraToken();
            if(token.getStr().toString().equals("to")){
                token = mt.geraToken();
                EXP_ATRIB();
                
                token = mt.geraToken();
                if(token.getStr().toString().equals("do")){
                    CORPO2();
                }
                else ERRO("do", token.getStr().toString());
            }
            else ERRO("to", token.getStr().toString());
        }
        else ERRO("for", token.getStr().toString());
    }
    
    //WHILE
    
    public void WHILE(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("while")){
            EXP_BOOL();
            
            //token = mt.geraToken();
            if(token.getStr().toString().equals("do")){
                //observação
                CORPO();
            }
            else ERRO("do", token.getStr().toString());
        }
        else ERRO("while", token.getStr().toString());
    }
    
    public void WHILE2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("while")){
            EXP_BOOL();
            
            token = mt.geraToken();
            if(token.getStr().toString().equals("do")){
                CORPO2();
            }
            else ERRO("do", token.getStr().toString());
        }
        else ERRO("while", token.getStr().toString());
    }
    
    //REPEAT
    
    public void REPEAT(){
            //token = mt.geraToken();
            if(token.getStr().toString().equals("repeat")){
                LISTA_COM();
                
                //token = mt.geraToken();
                if(token.getStr().toString().equals("until")){
                    EXP_BOOL();
                }
                else ERRO("until", token.getStr().toString());
            }
            else ERRO("repeat", token.getStr().toString());
    }
    
    //IF
    
    public void IF(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("if")){
            EXP_BOOL();
            
            //token = mt.geraToken();
            if(token.getStr().toString().equals("then")){
                CORPO2();
                token = mt.geraToken();
                IF2();
            }
            else ERRO("then", token.getStr().toString());
        }
        else ERRO("if", token.getStr().toString());
        
    }
    
    public void IF2(){
        //token = mt.geraToken();
        if(token.getStr().toString().equals("else")){
            CORPO2();
        }else{
            return;
        }
    }
    
    public void ERRO(String esperado, String obtido) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro na análise! Era esperado " + esperado + " e foi obtido " + obtido);
        return;
    }
}
