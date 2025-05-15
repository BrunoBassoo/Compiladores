import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens1 = null; // = null
        String everything;

        // ------------------------------------------------------ //
        System.out.println("---------------------");
        System.out.println("ANALISADOR LEXICO\n");

        //String data = "int x;incendio(amigo > 3){x = 10;}protego{x = 0;}";

        BufferedReader br = new BufferedReader(new FileReader("code.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }

        System.out.println(everything);

        List<Token> tokens =  new ArrayList<>();

        Lexer lexer = new Lexer(everything);
        tokens1 = lexer.getTokens();
        for(Token token : tokens1) {
            String temp1 = token.getLexema();
            TokenType temp = token.getTipo();
            tokens.add(new Token(temp,temp1));
        }
        System.out.println("\nTUDO CERTO!");
        System.out.println("---------------------\n");

        System.out.println("---------------------");
        System.out.println("ANALISADOR SINTÁTICO\n");

        Parser parser1 = new Parser(tokens);
        parser1.main();

        // --------------------------------------------------------- //
    }
}
