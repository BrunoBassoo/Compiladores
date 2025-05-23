# ⚡️ PotterScript: A Linguagem de Programação do Mundo Bruxo 🪄

[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-brightgreen.svg)](https://shields.io/)
[![Versão](https://img.shields.io/badge/Versão-1.0-blue.svg)](https://shields.io/)

[![harrypotter](https://media1.tenor.com/m/M9kOGVX74UsAAAAC/abell46s-voldemor.gif)](https://www.youtube.com/watch?v=PIwhCiZeVjg)

## ✨ *Revelio Código!* ✨

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

## ⌨️ Palavras Reservadas

Categoria | Palavra-chave | Descrição
--------- | ------------- | -----------
Declaração | `magic` ... `endmagic` | Delimita o início e o fim do programa.
Tipos de Dados | `int` | Números inteiros.
ㅤ| `dec` | Números de ponto flutuante.
ㅤ| `str` | Strings (cadeias de caracteres).
ㅤ| `boolean` | Valores lógicos (`true` ou `false`).
ㅤ| `NULL` | Valores NULL.
Controle de Fluxo | `incendio` | Equivalente a `if`.
ㅤ| `deflexio` | Equivalente a `else if`.
ㅤ| `protego` | Equivalente a `else`.
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
Comentário | <  > | 

## 🚀 Exemplos de Código

CÓDIGO 1:

```potterscript

magic

<| esse codigo faz um loop e um print |>
<| espero que o charlao goste |>

int a = 0;
a = 1 + "oi"; 

accio(int i = 3; i<10; 2){
    revelio(i + "° numero: " + i);
    str c = i + 2;
    incendio(i == 3){
        revelio("Charlão é muito gente boa");
    } deflexio (i == 5){
        revelio("Eu acho que o Charles da duas aulas pra gente...");
    } deflexio (i == 7){
        revelio("Charlao, voce tem irmão gêmeo??");
    } protego{
        revelio("Eu acho que o seu irmão da aula pra gente de SO! :0");
    }


    a = 5 + i;
    revelio("----> valor do A: " + a);
}

endmagic

```

CÓDIGO 2:


```potterscript
magic

revelio("Digite sua nota: ");
int nota = legilimens();


incendio(nota < 5){
	revelio("abaixo da media");
}
deflexio (nota == 5) {
	revelio("na media");
}
protego{
	revelio("acima da media");
}

endmagic
```

CÓDIGO 3:

```potterscript
magic

revelio("Qual é a caracteristica mais importante pra você?");
revelio("1- Curiosidade");
revelio("2- Lealdade");
revelio("3- Ambição");
revelio("4- Coragem");

<| charlao, vc deveria ser o coordenador do curso. |>


int choice = legilimens();

revelio("Sua casa é...");

incendio(choice == 1){
    revelio("Corvinal");
}
deflexio(choice == 2){
    revelio("Lufa-Lufa");
}
deflexio(choice == 3){
    revelio("Sonserina");
}
deflexio(choice == 4){
    revelio("Grifinória");
}
protego{
    revelio("valor incorreto!);
}

endmagic
```

## 🛠️ Como Compilar e Executar

1.  **Instale o Java** na sua máquina
2.  **Escreva seu código** em um arquivo `.txt` e insira o caminho juntamente com o nome no arquivo `Main.java`.
3.  **Compile** (rode o código). Isso gerará um executável `.kt`.
4.  **Execute**

---

**Desenvolvido com ⚡️, ✨ e muito 🧪 por fãs de Harry Potter!**

**Lumos Máxima!**
