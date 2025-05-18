import java.util.*;

public class Semantico {
    private List<Token> tokens;
    private Map<String, String> variaveisDeclaradas = new HashMap<>();

    public Semantico(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analise() {
        for (int i = 0; i < tokens.size(); i++) {
            Token current = tokens.get(i);

            // Declaração de variável
            if (isTipo(current)) {
                Token proximo = tokens.get(i + 1);
                if (proximo.getTipo() == TokenType.IDENTIFIER) {
                    String tipo = current.getLexema();
                    String nome = proximo.getLexema();
                    if (variaveisDeclaradas.containsKey(nome)) {
                        System.out.println("Erro: variável '" + nome + "' já declarada.");
                    } else {
                        variaveisDeclaradas.put(nome, tipo);
                        System.out.println("Declarada variável '" + nome + "' do tipo " + tipo.toUpperCase());
                    }

                    // Verifica se há uma atribuição
                    if (i + 2 < tokens.size() && tokens.get(i + 2).getTipo() == TokenType.EQUAL) {
                        int j = i + 3;
                        List<Token> expressao = new ArrayList<>();
                        while (j < tokens.size() && tokens.get(j).getTipo() != TokenType.SEMICOLON) {
                            expressao.add(tokens.get(j));
                            j++;
                        }
                        String tipoExpressao = avaliarExpressao(expressao);
                        if (!tipoExpressao.equals(tipo) && !tipoExpressao.equals("erro")) {
                            System.out.println("Erro: atribuição incompatível para '" + nome + "'");
                        }
                    }
                }
            }

            // Atribuição sem declaração
            if (current.getTipo() == TokenType.IDENTIFIER &&
                i + 1 < tokens.size() &&
                tokens.get(i + 1).getTipo() == TokenType.EQUAL) {
                String nome = current.getLexema();
                if (!variaveisDeclaradas.containsKey(nome)) {
                    System.out.println("Erro: variável '" + nome + "' não declarada.");
                } else {
                    int j = i + 2;
                    List<Token> expressao = new ArrayList<>();
                    while (j < tokens.size() && tokens.get(j).getTipo() != TokenType.SEMICOLON) {
                        expressao.add(tokens.get(j));
                        j++;
                    }
                    String tipoEsperado = variaveisDeclaradas.get(nome);
                    String tipoExpressao = avaliarExpressao(expressao);
                    if (!tipoExpressao.equals(tipoEsperado) && !tipoExpressao.equals("erro")) {
                        System.out.println("Erro: atribuição incompatível para '" + nome + "'");
                    }
                }
            }

            // Condições em comandos como 'incendio'
            if (isComandoCondicional(current)) {
                int j = i + 1;
                List<Token> condicao = new ArrayList<>();
                int par = 0;
                while (j < tokens.size()) {
                    if (tokens.get(j).getTipo() == TokenType.LEFT_PAREN) {
                        par++;
                    } else if (tokens.get(j).getTipo() == TokenType.RIGHT_PAREN) {
                        par--;
                        if (par == 0) break;
                    }
                    condicao.add(tokens.get(j));
                    j++;
                }
                String tipoCondicao = avaliarExpressao(condicao);
                if (!tipoCondicao.equals("boolean")) {
                    System.out.println("Erro: condição inválida em comando '" + current.getLexema() + "'");
                }
            }
        }
    }

    private boolean isTipo(Token token) {
        return token.getTipo() == TokenType.INT ||
               token.getTipo() == TokenType.STR ||
               token.getTipo() == TokenType.BOOLEAN ||
               token.getTipo() == TokenType.DEC;
    }

    private boolean isComandoCondicional(Token token) {
        return token.getTipo() == TokenType.INCENDIO || token.getTipo() == TokenType.PROTEGO;
    }

    private String avaliarExpressao(List<Token> expressao) {
        if (expressao.isEmpty()) return "erro";

        // Expressão com um único valor
        if (expressao.size() == 1) {
            Token t = expressao.get(0);
            return obterTipoToken(t);
        }

        // Expressão binária simples (a + b, x == y, etc.)
        if (expressao.size() == 3) {
            String tipoEsq = obterTipoToken(expressao.get(0));
            Token op = expressao.get(1);
            String tipoDir = obterTipoToken(expressao.get(2));

            switch (op.getTipo()) {
                case PLUS:
                    if (tipoEsq.equals("int") && tipoDir.equals("int")) return "int";
                    if (tipoEsq.equals("dec") && tipoDir.equals("dec")) return "dec";
                    if (tipoEsq.equals("str") && tipoDir.equals("str")) return "str";
                    break;
                case MINUS: case TIMES: case DIVIDE:
                    if (tipoEsq.equals("int") && tipoDir.equals("int")) return "int";
                    if (tipoEsq.equals("dec") && tipoDir.equals("dec")) return "dec";
                    break;
                case EQUAL: case GREATER: case LESS:
                    if (tipoEsq.equals(tipoDir)) return "boolean";
                    System.out.println("Erro: comparação entre tipos incompatíveis: '" + tipoEsq + "' e '" + tipoDir + "'");
                    return "erro";
                case AND: case OR:
                    if (tipoEsq.equals("boolean") && tipoDir.equals("boolean")) return "boolean";
                    break;
            }
        }

        return "erro";
    }

    private String obterTipoToken(Token token) {
        switch (token.getTipo()) {
            case NUMBER:
                if (token.getLexema().contains(".")) return "dec";
                return "int";
            case TEXT: return "str";
            case NULL: return "null";
            case NOT: return "boolean";
            case IDENTIFIER:
                return variaveisDeclaradas.getOrDefault(token.getLexema(), "erro");
            default: return "erro";
        }
    }
}
