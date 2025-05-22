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
                        System.out.println("ERRO ====> VARIÁVEL '" + nomeVar + "' JÁ DECLARADA");
                    } else {
                        variaveisDeclaradas.put(nomeVar, tipo);
                    }

                    // Atribuição direta no momento da declaração
                    if (i + 2 < tokens.size() && tokens.get(i + 2).getTipo() == TokenType.EQUAL) {
                        int fimExpr = encontrarFimExpressao(i + 1);
                        String tipoExpr;
                        if (tokens.get(i + 3).getTipo() == TokenType.LEGILIMENS) {
                            tipoExpr = tipo;
                        } else {
                            tipoExpr = avaliarExpressao(i + 3, fimExpr);
                        }
                        if (!tipoExpr.equals(tipo)) {
                            System.out.println("ERRO ====> ATRIBUIÇÃO INCOMPATÍVEL PARAA '" + nomeVar + "'");
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
                String tipoExpr;
                System.out.println(tokens.get(i + 2).getTipo());
                if (tokens.get(i + 2).getTipo() == TokenType.LEGILIMENS) {
                    tipoExpr = tipoVar;
                } else {
                    if (tipoVar == null) {
                        String tipo = tokens.get(i - 1).getTipo().toString();
                        if (tokens.get(i - 1).getTipo() == TokenType.INT
                                || tokens.get(i - 1).getTipo() == TokenType.BOOLEAN
                                || tokens.get(i - 1).getTipo() == TokenType.DEC
                                || tokens.get(i - 1).getTipo() == TokenType.STR) {
                            variaveisDeclaradas.put(nomeVar, tipo);
                            continue;
                        } else {
                            System.out.println("ERRO ====> VARIÁVEL '" + nomeVar + "' NÃO FOI DECLARADA");
                        }
                    } else {
                        int fimExpr = encontrarFimExpressao(i + 2);
                        tipoExpr = avaliarExpressao(i + 2, fimExpr);
                        if (!tipoExpr.equalsIgnoreCase(tipoVar)) {
                            System.out.println("ERRO ====> ATRIBUIÇÃO INCOMPATÍVEL PARA '" + nomeVar + "'");
                        }
                        i = fimExpr;
                    }
                }
            }

            // Comando com expressão lógica: incendio(condição)
            if (isComandoCondicional(token.getTipo())) {
                ifCerto = true;
                if (i + 2 < tokens.size() && tokens.get(i + 1).getTipo() == TokenType.LEFT_PAREN) {
                    int fimExpr = encontrarParentesesFechando(i + 1);
                    String tipoCondicao = avaliarExpressao(i + 2, fimExpr);
                    if (!tipoCondicao.equals("boolean")) {
                        System.out.println("ERRO ====> CONDIÇÃO INVÁLIDA EM COMANDO '" + token.getLexema() + "'");
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
                        System.out.println("ERRO ====> CONDIÇÃO INVÁLIDA EM LOOP '" + token.getLexema() + "'");
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
        return tipo == TokenType.INCENDIO || tipo == TokenType.DEFLEXIO || tipo == TokenType.CRUCIO;
    }

    private boolean isComandoLoop(TokenType tipo) {
        return tipo == TokenType.ACCIO;
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
        if (start > end)
            return "erro";

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
                        System.out.println("ERRO ====> VARIÁVEL '" + token.getLexema() + "' NÃO FOI DECLARADA");
                        return "erro";
                    }
                    return tipo;
                case INT:
                    return "int";
                default:
                    return "erro";
            }
        }

        if (end - start == 2) {
            String tipoEsq = avaliarExpressao(start, start);
            Token operador = tokens.get(start + 1);
            Token verificaToken = tokens.get(start - 2);
            String tipoDir = avaliarExpressao(end, end);

            if(verificaToken.getTipo() != TokenType.REVELIO){
                switch (operador.getTipo()) {
                    case PLUS:
                        if (tipoEsq.equals("int") && tipoDir.equals("int"))
                            return "int";
                        if (tipoEsq.equals("str") && tipoDir.equals("str"))
                            return "str";
                        System.out.println("ERRO ====> OPERADOR '+' INVÁLIDO ENTRE '" + tipoEsq + "' e '" + tipoDir + "'");
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
                                "ERRO ====> OPERADOR DE COMPARAÇÃO INVÁLIDO ENTRE TIPOS INCOMPATÍVEIS '" + tipoEsq + "' e '"
                                        + tipoDir + "'");
                        return "erro";
                    default:
                        System.out.println("ERRO ====> OPERADOR '" + operador.getLexema() + "' NÃO SUPORTADO.");
                        return "erro";
                }
            }
        }

        if (end != start) {

            if (ifCerto) {
                String tipoEsq = avaliarExpressao(start, start);
                Token operador = tokens.get(start + 1);
                String tipoDir = avaliarExpressao(end - 1, end - 1);
                Token verificaToken = tokens.get(start - 2);

                if(verificaToken.getTipo() != TokenType.REVELIO){
                    switch (operador.getTipo()) {
                        case PLUS:
                            if (tipoEsq.equals("int") && tipoDir.equals("int"))
                                return "int";
                            if (tipoEsq.equals("str") && tipoDir.equals("str"))
                                return "str";
                            System.out.println(
                                    "ERRO ====> OPERADOR '+' INVÁLIDO ENTRE '" + tipoEsq + "' e '" + tipoDir + "'");
                            return "erro";
                        case EQUAL:
                        case GREATER:
                        case LESS:
                            if (tokens.get(start + 2).getTipo() == TokenType.EQUAL) {
                                if (tipoEsq.equals(tipoDir)) {
                                    return "boolean";
                                } else {
                                    System.out.println(
                                            "ERRO ====> OPERADOR DE COMPARAÇÃO ENTRE TIPOS INCOMPATÍVEIS '" + tipoEsq
                                                    + "' e '" + tipoDir
                                                    + "'");
                                    return "erro";
                                }
                            } else {
                                System.out.println("ERRO ====> OPERADOR INVÁLIDO");
                            }
                    }
                }
            }

            if (loopCerto) {
                String tipoEsq = avaliarExpressao(start, start);
                String tipoDir = avaliarExpressao(start + 3, start + 3);
                if (tipoEsq.equals(tipoDir)) {
                    String nomeVar = tokens.get(start + 1).getLexema();
                    // declara a variavel
                    variaveisDeclaradas.put(nomeVar, tipoDir);

                    int primeiroPV = encontrarPontoVirgula(start + 1);
                    int tokendpv = primeiroPV + 1;
                    int tokenAntesDoPV = 0;

                    Token operador = tokens.get(tokendpv + 1);
                    switch (operador.getTipo()) {
                        case EQUAL:
                        case GREATER:
                        case LESS:
                            if (tokens.get(tokendpv + 2).getTipo() == TokenType.EQUAL) {
                                tokenAntesDoPV = tokendpv + 3;
                            } else {
                                tokenAntesDoPV = tokendpv + 2;
                            }
                            break;

                        default:
                            System.out.println("Operador inesperado: " + operador.getTipo());
                            break;
                    }

                    String tipoEsqPV = avaliarExpressao(tokendpv, tokendpv);
                    String tipoDirPV = avaliarExpressao(tokenAntesDoPV,tokenAntesDoPV);
                    if(tipoEsqPV.equals(tipoDirPV)){
                        int segundoPV = encontrarPontoVirgula(tokenAntesDoPV);
                        Token IncOrDec = tokens.get(segundoPV + 1);
                        if(IncOrDec.getTipo() == TokenType.NUMBER){
                            return "boolean";
                        }            

                    }

                }
            }

        } else {
            return "indefinido";
        }
        return "indefinido";
    }
}