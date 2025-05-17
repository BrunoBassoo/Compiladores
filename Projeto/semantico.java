// import java.util.HashMap;

// public class Semantico {
//     private static HashMap<String, Integer> tabelaSimbolos = new HashMap<>();

//     public static int evaluate(Node node) {
//         switch (node.type) {
//             case "NUM":
//                 return Integer.parseInt(node.value);

//             case "VAR":
//                 if (!tabelaSimbolos.containsKey(node.value)) {
//                     throw new RuntimeException("Variável não declarada: " + node.value);
//                 }
//                 return tabelaSimbolos.get(node.value);

//             case "ATRIB":
//                 String nomeVar = node.children.get(0).value;
//                 int valor = evaluate(node.children.get(1));
//                 tabelaSimbolos.put(nomeVar, valor);
//                 return valor;

//             case "PLUS":
//                 return evaluate(node.children.get(0)) + evaluate(node.children.get(1));

//             case "MUL":
//                 return evaluate(node.children.get(0)) * evaluate(node.children.get(1));

//             case "SUB":
//                 return evaluate(node.children.get(0)) - evaluate(node.children.get(1));

//             case "DIV":
//                 return evaluate(node.children.get(0)) / evaluate(node.children.get(1));

//             default:
//                 throw new RuntimeException("Nó semântico desconhecido: " + node.type);
//         }
//     }
// }
