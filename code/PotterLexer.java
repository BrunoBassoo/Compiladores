import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisadorLexicoPotter {

    private String codigoFonte;
    private int posicao;
    private int linha;
    private int coluna;

    public AnalisadorLexicoPotter(String codigoFonte) {
        this.codigoFonte = codigoFonte;
        this.posicao = 0;
        this.linha = 1;
        this.coluna = 1;
    }

    public List<Token> analisar() throws LexerException {
        List<Token> tokens = new ArrayList<>();
        Token token;

        while ((token = proximoToken()) != null) {
            tokens.add(token);
        }

        return tokens;
    }
    
    //Função para verificar se a string é um número
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Token proximoToken() throws LexerException {
        ignorarEspacosEmBrancoEComentarios();

        if (posicao >= codigoFonte.length()) {
            return null; // Fim do código fonte
        }

        // Tentativa de casamento com os padrões de token
        for (TipoToken tipo : TipoToken.values()) {
            Pattern padrao = Pattern.compile(tipo.getRegex());
            Matcher matcher = padrao.matcher(codigoFonte.substring(posicao));

            if (matcher.find()) {
                String lexema = matcher.group();
                Token token = new Token(tipo, lexema, linha, coluna);

                //Ajuste da posição, linha e coluna
                int tamanhoLexema = lexema.length();
                posicao += tamanhoLexema;
                coluna += tamanhoLexema;

                //Tratamento para palavras-chave
                if (tipo == TipoToken.IDENTIFICADOR) {
                    if (lexema.equals("magia")) {
                        token = new Token(TipoToken.MAGIA, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("fim_magia")) {
                        token = new Token(TipoToken.FIM_MAGIA, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("feitico")) {
                        token = new Token(TipoToken.FEITICO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("fim_feitico")) {
                        token = new Token(TipoToken.FIM_FEITICO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("retorna")) {
                        token = new Token(TipoToken.RETORNA, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("inteiro")) {
                        token = new Token(TipoToken.INTEIRO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("decimal")) {
                        token = new Token(TipoToken.DECIMAL, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("texto")) {
                        token = new Token(TipoToken.TEXTO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("booleano")) {
                        token = new Token(TipoToken.BOOLEANO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("revelio")) {
                        token = new Token(TipoToken.REVELIO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("protego")) {
                        token = new Token(TipoToken.PROTEGO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("colloportus")) {
                        token = new Token(TipoToken.COLLOPORTUS, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("geminio")) {
                        token = new Token(TipoToken.GEMINIO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("incendio")) {
                        token = new Token(TipoToken.INCENDIO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("lumus")) {
                        token = new Token(TipoToken.LUMUS, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("accio")) {
                        token = new Token(TipoToken.ACCIO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("e")) {
                        token = new Token(TipoToken.E, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("ou")) {
                        token = new Token(TipoToken.OU, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("nao")) {
                        token = new Token(TipoToken.NAO, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("atribui")) {
                        token = new Token(TipoToken.ATRIBUI, lexema, linha, coluna - tamanhoLexema);
                    } else if (lexema.equals("verdadeiro") ) {
                        token = new Token(TipoToken.BOOLEANO, lexema, linha, coluna - tamanhoLexema, true);
                    }
                    else if (lexema.equals("falso")) {
                        token = new Token(TipoToken.BOOLEANO, lexema, linha, coluna - tamanhoLexema, false);
                    }
                }
                else if (tipo == TipoToken.NUMERO)
                {
                    //Verifica se é inteiro ou decimal
                    if(!lexema.contains(".")){
                        token = new Token(TipoToken.INTEIRO, lexema, linha, coluna - tamanhoLexema, Integer.parseInt(lexema));
                    }
                    else{
                        token = new Token(TipoToken.DECIMAL, lexema, linha, coluna - tamanhoLexema, Double.parseDouble(lexema));
                    }
                }
                else if(tipo == TipoToken.STRING){
                    token = new Token(TipoToken.TEXTO, lexema.substring(1, lexema.length() -1), linha, coluna - tamanhoLexema);
                }

                return token;
            }
        }

        // Se chegou aqui, é porque encontrou um caractere inválido
        throw new LexerException("Caractere inválido '" + codigoFonte.charAt(posicao) + "' na linha " + linha + ", coluna " + coluna);
    }

    private void ignorarEspacosEmBrancoEComentarios() {
        while (posicao < codigoFonte.length()) {
            char c = codigoFonte.charAt(posicao);

            // Ignora espaços em branco, tabulações e quebras de linha
            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    linha++;
                    coluna = 1;
                } else {
                    coluna++;
                }
                posicao++;
                continue;
            }

            // Ignora comentários de linha (//)
            if (c == '/' && posicao + 1 < codigoFonte.length() && codigoFonte.charAt(posicao + 1) == '/') {
                while (posicao < codigoFonte.length() && codigoFonte.charAt(posicao) != '\n') {
                    posicao++;
                }
                continue;
            }

            // Ignora comentários de bloco (/* ... */)
            if (c == '/' && posicao + 1 < codigoFonte.length() && codigoFonte.charAt(posicao + 1) == '*') {
                posicao += 2; // Avança os caracteres "/*"
                while (posicao + 1 < codigoFonte.length()) {
                    if (codigoFonte.charAt(posicao) == '*' && codigoFonte.charAt(posicao + 1) == '/') {
                        posicao += 2; // Avança os caracteres "*/"
                        break;
                    } else if (codigoFonte.charAt(posicao) == '\n') {
                        linha++;
                        coluna = 1;
                    } else {
                        coluna++;
                    }
                    posicao++;
                }
                continue;
            }

            break; // Se não for espaço em branco nem comentário, sai do loop
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String codigoFonte = """
            magia Exemplo
                inteiro vida atribui 100;
                texto nome atribui "Harry Potter";
                // Comentário de linha
                /*
                 Comentário
                 de bloco
                */
                lumus("Olá, " + nome + "! Sua vida é: " + vida);
                
                /*
                Vamos testar as novas funcionalidades
                */
                
                //geminio, incendio, etc
                
                geminio(vida > 50){
                    lumus("Você ainda tem bastante vida");
                }
                
                
            fim_magia
            """;

        try {
            AnalisadorLexicoPotter lexer = new AnalisadorLexicoPotter(codigoFonte);
            List<Token> tokens = lexer.analisar();

            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (LexerException e) {
            System.err.println(e.getMessage());
        }
    }
}

// Classe Token
class Token {
    private TipoToken tipo;
    private String lexema;
    private int linha;
    private int coluna;
    private Object valor; // Adicionado para armazenar valor numérico, string, etc.

    public Token(TipoToken tipo, String lexema, int linha, int coluna) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linha = linha;
        this.coluna = coluna;
        this.valor = null; // Valor padrão é null
    }
    public Token(TipoToken tipo, String lexema, int linha, int coluna, Object valor) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linha = linha;
        this.coluna = coluna;
        this.valor = valor; // Atribui um valor
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
    public Object getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "<" + tipo + ", '" + lexema + "', " + linha + ":" + coluna + ">" + (valor != null ? " Valor: " + valor : "");
    }
}

// Enum para os tipos de token
enum TipoToken {
    // Palavras-chave
    MAGIA("magia"),
    FIM_MAGIA("fim_magia"),
    FEITICO("feitico"),
    FIM_FEITICO("fim_feitico"),
    RETORNA("retorna"),
    INTEIRO("inteiro"),
    DECIMAL("decimal"),
    TEXTO("texto"),
    BOOLEANO("booleano"),
    REVELIO("revelio"),
    PROTEGO("protego"),
    COLLOPORTUS("colloportus"),
    GEMINIO("geminio"),
    INCENDIO("incendio"),
    LUMUS("lumus"),
    ACCIO("accio"),
    E("e"),
    OU("ou"),
    NAO("nao"),
    ATRIBUI("atribui"),


    // Identificador e literais
    IDENTIFICADOR("[a-zA-Z_][a-zA-Z0-9_]*"),
    NUMERO("[0-9]+(\\.[0-9]+)?"), // Inteiro ou decimal
    STRING("\"[^\"]*\""),

    // Operadores e pontuação
    MAIS("\\+"),
    MENOS("-"),
    VEZES("\\*"),
    DIVIDE("/"),
    IGUAL("=="),
    DIFERENTE("!="),
    MAIOR(">"),
    MENOR("<"),
    MAIOR_IGUAL(">="),
    MENOR_IGUAL("<="),
    PONTO_E_VIRGULA(";"),
    VIRGULA(","),
    PARENTESES_ESQ("\\("),
    PARENTESES_DIR("\\)"),
    CHAVES_ESQ("\\{"),
    CHAVES_DIR("\\}");


    private final String regex;

    TipoToken(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}

// Exceção para erros léxicos
class LexerException extends Exception {
    public LexerException(String message) {
        super(message);
    }
}
