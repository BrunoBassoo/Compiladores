import java.util.List;

public class Parser {
    
    // ------------------------ //
    // PADRAO DE QUALQUER CÓDIGO //
    private List<Token> tokens;
    private Token token;
    private int tokenIndex;


    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.tokenIndex = 0;
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

    public void main(){
        HEADER();
        token = getNextToken();
        if (programa()){
            if(token.getTipo().equals(TokenType.EOF)){
                SHOLDER();
                System.out.println("\n\nSintaticamente correto!");
                return;
            }
        }
        
        erro("main");
    }


    // V = CRIANDO A MAIN 
    private void HEADER(){
        System.out.println("import java.util.Scanner;\n");
        System.out.println("fun main(){");
        System.out.println("    Scanner scanner = new Scanner(System.'in')\n");
    }

    // V
    public void SHOLDER(){
        System.out.println("}");
    }
    
    // V = Programa → 'magic' ListaComandos 'endmagic'
    public boolean programa(){
        return(matchT(TokenType.MAGIC,"")
        && listaComandos()
        && matchT(TokenType.END_MAGIC,"")
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
        return(declaracao() 
        || atribuicao() 
        || incendio() 
        || accio() 
        || crucio() 
        || alohomora()
        || spell()
        || revelio()
        || legilimens()
        || matchT(TokenType.RELASHIO, "continue") && matchT(TokenType.SEMICOLON,"\n")
        || matchT(TokenType.AVADAKEDAVRA, "break")
        || matchT(TokenType.FINITE,"\nreturn ") && expressao() && matchT(TokenType.SEMICOLON,"\n")
        );
    }

    // V = Declaracao → Tipo 'id' declaracaoL
    public boolean declaracao(){
        return(tipo() 
        && identifier() 
        && declaracaoL());
    }


    // V = Declaracao →  ';' |  '=' declaracaoL2
    public boolean declaracaoL(){
        return(matchT(TokenType.SEMICOLON,"\n") 
        || matchT(TokenType.EQUAL,"=") && expressao() && matchL(";","\n"));
    }


    // V = Atribuicao → 'id' '=' atribuicaoL
    public boolean atribuicao(){
        return(identifier() 
        && matchT(TokenType.EQUAL,"=")
        && atribuicaoL()
        );
    }

    // V = AtribuicaoL → expressao ';' | valor ';'
    public boolean atribuicaoL(){
        return(expressao() && matchT(TokenType.SEMICOLON, "\n")
        || valor() && matchT(TokenType.SEMICOLON, "\n"));
    }


    // V = Tipo → 'int' | 'dec' | 'str' | 'boolean'
    public boolean tipo(){
        return(matchL("int", "val ")
        || matchL("dec", "val ")
        || matchL("str", "val ")
        || matchL("boolean", "val "));
    }

    // V = Incendio → 'incendio' '(' Expressao ')' '{' ListaComandos '}' Deflexio
    public boolean incendio(){
        return(matchT(TokenType.INCENDIO, "if ") 
            && matchL("(","(")
            && expressao()
            && matchL(")",")")
            && matchL("{","{\n\t")
            && listaComandos()
            && matchL("}","}")
            && deflexio()
            );
    }

    // V = Deflexio → 'deflexio' '(' Expressao ')' '{' ListaComandos '}' Deflexio | Protego | ε
    public boolean deflexio(){
        return((matchT(TokenType.DEFLEXIO, "else if ")
        && matchL("(","(")
        && expressao()
        && matchL(")",")")
        && matchL("{","{\n\t")
        && listaComandos()
        && matchL("}","}")
        && deflexio()) 
        || protego() 
        || true
        );
    }
    
    // V = Protego → 'protego' '{' ListaComandos '}' | ε
    public boolean protego(){
        return((matchT(TokenType.PROTEGO, "else")
        && matchL("{","{\n\t")
        && listaComandos()
        && matchL("}","}"))
        || true
        );
    }

    // V = Accio → 'accio' '(' Atribuicao Expressao ';' Atualizacao ')' '{' ListaComandos '}'
    // FALTA O FOR E VER O ";"
    public boolean accio(){
        return(matchT(TokenType.ACCIO, "for ") 
        && matchL("(","(")
        && atribuicao()
        && expressao()
        && matchT(TokenType.SEMICOLON,"\n")
        && atualizacao()
        && matchL(")",")")
        && matchL("{","{\n\t")
        && listaComandos()
        && matchL("}","}")
        );
    }

    // V = Crucio → 'crucio' '(' Expressao ')' '{' ListaComandos '}'
    public boolean crucio(){
        return(matchT(TokenType.CRUCIO,"while ")
        && matchL("(","(")
        && expressao()
        && matchL(")",")")
        && matchL("{","{\n\t")
        && listaComandos()
        && matchL("}","}")
        );
    }
    
    // V = Atualizacao → 'id' OpFor
    public boolean atualizacao(){
        return(identifier() && operadorFor());
    }

    // V = OpFor → '++' | '--'
    public boolean operadorFor(){
        return(matchL("+","+") && matchL("+","+") 
        || matchL("-","-") && matchL("-","-"));
    }

    // Alohomora → 'alohomora' '(' 'id' ')' '{' ListaCases '}'
    public boolean alohomora(){
        return(matchT(TokenType.ALOHOMORA, "switch ")
        && matchL("(","(")
        && identifier()
        && matchL(")",")")
        && matchL("{","{\n\t")
        && listaCases()
        && matchL("}","}")
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
        return(matchT(TokenType.DOOR, "case")
        && valor()
        && matchT(TokenType.SEMICOLON,"\n")
        && listaComandos()
        && matchT(TokenType.AVADAKEDAVRA, "break")
        && matchT(TokenType.SEMICOLON,"\n")
        );
    }

    // V = Spell → 'spell' Tipo 'id' '(' Parametros ')' '{' ListaComandos '}' 
    public boolean spell(){
        return(matchT(TokenType.SPELL, "fun ")
        && tipo()
        && identifier()
        && matchL("(","(")
        && parametros()
        && matchL(")",")")
        && matchL("{","{\n")
        && listaComandos()
        && matchL("}","}\n")
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
        return(matchL(",",",")
        && tipo()
        && identifier()
        && ListaParametros()
        || true
        );
    }

    // V = Revelio → 'revelio' '(' '"' Texto '"' ')' ';'
    public boolean revelio(){
        if(matchT(TokenType.REVELIO, "println")
        && matchL("(","(")
        && texto()
        && matchL(")",")")
        && matchT(TokenType.SEMICOLON,"\n")
        ){
            return true;
        } return false;
    }

    public boolean identifier(){
        return (matchT(TokenType.IDENTIFIER,token.getLexema()));
    }

    public boolean number(){
        return (matchT(TokenType.NUMBER,token.getLexema()));
    }

    public boolean bool(){
        return (matchT(TokenType.BOOLEAN,token.getLexema()));
    }

    public boolean fim(){
        return (matchT(TokenType.SEMICOLON,token.getLexema()));
    }

    public boolean nu(){
        return(matchT(TokenType.NULL,token.getLexema()));
    }

    // V = OpArit → '+' | '-' | '*' | '/' | '%'
    public boolean operadorArit(){
        return (matchL("+","+") || 
        matchL("*","*") || 
        matchL("-","-") ||
        matchL("/","/") ||
        matchL("%","%"));
    }

    // V = OpComp → '==' | '!=' | '>' | '>=' | '<' | '<='
    public boolean operadorComp(){
        return (matchL("=","=") && matchL("=","=") || 
        matchL("!","!") && matchL("=","=") || 
        matchL(">",">") && operadorCompL() ||
        matchL("<","<") && operadorCompL());
    }

    // V
    public boolean operadorCompL(){
        return (matchL("=","=") 
        || true);
    }

    // V = OpLogico → 'and' | 'or' | 'not' // VER O NOT
    public boolean operadorLogico(){
        return (matchL("and", "&&") || 
        matchL("or", "||") || 
        matchL("not", "!"));
    }

    // V = Texto → valor Texto | e
    public boolean texto() {
        return (matchT(TokenType.TEXT, token.getLexema()) && texto()
        || true);
    }

    // V = Legilimens →  legilimens  '('  ')'  `;`

    public boolean legilimens(){
        return( 
        matchT(TokenType.LEGILIMENS, "scanner.nextLine")
        && matchL("(", "(")
        && matchL(")", ")")
        && matchL(";", "\n"));
    }

    
    // V = Expressao → 'id''Expressao' | 'num'Expressao' | 'true'Expressao' | 'false'Expressao' | 'NULL'Expressao' | 'str'Expressao' | '(' Expressao ')' 
    public boolean expressao(){
        return((identifier() && expressaoL())
        || (number() && expressaoL())
        || (bool() && expressaoL())
        || (nu() && expressaoL())
        //|| (texto() && expressaoL())
        || (matchL("(","(") && expressao() && matchL(")",")"))
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
        //|| texto()
        || identifier()
        || bool()
        || nu());
        //|| texto());
    }


    // ------- NOVO CÓDIGO AULA ------

    public boolean matchL(String lexema, String newcode){
        if(token.getLexema().equals(lexema)){
            traduz(newcode);
            token = getNextToken();
            return true;
        } return false;
    }
    
    public boolean matchT(TokenType tipo, String newcode){
        if(token.getTipo().equals(tipo)){
            traduz(newcode);
            token = getNextToken();
            return true;
        } return false;
    }

    private void traduz(String code){
        System.out.print(code);
    }

}