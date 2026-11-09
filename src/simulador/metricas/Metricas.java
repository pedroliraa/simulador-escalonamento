package simulador.metricas;

import java.util.ArrayList;
import java.util.List;

import simulador.modelo.Processo;

/**
 * Transforma a execução em números: ordem, trocas e tempo médio de espera.
 * TODO (Pessoa 5): implementar.
 */
public class Metricas {

    /** Chamado pela CPU toda vez que um processo entra na CPU. Conta trocas e ordem. */
    public void registrarDespacho(Processo p) {
        // TODO
    }

    /** Chamado pela CPU quando um processo termina. */
    public void registrarFim(Processo p) {
        // TODO
    }

    public double tempoMedioEspera() {
        return 0; // TODO
    }

    public int trocas() {
        return 0; // TODO
    }

    /** Sequência de IDs que passaram pela CPU, sem repetir o mesmo ID seguido. */
    public List<Integer> ordemExecucao() {
        return new ArrayList<>(); // TODO
    }

    /** Linha pronta para a tabela do relatório. */
    public String linhaTabela() {
        return ""; // TODO
    }
}
