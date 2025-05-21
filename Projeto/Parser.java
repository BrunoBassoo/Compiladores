import java.util.List;

public class Parser {
    
    // ------------------------ //
    // PADRAO DE QUALQUER CÓDIGO //
    private List<Token> tokens;
    private Token token;
    private int tokenIndex;
    private Tree tree;
    private Node root;
    private KotlinWriter writer;

    public Parser(List<Token> tokens, KotlinWriter writer){
        this.tokens = tokens;
        this.tokenIndex = 0;
        this.root = new Node("--> RAIZ <--");
        this.tree = new Tree(root);
        this.writer = writer;
    }

    public Token getNextToken(){
        if(tokenIndex < tokens.size()){
            return tokens.get(tokenIndex++);
        }
        return null;
    }

    private void erro(String regra){
        System.out.println("Regra: " + regra);
        System.out.println("token inválido: " + token.getLexema());
        System.exit(0);
    }

    public Tree main(){
        HEADER();
        token = getNextToken();
        if (programa(root)){
            if(token.getTipo().equals(TokenType.EOF)){
                SHOLDER();
                System.out.println("\nSintaticamente correto!\n");

                System.out.println("---------------------");
                return tree;
            } 
            System.out.println("---------------------");
            System.out.println("\n\nSintaticamente incorreto!");
            return null;
        }
        
        erro("main");
        return null;
        
    }


    // V = CRIANDO A MAIN 
    private void HEADER(){
        System.out.println("fun main(){\n");
        writer.write("fun main(){\n");
    }

    // V
    public void SHOLDER(){
        System.out.println("\n}");
        writer.write("\n}");
    }
    
    // V = Programa → 'magic' ListaComandos 'endmagic'
    public boolean programa(Node root){
        Node programa = new Node("PROGRAMA");

        if(matchT(TokenType.MAGIC,"",programa)
        && listaComandos(programa)
        && matchT(TokenType.END_MAGIC,"",programa)
        ){
            root.addNode(programa);
            return true;
        } return false;
    }

    // V = ListaComandos → Comando ListaComandos | ε
    public boolean listaComandos(Node root){
        Node listaComando = new Node("LISTA COMANDOS");
        
        if(comando(listaComando) && listaComandos(listaComando)
        || true
        ){
            root.addNode(listaComando);
            return true;
        } return false;
    }

    // V = Comando → Declaracao | Atribuicao | Incendio | Accio | Crucio | Alohomora | Spell | Revelio | Legilimens | 'relashio' ';' | 'avadakedavra' ';' | 'finite' Expressao ';'
    public boolean comando(Node root){
        Node comando = new Node("COMANDO");

        if(declaracao(comando) 
        || atribuicao(comando) 
        || incendio(comando) 
        || accio(comando) 
        || crucio(comando) 
        || spell(comando)
        || revelio(comando)
        || legilimens(comando)
        || comentario(comando)
        || matchT(TokenType.RELASHIO, "\ncontinue",comando) && fim(comando)
        || matchT(TokenType.AVADAKEDAVRA, "\nbreak",comando) && fim(comando)
        || matchT(TokenType.FINITE,"\nreturn ",comando) && expressao(comando) && fim(comando)
        ){
            root.addNode(comando);
            return true;
        } 
        return false;
    }

    // V = Declaracao → Tipo 'id' declaracaoL
    public boolean declaracao(Node root){
        Node declaracao = new Node("DECLARACAO");
        
        if(tipo(declaracao) 
        && identifier(declaracao) 
        && declaracaoL(declaracao)){
            root.addNode(declaracao);
            return true;
        } 
        return false;
    }


    // V = Declaracao → '=' declaracaoL2
    public boolean declaracaoL(Node root){
        Node declaracaoL = new Node("DECLARACAOL");

        if(matchT(TokenType.EQUAL,"=",declaracaoL) && declaracaoL2(declaracaoL)){
            root.addNode(declaracaoL);
            return true;
        } 
        return false;
    }

    public boolean declaracaoL2(Node root){
        Node declaracaoL2 = new Node("DECLARACAOL2");

        if(expressao(declaracaoL2) && fim(declaracaoL2)
        || texto(declaracaoL2) && fim(declaracaoL2)){
            root.addNode(declaracaoL2);
            return true;
        } 
        return false;
    }


    // V = Atribuicao → 'id' '=' atribuicaoL
    public boolean atribuicao(Node root){
        Node atribuicao = new Node("ATRIBUICAO");

        if(identifier(atribuicao) 
        && matchT(TokenType.EQUAL,"=",atribuicao)
        && atribuicaoL(atribuicao)
        ){
            root.addNode(atribuicao);
            return true;
        } 
        return false;
    }

    // V = AtribuicaoL → expressao ';' | valor ';'
    public boolean atribuicaoL(Node root){
        Node atribuicaoL = new Node("ATRIBUICAO L");
        if(expressao(atribuicaoL) && fim(atribuicaoL)
        || valor(atribuicaoL) && fim(atribuicaoL)
        || texto(atribuicaoL) && fim(atribuicaoL)){
            root.addNode(atribuicaoL);
            return true;
        } 
        return false;
    }


    public boolean atribuicaoFor(Node root){
        Node atribuicaoFor = new Node("atribuicaoFor");

        if((matchT(TokenType.INT, "", atribuicaoFor) 
            && identifier(atribuicaoFor) 
            && matchL("=", " in ", atribuicaoFor) 
            && number(atribuicaoFor) 
            && matchL(";", "", atribuicaoFor))
            ||
            (identifier(atribuicaoFor) 
            && matchL("=", " in ", atribuicaoFor) 
            && number(atribuicaoFor) 
            && matchL(";", "", atribuicaoFor))){
            root.addNode(atribuicaoFor);
            return true;
        } // int x = 0; ====== x in 0
        return false;
    }


    // V = Tipo → 'int' | 'dec' | 'str' | 'boolean'
    public boolean tipo(Node root){
        Node tipo = new Node("TIPO");
        if(matchL("int", "var ",tipo)
        || matchL("dec", "var ",tipo)
        || matchL("str", "var ",tipo)
        || matchL("boolean", "var ",tipo)){
            root.addNode(tipo);
            return true;
        } 
        return false;
    }

    // V = Incendio → 'incendio' '(' Expressao ')' '{' ListaComandos '}' Deflexio
    public boolean incendio(Node root){
        Node incendio = new Node("INCENDIO");
        
        if(matchT(TokenType.INCENDIO, "\nif ",incendio) 
            && matchL("(","(",incendio)
            && expressao(incendio)
            && matchL(")",")",incendio)
            && matchL("{","{\n",incendio)
            && listaComandos(incendio)
            && matchL("}","}",incendio)
            && deflexio(incendio)
            ){
            root.addNode(incendio);
            return true;
        } 
        return false;
    }

    // V = Deflexio → 'deflexio' '(' Expressao ')' '{' ListaComandos '}' Deflexio | Protego | ε
    public boolean deflexio(Node root){
        Node deflexio = new Node("DEFLEXIO");
        if((matchT(TokenType.DEFLEXIO, "\nelse if ",deflexio)
        && matchL("(","(",deflexio)
        && expressao(deflexio)
        && matchL(")",")",deflexio)
        && matchL("{","{\n",deflexio)
        && listaComandos(deflexio)
        && matchL("}","}",deflexio)
        && deflexio(deflexio)) 
        || protego(deflexio) 
        || true
        ){
            root.addNode(deflexio);
            return true;
        } 
        return false;
    }
    
    // V = Protego → 'protego' '{' ListaComandos '}' | ε
    public boolean protego(Node root){
        Node protego = new Node("PROTEGO");
        if((matchT(TokenType.PROTEGO, "\nelse",protego)
        && matchL("{","{\n",protego)
        && listaComandos(protego)
        && matchL("}","}",protego))
        || true
        ){
            root.addNode(protego);
            return true;
        } 
        return false;
    }
 
    // V = Accio → 'accio' '(' Atribuicao Expressao ';' Atualizacao ')' '{' ListaComandos '}'   for (x in 0..10){}
    // FALTA O FOR E VER O ";"
    public boolean accio(Node root){
        Node accio = new Node("ACCIO");

        if(matchT(TokenType.ACCIO, "\nfor ",accio) //
        && matchL("(","(",accio) //
        && atribuicaoFor(accio) // int id = 0 ;//
        && expressaoFor(accio) 
        && number(accio) //in
        && matchL(";","", accio)
        && atualizacaoFor(accio)
        && matchL(")",")",accio)
        && matchL("{","{\n",accio)
        && listaComandos(accio)
        && matchL("}","}",accio)){
            root.addNode(accio);
            return true;
        } 
        return false;
    }

    public boolean expressaoFor(Node root){
        Node expressaoFor = new Node("EXPRESSAO FOR");
        
        if((matchT(TokenType.IDENTIFIER, "", expressaoFor) && expressaoForL(expressaoFor))
        ){
            root.addNode(expressaoFor);
            return true;
        } 
        return false;        
    }

    public boolean expressaoForL(Node root){
        Node expressaoForL = new Node("EXPRESSAO FOR L");

        if((operadorAritFor(expressaoForL))
        || (operadorCompFor(expressaoForL))
        || (operadorLogicoFor(expressaoForL))){
            root.addNode(expressaoForL);
            return true;
        } 
        return false;
    }

    // V = OpArit → '+' | '-' | '*' | '/' | '%'
    public boolean operadorAritFor(Node root){
        Node operadorAritFor = new Node("OPERADOR ARIT FOR");

        if(matchL("+","",operadorAritFor) || 
        matchL("*","",operadorAritFor) || 
        matchL("-","",operadorAritFor) ||
        matchL("/","",operadorAritFor) ||
        matchL("%","",operadorAritFor)){
            root.addNode(operadorAritFor);
            return true;
        } 
        return false;
    }

    // V = OpComp → '==' | '!=' | '>' | '>=' | '<' | '<='
    public boolean operadorCompFor(Node root){
        Node operadorCompFor = new Node("OPERADOR COMP FOR");

        if (matchL("=","..",operadorCompFor) && matchL("=","",operadorCompFor) || 
        matchL("!","..",operadorCompFor) && matchL("=","",operadorCompFor) || 
        matchL(">"," downTo ",operadorCompFor) && operadorCompForL(operadorCompFor) ||
        matchL("<","..",operadorCompFor) && operadorCompForL(operadorCompFor)){
            root.addNode(operadorCompFor);
            return true;
        } 
        return false;
    }

    // V
    public boolean operadorCompForL(Node root){
        Node operadorCompForL = new Node("OPERADOR COMP FOR L");

        if (matchL("=","",operadorCompForL) 
        || true){
            root.addNode(operadorCompForL);
            return true;
        } 
        return false;
    }

    // V = OpLogico → 'and' | 'or' | 'not' // VER O NOT
    public boolean operadorLogicoFor(Node root){
        Node operadorLogico = new Node("OPERADOR LOGICO");

        if (matchL("and", "",operadorLogico) || 
        matchL("or", "",operadorLogico) || 
        matchL("not", "",operadorLogico)){
            root.addNode(operadorLogico);
            return true;
        } 
        return false;
    }

    
    // V = Crucio → 'crucio' '(' Expressao ')' '{' ListaComandos '}'
    public boolean crucio(Node root){
        Node crucio = new Node("CRUCIO");

        if(matchT(TokenType.CRUCIO,"\nwhile ",crucio)
        && matchL("(","(",crucio)
        && expressao(crucio)
        && matchL(")",")",crucio)
        && matchL("{","{\n\t",crucio)
        && listaComandos(crucio)
        && matchL("}","}",crucio)
        ){
            root.addNode(crucio);
            return true;
        } 
        return false;
    }
    
    // V = Atualizacao → 'id' OpFor
    public boolean atualizacaoFor(Node root){
        Node atualizacaofor = new Node("ATUALIZACAO FOR");
        if(matchL(token.getLexema(), " step " + token.getLexema(),atualizacaofor)
        ){
            root.addNode(atualizacaofor);
            return true;
        } 
        return false;
    }


    // V = Spell → 'spell' Tipo 'id' '(' Parametros ')' '{' ListaComandos '}' 
    public boolean spell(Node root){
        Node spell = new Node("SPELL");
        if(matchT(TokenType.SPELL, "fun ",spell)
        && tipo(spell)
        && identifier(spell)
        && matchL("(","(",spell)
        && parametros(spell)
        && matchL(")",")",spell)
        && matchL("{","{\n",spell)
        && listaComandos(spell)
        && matchL("}","}\n",spell)
        ){
            root.addNode(spell);
            return true;
        } 
        return false;
    }

    // V = Parametros → Tipo 'id' ListaParametros | ε
    public boolean parametros(Node root){
        Node parametros = new Node("PARAMETROS");
        if(tipo(parametros)
        && identifier(parametros)
        && ListaParametros(parametros)
        || true
        ){
            root.addNode(parametros);
            return true;
        } 
        return false;
    }

    // V = ListaParametros → ',' Tipo 'id' ListaParametros | ε
    public boolean ListaParametros(Node root){
        Node ListaParametros = new Node("LISTA PARAMETROS");

        if(matchL(",",",",ListaParametros)
        && tipo(ListaParametros)
        && identifier(ListaParametros)
        && ListaParametros(ListaParametros)
        || true
        ){
            root.addNode(ListaParametros);
            return true;
        } 
        return false;
    }

    // V = Revelio → 'revelio' '(' '"' Revelio2 '"' ')' ';'
    public boolean revelio(Node root){
        Node revelio = new Node("REVELIO");

        if(matchT(TokenType.REVELIO, "println",revelio)
        && matchL("(","(",revelio)
        && revelio2(revelio)
        
        ){
            root.addNode(revelio);
            return true;
        } 
        return false;
    }

    public boolean revelio2(Node root){
        Node revelio2 = new Node("REVELIO 2");

        if(texto(revelio2) 
        && matchL(")",")",revelio2)
        && fim(revelio2)
        || identifier(revelio2)
        && matchL(")",")",revelio2)
        && fim(revelio2)
        ){
            root.addNode(revelio2);
            return true;
        } 
        return false;
    }

    public boolean identifier(Node root){
        Node identifier = new Node("IDENTIFIER");

        if (matchT(TokenType.IDENTIFIER,token.getLexema(),identifier)){
            root.addNode(identifier);
            return true;
        } 
        return false;
    }

    public boolean number(Node root){
        Node number = new Node("NUMBER");

        if (matchT(TokenType.NUMBER,token.getLexema(),number)){
            root.addNode(number);
            return true;
        } 
        return false;
    }

    public boolean bool(Node root){
        Node bool = new Node("BOOLEAN");

        if (matchT(TokenType.BOOLEAN,token.getLexema(),bool)){
            root.addNode(bool);
            return true;
        } 
        return false;
    }

    public boolean fim(Node root){
        Node fim = new Node("FIM");

        if (matchT(TokenType.SEMICOLON,"\n",fim)){
            root.addNode(fim);
            return true;
        } 
        return false;
    }

    public boolean nu(Node root){
        Node nu = new Node("NULL");

        if(matchT(TokenType.NULL,token.getLexema(),nu)){
            root.addNode(nu);
            return true;
        } 
        return false;
    }

    // V = OpArit → '+' | '-' | '*' | '/' | '%'
    public boolean operadorArit(Node root){
        Node operadorArit = new Node("OPERADOR ARIT");

        if (matchL("+","+",operadorArit) || 
        matchL("*","*",operadorArit) || 
        matchL("-","-",operadorArit) ||
        matchL("/","/",operadorArit) ||
        matchL("%","%",operadorArit)){
            root.addNode(operadorArit);
            return true;
        } 
        return false;
    }

    // V = OpComp → '==' | '!=' | '>' | '>=' | '<' | '<='
    public boolean operadorComp(Node root){
        Node operadorComp = new Node("OPERADOR COMP");

        if (matchL("=","=",operadorComp) && matchL("=","=",operadorComp) || 
        matchL("!","!",operadorComp) && matchL("=","=",operadorComp) || 
        matchL(">",">",operadorComp) && operadorCompL(operadorComp) ||
        matchL("<","<",operadorComp) && operadorCompL(operadorComp)){
            root.addNode(operadorComp);
            return true;
        } 
        return false;
    }

    // V
    public boolean operadorCompL(Node root){
        Node operadorCompL = new Node("OPERADOR COMPL");

        if (matchL("=","=",operadorCompL) 
        || true){
            root.addNode(operadorCompL);
            return true;
        } 
        return false;
    }

    // V = OpLogico → 'and' | 'or' | 'not' // VER O NOT
    public boolean operadorLogico(Node root){
        Node operadorLogico = new Node("OPERADOR LOGICO");

        if (matchL("and", "&&",operadorLogico) || 
        matchL("or", "||",operadorLogico) || 
        matchL("not", "!",operadorLogico)){
            root.addNode(operadorLogico);
            return true;
        } 
        return false;
    }

    // V = Texto → valor Texto | e
    public boolean texto(Node root) {
        Node texto = new Node("TEXTO");

        if (matchT(TokenType.TEXT, token.getLexema(),texto) && texto(texto)
        || true){
            root.addNode(texto);
            return true;
        } 
        return false;
    }

    // V = Texto → valor Texto | e
    public boolean comentario(Node root) {
        Node comentario = new Node("COMENTARIO");

        if (matchL("-", "/", comentario)
        && matchL(">", "*", comentario)
        && matchT(TokenType.TEXT, token.getLexema(),comentario) && texto(comentario)
        && matchL("<", "*", comentario)
        && matchL("-", "/", comentario)){
            root.addNode(comentario);
            return true;
        } 
        return false;
    }

    // V = Legilimens →  legilimens  '('  ')'  `;`

    public boolean legilimens(Node root){
        Node legilimens = new Node("LEGILIMENS");

        if(matchT(TokenType.LEGILIMENS, "readLine",legilimens)
        && matchL("(", "(",legilimens)
        && matchL(")", ")",legilimens)
        && matchL(";", "\n",legilimens)){
            root.addNode(legilimens);
            return true;
        } 
        return false;
    }

    
    // V = Expressao → 'id''Expressao' | 'num'Expressao' | 'true'Expressao' | 'false'Expressao' | 'NULL'Expressao' | 'str'Expressao' | '(' Expressao ')' 
    public boolean expressao(Node root){
        Node expressao = new Node("EXPRESSAO");
        
        if((identifier(expressao) && expressaoL(expressao))
        || (number(expressao) && expressaoL(expressao))
        || (bool(expressao) && expressaoL(expressao))
        || (nu(expressao) && expressaoL(expressao))
        || (texto(expressao) && expressaoL(expressao))
        || (matchL("(","(",expressao) && expressao(expressao) && matchL(")",")",expressao))
        ){
            root.addNode(expressao);
            return true;
        } 
        return false;
    }

    // V = Expressao' → OpArit Expressao Expressao' | OpComp Expressao Expressao' | OpLogico Expressao Expressao' | ε
    public boolean expressaoL(Node root){
        Node expressaoL = new Node("EXPRESSAOL");

        if((operadorArit(expressaoL) && expressao(expressaoL) && expressaoL(expressaoL))
        || (operadorComp(expressaoL) && expressao(expressaoL) && expressaoL(expressaoL))
        || (operadorLogico(expressaoL) && expressao(expressaoL) && expressaoL(expressaoL))
        || true){
            root.addNode(expressaoL);
            return true;
        } 
        return false;
    }

    // V = Valor → 'num' | 'id' | 'str' | 'true' | 'false' | 'NULL'
    public boolean valor(Node root){
        Node valor = new Node("VALOR");

        if (number(valor)
        || texto(valor)
        || identifier(valor)
        || bool(valor)
        || nu(valor)){
            root.addNode(valor);
            return true;
        } 
        return false;
    }


    // ------- NOVO CÓDIGO AULA ------

    public boolean matchL(String lexema, String newcode, Node node){
    
        if(token.getLexema().equals(lexema)){
            Node root = new Node(token.getLexema());
            node.addNode(root);

            token = getNextToken();
            traduz(newcode);
            return true;
        } return false;
    }
    
    public boolean matchT(TokenType tipo, String newcode, Node node){
        

        if(token.getTipo().equals(tipo)){
            Node root = new Node(token.getLexema());
            node.addNode(root);

            traduz(newcode);
            token = getNextToken();
            return true;
        } return false;
    }

    private void traduz(String code){
        System.out.print(code);
        writer.write(code);

        if(code.equals("readLine")){
            for (int i = 0; i < tokens.size(); i++){
                token = tokens.get(i);
                if(token.getTipo().equals(TokenType.LEGILIMENS)){
                    TokenType tokenTipo = (tokens.get(i - 3).getTipo());
                    if(tokenTipo.equals(TokenType.INT)){
                        System.out.print("()?.toInt");
                        writer.write("()?.toInt");
                    }
                    if(tokenTipo.equals(TokenType.DEC)){
                        System.out.print("()?.toDouble");
                        writer.write("()?.toDouble");
                    }
                    if(tokenTipo.equals(TokenType.BOOLEAN)){
                        System.out.print("()?.toBoolean");
                        writer.write("()?.toBoolean");
                    }
                }
            }
        }
    }
}