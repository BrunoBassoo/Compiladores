import java.util.List;

public class Parser {
    
    // ------------------------ //
    // PADRAO DE QUALQUER CÓDIGO //
    private List<Token> tokens;
    private Token token;
    private Node node;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public Token getNextToken(){
        if(tokens.size() > 0){
            return tokens.remove(0);
        }
        return null;
    }

    private void erro(String regra){
        System.out.println("Regra: " + regra);
        System.out.println("token inválido: " + token.getLexema());
        System.exit(0);
    }

    public void main(){
        HEADER();
        token = getNextToken();
        if (programa()){
            if(token.getTipo().equals(TokenType.EOF)){
                System.out.println("\n\nSintaticamente correto!");
                SHOLDER();
                return;
            }
        }
        erro("main");
    }


    // V = CRIANDO A MAIN 
    private void HEADER(){
        System.out.println("import java.util.Scanner;\n");
        System.out.println("fun main(){");
        System.out.println("    Scanner scanner = new Scanner(System.in);\n");
    }

    // V
    public void SHOLDER(){
        System.out.println("}");
    }
    
    // V = Programa → 'magic' ListaComandos 'endmagic'
    public boolean programa(){
        return(matchT(TokenType.MAGIC,"",node)
        && listaComandos()
        && matchT(TokenType.END_MAGIC,"",node)
        );
    }

    // V = ListaComandos → Comando ListaComandos | ε
    public boolean listaComandos(){
        return(comando() && listaComandos()
        || true
        );
    }

    // V = Comando → Declaracao | Atribuicao | Incendio | Accio | Crucio | Alohomora | Spell | Revelio | Legilimens | 'relashio' ';' | 'avadakedavra' ';' | 'finite' Expressao ';'
    public boolean comando(){
        return(declaracao() //
        || atribuicao() //
        || incendio() //
        || accio() //
        || crucio() //
        || alohomora()
        || spell()
        || revelio()
        || legilimens()
        || matchT(TokenType.RELASHIO, "continue",node) && matchT(TokenType.SEMICOLON,"\n",node)
        || matchT(TokenType.AVADAKEDAVRA, "break",node)
        || matchT(TokenType.FINITE,"\nreturn ",node) && expressao() && matchT(TokenType.SEMICOLON,"\n",node)
        );
    }

    // V = Declaracao → Tipo 'id' declaracaoL
    public boolean declaracao(){
        return(tipo() 
        && identifier() 
        && declaracaoL());
    }


    // V = Declaracao →  ';' |  '=' valor'';'
    public boolean declaracaoL(){
        return(matchT(TokenType.SEMICOLON,"\n",node) 
        || matchT(TokenType.EQUAL,"=",node) && valor() && matchT(TokenType.SEMICOLON,"\n",node));
    }

    // V = Atribuicao → 'id' '=' atribuicaoL
    public boolean atribuicao(){
        return(identifier() 
        && matchT(TokenType.EQUAL,"=",node)
        && atribuicaoL()
        );
    }

    // V = AtribuicaoL → expressao ';' | valor ';'
    public boolean atribuicaoL(){
        return(expressao() && matchT(TokenType.SEMICOLON, "\n",node)
        || valor() && matchT(TokenType.SEMICOLON, "\n",node));
    }


    // V = Tipo → 'int' | 'dec' | 'str' | 'boolean'
    public boolean tipo(){
        return(matchL("int", "val ",node)
        || matchL("dec", "val ",node)
        || matchL("str", "val ",node)
        || matchL("boolean", "val ",node));
    }

    // V = Incendio → 'incendio' '(' Expressao ')' '{' ListaComandos '}' Deflexio
    public boolean incendio(){
        return(matchT(TokenType.INCENDIO, "if ",node) 
            && matchL("(","(",node)
            && expressao()
            && matchL(")",")",node)
            && matchL("{","{\n\t",node)
            && listaComandos()
            && matchL("}","}",node)
            && deflexio()
            );
    }

    // V = Deflexio → 'deflexio' '(' Expressao ')' '{' ListaComandos '}' Deflexio | Protego | ε
    public boolean deflexio(){
        return((matchT(TokenType.DEFLEXIO, "else if ",node)
        && matchL("(","(",node)
        && expressao()
        && matchL(")",")",node)
        && matchL("{","{\n\t",node)
        && listaComandos()
        && matchL("}","}",node)
        && deflexio()) 
        || protego() 
        || true
        );
    }
    
    // V = Protego → 'protego' '{' ListaComandos '}' | ε
    public boolean protego(){
        return((matchT(TokenType.PROTEGO, "else",node)
        && matchL("{","{\n\t",node)
        && listaComandos()
        && matchL("}","}",node))
        || true
        );
    }

    // V = Accio → 'accio' '(' Atribuicao Expressao ';' Atualizacao ')' '{' ListaComandos '}'
    // FALTA O FOR E VER O ";"
    public boolean accio(){
        return(matchT(TokenType.ACCIO, "for ",node) 
        && matchL("(","(",node)
        && atribuicao()
        && expressao()
        && matchT(TokenType.SEMICOLON,"\n",node)
        && atualizacao()
        && matchL(")",")",node)
        && matchL("{","{\n\t",node)
        && listaComandos()
        && matchL("}","}",node)
        );
    }

    // V = Crucio → 'crucio' '(' Expressao ')' '{' ListaComandos '}'
    public boolean crucio(){
        return(matchT(TokenType.CRUCIO,"while ",node)
        && matchL("(","(",node)
        && expressao()
        && matchL(")",")",node)
        && matchL("{","{\n\t",node)
        && listaComandos()
        && matchL("}","}",node)
        );
    }
    
    // V = Atualizacao → 'id' OpFor
    public boolean atualizacao(){
        return(identifier() && operadorFor());
    }

    // V = OpFor → '++' | '--'
    public boolean operadorFor(){
        return(matchL("+","+",node) && matchL("+","+",node) 
        || matchL("-","-",node) && matchL("-","-",node));
    }

    // Alohomora → 'alohomora' '(' 'id' ')' '{' ListaCases '}'
    public boolean alohomora(){
        return(matchT(TokenType.ALOHOMORA, "switch ",node)
        && matchL("(","(",node)
        && identifier()
        && matchL(")",")",node)
        && matchL("{","{\n\t",node)
        && listaCases()
        && matchL("}","}",node)
        );
    }

    // ListaCases → Case ListaCases | ε
    public boolean listaCases(){
        return(cases() 
        && listaCases() 
        || true
        );
    }

    // Case → 'door' Valor ':' ListaComandos 'avadakedavra' ';'
    public boolean cases(){
        return(matchT(TokenType.DOOR, "case",node)
        && valor()
        && matchT(TokenType.SEMICOLON,"\n",node)
        && listaComandos()
        && matchT(TokenType.AVADAKEDAVRA, "break",node)
        && matchT(TokenType.SEMICOLON,"\n",node)
        );
    }

    // V = Spell → 'spell' Tipo 'id' '(' Parametros ')' '{' ListaComandos '}' 
    public boolean spell(){
        return(matchT(TokenType.SPELL, "fun ",node)
        && tipo()
        && identifier()
        && matchL("(","(",node)
        && parametros()
        && matchL(")",")",node)
        && matchL("{","{\n",node)
        && listaComandos()
        && matchL("}","}\n",node)
        );
    }

    // V = Parametros → Tipo 'id' ListaParametros | ε
    public boolean parametros(){
        return(tipo()
        && identifier()
        && ListaParametros()
        || true
        );
    }

    // V = ListaParametros → ',' Tipo 'id' ListaParametros | ε
    public boolean ListaParametros(){
        return(matchL(",",",",node)
        && tipo()
        && identifier()
        && ListaParametros()
        || true
        );
    }

    // V = Revelio → 'revelio' '(' '"' Texto '"' ')' ';'
    public boolean revelio(){
        return(matchT(TokenType.REVELIO, "println",node)
        && matchL("(","(",node)
        && texto()
        && matchL(")",")",node)
        && matchT(TokenType.SEMICOLON,"\n",node)
        );
    }

    public boolean identifier(){
        return (matchT(TokenType.IDENTIFIER,token.getLexema(),node));
    }

    public boolean number(){
        return (matchT(TokenType.NUMBER,token.getLexema(),node));
    }

    public boolean bool(){
        return (matchT(TokenType.BOOLEAN,token.getLexema(),node));
    }

    public boolean fim(){
        return (matchT(TokenType.SEMICOLON,token.getLexema(),node));
    }

    public boolean nu(){
        return(matchT(TokenType.NULL,token.getLexema(),node));
    }

    // V = OpArit → '+' | '-' | '*' | '/' | '%'
    public boolean operadorArit(){
        return (matchL("+","+",node) || 
        matchL("*","*",node) || 
        matchL("-","-",node) ||
        matchL("/","/",node) ||
        matchL("%","%",node));
    }

    // V = OpComp → '==' | '!=' | '>' | '>=' | '<' | '<='
    public boolean operadorComp(){
        return (matchL("=","=",node) && matchL("=","=",node) || 
        matchL("!","!",node) && matchL("=","=",node) || 
        matchL(">",">",node) && operadorCompL() ||
        matchL("<","<",node) && operadorCompL());
    }

    // V
    public boolean operadorCompL(){
        return (matchL("=","=",node) 
        || true);
    }

    // V = OpLogico → 'and' | 'or' | 'not' // VER O NOT
    public boolean operadorLogico(){
        return (matchL("and", "&&",node) || 
        matchL("or", "||",node) || 
        matchL("not", "!",node));
    }

    // V = Texto → valor Texto | e
    public boolean texto() {
        return (matchT(TokenType.TEXT, token.getLexema(),node) && texto()
            || true);
    }

    // V = Legilimens →  legilimens  '('  ')'  `;`

    public boolean legilimens(){
        return( 
        matchT(TokenType.LEGILIMENS, "scanner.nextLine",node)
        && matchL("(", "(",node)
        && matchL(")", ")",node)
        && matchL(";", "\n",node));
    }

    
    // V = Expressao → 'id''Expressao' | 'num'Expressao' | 'true'Expressao' | 'false'Expressao' | 'NULL'Expressao' | 'str'Expressao' | '(' Expressao ')' 
    public boolean expressao(){
        return((identifier() && expressaoL())
        || (number() && expressaoL())
        || (bool() && expressaoL())
        || (nu() && expressaoL())
        || (texto() && expressaoL())
        || (matchL("(","(",node) && expressao() && matchL(")",")",node))
        );
    }

    // V = Expressao' → OpArit Expressao Expressao' | OpComp Expressao Expressao' | OpLogico Expressao Expressao' | ε
    public boolean expressaoL(){
        return((operadorArit() && expressao() && expressaoL())
        || (operadorComp() && expressao() && expressaoL())
        || (operadorLogico() && expressao() && expressaoL())
        || true);
    }

    // V = Valor → 'num' | 'id' | 'str' | 'true' | 'false' | 'NULL'
    public boolean valor(){
        return (number()
        || texto()
        || identifier()
        || bool()
        || nu()
        || texto());
    }


    // ------- NOVO CÓDIGO AULA ------

    public boolean matchL(String lexema, String newcode, Node node){
        if(token.getLexema().equals(lexema)){
            traduz(newcode);
            token = getNextToken();
            node.addNode(token.getLexema());
            return true;
        } return false;
    }
    
    public boolean matchT(TokenType tipo, String newcode, Node node){
        if(token.getTipo().equals(tipo)){
            traduz(newcode);
            token = getNextToken();
            node.addNode(token.getLexema());
            return true;
        } return false;
    }

    private void traduz(String code){
        System.out.print(code);
    }

}