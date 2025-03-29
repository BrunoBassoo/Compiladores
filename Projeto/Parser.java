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
        token = getNextToken();
        if (ifelse()){
            if(token.getTipo().equals("EOF")){
                System.out.println("sintaticamente correto");
                return;
            }
        }
        erro("main");
    }

    // CODIGO ESPERADO: incendio(x > 3){arroz=10;}protego{arroz=0;}

    public boolean ifelse(){
        if(matchL("incendio") && 
            condicao() && 
            expressao() && 
            matchL("protego") && 
            expressao())
            {
            return true;
            }
        return false;
    }
    
    public boolean condicao(){
        return (matchL("(") && identifier() && operador() && number() && matchL(")") &&matchT("SEMICOLON"));
    }

    public boolean identifier(){
        return (matchT("IDENTIFIER"));
    }

    public boolean number(){
        return (matchT("NUMBER"));
    }

    public boolean operador(){
        return (matchL(">") || matchL("<") || matchL("=="));
    }

    public boolean expressao(){
        return (matchL("{") && identifier() && matchL("=") && number() && matchL("}") && matchT("SEMICOLON"));
    }
    

    public boolean matchL(String lexema){
        if(token.getLexema().equals(lexema)){
            token = getNextToken();
            return true;
        } return false;
    }
    
    public boolean matchT(String tipo){
        if(token.getTipo().equals(tipo)){
            token = getNextToken();
            return true;
        } return false;
    }
}