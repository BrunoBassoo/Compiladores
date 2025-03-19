import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PotterLexer {

    private String sourceCode;
    private int position;
    private int line;
    private int column;

    public PotterLexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.position = 0;
        this.line = 1;
        this.column = 1;
    }

    public List<Token> tokenize() throws LexerException {
        List<Token> tokens = new ArrayList<>();
        Token token;

        while ((token = nextToken()) != null) {
            tokens.add(token);
        }

        return tokens;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Token nextToken() throws LexerException {
        skipWhitespaceAndComments();

        if (position >= sourceCode.length()) {
            return null; // End of source code
        }

        // Attempt to match token patterns
        for (TokenType type : TokenType.values()) {
            Pattern pattern = Pattern.compile(type.getRegex());
            Matcher matcher = pattern.matcher(sourceCode.substring(position));

            if (matcher.find()) {
                String lexeme = matcher.group();
                Token token = new Token(type, lexeme, line, column);

                // Adjust position, line, and column
                int lexemeLength = lexeme.length();
                position += lexemeLength;
                column += lexemeLength;

                // Handling for keywords (using PotterScript keywords)
                if (type == TokenType.IDENTIFIER) {
                    if (lexeme.equals("magic")) {
                        token = new Token(TokenType.MAGIC, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("endmagic")) {
                        token = new Token(TokenType.END_MAGIC, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("spell")) {
                        token = new Token(TokenType.SPELL, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("endspell")) {
                        token = new Token(TokenType.END_SPELL, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("return")) {
                        token = new Token(TokenType.RETURN, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("integer")) {
                        token = new Token(TokenType.INTEGER, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("decimal")) {
                        token = new Token(TokenType.DECIMAL, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("string")) {
                        token = new Token(TokenType.STRING, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("boolean")) {
                        token = new Token(TokenType.BOOLEAN, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("revelio")) {
                        token = new Token(TokenType.REVELIO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("protego")) {
                        token = new Token(TokenType.PROTEGO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("colloportus")) {
                        token = new Token(TokenType.COLLOPORTUS, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("geminio")) {
                        token = new Token(TokenType.GEMINIO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("incendio")) {
                        token = new Token(TokenType.INCENDIO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("lumus")) {
                        token = new Token(TokenType.LUMUS, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("accio")) {
                        token = new Token(TokenType.ACCIO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("and")) {
                        token = new Token(TokenType.AND, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("or")) {
                        token = new Token(TokenType.OR, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("not")) {
                        token = new Token(TokenType.NOT, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("assign")) {
                        token = new Token(TokenType.ASSIGN, lexeme, line, column - lexemeLength);
                    }  else if (lexeme.equals("true") ) {
                        token = new Token(TokenType.BOOLEAN, lexeme, line, column - lexemeLength, true);
                    }
                    else if (lexeme.equals("false")) {
                        token = new Token(TokenType.BOOLEAN, lexeme, line, column - lexemeLength, false);
                    }
                }
                else if(type == TokenType.NUMBER)
                {
                    if(!lexeme.contains(".")){
                        token = new Token(TokenType.INTEGER, lexeme, line, column - lexemeLength, Integer.parseInt(lexeme));
                    }
                    else{
                        token = new Token(TokenType.DECIMAL, lexeme, line, column - lexemeLength, Double.parseDouble(lexeme));
                    }
                }
                else if(type == TokenType.LITERAL_STRING){
                    token = new Token(TokenType.STRING, lexeme.substring(1, lexeme.length() -1), line, column - lexemeLength); //Keep using STRING
                }

                return token;
            }
        }

        // If it got here, it's because it found an invalid character
        throw new LexerException("Invalid character '" + sourceCode.charAt(position) + "' at line " + line + ", column " + column);
    }

    private void skipWhitespaceAndComments() {
        while (position < sourceCode.length()) {
            char c = sourceCode.charAt(position);

            // Ignore whitespace, tabs, and newlines
            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
                position++;
                continue;
            }

            // Ignore single-line comments (//)
            if (c == '/' && position + 1 < sourceCode.length() && sourceCode.charAt(position + 1) == '/') {
                while (position < sourceCode.length() && sourceCode.charAt(position) != '\n') {
                    position++;
                }
                continue;
            }

            // Ignore block comments (/* ... */)
            if (c == '/' && position + 1 < sourceCode.length() && sourceCode.charAt(position + 1) == '*') {
                position += 2; // Advance past "/*"
                while (position + 1 < sourceCode.length()) {
                    if (sourceCode.charAt(position) == '*' && sourceCode.charAt(position + 1) == '/') {
                        position += 2; // Advance past "*/"
                        break;
                    } else if (sourceCode.charAt(position) == '\n') {
                        line++;
                        column = 1;
                    } else {
                        column++;
                    }
                    position++;
                }
                continue;
            }

            break; // If not whitespace or comment, exit loop
        }
    }

    public static void main(String[] args) {
        // Example usage
        String sourceCode = """
            magic Exemplo
                integer vida assign 100;
                string nome assign "Harry Potter";
                boolean teste assign true;
                // Line comment
                /*
                 Block
                 comment
                */
                lumus("Olá, " + nome + "! Sua vida é: " + vida);

                /*
                Let's test the new features
                */

                //geminio, incendio, etc

                geminio(vida > 50){
                    lumus("You still have a lot of life");
                }

                revelio(teste){
                    lumus("O valor booleano é verdadeiro");
                }


            endmagic
            """;

        try {
            PotterLexer lexer = new PotterLexer(sourceCode);
            List<Token> tokens = lexer.tokenize();

            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (LexerException e) {
            System.err.println(e.getMessage());
        }
    }
}
