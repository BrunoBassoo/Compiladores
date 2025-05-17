import java.util.List;

public class Parser {
    
    // ------------------------ //
    // PADRAO DE QUALQUER CÓDIGO //
    private List<Token> tokens;
    private Token token;

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


    // CRIANDO A MAIN 
    private void HEADER(){
        System.out.println("import java.util.Scanner;\n");
        System.out.println("fun main(){");
        System.out.println("    Scanner scanner = new Scanner(System.in);\n");
    }

    public void SHOLDER(){
        System.out.println("}");
    }
    
    // Programa → 'magic' ListaComandos 'endmagic'
    public boolean programa(){
        return(matchT(TokenType.MAGIC,"")
        && listaComandos()
        && matchT(TokenType.END_MAGIC,"")
        );
    }

    // ListaComandos → Comando ListaComandos | ε
    public boolean listaComandos(){
        return(comando() && listaComandos()
        || true
        );
    }

    // Comando → Declaracao | Atribuicao | Incendio | Accio | Crucio | Alohomora | Spell | Revelio | Legilimens | 'relashio' ';' | 'avadakedavra' ';' | 'finite' Expressao ';'
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
        || matchT(TokenType.RELASHIO, "continue") && matchT(TokenType.SEMICOLON,"\n")
        || matchT(TokenType.AVADAKEDAVRA, "break")
        || matchT(TokenType.FINITE,"\nreturn ") && expressao() && matchT(TokenType.SEMICOLON,"\n")
        );
    }

    // Declaracao → Tipo 'id' declaracaoL
    // Fatoração
    public boolean declaracao(){
        return(tipo() 
        && identifier() 
        && declaracaoL());
    }


    // Declaracao →  ';' |  '=' valor'';'
    public boolean declaracaoL(){
        return(matchT(TokenType.SEMICOLON,"\n") 
        || matchT(TokenType.EQUAL,"=") && valor() && matchT(TokenType.SEMICOLON,"\n"));
    }

    // Atribuicao → 'id' '=' atribuicaoL
    // Fatoração
    public boolean atribuicao(){
        return(identifier() 
        && matchT(TokenType.EQUAL,"=")
        && atribuicaoL()
        );
    }

    // AtribuicaoL → expressao ';' | valor ';'
    public boolean atribuicaoL(){
        return(expressao() && matchT(TokenType.SEMICOLON, "\n")
        || valor() && matchT(TokenType.SEMICOLON, "\n"));
    }


    // Tipo → 'int' | 'dec' | 'str' | 'boolean'
    // NAO SEI SE TA CERTO!
    public boolean tipo(){
        return(matchL("int", "val ")
        || matchL("dec", "val ")
        || matchL("str", "val ")
        || matchL("boolean", "val "));
    }

    // Incendio → 'incendio' '(' Expressao ')' '{' ListaComandos '}' Deflexio
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

    // Deflexio → 'deflexio' '(' Expressao ')' '{' ListaComandos '}' Deflexio | Protego | ε
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
    
    // Protego → 'protego' '{' ListaComandos '}' | ε
    public boolean protego(){
        return((matchT(TokenType.PROTEGO, "else")
        && matchL("{","{\n\t")
        && listaComandos()
        && matchL("}","}"))
        || true
        );
    }

    // Accio → 'accio' '(' Atribuicao Expressao ';' Atualizacao ')' '{' ListaComandos '}'
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

    // Crucio → 'crucio' '(' Expressao ')' '{' ListaComandos '}'
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
    
    // Atualizacao → 'id' OpFor
    public boolean atualizacao(){
        return(identifier() && operadorFor());
    }

    // OpFor → '++' | '--'
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

    // Spell → 'spell' Tipo 'id' '(' Parametros ')' '{' ListaComandos '}' 'endspell'
    // PRECISA VER ESSA PARTE DE CRIAR FUNCAO
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
        && matchT(TokenType.END_SPELL,token.getLexema())
        );
    }

    // Parametros → Tipo 'id' ListaParametros | ε
    public boolean parametros(){
        return(tipo()
        && identifier()
        && ListaParametros()
        || true
        );
    }

    // ListaParametros → ',' Tipo 'id' ListaParametros | ε
    public boolean ListaParametros(){
        return(matchL(",",",")
        && tipo()
        && identifier()
        && ListaParametros()
        || true
        );
    }

    // Revelio → 'revelio' '(' '"' Texto '"' ')' ';'
    public boolean revelio(){
        return(matchT(TokenType.REVELIO, "println")
        && matchL("(","(")
        && texto()
        && matchL(")",")")
        && matchT(TokenType.SEMICOLON,"\n")
        );
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

    // OpArit → '+' | '-' | '*' | '/' | '%'
    public boolean operadorArit(){
        return (matchL("+","+") || 
        matchL("*","*") || 
        matchL("-","-") ||
        matchL("/","/") ||
        matchL("%","%"));
    }

    // OpComp → '==' | '!=' | '>' | '>=' | '<' | '<='
    public boolean operadorComp(){
        return (matchL("=","=") && matchL("=","=") || 
        matchL("!","!") && matchL("=","=") || 
        matchL(">",">") && operadorCompL() ||
        matchL("<","<") && operadorCompL());
    }

    public boolean operadorCompL(){
        return (matchL("=","=") 
        || true);
    }

    // OpLogico → 'and' | 'or' | 'not' // VER O NOT
    public boolean operadorLogico(){
        return (matchL("and", "&&") || 
        matchL("or", "||") || 
        matchL("not", "!"));
    }

    // Texto → valor Texto | e
    public boolean texto() {
        return (matchT(TokenType.TEXT, token.getLexema()) && texto()
            || true);
    }

    // Legilimens → tipo 'id' ''='' legilimens  '(' legilimensL ')'  `;`

    // FALTA ESSE TB"
    public boolean legilimens(){
        return( tipo()
        && identifier()
        && matchT(TokenType.EQUAL,"=") 
        && matchT(TokenType.LEGILIMENS, "scanner.nextLine")
        && matchL("(", "(")
        && legilimensL()
        && matchL(")", ")")
        && matchL(";", "\n"));
    }

    public boolean legilimensL(){
        return(number() || identifier());
    }
    
    // Expressao → 'id''Expressao' | 'num'Expressao' | 'true'Expressao' | 'false'Expressao' | 'NULL'Expressao' | 'str'Expressao' | '(' Expressao ')' 
    public boolean expressao(){
        return((identifier() && expressaoL())
        || (number() && expressaoL())
        || (bool() && expressaoL())
        || (nu() && expressaoL())
        || (texto() && expressaoL())
        || (matchL("(","(") && expressao() && matchL(")",")"))

        );
    }

    // Expressao' → OpArit Expressao Expressao' | OpComp Expressao Expressao' | OpLogico Expressao Expressao' | ε
    public boolean expressaoL(){
        return((operadorArit() && expressao() && expressaoL())
        || (operadorComp() && expressao() && expressaoL())
        || (operadorLogico() && expressao() && expressaoL())
        || true);
    }

    // Valor → 'num' | 'id' | 'str' | 'true' | 'false' | 'NULL'
    public boolean valor(){
        return (number()
        || texto()
        || identifier()
        || bool()
        || nu()
        || texto());
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