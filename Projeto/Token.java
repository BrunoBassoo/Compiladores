public class Token {
    private String lexema;
    private TokenType tipo;

    public Token(TokenType tipo, String lexema){
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public String getLexema(){
        return lexema;
    }

    public TokenType getTipo(){
        return tipo;
    }

    @Override
    public String toString(){
        String tipoToken = String.valueOf(this.tipo);
        return "<" + tipoToken + ", " + lexema + ">";
    }
}
