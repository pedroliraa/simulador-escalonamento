package simulador.escalonador;

import java.util.ArrayList;
import java.util.List;

import simulador.modelo.Processo;

/**
 * First Come, First Served: fila simples, não preemptivo.
 * TODO (Pessoa 3): implementar.
 */
public class FCFS implements Escalonador {

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
        return Integer.MAX_VALUE;
    }

    @Override
    public String nome() {
        return "FCFS";
    }
}
