package simulador.escalonador;

import java.util.ArrayList;
import java.util.List;

import simulador.modelo.Processo;

/**
 * Round Robin: fila FIFO; quem estoura o quantum volta para o fim da fila
 * (quem devolve é a CPU, chamando adicionar).
 * TODO (Pessoa 4): implementar.
 */
public class RoundRobin implements Escalonador {

    private final int quantum;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public void adicionar(Processo p) {
        // TODO
    }

    @Override
    public Processo proximo() {
        return null; // TODO
    }

    @Override
    public boolean vazio() {
        return true; // TODO
    }

    @Override
    public List<Processo> fila() {
        return new ArrayList<>(); // TODO
    }

    @Override
    public int quantum() {
        return quantum;
    }

    @Override
    public String nome() {
        return "RR(q=" + quantum + ")";
    }
}
