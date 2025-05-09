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
        if (INTEIRO()){
            if(token.getTipo().equals(TokenType.EOF)){
                System.out.println("sintaticamente correto");
                SHOLDER();
                return;
            }
        }
        erro("main");
    }


    // CRIANDO A MAIN 
    private void HEADER(){
        System.out.println("import java.Util.Scanner");
        System.out.println("public class Code{");
        System.out.println("public static void main(String[]agrs){");
        System.out.println("val scanner = Scanner()");
    }

    public class SHOLDER(){
        System.out.println("}");
        System.out.println("}");
    }
    

    // Incendio → 'incendio' '(' Expressao ')' '{' ListaComandos '}' Deflexio
    public boolean incendio(){
        return(matchT(TokenType.INCENDIO, "if") 
            && matchL("(")
            && expressao();
            && matchL(")");
            && matchL("{");
            && listaComandos();
            && matchL("}");
            && deflexio();
            );
    }

    // Deflexio → 'deflexio' '(' Expressao ')' '{' ListaComandos '}' Deflexio | Protego | ε
    public boolean deflexio(){
        return((matchT(TokenType.DEFLEXIO, "else if")
        && matchL("(")
        && expressao();
        && matchL(")")
        && matchL({);
        && listaComandos();
        && matchL("}");
        && deflexio();)
        || protego();
        || true;
        )
    }
    
    // Protego → 'protego' '{' ListaComandos '}' | ε
    public boolean protego(){
        return((matchT(TokenType.PROTEGO, "else");
        && matchL("{");
        && listaComandos();
        && matchL("}");)
        || true;
        )
    }

    public boolean identifier(){
        return (matchT(TokenType.IDENTIFIER));
    }

    public boolean number(){
        return (matchT(TokenType.NUMBER));
    }

    public boolean string(){
        return (matchT(TokenType.STR));
    }

    public boolean bool(){
        return (matchT(TokenType.BOOLEAN));
    }

    public boolean fim(){
        return (matchT(TokenType.SEMICOLON));
    }

    public boolean nu(){
        return(matchT(TokenType.NULL));
    }

    // OpArit → '+' | '-' | '*' | '/' | '%'
    public boolean operadorArit(){
        return (matchL("+") || 
        matchL("*") || 
        matchL("-") ||
        matchL("/") ||
        matchL("%"));
    }

    // OpComp → '==' | '!=' | '>' | '>=' | '<' | '<='
    public boolean operadorComp(){
        return (matchL("==") || 
        matchL("!=") || 
        matchL(">") ||
        matchL(">=") ||
        matchL("<") ||
        matchL("<="));
    }

    // OpLogico → 'and' | 'or' | 'not' // VER O NOT
    public boolean operadorLogico(){
        return (matchL("and", "&&") || 
        matchL("or", "||") || 
        matchL("not", "!"));
    }

    // Texto → 'id' Texto | ε
    public bool

    // Legilimens → 'legilimens' '(' ')' ';' | 'legilimens' '(' 'str' ')' ';'
    public boolean legilimens(){

        return(matchT(TokenType.LEGILIMENS, "Scanner()") &&)
    }

    
    // Expressao → 'id''Expressao' | 'num'Expressao' | 'true'Expressao' | 'false'Expressao' | 'NULL'Expressao' | 'str'Expressao' | '(' Expressao ')' 
    public boolean expressao(){
        return((identifier() && expressao())
        || (number() && expressao())
        || (bool() && expressao())
        || (nu() && expressao())
        || (string() && expressao())
        || (matchL("(") && expressao() && matchL(")"))
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
    public boolean Valor(){
        return (number()
        || identifier()
        || string()
        || bool()
        || nu());
    }






















    // // VALIDAR VALORES -------------------------
    public boolean matchL(String lexema){
        if(token.getLexema().equals(lexema)){
            token = getNextToken();
            return true;
        } return false;
    }
    
    public boolean matchT(TokenType tipo){
        if(token.getTipo().equals(tipo)){
            token = getNextToken();
            return true;
        } return false;
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
        System.out.println(code);
    }
}
