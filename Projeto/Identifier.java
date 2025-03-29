import java.text.CharacterIterator;

public class Identifier extends AFD {

    @Override
    public Token evaluate(CharacterIterator code){
        if(Character.isLetter(code.current())){
            String letter = readString(code);

            if (isTokenSeparator(code)){
                return new Token(TokenType.IDENTIFIER, letter);
            }
        }
        return null;
    }
    private String readString(CharacterIterator code){
        String letter="";
        while(Character.isLetter(code.current())){
            letter += code.current();
            code.next();
        }
        return letter;
    }
}