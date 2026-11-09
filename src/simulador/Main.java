package simulador;

import java.util.List;

import simulador.modelo.Processo;

/**
 * Ponto de entrada. Por enquanto só cria 3 processos na mão e imprime.
 * No final (Pessoa 1) vai rodar os 6 cenários: FCFS, SJF, Prioridade, RR q=2, q=5, q=10.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Simulador de Escalonamento - esqueleto OK");

        List<Processo> processos = List.of(
                new Processo(1, 5, 2, 0),
                new Processo(2, 3, 5, 1),
                new Processo(3, 8, 1, 2)
        );

        for (Processo p : processos) {
            System.out.println("  " + p + " chega em t=" + p.getChegada());
        }

        // Teste rápido do copia(): a cópia não pode ser afetada pelo original
        Processo original = processos.get(0);
        Processo copia = original.copia();
        original.executarInstrucao();
        System.out.println("Original depois de 1 instrucao: " + original);
        System.out.println("Copia continua cheia:           " + copia);
    }
}
