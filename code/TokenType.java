enum TokenType {
    // Keywords (PotterScript keywords remain in Portuguese)
    MAGIC("magic"),
    END_MAGIC("endmagic"),
    SPELL("spell"),
    END_SPELL("endspell"),
    RETURN("return"),
    INTEGER("integer"),
    DECIMAL("decimal"),
    STRING("string"),
    BOOLEAN("boolean"),
    REVELIO("revelio"),
    PROTEGO("protego"),
    COLLOPORTUS("colloportus"),
    GEMINIO("geminio"),
    INCENDIO("incendio"),
    LUMUS("lumus"),
    ACCIO("accio"),
    AND("and"),
    OR("or"),
    NOT("not"),
    ASSIGN("assign"),


    // Identifier and literals
    IDENTIFIER("[a-zA-Z_][a-zA-Z0-9_]*"),
    NUMBER("[0-9]+(\\.[0-9]+)?"),
    LITERAL_STRING("\"[^\"]*\""), // Changed the name, to avoid conflict

    // Operators and punctuation
    PLUS("\\+"),
    MINUS("-"),
    TIMES("\\*"),
    DIVIDE("/"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER(">"),
    LESS("<"),
    GREATER_EQUAL(">="),
    LESS_EQUAL("<="),
    SEMICOLON(";"),
    COMMA(","),
    LEFT_PAREN("\\("),
    RIGHT_PAREN("\\)"),
    LEFT_BRACE("\\{"),
    RIGHT_BRACE("\\}");


    private final String regex;

    TokenType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
