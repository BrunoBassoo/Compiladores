import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens = new ArrayList<>(); // = null

        // ------------------------------------------------------ //
        // ANALISADOR LEXICO //

        // String data = "int x = 7; incendio(){revelio(oi)}";
        // Lexer lexer = new Lexer(data);
        // tokens = lexer.getTokens();
        // for(Token token : tokens) {
        //     System.out.println(token);
        // }

        // ------------------------------------------------------ //


        // ------------------------------------------------------ //
        // ANALISADOR LEXICO

        // List<Token> tokens = new ArrayList<>();

        // CODIGO ESPERADO: incendio(x > 3){arroz=10;}protego{arroz=0;}

        // IF

        tokens.add(new Token(TokenType.INCENDIO,"incendio"));

        // CONDICAO

        tokens.add(new Token(TokenType.LEFT_PAREN,"("));
        tokens.add(new Token(TokenType.IDENTIFIER, "amigao"));
        tokens.add(new Token(TokenType.GREATER,">"));
        tokens.add(new Token(TokenType.NUMBER,"3"));
        tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));

        // EXPRESSAP

        tokens.add(new Token(TokenType.RIGHT_BRACE, "{"));
        tokens.add(new Token(TokenType.IDENTIFIER, "arroz"));
        tokens.add(new Token(TokenType.EQUAL, "="));
        tokens.add(new Token(TokenType.NUMBER,"10"));
        tokens.add(new Token(TokenType.SEMICOLON, ";"));
        tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));

        // ELSE 

        tokens.add(new Token(TokenType.PROTEGO, "protego"));

        // OPERADOR

        tokens.add(new Token(TokenType.RIGHT_BRACE, "{"));
        tokens.add(new Token(TokenType.IDENTIFIER, "arroz"));
        tokens.add(new Token(TokenType.EQUAL, "=="));
        tokens.add(new Token(TokenType.NUMBER,"0"));
        tokens.add(new Token(TokenType.SEMICOLON, ";"));
        tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));

        // FIM

        tokens.add(new Token(TokenType.EOF, "$"));

        Parser parser = new Parser(tokens);
        parser.main();

        // --------------------------------------------------------- //
    }
}
