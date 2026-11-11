package simulador.escalonador;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import simulador.modelo.Processo;

public class SJF implements Escalonador {

    private final List<Processo> fila = new ArrayList<>();

    private static final Comparator<Processo> CRITERIO =
            Comparator.comparingInt(Processo::getQuantidadeInstrucoes)
                      .thenComparingInt(Processo::getChegada)
                      .thenComparingInt(Processo::getId);

    @Override
    public void adicionar(Processo p) {
        fila.add(p);
    }

    @Override
    public Processo proximo() {
        if(fila.isEmpty()){
            return null;
        }
        Processo menor = Collections.min(fila, CRITERIO);
        fila.remove(menor);
        return(menor);
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
        return "SJF";
    }
}
