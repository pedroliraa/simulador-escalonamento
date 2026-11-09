package simulador.gerador;

import java.util.ArrayList;
import java.util.List;

import simulador.modelo.Processo;

/**
 * Cria os processos (aleatórios com semente fixa) e lê/grava o CSV de cenário.
 * TODO (Pessoa 3): implementar.
 */
public class GeradorDeProcessos {

    private final long semente;
    private final int quantidade;
    private final int intervaloChegada;

    public GeradorDeProcessos(long semente, int quantidade, int intervaloChegada) {
        this.semente = semente;
        this.quantidade = quantidade;
        this.intervaloChegada = intervaloChegada;
    }

    /** Aleatório com semente fixa: instruções 10 a 50, prioridade 1 a 5. */
    public List<Processo> gerar() {
        return new ArrayList<>(); // TODO
    }

    /** Carrega um CSV no formato id,instrucoes,prioridade,chegada. */
    public static List<Processo> lerCsv(String caminho) {
        return new ArrayList<>(); // TODO
    }

    public static void salvarCsv(List<Processo> ps, String caminho) {
        // TODO
    }

    /** Quem chega neste ciclo. */
    public List<Processo> chegadasNoCiclo(int ciclo) {
        return new ArrayList<>(); // TODO
    }
}
