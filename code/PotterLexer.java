// File: PotterLexer.java

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
        for (TokenType type : TokenType.values()) { // TokenType em inglês
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
                if (type == TokenType.IDENTIFIER) { // TokenType em inglês
                    if (lexeme.equals("magia")) {
                        token = new Token(TokenType.MAGIA, lexeme, line, column - lexemeLength); // TokenType em inglês
                    } else if (lexeme.equals("fim_magia")) {
                        token = new Token(TokenType.FIM_MAGIA, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("feitico")) {
                        token = new Token(TokenType.FEITICO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("fim_feitico")) {
                        token = new Token(TokenType.FIM_FEITICO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("retorna")) {
                        token = new Token(TokenType.RETORNA, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("inteiro")) {
                        token = new Token(TokenType.INTEIRO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("decimal")) {
                        token = new Token(TokenType.DECIMAL, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("texto")) {
                        token = new Token(TokenType.TEXTO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("booleano")) {
                        token = new Token(TokenType.BOOLEANO, lexeme, line, column - lexemeLength);
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
                    } else if (lexeme.equals("e")) {
                        token = new Token(TokenType.E, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("ou")) {
                        token = new Token(TokenType.OU, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("nao")) {
                        token = new Token(TokenType.NAO, lexeme, line, column - lexemeLength);
                    } else if (lexeme.equals("atribui")) {
                        token = new Token(TokenType.ATRIBUI, lexeme, line, column - lexemeLength);
                    }  else if (lexeme.equals("verdadeiro") ) {
                        token = new Token(TokenType.BOOLEAN, lexeme, line, column - lexemeLength, true); //TokenType em inglês
                    }
                    else if (lexeme.equals("falso")) {
                        token = new Token(TokenType.BOOLEAN, lexeme, line, column - lexemeLength, false);//TokenType em inglês
                    }

                }
                else if(type == TokenType.NUMBER) //TokenType em inglês
                {
                    if(!lexeme.contains(".")){
                        token = new Token(TokenType.INTEIRO, lexeme, line, column - lexemeLength, Integer.parseInt(lexeme));
                    }
                    else{
                        token = new Token(TokenType.DECIMAL, lexeme, line, column - lexemeLength, Double.parseDouble(lexeme));
                    }
                }
                else if(type == TokenType.STRING){ //TokenType em inglês
                    token = new Token(TokenType.TEXTO, lexeme.substring(1, lexeme.length() -1), line, column - lexemeLength);
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
            magia Exemplo
                inteiro vida atribui 100;
                texto nome atribui "Harry Potter";
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
                
                
            fim_magia
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
