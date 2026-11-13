# Entrega Pessoa 3: Gerador e FCFS

Branch: `feature/Gerador-FCFS`, criada a partir de `develop`.

## Gerador de processos

O gerador usa `new Random(semente)`, IDs a partir de 1, instruções de 10 a 50 e prioridades de 1 a 5.
As chegadas começam em zero e avançam pelo intervalo informado; intervalo zero permite chegadas simultâneas.
A semente fixa permite reproduzir a mesma carga para comparar os algoritmos de forma justa.
A carga é sintética e não representa necessariamente a distribuição de processos de um sistema real.

`gerar()` reinicia o Random em cada chamada e retorna objetos novos. `chegadasNoCiclo(ciclo)` consulta essa mesma sequência sem consumi-la, também com objetos novos: use uma única consulta por ciclo ao alimentar uma simulação. A CPU atual já consulta as chegadas na lista completa, então não chame os dois mecanismos juntos.

O CSV usa UTF-8 e cabeçalho `id,instrucoes,prioridade,chegada`. A leitura preserva a ordem das linhas e aceita instruções positivas abaixo de 10 para o cenário pequeno. IDs devem ser positivos e únicos, prioridades entre 1 e 5 e chegadas não negativas. Falhas de conteúdo lançam `IllegalArgumentException`; falhas de arquivo lançam `UncheckedIOException`. A pasta de destino deve existir. A gravação usa instruções totais, mesmo se o processo já executou.

## FCFS: texto para o relatório

O FCFS mantém uma fila FIFO: processos entram no fim e são retirados do início.
É não preemptivo: a CPU executa o processo escolhido até terminar, usando quantum igual a `Integer.MAX_VALUE`.
Sua vantagem é a implementação simples e a preservação da ordem de chegada à fila.
Sua desvantagem é o efeito comboio: um processo longo pode atrasar vários processos curtos.

## Integração pelas Pessoas 1, 2 e 5

O arquivo `cenarios/cenario_oficial.csv` já foi gerado com 10 processos, semente 42 e intervalo 3. Versionar e reutilizar esse arquivo; não regenerar a carga em cada execução. O cenário fixo original permanece intacto.

No Main integrado, ler uma vez e criar cópias independentes para CADA algoritmo:

```java
List<Processo> base = GeradorDeProcessos.lerCsv("cenarios/cenario_oficial.csv");
List<Processo> copia = base.stream().map(Processo::copia)
        .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
Metricas resultado = new CPU(new FCFS(), copia, 0).executar();
```

Repetir a criação de cópias e criar outro escalonador para cada execução. A CPU altera os processos recebidos. `FCFS.fila()` copia a estrutura da fila para exibição, mantendo as referências dos processos. A CPU deve chamar `adicionar` na ordem de chegada; para chegadas simultâneas, adicionar por ID crescente (o gerador já entrega nessa ordem). CSVs externos não são reordenados automaticamente.

O Main atual ainda é demonstrativo e Metricas ainda contém TODOs: sua integração e a tabela final continuam sob responsabilidade das Pessoas 1 e 5. As verificações desta entrega usam os tempos dos processos diretamente, sem depender das métricas incompletas. O relatório final pode incorporar os parágrafos acima; o trecho de SJF será fornecido pelo responsável por ele.

## Verificação (PowerShell na raiz)

```powershell
javac --release 17 -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp out simulador.gerador.TesteGeradorFCFS
```

A execução deve terminar com `OK`. Verifica geração reproduzível, limites, cópias independentes, consultas por ciclo, chegadas simultâneas, cenário vazio, CSV ida/volta, rejeição de IDs duplicados, correspondência do cenário oficial, FIFO, cópia da fila e integração com a CPU, inclusive ociosidade.

No cenário fixo, os fins são `5, 8, 16, 18, 22`, na ordem `1-2-3-4-5`. As esperas são `0, 4, 6, 13, 14`, média `7,4`. São quatro trocas pela definição do PDF; a contagem automática depende da implementação de Metricas.
