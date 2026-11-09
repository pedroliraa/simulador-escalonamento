package simulador.escalonador;

import java.util.ArrayList;
import java.util.List;

import simulador.modelo.Processo;

/**
 * Shortest Job First: menos instruções restantes primeiro, não preemptivo.
 * Desempate: chegada, depois menor ID.
 * TODO (Pessoa 3): implementar.
 */
public class SJF implements Escalonador {

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
        return "SJF";
    }
}
