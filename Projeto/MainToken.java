public class MainToken {
    
    public static void main(String[] args){
        Token id = new Token(TokenType.IDENTIFIER, "3");
        Token num = new Token(TokenType.NUMBER, "2");

        System.out.println(id);
        System.out.println(num);
    }
}
