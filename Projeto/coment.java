import java.text.CharacterIterator;

public class coment extends AFD {

    @Override
    public Token evaluate(CharacterIterator code){
        String letter = readString(code);
        if (isTokenSeparator(code)){
            return new Token(TokenType.COMENT, letter);
        }
        
        return null;
    }
    private String readString(CharacterIterator code){
        String letter="";
        if (code.current() == '|') {
            code.next();
        } else {
            return null;
        }

        while (code.current() != '|') {
            letter += code.current();
            code.next();
        }

        if (code.current() == '|') {
            code.next();
        } else {
            return null;
        }

        return letter;
    }
}