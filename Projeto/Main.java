import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens1 = null; // = null 

        // ------------------------------------------------------ //
        System.out.println("---------------------");
        System.out.println("ANALISADOR LEXICO\n");

        String filePath = "code.txt"; // Arquivo deve estar no mesmo diretório do projeto
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Substitui tabulações por espaço e concatena com espaço no final
                result.append(line.replace("\t", " ")).append(" ");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // String final sem espaços extras no início/fim
        String everything = result.toString().trim();

        List<Token> tokens =  new ArrayList<>();

        Lexer lexer = new Lexer(everything);
        tokens1 = lexer.getTokens();
        for(Token token : tokens1) {
            String temp1 = token.getLexema();
            TokenType temp = token.getTipo();
            tokens.add(new Token(temp,temp1));
        }
        System.out.println("--Lista de tokens--\n");
        System.out.println(tokens);

        System.out.println("\nTUDO CERTO!");
        System.out.println("---------------------\n");

        System.out.println("---------------------");
        System.out.println("ANALISADOR SINTÁTICO\n");

        
        Parser parser = new Parser(tokens);
        Tree tree = parser.main();
        if(tree != null){
            System.out.println("\n---------------------");
            System.out.println("--ÁRVORE--\n\n");
            tree.printTree();

            System.out.println("ÁRVORE CORRETA!");
            System.out.println("---------------------\n\n");
        }
        


        System.out.println("---------------------");
        System.out.println("ANALISADOR SEMÂNTICO\n");

        Semantico semantico = new Semantico(tokens);
        semantico.analise();

        System.out.println("\nSEMÂNTICO CORRETO!");
        System.out.println("---------------------\n\n");

        
    }
}
