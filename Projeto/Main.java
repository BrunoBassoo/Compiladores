import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens1 = null; // = null

        // ------------------------------------------------------ //
        System.out.println("---------------------");
        System.out.println("ANALISADOR LEXICO\n");

        //String data = "int x;incendio(amigo > 3){x = 10;}protego{x = 0;}";

        String data = "";

        try (Scanner scanner = new Scanner(new File("code.txt"))) {
            while (scanner.hasNextLine()) {
                data += scanner.nextLine();
            }
            System.out.println("Conteúdo lido: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
         // Remover todos os espaços da string
        //data = data.replace(" ", "");

        List<Token> tokens =  new ArrayList<>();

        Lexer lexer = new Lexer(data);
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
