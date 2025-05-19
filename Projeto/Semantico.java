import java.util.*;

public class Semantico {
    private List<Token> tokens;
    private Map<String, String> variaveisDeclaradas = new HashMap<>();
    public boolean ifCerto = false;
    public boolean loopCerto = false;

    public Semantico(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analise() {
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            // Declaração de variável: tipo seguido de identificador
            if (isTipo(token.getTipo())) {
                if (i + 1 < tokens.size() && tokens.get(i + 1).getTipo() == TokenType.IDENTIFIER) {
                    String tipo = token.getTipo().toString().toLowerCase();
                    String nomeVar = tokens.get(i + 1).getLexema();

                    if (variaveisDeclaradas.containsKey(nomeVar)) {
                        System.out.println("Erro: variável '" + nomeVar + "' já foi declarada.");
                    } else {
                        variaveisDeclaradas.put(nomeVar, tipo);
                    }

                    // Atribuição direta no momento da declaração
                    if (i + 2 < tokens.size() && tokens.get(i + 2).getTipo() == TokenType.EQUAL) {
                        int fimExpr = encontrarFimExpressao(i + 1);
                        String tipoExpr = avaliarExpressao(i + 3, fimExpr);
                        if (!tipoExpr.equals(tipo)) {
                            System.out.println("Erro: atribuição incompatível para '" + nomeVar + "'");
                        }
                        i = fimExpr;
                    } else {
                        i += 1;
                    }
                }
            }

            // Atribuição fora da declaração
            if (token.getTipo() == TokenType.IDENTIFIER && i + 1 < tokens.size()
                    && tokens.get(i + 1).getTipo() == TokenType.EQUAL) {
                String nomeVar = token.getLexema();
                String tipoVar = variaveisDeclaradas.get(nomeVar);

                if (tipoVar == null) {
                    System.out.println("Erro: variável '" + nomeVar + "' não foi declarada.");
                } else {
                    int fimExpr = encontrarFimExpressao(i + 2);
                    String tipoExpr = avaliarExpressao(i + 2, fimExpr);
                    if (!tipoExpr.equalsIgnoreCase(tipoVar)) {
                        System.out.println("Erro: atribuição incompatível para '" + nomeVar + "'");
                    }
                    i = fimExpr;
                }
            }

            // Comando com expressão lógica: incendio(condição)
            if (isComandoCondicional(token.getTipo())) {
                ifCerto = true;
                if (i + 2 < tokens.size() && tokens.get(i + 1).getTipo() == TokenType.LEFT_PAREN) {
                    int fimExpr = encontrarParentesesFechando(i + 1);
                    String tipoCondicao = avaliarExpressao(i + 2, fimExpr);
                    if (!tipoCondicao.equals("boolean")) {
                        System.out.println("Erro: condição inválida em comando '" + token.getLexema() + "'");
                    }
                    i = fimExpr;
                }
            }

            if (isComandoLoop(token.getTipo())) {
                loopCerto = true;
                if (i + 2 < tokens.size() && tokens.get(i + 1).getTipo() == TokenType.LEFT_PAREN) {
                    int fimExpr = encontrarParentesesFechando(i + 1);
                    String tipoCondicao = avaliarExpressao(i + 2, fimExpr);
                    if (!tipoCondicao.equals("boolean")) {
                        System.out.println("Erro: condição inválida em loop '" + token.getLexema() + "'");
                    }
                    i = fimExpr;
                }
            }

            // Comando de saída: revellio(expr)
            if (token.getTipo() == TokenType.REVELIO) {
                if (i + 2 < tokens.size() && tokens.get(i + 1).getTipo() == TokenType.LEFT_PAREN) {
                    int fimExpr = encontrarParentesesFechando(i + 1);
                    String tipoExpr = avaliarExpressao(i + 2, fimExpr - 1);
                    // Aqui você pode validar se o tipo é permitido para print, se quiser
                    i = fimExpr;
                }
            }

        }
    }

    private boolean isTipo(TokenType tipo) {
        return tipo == TokenType.INT || tipo == TokenType.DEC || tipo == TokenType.STR || tipo == TokenType.BOOLEAN;
    }

    private boolean isComandoCondicional(TokenType tipo) {
        return tipo == TokenType.INCENDIO || tipo == TokenType.DEFLEXIO;
    }

    private boolean isComandoLoop(TokenType tipo) {
        return tipo == TokenType.CRUCIO || tipo == TokenType.ACCIO;
    }

    private int encontrarParentesesFechando(int inicio) {
        int contador = 0;
        for (int i = inicio; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo() == TokenType.LEFT_PAREN)
                contador++;
            else if (tokens.get(i).getTipo() == TokenType.RIGHT_PAREN)
                contador--;
            if (contador == 0)
                return i;
        }
        return tokens.size() - 1;
    }

    private int encontrarPontoVirgula(int inicio) {
        for (int i = inicio; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo() == TokenType.SEMICOLON)
                return i;
        }
        return tokens.size() - 1;
    }

    private int encontrarFimExpressao(int inicio) {
        for (int i = inicio; i < tokens.size(); i++) {
            TokenType tipo = tokens.get(i).getTipo();
            if (tipo == TokenType.SEMICOLON || tipo == TokenType.RIGHT_PAREN)
                return i - 1;
        }
        return tokens.size() - 1;
    }

    private String avaliarExpressao(int start, int end) {
        System.out.println(end + " - " + start);
        if (start > end)
            return "erro";

        // Expressão simples: um valor ou identificador
        if (start == end) {
            Token token = tokens.get(start);
            switch (token.getTipo()) {
                case NUMBER:
                    return "int";
                case TEXT:
                    return "str";
                case IDENTIFIER:

                    String tipo = variaveisDeclaradas.get(token.getLexema());
                    if (tipo == null) {
                        System.out.println("Erro: variável '" + token.getLexema() + "' não foi declarada.");
                        return "erro";
                    }
                    return tipo;
                default:
                    return "erro";
            }
        }

        // Expressão binária simples: x + y, x == y
        if (end - start == 2) {
            String tipoEsq = avaliarExpressao(start, start);
            Token operador = tokens.get(start + 1);
            String tipoDir = avaliarExpressao(end, end);

            switch (operador.getTipo()) {
                case PLUS:
                    if (tipoEsq.equals("int") && tipoDir.equals("int"))
                        return "int";
                    if (tipoEsq.equals("str") && tipoDir.equals("str"))
                        return "str";
                    System.out.println("Erro: operador '+' inválido entre '" + tipoEsq + "' e '" + tipoDir + "'");
                    return "erro";
                case EQUAL_EQUAL:
                case LESS:
                case GREATER:
                case LESS_EQUAL:
                case GREATER_EQUAL:
                case NOT_EQUAL:
                    if (tipoEsq.equals(tipoDir))
                        return "boolean";
                    System.out.println(
                            "Erro: operador '==' entre tipos incompatíveis '" + tipoEsq + "' e '" + tipoDir + "'");
                    return "erro";
                default:
                    System.out.println("Erro: operador '" + operador.getLexema() + "' não suportado.");
                    return "erro";
            }
        }

        if (end != start) {

            if (ifCerto) {
                String tipoEsq = avaliarExpressao(start, start);
                System.out.println("tipoEsq: " + tipoEsq);
                Token operador = tokens.get(start + 1);
                System.out.println("operador: " + operador.getLexema());
                System.out.println("operador: " + operador.getTipo());
                String tipoDir = avaliarExpressao(end - 1, end - 1);
                System.out.println("tipoDir: " + tipoDir);

                switch (operador.getTipo()) {
                    case PLUS:
                        if (tipoEsq.equals("int") && tipoDir.equals("int"))
                            return "int";
                        if (tipoEsq.equals("str") && tipoDir.equals("str"))
                            return "str";
                        System.out.println("Erro: operador '+' inválido entre '" + tipoEsq + "' e '" + tipoDir + "'");
                        return "erro";
                    case EQUAL:
                    case GREATER:
                    case LESS:
                        if (tokens.get(start + 2).getTipo() == TokenType.EQUAL) {
                            if (tipoEsq.equals(tipoDir)) {
                                return "boolean";
                            } else {
                                System.out.println(
                                        "Erro: operador '==' entre tipos incompatíveis '" + tipoEsq + "' e '" + tipoDir
                                                + "'");
                                return "erro";
                            }
                        }
                }
            }

            if (loopCerto) {
                String tipoEsq = avaliarExpressao(start, start);
                System.out.println("tipoEsq: " + tipoEsq);
                Token operador = tokens.get(start + 1);
                String tipoDir = avaliarExpressao(start + 2,start + 2);
                System.out.println("tipoDir: " + tipoDir);
                if (tipoEsq.equals(tipoDir)) {
                    int primeiroPontoVirgula = encontrarPontoVirgula(start + 3);
                    System.out.println(primeiroPontoVirgula);
                    String tipoEsq2 = avaliarExpressao(primeiroPontoVirgula + 1, primeiroPontoVirgula + 1);
                    System.out.println("tipoEsq2: " + tipoEsq2);
                    String tipoDir2 = avaliarExpressao(primeiroPontoVirgula + 4, primeiroPontoVirgula + 4);
                    System.out.println("tipoDir2: " + tipoDir2);
                    if(tipoEsq2.equals(tipoDir2)){
                        int segundoPontoVirgula = encontrarPontoVirgula(primeiroPontoVirgula + 1);
                        String tipoDir3 = avaliarExpressao( segundoPontoVirgula + 2, segundoPontoVirgula + 2);
                        System.out.println("tipoDir3: " + tipoDir3);
                        String tipoDir3_2 = avaliarExpressao( segundoPontoVirgula + 3, segundoPontoVirgula + 3);
                        System.out.println("tipoDir3_2: " + tipoDir3_2);
                        if(tipoDir3 == "+" && tipoDir3_2 == "+" || tipoDir3 == "-" && tipoDir3_2 == "-" ) {
                            return "boolean";
                        }
                    }
                    return "boolean";
                    
                }

            } else {
                return "indefinido";
            }

        }
        return "indefinido"; // para casos mais complexos
    }
}