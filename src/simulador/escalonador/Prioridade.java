package simulador.escalonador;

import java.util.ArrayList;
import java.util.List;

import simulador.modelo.Processo;

/**
 * Prioridade: maior prioridade primeiro, não preemptivo.
 * Desempate: chegada, depois menor ID.
 * TODO (Pessoa 4): implementar.
 */
public class Prioridade implements Escalonador {

    /**
     * Contradição do enunciado: true = número maior vence (5 é a maior prioridade).
     * Trocar para false se o professor disser que o menor número vence.
     */
    public static final boolean MAIOR_NUMERO_VENCE = true;

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
        return "Prioridade";
    }
}
