package simulador.escalonador;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import simulador.modelo.Processo;

/** FIFO não preemptivo. A CPU adiciona os processos conforme chegam. */
public class FCFS implements Escalonador {
    private final Deque<Processo> processos = new ArrayDeque<>();

    @Override
    public void adicionar(Processo p) { processos.addLast(Objects.requireNonNull(p)); }

    @Override
    public Processo proximo() { return processos.pollFirst(); }

    @Override
    public boolean vazio() { return processos.isEmpty(); }

    @Override
    public List<Processo> fila() { return new ArrayList<>(processos); }

    @Override
    public int quantum() { return Integer.MAX_VALUE; }

    @Override
    public String nome() { return "FCFS"; }
}
