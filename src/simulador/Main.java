package simulador;

import java.util.ArrayList;
import java.util.List;

import simulador.cpu.CPU;
import simulador.escalonador.Escalonador;
import simulador.escalonador.FCFS;
import simulador.escalonador.Prioridade;
import simulador.escalonador.RoundRobin;
import simulador.escalonador.SJF;
import simulador.gerador.GeradorDeProcessos;
import simulador.modelo.Processo;

/**
 * Ponto de entrada: roda os 6 cenários (FCFS, SJF, Prioridade, RR q=2, q=5, q=10)
 * sobre o mesmo conjunto de processos.
 * Uso: Main [caminho do CSV]. Sem argumento, usa cenarios/cenario_fixo.csv.
 */
public class Main {

    private static final String CENARIO_PADRAO = "cenarios/cenario_fixo.csv";
    private static final int SLEEP_MS = 0;

    public static void main(String[] args) {
        String caminho = args.length > 0 ? args[0] : CENARIO_PADRAO;
        List<Processo> base = GeradorDeProcessos.lerCsv(caminho);

        List<Escalonador> algoritmos = List.of(
                new FCFS(),
                new SJF(),
                new Prioridade(),
                new RoundRobin(2),
                new RoundRobin(5),
                new RoundRobin(10)
        );

        for (Escalonador algoritmo : algoritmos) {
            List<Processo> copias = new ArrayList<>();
            for (Processo p : base) {
                copias.add(p.copia());
            }

            System.out.println();
            System.out.println("===== " + algoritmo.nome() + " =====");
            new CPU(algoritmo, copias, SLEEP_MS).executar();

            int soma = 0;
            for (Processo p : copias) {
                System.out.printf("P%d: fim=%d, espera=%d%n", p.getId(), p.getFim(), p.tempoEspera());
                soma += p.tempoEspera();
            }
            System.out.printf("Espera média: %.1f%n", soma / (double) copias.size());
        }
    }
}
