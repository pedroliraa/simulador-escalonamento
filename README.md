# Simulador de Escalonamento de Processos

Projeto de Sistemas Operacionais: simulador de CPU em Java que compara 4 algoritmos de
escalonamento (FCFS, SJF, Prioridade e Round Robin com quantum 2, 5 e 10).

## Integrantes

| Pessoa | Bloco | Nome |
|---|---|---|
| 1 | Base, repositório e integração (`Processo`, `Escalonador`, `Main`) | |
| 2 | CPU e exibição na tela (`CPU`) | |
| 3 | Gerador + FCFS + SJF | |
| 4 | Prioridade + Round Robin | |
| 5 | Métricas, testes e relatório | |

## Requisitos

- JDK 17 ou mais novo (`java -version` e `javac -version` para conferir)

## Como rodar

O programa roda os 6 cenários (FCFS, SJF, Prioridade, RR q=2, q=5 e q=10) sobre o mesmo
conjunto de processos. Sem argumento, usa `cenarios/cenario_fixo.csv`.

### Pelo terminal (VS Code, PowerShell, Git Bash, Linux ou Mac)

**PowerShell**, na raiz do repositório:

```powershell
javac --release 17 -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp out simulador.Main
```

**Git Bash / Linux / Mac:**

```bash
javac --release 17 -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out simulador.Main
```

> Se mudou arquivos de lugar, apague a pasta `out/` antes de compilar para não rodar
> classes antigas.

Para usar outro cenário:

```bash
java -cp out simulador.Main cenarios/cenario_oficial.csv
```

### No VS Code

Instale a extensão **Extension Pack for Java**, abra `src/simulador/Main.java` e clique em
**Run** acima do `main`. Abra a pasta do repositório (a raiz) como workspace, senão o
caminho `cenarios/...` não é encontrado.

### No Eclipse

`File > Import > General > Existing Projects into Workspace`, escolha a pasta do repositório
e deixe **desmarcado** "Copy projects into workspace". Para rodar: botão direito em
`src/simulador/Main.java` > `Run As > Java Application`. Se o Eclipse mostrar código
antigo, selecione o projeto e aperte **F5** (Refresh).

## Como testar

Valores esperados da espera média no `cenarios/cenario_fixo.csv`:

| Algoritmo | Espera média |
|---|---|
| FCFS | 7,4 |
| SJF | 5,2 |
| Prioridade | 5,4 |
| RR q=2 | 9,4 |
| RR q=5 | 7,4 |
| RR q=10 | 7,4 |

Testes individuais (depois de compilar):

```bash
java -cp out simulador.escalonador.TesteSJF
java -cp out simulador.escalonador.TestePrioridade
java -cp out simulador.escalonador.TesteRoundRobin
java -cp out simulador.gerador.TesteGeradorFCFS
java -cp out simulador.metricas.TesteBateria
```

## Estrutura

```
src/simulador/
├── Main.java
├── modelo/Processo.java
├── gerador/GeradorDeProcessos.java
├── escalonador/Escalonador.java   (interface)
├── escalonador/FCFS.java, SJF.java, Prioridade.java, RoundRobin.java
├── cpu/CPU.java
└── metricas/Metricas.java, Bateria.java
cenarios/    cenário pequeno de validação e cenário oficial
resultados/  saídas geradas
relatorio/   relatório final
```

## Regras do grupo

- **Não mudar as assinaturas** de `Processo` e `Escalonador` sem avisar no grupo.
- `main` = versão de entrega. Ninguém commita direto nela.
- `develop` = integração. Cada pessoa cria a sua branch **a partir da `develop`**
  e abre Pull Request **para a `develop`**:
  `feature/cpu`, `feature/fcfs-sjf`, `feature/prioridade-rr`, `feature/metricas`.
- Antes de começar: `git checkout develop` e `git pull`.
- Na entrega, um único Pull Request de `develop` para `main`.
- Validar sempre no `cenarios/cenario_fixo.csv` antes de rodar o cenário oficial.
- Prioridade: o grupo usa **5 = maior prioridade** (constante
  `Prioridade.MAIOR_NUMERO_VENCE`)

## Gerador e FCFS (Pessoa 3)

Implementação, cenário oficial e verificação estão em `feature/Gerador-FCFS`.
As orientações de integração e o texto para o relatório estão em
[relatorio/pessoa3-gerador-fcfs.md](relatorio/pessoa3-gerador-fcfs.md).
Para validar esta parte com a CPU: `java -cp out simulador.gerador.TesteGeradorFCFS`.
A `Main` roda os 6 cenários sobre o CSV informado.
