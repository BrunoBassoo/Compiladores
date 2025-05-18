import java.util.ArrayList;
import java.util.List;

public class Semantico {
    private List<Token> tokens;
    private List<Token> tokensCriadosList;
    private Token previousToken;

    public Semantico(List<Token> tokens){
        this.tokens = tokens;
        this.tokensCriadosList = new ArrayList<>();
        this.previousToken = null;
    }

    public void analise(){
        for(int i = 0; i < tokens.size(); i++){ 
            Token currentToken = tokens.get(i);

            if(currentToken.getTipo().equals(TokenType.IDENTIFIER) && previousToken != null){
                if(previousToken.getTipo().equals(TokenType.BOOLEAN) 
                    || previousToken.getTipo().equals(TokenType.INT)
                    || previousToken.getTipo().equals(TokenType.STR)
                    || previousToken.getTipo().equals(TokenType.DEC)){
                    
                    tokensCriadosList.add(currentToken);
                    System.out.println(tokensCriadosList);
                }
            }

            previousToken = currentToken; // Atualiza o anterior no final
        }
    }
}