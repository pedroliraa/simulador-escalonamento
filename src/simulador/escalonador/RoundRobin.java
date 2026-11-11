package simulador.escalonador;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import simulador.modelo.Processo;

/**
 * Round Robin: fila FIFO; quem estoura o quantum volta para o fim da fila
 * (quem devolve é a CPU, chamando adicionar).
 * Pessoa 4 (Kailany).
 */
public class RoundRobin implements Escalonador {
    private final int quantum;
    private final Deque<Processo> fila = new ArrayDeque<>();
    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public void adicionar(Processo processoPronto) {
        fila.addLast(processoPronto);
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
        return quantum;
    }

    @Override
    public String nome() {
        return "RR(quantum=" + quantum + ")";
    }

}
