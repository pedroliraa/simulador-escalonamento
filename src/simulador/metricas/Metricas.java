package simulador.metricas;

import simulador.modelo.Processo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Metricas {
    private final List<Integer> ordem = new ArrayList<>();
    private final Map<Integer, Integer> esperas = new TreeMap<>(); // id -> espera
    private String nome = "";

    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return nome; }

    /** Chamado toda vez que um processo entra na CPU. Não repete o mesmo ID seguido. */
    public void registrarDespacho(Processo p) {
        if (ordem.isEmpty() || ordem.get(ordem.size() - 1) != p.getId()) {
            ordem.add(p.getId());
        }
    }

    public void registrarFim(Processo p) {
        esperas.put(p.getId(), p.tempoEspera());
    }

    public double tempoMedioEspera() {
        return esperas.values().stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    /** Primeira carga não conta: n entradas na ordem = n-1 trocas. */
    public int trocas() { return Math.max(0, ordem.size() - 1); }

    public List<Integer> ordemExecucao() { return new ArrayList<>(ordem); }

    public List<Integer> esperasPorProcesso() { return new ArrayList<>(esperas.values()); }

    public String linhaTabela() {
        String ordemTxt = ordem.toString().replaceAll("[\\[\\] ]", "").replace(",", "-");
        String esp = esperas.values().toString().replaceAll("[\\[\\]]", "");
        return String.format(Locale.forLanguageTag("pt-BR"),
                "%-11s | %-32s | %-30s | %.1f | %d",
                nome, ordemTxt, esp, tempoMedioEspera(), trocas());
    }

    public static void imprimirTabela(List<Metricas> todas) {
        System.out.println("Algoritmo   | Ordem de execução                | Espera por processo            | Média | Trocas");
        for (Metricas m : todas) System.out.println(m.linhaTabela());
    }

    public static void salvarCsv(List<Metricas> todas, String caminho) throws IOException {
        try (PrintWriter w = new PrintWriter(caminho, "UTF-8")) {
            w.println("algoritmo,ordem,esperas,espera_media,trocas");
            for (Metricas m : todas) {
                w.printf(Locale.US, "%s,%s,%s,%.2f,%d%n",
                        m.nome,
                        m.ordem.toString().replaceAll("[\\[\\] ]", "").replace(",", "-"),
                        m.esperas.values().toString().replaceAll("[\\[\\] ]", "").replace(",", "-"),
                        m.tempoMedioEspera(), m.trocas());
            }
        }
    }
}