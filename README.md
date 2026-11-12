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

## Como abrir no Eclipse

`File > Import > General > Existing Projects into Workspace`, escolha a pasta do repositório
e deixe **desmarcado** "Copy projects into workspace". Para rodar: botão direito em
`src/simulador/Main.java` > `Run As > Java Application`.

## Como compilar e rodar pelo terminal

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

## Estrutura

```
src/simulador/
├── Main.java
├── modelo/Processo.java
├── gerador/GeradorDeProcessos.java
├── escalonador/Escalonador.java   (interface)
├── escalonador/FCFS.java, SJF.java, Prioridade.java, RoundRobin.java
├── cpu/CPU.java
└── metricas/Metricas.java
cenarios/    cenário pequeno de validação e cenário oficial
resultados/  saídas geradas
relatorio/   relatório final
```

## Regras do grupo

- **Não mudar as assinaturas** de `Processo` e `Escalonador` sem avisar no grupo.
- Cada pessoa trabalha na sua branch e abre Pull Request para a `main`:
  `cpu`, `fcfs-sjf`, `prioridade-rr`, `metricas`.
- Antes de começar: `git checkout main` e `git pull`.
- Validar sempre no `cenarios/cenario_fixo.csv` antes de rodar o cenário oficial.
- Prioridade: o grupo usa **5 = maior prioridade** (constante
  `Prioridade.MAIOR_NUMERO_VENCE`), até o professor confirmar.
- `main` = versão de entrega. Ninguém commita direto nela.
- `develop` = integração. Cada pessoa cria a sua branch **a partir da `develop`**
  e abre Pull Request **para a `develop`**:
  `feature/cpu`, `feature/fcfs-sjf`, `feature/prioridade-rr`, `feature/metricas`.
- Antes de começar: `git checkout develop` e `git pull`.

## Gerador e FCFS (Pessoa 3)

Implementação, cenário oficial e verificação disponíveis na branch `feature/Gerador-FCFS`.
As orientações para integração e o texto para o relatório estão em [relatorio/pessoa3-gerador-fcfs.md](relatorio/pessoa3-gerador-fcfs.md).
Depois de compilar, execute `java -cp out simulador.gerador.TesteGeradorFCFS` para validar esta parte com a CPU existente.
O SJF é mantido pelo outro integrante. Main e Metricas ainda precisam da integração dos responsáveis.
