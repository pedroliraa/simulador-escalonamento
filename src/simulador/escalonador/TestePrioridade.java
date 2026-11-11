package simulador.escalonador;

import java.util.List;

import simulador.cpu.CPU;
import simulador.modelo.Processo;


public class TestePrioridade {
    public static void main(String[] args) {

        // teste 1: sem CPU. Ordem esperada: P2 (5), P4 (4), P1 (2), P3 (1), null
        Escalonador prioridade = new Prioridade ();
        prioridade.adicionar(new Processo(1, 5, 2, 0));
        prioridade.adicionar(new Processo(2, 3, 5, 1));
        prioridade.adicionar(new Processo(3, 8, 1, 2));
        prioridade.adicionar(new Processo(4, 2, 4, 3));

        System.out.println("TESTE 1: escolhendo na mão");
        System.out.println("Fila: " + prioridade.fila());
        System.out.println("Próximo: " + prioridade.proximo());
        System.out.println("Próximo: " + prioridade.proximo());
        System.out.println("Próximo: " + prioridade.proximo());
        System.out.println("Próximo: " + prioridade.proximo());
        System.out.println("Próximo: " + prioridade.proximo());

        // Teste 2: com a CPU, cenário fixo. Esperado: ordem 1-2-4-5-3, média 5,4
        System.out.println();
        System.out.println("TESTE 2: com a CPU, cenário fixo");
        List<Processo> processos = List.of(
                new Processo(1, 5, 2, 0),
                new Processo(2, 3, 5, 1),
                new Processo(3, 8, 1, 2),
                new Processo(4, 2, 4, 3),
                new Processo(5, 4, 3, 4)
        );
        new CPU(new Prioridade(), processos, 500).executar();

        double soma = 0;
        for (Processo processo : processos) {
            System.out.println("P" + processo.getId() + ": terminou em t=" + processo.getFim()
                    + ", esperou " + processo.tempoEspera());
            soma += processo.tempoEspera();
        }
        System.out.println("Tempo médio de espera: " + soma / processos.size());
    }

}
