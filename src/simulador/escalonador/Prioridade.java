package simulador.escalonador;

import java.util.ArrayList;
import java.util.List;
import simulador.modelo.Processo;

/**
 * Prioridade: maior prioridade primeiro, não preemptivo.
 * Desempate: chegada, depois menor ID.
 * Pessoa 4
 */
public class Prioridade implements Escalonador {

    public static final boolean MAIOR_NUMERO_VENCE = true;
    private final List<Processo> fila = new ArrayList<>();

    @Override
    public void adicionar(Processo processoPronto) {
        fila.add(processoPronto);
    }

    @Override
    public Processo proximo() {
        if (fila.isEmpty()) {
            return null;
        }

        Processo maisUrgente = fila.get(0);
        for (Processo candidato : fila) {
            if (temMaisPrioridade(candidato, maisUrgente)) {
                maisUrgente = candidato;
            }
        }

        fila.remove(maisUrgente);
        return maisUrgente;
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
        return "Prioridade";
    }

    private boolean temMaisPrioridade(Processo candidato, Processo atual) {
        if (candidato.getPrioridade() != atual.getPrioridade()) {
            if (MAIOR_NUMERO_VENCE) {
                return candidato.getPrioridade() > atual.getPrioridade();
            }
            return candidato.getPrioridade() < atual.getPrioridade();
        }
        if (candidato.getChegada() != atual.getChegada()) {
            return candidato.getChegada() < atual.getChegada();
        }
        return candidato.getId() < atual.getId();
    }


}

