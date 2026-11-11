package simulador.escalonador;

import java.util.List;

import simulador.cpu.CPU;
import simulador.modelo.Processo;

/**
 * Testes do Round Robin.
 * Pessoa 4 (Kailany).
 */
public class TesteRoundRobin {

    public static void main(String[] args) {

        // TESTE 1: a fila sai na ordem de chegada, sem olhar a prioridade
        System.out.println("TESTE 1: fila sem a CPU");
        Escalonador roundRobin = new RoundRobin(2);
        roundRobin.adicionar(new Processo(1, 5, 2, 0));
        roundRobin.adicionar(new Processo(2, 3, 5, 1));
        roundRobin.adicionar(new Processo(3, 8, 1, 2));
        System.out.println("Nome: " + roundRobin.nome());
        System.out.println("Fila: " + roundRobin.fila());
        System.out.println("Próximo: " + roundRobin.proximo());
        System.out.println("Próximo: " + roundRobin.proximo());
        System.out.println("Próximo: " + roundRobin.proximo());
        System.out.println("Próximo: " + roundRobin.proximo());

        // TESTE 1B: executa um quantum na mão e devolve o processo para o fim da fila
        System.out.println();
        System.out.println("TESTE 1B: mostrando revezamento");
        Escalonador rrNaMao = new RoundRobin(2);
        rrNaMao.adicionar(new Processo(1, 5, 2, 0));
        rrNaMao.adicionar(new Processo(2, 3, 5, 1));
        System.out.println("Fila antes: " + rrNaMao.fila());

        Processo processoNaCPU = rrNaMao.proximo();
        for (int instrucao = 1; instrucao <= rrNaMao.quantum(); instrucao++) {
            processoNaCPU.executarInstrucao();
            System.out.println("Executou: " + processoNaCPU);
        }
        rrNaMao.adicionar(processoNaCPU);
        System.out.println("Fila depois do quantum: " + rrNaMao.fila());

        // TESTE 2: cenário fixo com a CPU. Média esperada: q=2 → 9.4, q=5 → 7.4, q=10 → 7.4
        rodarComCPU(2);
        rodarComCPU(5);
        rodarComCPU(10);
    }

    // Cria processos novos a cada chamada, porque a CPU deixa todos com rest=0
    private static void rodarComCPU(int quantum) {
        System.out.println();
        System.out.println("TESTE 2: com a CPU, quantum = " + quantum);

        List<Processo> processos = List.of(
                new Processo(1, 5, 2, 0),
                new Processo(2, 3, 5, 1),
                new Processo(3, 8, 1, 2),
                new Processo(4, 2, 4, 3),
                new Processo(5, 4, 3, 4)
        );
        for (Processo processo : processos) {
            System.out.println("P" + processo.getId() + ": instruções=" + processo.getInstrucoesTotais() + ", prioridade=" + processo.getPrioridade() + ", chegada=" + processo.getChegada());
        }
        new CPU(new RoundRobin(quantum), processos, 500).executar();

        double somaEspera = 0;
        for (Processo processo : processos) {
            System.out.println("P" + processo.getId() + ": terminou em t=" + processo.getFim() + ", esperou " + processo.tempoEspera());
            somaEspera += processo.tempoEspera();
        }
        System.out.println("Tempo médio de espera: " + somaEspera / processos.size());
    }
}