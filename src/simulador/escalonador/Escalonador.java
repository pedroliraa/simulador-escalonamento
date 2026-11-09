package simulador.escalonador;

import java.util.List;

import simulador.modelo.Processo;

/**
 * Contrato comum dos 4 algoritmos. A CPU só conversa com esta interface,
 * então o mesmo código de CPU serve para FCFS, SJF, Prioridade e Round Robin.
 */
public interface Escalonador {

    /** Processo chegou ou voltou para a fila (fim do quantum no RR). */
    void adicionar(Processo p);

    /** Remove e devolve o escolhido. null se a fila estiver vazia. */
    Processo proximo();

    boolean vazio();

    /** Cópia da fila, só para exibir na tela. */
    List<Processo> fila();

    /** Integer.MAX_VALUE se o algoritmo não é preemptivo. */
    int quantum();

    /** "FCFS", "SJF", "Prioridade", "RR(q=5)". */
    String nome();
}
