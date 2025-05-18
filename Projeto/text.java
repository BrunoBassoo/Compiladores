import java.text.CharacterIterator;

public class text extends AFD {

    @Override
    public Token evaluate(CharacterIterator code){
        String letter = readString(code);
        System.out.println(code.current());
        if (isTokenSeparator(code)){
            return new Token(TokenType.TEXT, letter);
        }
        
        return null;
    }
    private String readString(CharacterIterator code){
        String letter="\"";
        System.out.println("a");
        if (code.current() == '"') {
            code.next();
        } else {
            return null;
        }

        while (code.current() != '"') {
            letter += code.current();
            code.next();
        }

        if (code.current() == '\"') {
            letter += "\"";
            code.next();
        } else {
            return null;
        }

        return letter;
    }
}