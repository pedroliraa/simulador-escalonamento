
package simulador.cpu;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import simulador.escalonador.Escalonador;
import simulador.modelo.Processo;

public class TesteCPU {

    // Teste temporario do algoritmo FCFS
    static class FCFSTeste implements Escalonador {

        private final Deque<Processo> fila = new ArrayDeque<>();

        @Override
        public void adicionar(Processo p) {
            fila.addLast(p);
        }

        @Override
        public Processo proximo() {
            return fila.pollFirst();
        }

        @Override
        public boolean vazio() {
            return fila.isEmpty();
        }

        @Override
        public List<Processo> fila() {
            return new ArrayList<>(fila);
        }

        @Override
        public int quantum() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String nome() {
            return "FCFS Teste";
        }
    }

    // Teste temporario do Round Robin
    static class RoundRobinTeste extends FCFSTeste {

        private final int quantum;

        public RoundRobinTeste(int quantum) {
            this.quantum = quantum;
        }

        @Override
        public int quantum() {
            return quantum;
        }

        @Override
        public String nome() {
            return "Round Robin (q=" + quantum + ")";
        }
    }

    public static void main(String[] args) {

        /*List<Processo> processos = List.of(
                new Processo(1, 5, 2, 0),
                new Processo(2, 3, 5, 1),
                new Processo(3, 8, 1, 2),
                new Processo(4, 2, 4, 3),
                new Processo(5, 4, 3, 4)
        );*/
        List<Processo> processos = List.of(
                new Processo(1, 3, 2, 3),
                new Processo(2, 2, 4, 8)
        );

        CPU cpu = new CPU(
                new RoundRobinTeste(10),
                processos,
                0
        );

        cpu.executar();

        System.out.println("\nRESULTADO DO TESTE");

        for (Processo p : processos) {
            System.out.printf(
                    "P%d terminou em t=%d%n",
                    p.getId(),
                    p.getFim()
            );
        }
    }
}
