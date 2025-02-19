# ⚔️ KombatKompiler: A Linguagem de Programação Mortal Kombat Edition 🐉

[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow.svg)](https://shields.io/)
[![Versão](https://img.shields.io/badge/Versão-0.1-blue.svg)](https://shields.io/)

## 🔥 FATALITY na sua Forma de Programar! 🔥

**KombatKompiler** é uma linguagem de programação de propósito geral com uma *skin* temática inspirada no universo de Mortal Kombat.  Ela combina a funcionalidade de uma linguagem completa com a estética e o humor da icônica franquia de luta. Prepare-se para codificar como um verdadeiro guerreiro do Outworld! 

## 📜 Sinopse

Você já imaginou escrever código com a mesma intensidade de um kombate épico? Com **KombatKompiler**, você pode!  Use palavras-chave como `versus`, `round`, `announce` e `fatality` para criar programas que parecem ter saído diretamente de um fliperama dos anos 90.  Mas não se engane: por trás da aparência "arcade", há uma linguagem poderosa e flexível, pronta para qualquer desafio.

## ✨ Características Principais

*   **Sintaxe Temática:** Palavras-chave inspiradas em Mortal Kombat (veja a lista completa abaixo).
*   **Propósito Geral:** Crie programas para qualquer finalidade, não apenas jogos!
*   **Tipos de Dados Básicos:** `inteiro`, `decimal`, `texto`, `booleano`.
*   **Estruturas de Controle:** Condicionais (`versus`, `rematch`, `flawless_victory`) e laços (`round`, `combo`).
*   **Funções:** Crie suas próprias funções e use funções temáticas pré-definidas (veja abaixo).
*   **Comentários:** Use `//` para comentários de linha e `/* ... */` para blocos de comentário.
*   **Fácil de Aprender:** Se você já programa em outras linguagens, vai se sentir em casa (ou melhor, no Reino da Terra!).
*   **Em Desenvolvimento:**  A linguagem está em constante evolução.  Novas *fatalities* (ops, funcionalidades!) serão adicionadas em breve.

## ⌨️ Palavras-chave

Categoria | Palavra-chave | Descrição
--------- | ------------- | -----------
Declaração | `kombat` ... `fimkombat` | Delimita o início e o fim do programa.
Tipos de Dados | `inteiro` | Números inteiros.
 | `decimal` | Números de ponto flutuante.
 | `texto` | Strings (cadeias de caracteres).
 | `booleano` | Valores lógicos (`verdadeiro` ou `falso`).
Condicional | `versus` | Equivalente a `if`.
 | `rematch` | Equivalente a `else if`.
 | `flawless_victory` | Equivalente a `else`.
Laços | `round` | Equivalente a `while`.
 | `combo` | Equivalente a `for`.
Funções | `funcao` ... `fimfuncao` | Define uma função.
 | `retorna` | Retorna um valor de uma função.
Entrada/Saída | `announce` | Exibe mensagens na tela (equivalente a `print`).
 | `aguarda_comando` | Lê entrada do usuário (equivalente a `input`).
Outros | `e` | Operador lógico "E".
 | `ou` | Operador lógico "OU".
 | `nao` | Operador lógico "NÃO".
Variados| `becomes` | Atribui valor à variável.

## 🕹️ Funções Temáticas Pré-definidas

Função | Descrição | Exemplo
------- | -------- | -------
`subzero([tempo_ms])` | 🧊 "Congela" a execução por um tempo (em milissegundos). Se `tempo_ms` não for fornecido, congela por 1 segundo (1000 ms). | `subzero(500);`
`scorpion([nome_variavel])` | 🦂 "Deleta" uma variável (torna-a indefinida). Se `nome_variavel` não for fornecido, remove a última variável definida. *Cuidado ao usar!* | `scorpion(pontuacao);`
`fatality()` | 💀 Encerra o programa imediatamente. | `fatality();`
`brutality()` | 💥 Encerra a função atual (retorna imediatamente). | `brutality();`
`kano(alvo, [intensidade])` | 🔪 "Corta" uma string em duas. Retorna um array com as partes. | `texto[] partes = kano("Get over here!", 4);`
`raiden([mensagem])` | ⚡ Exibe uma mensagem com efeito de "trovão" (piscando) ou apenas simula o trovão. | `raiden("Prepare-se!");`
`liu_kang(...)` | 🐉 Executa uma sequência de funções (passadas como argumentos) em rápida sucessão, como um "combo". | `liu_kang(soco, chute, especial);`
`kitana(texto, [separador], [parte])` | 🪭 Divide uma string (`texto`) em um array de substrings, usando um `separador` (ex: espaço, vírgula). Se `separador` não for fornecido, usa o espaço como padrão. Se `parte` (um número inteiro) for fornecido, retorna apenas a parte especificada (começando em 0). Se `parte` não for fornecido, retorna todas as partes.  | `texto[] palavras = kitana("Olá Mundo", " ");` <br> `announce(palavras[0]);` // Imprime "Olá" <br> `announce(kitana("Olá,Mundo", ",", 1));` // Imprime "Mundo"
`ermac(...)` | 👻 Troca os valores de duas ou mais variáveis. | `ermac(vida, energia);`
`reptile([mensagem])` | 🦎 Oculta a saída padrão por um tempo e depois a revela (opcionalmente com uma mensagem). | `reptile("Invisível...");`
`smoke()` |💨 Limpa a tela. | `smoke();`
`cyrax(origem, destino)` |🦾 Copia o valor da variável de *origem* para a variável de *destino*. | `cyrax(vidaMaxima, vidaAtual);`
`shang_tsung(variavel, novo_valor)` | 🧙‍♂️ Tenta converter o valor de uma variável para o tipo de `novo_valor`, se possível. Se a conversão for impossível, causa um erro (com `sektor`). | `shang_tsung(idade, "30");`
`cage_kick([mensagem])` | 🦵 Exibe uma mensagem na parte inferior da tela (simulando o *Shadow Kick*). Se `mensagem` não for fornecido, exibe uma mensagem padrão. | `cage_kick("Sombra!");`
`sektor(mensagem)` | 🚀 Lança um erro com a `mensagem` especificada, interrompendo a execução do programa (equivalente a `throw` ou `raise`). | `sektor("Erro: divisão por zero!");`
`shao_kahn(comando)` | 🔨 Executa um comando do sistema operacional (ex: listar arquivos, criar diretórios). *Extremamente poderoso e potencialmente perigoso! Use com extrema cautela.* | `shao_kahn("ls -l");` (Linux/macOS)


## 🚀 Exemplos de Código

**Olá, Mundo! (ou melhor, Olá, Outworld!):**

```markdown
kombat OlaOutworld
inteiro main() {
announce("Olá, Outworld!");
retorna 0;
}fimkombat
```

**Calculadora Simples:**

```markdow
kombat CalculadoraMK

funcao soma(inteiro a, inteiro b) retorna inteiro {
retorna a + b;
} fimfuncao

inteiro main() {
inteiro x;
inteiro y;

announce("Digite um número:");
aguarda_comando(x);

announce("Digite outro número:");
aguarda_comando(y);

announce("A soma é: " + soma(x, y));
 retorna 0;
content_copy
download
Use code with caution.

} fimkombat
```


**Usando Funções Temáticas:**

```markdown
kombat FuncoesTematicas
inteiro main(){
texto frase = "Finish Him!";
texto[] partes;

raiden("PREPARE-SE!"); // Efeito de trovão
subzero(1000);       // Congela por 1 segundo

partes = kano(frase, 6); //Corta a frase
announce(partes[0]);//Finish
announce(partes[1]);//Him!

reptile();//Oculta a saída
subzero(2000);
reptile("Agora você me vê!"); // Revela a saída, com mensagem
retorna 0;
content_copy
download
Use code with caution.

}fimkombat
```
**Usando Funções Temáticas II:**

```markdown
kombat FuncoesTematicasMKII
inteiro main(){
    shao_kahn("mkdir nova_pasta"); //CUIDADO, COMANDO DO SISTEMA OPERACIONAL
    retorna 0;
}fimkombat
```

## 🛠️ Como Compilar e Executar

1.  **Instale o KombatKompiler:** (Instruções detalhadas virão quando o compilador estiver pronto).
2.  **Escreva seu código** em um arquivo com extensão `.kk` (ex: `meu_programa.kk`).
3.  **Compile:** Use o comando `kombatkompiler meu_programa.kk` (ou similar). Isso gerará um executável.
4.  **Execute:** Rode o executável!

## 🤝 Contribua

Este é um projeto de código aberto! Se você quiser contribuir, siga estes passos:

1.  Faça um *fork* deste repositório.
2.  Crie uma *branch* para a sua funcionalidade (`git checkout -b feature/minha-funcionalidade`).
3.  Faça *commit* das suas mudanças (`git commit -am 'Adiciona funcionalidade X'`).
4.  Faça *push* para a *branch* (`git push origin feature/minha-funcionalidade`).
5.  Abra um *Pull Request*.

## 📝 Licença

Este projeto está sob a licença MIT – veja o arquivo [LICENSE.md](LICENSE.md) para detalhes.

---

**Desenvolvido com 🩸, 😅 e muito ☕ por um fã de Mortal Kombat!**
**Kombat com K é mais divertido!**
