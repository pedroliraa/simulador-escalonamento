package simulador.modelo;

/**
 * Um processo da simulação. Cada ciclo da CPU executa 1 instrução dele.
 */
public class Processo {

    private final int id;
    private final int instrucoesTotais;    // valor original (10 a 50)
    private int quantidadeInstrucoes;      // restantes; diminui 1 por ciclo
    private final int prioridade;          // 1 a 5
    private final int chegada;             // ciclo em que o processo chega
    private int fim = -1;                  // ciclo em que terminou (-1 = ainda não terminou)

    public Processo(int id, int instrucoes, int prioridade, int chegada) {
        if (instrucoes <= 0) {
            throw new IllegalArgumentException("Processo precisa de pelo menos 1 instrução");
        }
        this.id = id;
        this.instrucoesTotais = instrucoes;
        this.quantidadeInstrucoes = instrucoes;
        this.prioridade = prioridade;
        this.chegada = chegada;
    }

    public void executarInstrucao() {
        quantidadeInstrucoes--;
    }

    public boolean terminou() {
        return quantidadeInstrucoes == 0;
    }

    /** Tempo que ficou na fila sem executar = fim - chegada - instrucoesTotais. */
    public int tempoEspera() {
        return fim - chegada - instrucoesTotais;
    }

    /**
     * Novo objeto com os mesmos dados, "zerado" (instruções cheias, sem fim).
     * Cada algoritmo recebe cópias, senão o primeiro gasta as instruções dos outros.
     */
    public Processo copia() {
        return new Processo(id, instrucoesTotais, prioridade, chegada);
    }

    public int getId() {
        return id;
    }

    public int getInstrucoesTotais() {
        return instrucoesTotais;
    }

    public int getQuantidadeInstrucoes() {
        return quantidadeInstrucoes;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getChegada() {
        return chegada;
    }

    public int getFim() {
        return fim;
    }

    public void setFim(int fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return "P" + id + "(rest=" + quantidadeInstrucoes + ", prio=" + prioridade + ")";
    }
}
