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
        header();
        token = getNextToken();
        if (INTEIRO()){
            if(token.getTipo().equals(TokenType.EOF)){
                System.out.println("sintaticamente correto");
                sholder();
                return;
            }
        }
        erro("main");
    }

    // CRIANDO A MAIN 
    private void header(){
        System.out.println("public class Code{");
        System.out.println("public static void main(String[]agrs){")
    }

    public class sholder(){
        System.out.println("}");
        System.out.println("}");
    }

    // CRIAR VARIAVEL INTEIRA
    public boolean INTEIRO(){
        return (matchT(TokenType.INT) && 
        matchT(TokenType.IDENTIFIER) && 
        matchT(TokenType.EQUAL) && 
        matchT(TokenType.NUMBER) && 
       fim() && INCENDIO());
    }

    // CRIAR VARIAVEL DECIMAL (FLOAT)
    public boolean DECIMAL(){
        return (matchT(TokenType.DEC) && 
        matchT(TokenType.IDENTIFIER) && 
        matchL("=") && 
        matchT(TokenType.NUMBER) && 
        matchL(".") && 
        matchT(TokenType.NUMBER) && 
        fim());
    }

    // CRIAR STRING 
    public boolean STRING(){
        return (matchT(TokenType.STR) && 
        matchT(TokenType.IDENTIFIER) && 
        matchL("=") && 
        matchT(TokenType.DOUBLE_QUOTES) && 
        matchT(TokenType.IDENTIFIER) && 
        matchT(TokenType.DOUBLE_QUOTES) && 
        fim());
    }

    // PARTE DO IF E ELSE
    public boolean INCENDIO(){
        return(matchT(TokenType.INCENDIO, "if") && 
            condicao() && 
            expressao() && 
            matchT(TokenType.PROTEGO) && 
            expressao()
            );
    }
    
    // PADROES ------------------------------------
    public boolean condicao(){
        return(matchL("(") &&
        identifier() &&
        operador() &&
        number() &&
        matchL(")"));
    }

    public boolean identifier(){
        return (matchT(TokenType.IDENTIFIER));
    }

    public boolean number(){
        return (matchT(TokenType.NUMBER));
    }

    public boolean fim(){
        return (matchT(TokenType.SEMICOLON));
    }

    public boolean operador(){
        return (matchL(">", ">") || 
        matchL("<", "<") || 
        matchL("=", "=") ||
        matchL("{", "{") ||
        matchL("}", "}"));
    }

    public boolean expressao(){
        return (operador() && 
        identifier() && 
        operador() && 
        number() && 
        fim() && 
        operador());
    }
    

    // VALIDAR VALORES -------------------------
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
        System.out.println(code)
    }
}
