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
        System.out.println(token);
        System.out.println(getNextToken());
        System.exit(0);
    }

    public void main(){
        token = getNextToken();
        if (INCENDIO()){
            if(token.getTipo().equals(TokenType.EOF)){
                System.out.println("sintaticamente correto");
                return;
            }
        }
        else if(INTEIRO()){
            if(token.getTipo().equals(TokenType.EOF)){
                System.out.println("sintaticamente correto");
                return;
            }
        }
        erro("main");
    }

    // CODIGO ESPERADO: incendio(x > 3){arroz=10;}protego{arroz=0;}

    // CRIAR VARIAVEL INTEIRA
    public boolean INTEIRO(){
        return (matchT(TokenType.INT) && 
        matchT(TokenType.IDENTIFIER) && 
        matchL("=") && 
        matchT(TokenType.NUMBER) && 
        matchT(TokenType.SEMICOLON));
    }

    // CRIAR VARIAVEL DECIMAL (FLOAT)
    public boolean DECIMAL(){
        return (matchT(TokenType.DEC) && 
        matchT(TokenType.IDENTIFIER) && 
        matchL("=") && 
        matchT(TokenType.NUMBER) && 
        matchL(".") && 
        matchT(TokenType.NUMBER) && 
        matchT(TokenType.SEMICOLON));
    }

    // CRIAR STRING 
    public boolean STRING(){
        return (matchT(TokenType.STR) && 
        matchT(TokenType.IDENTIFIER) && 
        matchL("=") && 
        matchT(TokenType.DOUBLE_QUOTES) && 
        matchT(TokenType.IDENTIFIER) && 
        matchT(TokenType.DOUBLE_QUOTES) && 
        matchT(TokenType.SEMICOLON));
    }

    // PARTE DO IF E ELSE
    public boolean INCENDIO(){
        if(matchT(TokenType.INCENDIO) && 
            condicao() && 
            expressao() && 
            matchT(TokenType.PROTEGO) && 
            expressao())
            {
            return true;
            }
        return false;
    }
    
    // PADROES ------------------------------------
    public boolean condicao(){
        return (matchL("(") && 
        matchT(TokenType.IDENTIFIER) && 
        matchL(">") && 
        matchT(TokenType.IDENTIFIER) && 
        matchL(")") && 
        matchT(TokenType.SEMICOLON));
    }

    public boolean operador(){
        return (matchL(">") || 
        matchL("<") || 
        matchL("="));
    }

    public boolean expressao(){
        return (matchL("{") && 
        matchT(TokenType.IDENTIFIER) && 
        matchL("=") && 
        matchT(TokenType.NUMBER) && 
        matchT(TokenType.SEMICOLON) && 
        matchL("}"));
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
}