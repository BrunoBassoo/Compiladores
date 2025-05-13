public enum TokenType {
    MAGIC("magic"),
    END_MAGIC("endmagic"),
    SPELL("spell"),
    END_SPELL("endspell"),
    FINITE("finite"),
    INT("int"),
    DEC("dec"),
    STR("str"),
    BOOLEAN("boolean"),
    DELETRIUS("deletrius"),
    INCENDIO("incendio"),
    DEFLEXIO("deflexio"),
    PROTEGO("protego"),
    ALOHOMORA("alohomora"),
    DOOR("door"),
    AVADAKEDAVRA("avadakedavra"),
    RELASHIO("relashio"),
    ACCIO("accio"),
    CRUCIO("crucio"),
    REVELIO("revelio"),
    LEGILIMENS("legilimens"),
    AND("and"),
    OR("or"),
    NOT("not"),
    NULL("NULL"),

    IDENTIFIER("[a-zA-Z_][a-zA-Z0-9_]*"),
    NUMBER("[0-9]+(.[0-9]+)?"),

    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIVIDE("/"),
    EQUAL("="),
    GREATER(">"),
    LESS("<"),
    GREATER_EQUAL(">="),
    LESS_EQUAL("<="),
    SEMICOLON(";"),
    COMMA(","),
    POINT("."),
    LEFT_PAREN("("),
    RIGHT_PAREN(")"),
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    QUESTION_MARK("?"),
    EXCLAMATION_MARK("!"),
    DOUBLE_QUOTES("\""),
    QUOTES("'"),

    EOF("$");

    private final String regex;

    TokenType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
