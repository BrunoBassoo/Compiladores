import java.text.CharacterIterator;

public class CodeTokens extends AFD {

   public Token evaluate(CharacterIterator code) {
        switch (code.current()) {
            case '+':
                code.next();
                return new Token(TokenType.PLUS, "+");
            case '-':
                code.next();
                return new Token(TokenType.MINUS, "-");
            case '*':
                code.next();
                return new Token(TokenType.TIMES, "*");
            case '/':
                code.next();
                return new Token(TokenType.DIVIDE, "/");
            case ';':
                code.next();
                return new Token(TokenType.SEMICOLON, ";");
            case ',':
                code.next();
                return new Token(TokenType.COMMA, ",");
            case '(':
                code.next();
                return new Token(TokenType.LEFT_PAREN, "(");
            case ')':
                code.next();
                return new Token(TokenType.RIGHT_PAREN, ")");
            case '{':
                code.next();
                return new Token(TokenType.LEFT_BRACE, "{");
            case '}':
                code.next();
                return new Token(TokenType.RIGHT_BRACE, "}");
            case '?':
                code.next();
                return new Token(TokenType.QUESTION_MARK, "?");
            case '!':
                code.next();
                return new Token(TokenType.EXCLAMATION_MARK, "!");
            case '=':
                code.next();
                return new Token(TokenType.EQUAL, "=");
            case '"':
                code.next();
                return new Token(TokenType.DOUBLE_QUOTES, "\"");
            case '\'':
                code.next();
                return new Token(TokenType.QUOTES, "\'");
            case CharacterIterator.DONE:
                code.next();
                return new Token(TokenType.EOF, "$");

// ======================================================================== //

// ======================================================================== //

        default:
            if (Character.isLetter(code.current())) {
                String lexema = readWord(code);
                    switch (lexema) {
                        case "magic":
                            return new Token(TokenType.MAGIC, lexema);
                        case "endmagic":
                            return new Token(TokenType.END_MAGIC, lexema);
                        case "spell":
                            return new Token(TokenType.SPELL, lexema);
                        case "endspell":
                            return new Token(TokenType.END_SPELL, lexema);
                        case "finite":
                            return new Token(TokenType.FINITE, lexema);
                        case "int":
                            return new Token(TokenType.INT, lexema);
                        case "dec":
                            return new Token(TokenType.DEC, lexema);
                        case "str":
                            return new Token(TokenType.STR, lexema);
                        case "boolean":
                            return new Token(TokenType.BOOLEAN, lexema);
                        case "deletrius":
                            return new Token(TokenType.DELETRIUS, lexema);
                        case "incendio":
                            return new Token(TokenType.INCENDIO, lexema);
                        case "deflexio":
                            return new Token(TokenType.DEFLEXIO, lexema);
                        case "protego":
                            return new Token(TokenType.PROTEGO, lexema);
                        case "alohomora":
                            return new Token(TokenType.ALOHOMORA, lexema);
                        case "door":
                            return new Token(TokenType.DOOR, lexema);
                        case "avadakedavra":
                            return new Token(TokenType.AVADAKEDAVRA, lexema);
                        case "relashio":
                            return new Token(TokenType.RELASHIO, lexema);
                        case "accio":
                            return new Token(TokenType.ACCIO, lexema);
                        case "crucio":
                            return new Token(TokenType.CRUCIO, lexema);
                        case "revelio":
                            return new Token(TokenType.REVELIO, lexema);
                        case "legilimens":
                            return new Token(TokenType.LEGILIMENS, lexema);
                        case "and":
                            return new Token(TokenType.AND, lexema);
                        case "or":
                            return new Token(TokenType.OR, lexema);
                        case "not":
                            return new Token(TokenType.NOT, lexema);
                            }
                        }
                    }
                return null;
            }
            
        private String readWord(CharacterIterator code) {
            StringBuilder lexema = new StringBuilder();

            while (Character.isLetterOrDigit(code.current())) {
                lexema.append(code.current());
                code.next();
            }
            
            return lexema.toString();
        }
        
}
