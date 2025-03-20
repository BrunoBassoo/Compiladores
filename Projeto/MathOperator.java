import java.text.CharacterIterator;

public class MathOperator extends AFD{
    
    @Override
    public Token evaluate(CharacterIterator code){

        switch(code.current()){
            case '+':
                code.next();
                return new Token(TokenType.PLUS, "+");

            case CharacterIterator.DONE:
                return new Token(TokenType.EOF, "$");
            
            default:
                return null;
        }
    }
}
