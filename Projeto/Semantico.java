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

            // tratamento do ID
            if(currentToken.getTipo().equals(TokenType.IDENTIFIER) && previousToken != null){


                if(previousToken.getTipo().equals(TokenType.BOOLEAN) 
                    || previousToken.getTipo().equals(TokenType.INT)
                    || previousToken.getTipo().equals(TokenType.STR)
                    || previousToken.getTipo().equals(TokenType.DEC)){
                    
                        System.out.println(currentToken);
                        if(tokensCriadosList.contains(currentToken)){
                            System.out.println("Error: variavel já criada.");
                            break;
                        }
                    tokensCriadosList.add(currentToken);
                    System.out.println(tokensCriadosList);
                    
                }
            }
            
            // // tratamento do number
            // if(currentToken.getTipo().equals(TokenType.NUMBER) && previousToken != null){
            //     return;
            // }

            previousToken = currentToken; // Atualiza o anterior no final
        }
    }
}