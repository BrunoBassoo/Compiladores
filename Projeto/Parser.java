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
        System.out.println("public class Code{");
        System.out.println("public static void main(String[]agrs){")
    }

    public class SHOLDER(){
        System.out.println("}");
        System.out.println("}");
    }
    

    // PARTE DO IF E ELSE
    public boolean INCENDIO(){
        return(matchT(TokenType.INCENDIO, "if") && 
            condicao() && 
            bloco() && 
            matchT(TokenType.PROTEGO) &&
            bloco()
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

    public boolean bloco(){
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
