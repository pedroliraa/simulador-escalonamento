package simulador.cpu;

import java.util.List;

import simulador.escalonador.Escalonador;
import simulador.metricas.Metricas;
import simulador.modelo.Processo;

/**
 * O "motor": laço de ciclos (1 ciclo = 1 instrução) e o que aparece na tela.
 * Seguir os 6 passos do ciclo descritos no escopo, nessa ordem.
 * TODO (Pessoa 2): implementar.
 */
public class CPU {

    private final Escalonador escalonador;
    private final List<Processo> processos;
    private final int sleepMs;

    public CPU(Escalonador escalonador, List<Processo> processos, int sleepMs) {
        this.escalonador = escalonador;
        this.processos = processos;
        this.sleepMs = sleepMs;
    }

    /** Roda até todos os processos terminarem. */
    public Metricas executar() {
        return new Metricas(); // TODO
    }
}
