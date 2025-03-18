# ⚡️ PotterScript: A Linguagem de Programação do Mundo Bruxo 🪄

[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-brightgreen.svg)](https://shields.io/)
[![Versão](https://img.shields.io/badge/Versão-0.1-blue.svg)](https://shields.io/)

[![harrypotter](https://media1.tenor.com/m/M9kOGVX74UsAAAAC/abell46s-voldemor.gif)](https://www.youtube.com/watch?v=PIwhCiZeVjg)

## ✨ *Accio Código!* ✨

**PotterScript** é uma linguagem de programação de propósito geral inspirada no universo mágico de Harry Potter. Com ela, você poderá conjurar programas com a mesma facilidade com que um bruxo lança feitiços. Prepare sua varinha (ou melhor, seu teclado) e embarque nessa aventura!

## 📜 Sinopse

Já imaginou controlar o fluxo do seu código com um *Accio* ou um *Alohomora*? Em **PotterScript**, isso é possível! Escreva programas com palavras-chave que remetem ao mundo de Hogwarts, mas com a funcionalidade de uma linguagem de programação completa. Perfeita para *trouxas* (quer dizer, iniciantes) e bruxos experientes!

## ✨ Características Principais

*   **Sintaxe Mágica:** Palavras-chave inspiradas em Harry Potter (veja a lista completa abaixo).
*   **Propósito Geral:** Crie programas para qualquer finalidade, não apenas para simular feitiços!
*   **Tipos de Dados Básicos:** `inteiro`, `decimal`, `texto`, `booleano`.
*   **Estruturas de Controle:** Condicionais (`revelio`, `colloportus`, `protego`) e laços (`geminio`, `incendio`).
*   **Funções:** Crie suas próprias funções (feitiços!) e use funções mágicas pré-definidas (veja abaixo).
*   **Comentários:** Use `//` para comentários de linha e `/* ... */` para blocos de comentário.
*   **Fácil de Aprender:** Se você já conhece outras linguagens, vai tirar de letra (ou melhor, de varinha!).
*   **Em Desenvolvimento:** A linguagem está sempre evoluindo. Novos feitiços serão adicionados em breve!

## ⌨️ Palavras-chave

Categoria | Palavra-chave | Descrição
--------- | ------------- | -----------
Declaração | `magia` ... `fim_magia` | Delimita o início e o fim do programa.
Tipos de Dados | `inteiro` | Números inteiros.
 | `decimal` | Números de ponto flutuante.
 | `texto` | Strings (cadeias de caracteres).
 | `booleano` | Valores lógicos (`verdadeiro` ou `falso`).
Condicional | `revelio` | Equivalente a `if`.
 | `protego` | Equivalente a `else if`.
 | `colloportus` | Equivalente a `else`.
Laços | `geminio` | Equivalente a `while`.
 | `incendio` | Equivalente a `for`.
Funções | `feitico` ... `fim_feitico` | Define uma função.
 | `retorna` | Retorna um valor de uma função.
Entrada/Saída | `lumus` | Exibe mensagens na tela (equivalente a `print`).
 | `nox` | Usada com `lumus` para criar efeitos.
 | `accio` | Lê entrada do usuário (equivalente a `input`).
Outros | `e` | Operador lógico "E".
 | `ou` | Operador lógico "OU".
 | `nao` | Operador lógico "NÃO".
Variados| `atribui` | Atribui valor à variável.

## 🧙 Funções Mágicas Pré-definidas

Função | Descrição | Exemplo
------- | -------- | -------
`avada_kedavra()` | 💀 Encerra o programa imediatamente. | `avada_kedavra();`
`expelliarmus([nome_variavel])` | 💥 "Desarma" uma variável (torna-a indefinida). | `expelliarmus(varinha);`
`reparo([tempo_ms])` | ⏳ "Conserta" o tempo, atrasando a execução (em milissegundos). |  `reparo(2000)`
`obliviate()` | 💫 Limpa a tela. | `obliviate();`
`alohomora(alvo, [posicao])` | 🔑 "Abre" uma string, dividindo-a em partes. | `texto[] partes = alohomora("Expecto Patronum", " ");`
`diffindo(var1, var2, ...)` | ✂️ Troca os valores de duas ou mais variáveis. | `diffindo(nome, sobrenome);`
`lumus_maxima([mensagem])` | 🔦 Exibe uma mensagem com efeito "flash" ou apenas simula o flash. | `lumus_maxima("Lumos Maxima!");`
`aparecium(texto, [separador], [parte])` | 🪶 Revela as partes de uma *string*, dividindo-a por um separador. | `texto[] segredos = aparecium("Segredos de Dumbledore", " ");`
`legilimens(origem, destino)` |🧠 Copia o valor da variável de *origem* para a variável de *destino*. | `legilimens(senhaAntiga, senhaNova);`
`priori_incantatem(variavel, novo_valor)` | 🪄 Tenta converter o valor de uma variável para o tipo de `novo_valor`. | `priori_incantatem(idade, "25");`
`riddikulus([mensagem])` | 😂 Exibe uma mensagem na parte inferior da tela, como uma "resposta engraçada". | `riddikulus("Bicho-papão!");`
`imperius(mensagem)` | 🗣️ Lança um erro com a `mensagem` personalizada, interrompendo o programa. | `imperius("Você não tem permissão!");`
`crucio(comando)` | 😫 Executa um comando do sistema operacional. *Use com extrema cautela e responsabilidade!* | `crucio("ls -l");` (Linux/macOS)

## 🚀 Exemplos de Código

**Olá, Mundo Bruxo!:**

```potterscript
magia OlaMundoBruxo
    lumus("Olá, Mundo Bruxo!");
fim_magia


Calculadora Mágica:

magia CalculadoraMagica

feitico somar(inteiro a, inteiro b) retorna inteiro {
    retorna a + b;
} fim_feitico
feitico subtrair(inteiro a, inteiro b) retorna inteiro{
    retorna a - b;
}fim_feitico

inteiro main() {
    inteiro x;
    inteiro y;
    texto operacao;

    lumus("Escolha sua operação: somar ou subtrair?");
    accio(operacao);

    lumus("Digite o primeiro número:");
    accio(x);

    lumus("Digite o segundo número:");
    accio(y);

    revelio (operacao == "somar") {
        lumus("O resultado é: " + somar(x, y));
    } protego (operacao == "subtrair") {
        lumus("O resultado é: " + subtrair(x,y));
    } colloportus {
        lumus("Operação inválida!");
    }
     retorna 0;
} fim_magia
```

**Usando Funções Mágicas:**

```potterscript
magia FeiticosDivertidos

inteiro main(){
    texto frase = "Expecto Patronum!";
    texto[] partes;

    lumus_maxima("Lumos Maxima!");  // Flash de luz
    reparo(1000);                   // Espera 1 segundo

    partes = alohomora(frase, " ");    // Divide a frase
    lumus(partes[0]);                  // Imprime "Expecto"
    lumus(partes[1]);                  // Imprime "Patronum!"

    riddikulus("Bicho-papão virou aranha de patins!"); // Mensagem na parte inferior

     retorna 0;
}fim_magia
```

**Funções Auxiliares:**

Função | Descrição
------ | --------
tamanho_texto | Retorna o tamanho do texto
caractere_em | Retorna o caractere em uma determinada posição
texto_para_inteiro | Converte um texto para inteiro
inteiro_para_texto | Converte um inteiro para texto

## 🛠️ Como Compilar e Executar

1.  **Instale o PotterScript:** (Instruções detalhadas virão quando o compilador estiver pronto).
2.  **Escreva seu código** em um arquivo com extensão `.ps` (ex: `meu_feitiço.ps`).
3.  **Compile:** Use o comando `potterscript meu_feitiço.ps` (ou similar). Isso gerará um executável.
4.  **Execute:** Rode o executável!

## 🤝 Contribua

Este é um projeto de código aberto! Se você quiser contribuir, siga estes passos:

1.  Faça um *fork* deste repositório.
2.  Crie uma *branch* para a sua funcionalidade (`git checkout -b feature/meu-feitico`).
3.  Faça *commit* das suas mudanças (`git commit -am 'Adiciona o feitiço X'`).
4.  Faça *push* para a *branch* (`git push origin feature/meu-feitico`).
5.  Abra um *Pull Request*.

## 📝 Licença

Este projeto está sob a licença MIT – veja o arquivo [LICENSE.md](LICENSE.md) para detalhes.

---

**Desenvolvido com ⚡️, ✨ e muito 🧪 por fãs de Harry Potter!**
**Lumos Máxima!**
