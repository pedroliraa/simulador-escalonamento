
package simulador.cpu;

import java.util.ArrayList;
import java.util.List;

import simulador.escalonador.Escalonador;
import simulador.metricas.Metricas;
import simulador.modelo.Processo;

public class CPU {

    private final Escalonador escalonador;
    private final List<Processo> processos;
    private final int sleepMs;

    public CPU(Escalonador escalonador, List<Processo> processos, int sleepMs) {
        this.escalonador = escalonador;
        this.processos = processos;
        this.sleepMs = sleepMs;
    }

    public Metricas executar() {
        Metricas metricas = new Metricas();
        List<Processo> finalizados = new ArrayList<>();

        Processo atual = null;
        int ciclo = 0;
        int quantumUsado = 0;

        while (finalizados.size() < processos.size()) {

            // 1. Adicionar os processos que chegaram
            for (Processo p : processos) {
                if (p.getChegada() == ciclo) {
                    escalonador.adicionar(p);
                }
            }

            // 2. Verificar se o quantum acabou
            if (atual != null &&
                    quantumUsado >= escalonador.quantum()) {

                escalonador.adicionar(atual);
                atual = null;
            }

            // 3. Escolher o próximo processo
            if (atual == null) {
                atual = escalonador.proximo();
                quantumUsado = 0;

                if (atual != null) {
                    metricas.registrarDespacho(atual);
                }
            }

            // 4. Executar uma instrução
            Processo executado = atual;

            if (atual != null) {
                atual.executarInstrucao();
                quantumUsado++;

                if (atual.terminou()) {
                    atual.setFim(ciclo + 1);
                    finalizados.add(atual);
                    metricas.registrarFim(atual);
                    atual = null;
                }
            }

            // 5. Exibir o estado da CPU
            System.out.printf("[t=%02d] ", ciclo);

            if (executado == null) {
                System.out.print("CPU OCIOSA");
            } else {
                System.out.print("EXECUTANDO: " + executado);
            }

            System.out.print(" | AGUARDANDO: ");
            for (Processo p : escalonador.fila()) {
                System.out.print("P" + p.getId() + " ");
            }

            System.out.print("| FINALIZADOS: ");
            for (Processo p : finalizados) {
                System.out.print("P" + p.getId() + " ");
            }

            System.out.println();

            // Pausa apenas para visualização
            try {
                Thread.sleep(Math.max(0, sleepMs));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            // 6. Avançar o ciclo
            ciclo++;
        }

        return metricas;
    }
}
