import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens1 = null; // = null

        // ------------------------------------------------------ //
        System.out.println("---------------------");
        System.out.println("ANALISADOR LEXICO\n");

        String data = "int x = 3;incendio(amigo > 3){x = 10;}protego{x = 0;}";
        Lexer lexer = new Lexer(data);
        tokens1 = lexer.getTokens();
        for(Token token : tokens1) {
            System.out.println(token);
        }
        System.out.println("\nTUDO CERTO!");
        System.out.println("---------------------\n");

        // ------------------------------------------------------ //


        // ------------------------------------------------------ //
        // ANALISADOR LEXICO

        // List<Token> tokens = new ArrayList<>();

        // CODIGO ESPERADO: int x = 3;incendio(x > 3){arroz=10;}protego{arroz=0;}

        List<Token> tokens =  new ArrayList<>();

        // CRIAR VARIAVEL INTEIRO

        tokens.add(new Token(TokenType.INT,"int"));
        tokens.add(new Token(TokenType.IDENTIFIER, "x"));
        tokens.add(new Token(TokenType.EQUAL,"="));
        tokens.add(new Token(TokenType.NUMBER,"3"));
        tokens.add(new Token(TokenType.SEMICOLON, ";"));
        // tokens.add(new Token(TokenType.EOF, "$"));


        // IF + CONDICAO

        tokens.add(new Token(TokenType.INCENDIO,"incendio"));
        tokens.add(new Token(TokenType.LEFT_PAREN,"("));
        tokens.add(new Token(TokenType.IDENTIFIER, "amigo"));
        tokens.add(new Token(TokenType.GREATER,">"));
        tokens.add(new Token(TokenType.NUMBER,"3"));
        tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));

        // EXPRESSAO

        tokens.add(new Token(TokenType.RIGHT_BRACE, "{"));
        tokens.add(new Token(TokenType.IDENTIFIER, "x"));
        tokens.add(new Token(TokenType.EQUAL, "="));
        tokens.add(new Token(TokenType.NUMBER,"10"));
        tokens.add(new Token(TokenType.SEMICOLON, ";"));
        tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));

        // ELSE + EXPRESSAO

        tokens.add(new Token(TokenType.PROTEGO, "protego"));
        tokens.add(new Token(TokenType.RIGHT_BRACE, "{"));
        tokens.add(new Token(TokenType.IDENTIFIER, "x"));
        tokens.add(new Token(TokenType.EQUAL, "="));
        tokens.add(new Token(TokenType.NUMBER,"0"));
        tokens.add(new Token(TokenType.SEMICOLON, ";"));
        tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));

        // FIM

        tokens.add(new Token(TokenType.EOF, "$"));

        Parser parser1 = new Parser(tokens);
        parser1.main();

        // --------------------------------------------------------- //
    }
}
