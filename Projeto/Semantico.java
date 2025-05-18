import java.util.List;

public class Semantico {
    private List<Token> tokens;

    public Semantico(List<Token> tokens){
        this.tokens = tokens;
    }

    
    public void análise(){
        //System.out.println(tokens);
        List<Token> tokensCriador;
        for(int i = 0; i < tokens.size();i++){ 
            Token currentToken = tokens.get(i);
            if(currentToken.getTipo().equals(TokenType.IDENTIFIER) && i > 0){
                Token anterior = tokens.get(i--);

                if(anterior.getTipo().equals(TokenType.BOOLEAN) 
                || anterior.getTipo().equals(TokenType.INT)
                || anterior.getTipo().equals(TokenType.STR)
                || anterior.getTipo().equals(TokenType.DEC)){
                    

                }
            }


            


        }
        
    } 
}   

