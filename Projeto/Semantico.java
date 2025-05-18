import java.util.List;

public class Semantico {
    private List<Token> tokens;
    private List<Token> tokensCriadosList;
    private Token previousToken;

    public Semantico(List<Token> tokens){
        this.tokens = tokens;
        this.previousToken = null;
    }

    
    public void análise(){


        for(int i = 0; i < tokens.size();i++){ 
            Token currentToken = tokens.get(i);
            if(i == 0){
                previousToken = tokens.get(i);
            } else{
                currentToken = tokens.get(i);
            }
            if(currentToken.getTipo().equals(TokenType.IDENTIFIER) && i > 0){
    
                if(previousToken.getTipo().equals(TokenType.BOOLEAN) 
                || previousToken.getTipo().equals(TokenType.INT)
                || previousToken.getTipo().equals(TokenType.STR)
                || previousToken.getTipo().equals(TokenType.DEC)){
                    tokensCriadosList.add(currentToken);
                    System.out.println(tokensCriadosList);

                }
            }


            

            previousToken = currentToken;
        }
        
    } 
} 