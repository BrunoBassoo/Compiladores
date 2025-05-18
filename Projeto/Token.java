import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Token token = (Token) obj;
        return tipo.equals(token.tipo) && lexema.equals(token.lexema); // ou outros atributos relevantes
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, lexema); // ou outros atributos relevantes
    }   
}
