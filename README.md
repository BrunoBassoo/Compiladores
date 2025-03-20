# ⚡️ PotterScript: A Linguagem de Programação do Mundo Bruxo 🪄

[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-brightgreen.svg)](https://shields.io/)
[![Versão](https://img.shields.io/badge/Versão-0.1-blue.svg)](https://shields.io/)

[![harrypotter](https://media1.tenor.com/m/M9kOGVX74UsAAAAC/abell46s-voldemor.gif)](https://www.youtube.com/watch?v=PIwhCiZeVjg)

## ✨ *Accio Código!* ✨

**PotterScript** é uma linguagem de programação de propósito geral inspirada no universo mágico de Harry Potter. Com ela, você poderá conjurar programas com a mesma facilidade com que um bruxo lança feitiços. Prepare sua varinha (ou melhor, seu teclado) e embarque nessa aventura!

## 📜 Sinopse

Já imaginou controlar o fluxo do seu código com um *Accio* ou um *Alohomora*? Em **PotterScript**, isso é possível! Escreva programas com palavras-chave que remetem ao mundo de Hogwarts, mas com a funcionalidade de uma linguagem de programação completa. Perfeita para *trouxas* (quer dizer, iniciantes) e bruxos experientes!

## ✨ Características Principais

*   **Sintaxe:** Palavras-chave inspiradas em Harry Potter (veja a lista completa abaixo).
*   **Propósito Geral:** Crie programas para qualquer finalidade, não apenas para simular feitiços!
*   **Tipos de Dados Básicos:** `int`, `dec`, `str`, `boolean`.
*   **Estruturas de Controle:** Condicionais (`incendio`, `deflexio`, `protego`) e laços (`accio`, `crucio`).
*   **Funções:** Crie suas próprias funções (feitiços!) e use funções pré-definidas (veja abaixo).
*   **Comentários:** Use `//` para comentários de linha e `/* ... */` para blocos de comentário.
*   **Fácil de Aprender:** Se você já conhece outras linguagens, vai tirar de letra (ou melhor, de varinha!).
*   **Em Desenvolvimento:** A linguagem está sempre evoluindo. Novos feitiços serão adicionados em breve!

## ⌨️ Palavras Reservadas

Categoria | Palavra-chave | Descrição
--------- | ------------- | -----------
Declaração | `magic` ... `endmagic` | Delimita o início e o fim do programa.
Tipos de Dados | `int` | Números inteiros.
ㅤ| `dec` | Números de ponto flutuante.
ㅤ| `str` | Strings (cadeias de caracteres).
ㅤ| `boolean` | Valores lógicos (`true` ou `false`).
ㅤ| `deletrius` | Valores NULL.
Controle de Fluxo | `incendio` | Equivalente a `if`.
ㅤ| `deflexio` | Equivalente a `else if`.
ㅤ| `protego` | Equivalente a `else`.
ㅤ| `alohomora` | Equivalente a `switch`.
ㅤ| `door` | Equivalente a `case`.
ㅤ| `avadakedavra` | Equivalente a `break`.
ㅤ| `relashio` | Equivalente a `continue`.
ㅤ| `accio` | Equivalente a `for`.
ㅤ| `crucio` | Equivalente a `while`.
Funções | `spell` ... `endspell` | Define uma função.
ㅤ| `finite` | Equivalente a `return`.
Entrada/Saída | `revelio` | Exibe mensagens na tela (equivalente a `print`).
ㅤ| `legilimens` | Lê entrada do usuário (equivalente a `input`).
Outros | `and` | Operador lógico "E".
ㅤ| `or` | Operador lógico "OU".
ㅤ| `not` | Operador lógico "NÃO".

## 🧙 Funções Pré-definidas

Função | Descrição | Exemplo
------- | -------- | -------
`reparo([tempo_ms])` | ⏳ "Conserta" o tempo, atrasando a execução (em milissegundos). |  `reparo(2000)`
`obliviate()` | 💫 Limpa a tela. | `obliviate();`
`imperius(mensagem)` | 🗣️ Lança um erro com a `mensagem` personalizada, interrompendo o programa. | `imperius("Você não tem permissão!");`

## 🚀 Exemplos de Código

**Olá, Mundo Bruxo!:**

```potterscript
magic OlaMundoBruxo
    revelio("Olá, Mundo Bruxo!");
endmagic
```

```potterscript
magic CalculadoraMagica

spell somar(int a, int b) finite int {
    finite a + b;
} endspell

spell subtrair(int a, int b) finite int {
    finite a - b;
} endspell

int main() {
    int x;
    int y;
    str operacao;

    revelio("Escolha sua operação: somar ou subtrair?");
    legilimens(operacao);

    revelio("Digite o primeiro número:");
    legilimens(x);

    revelio("Digite o segundo número:");
    legilimens(y);

    incendio (operacao == "somar") {
        revelio("O resultado é: " + somar(x, y));
    } deflexio (operacao == "subtrair") {
        revelio("O resultado é: " + subtrair(x,y));
    } protego {
        revelio("Operação inválida!");
    }
     finite 0;

} endmagic
```

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

---

**Desenvolvido com ⚡️, ✨ e muito 🧪 por fãs de Harry Potter!**
**Lumos Máxima!**
