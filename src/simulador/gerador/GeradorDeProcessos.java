package simulador.gerador;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import simulador.modelo.Processo;

/** Gera cenários determinísticos e lê/grava o formato CSV comum do grupo. */
public class GeradorDeProcessos {
    private static final String CABECALHO = "id,instrucoes,prioridade,chegada";
    private final long semente;
    private final int quantidade;
    private final int intervaloChegada;

    public GeradorDeProcessos(long semente, int quantidade, int intervaloChegada) {
        if (quantidade < 0 || intervaloChegada < 0) {
            throw new IllegalArgumentException("Quantidade e intervalo devem ser não negativos");
        }
        if (quantidade > 0) Math.multiplyExact(quantidade - 1, intervaloChegada);
        this.semente = semente;
        this.quantidade = quantidade;
        this.intervaloChegada = intervaloChegada;
    }

    /** Cada chamada devolve objetos novos, com a mesma sequência para a mesma semente. */
    public List<Processo> gerar() {
        Random random = new Random(semente);
        List<Processo> processos = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            processos.add(new Processo(i + 1, 10 + random.nextInt(41),
                    1 + random.nextInt(5), i * intervaloChegada));
        }
        return processos;
    }

    /** Aceita instruções positivas, inclusive os valores menores do cenário de validação. */
    public static List<Processo> lerCsv(String caminho) {
        try {
            List<String> linhas = Files.readAllLines(Path.of(caminho), StandardCharsets.UTF_8);
            if (linhas.isEmpty() || !CABECALHO.equals(linhas.get(0).replace("\uFEFF", "").trim())) {
                throw new IllegalArgumentException("Cabeçalho esperado: " + CABECALHO);
            }
            List<Processo> processos = new ArrayList<>();
            Set<Integer> ids = new HashSet<>();
            for (int i = 1; i < linhas.size(); i++) {
                if (linhas.get(i).isBlank()) continue;
                try {
                    String[] campos = linhas.get(i).split(",", -1);
                    if (campos.length != 4) throw new IllegalArgumentException("Esperados quatro campos");
                    int id = Integer.parseInt(campos[0].trim());
                    int instrucoes = Integer.parseInt(campos[1].trim());
                    int prioridade = Integer.parseInt(campos[2].trim());
                    int chegada = Integer.parseInt(campos[3].trim());
                    validar(id, instrucoes, prioridade, chegada);
                    if (!ids.add(id)) throw new IllegalArgumentException("ID duplicado: " + id);
                    processos.add(new Processo(id, instrucoes, prioridade, chegada));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("CSV inválido na linha " + (i + 1) + ": " + e.getMessage(), e);
                }
            }
            return processos;
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler " + caminho, e);
        }
    }

    /** Grava os valores originais, mesmo quando os processos já foram executados. */
    public static void salvarCsv(List<Processo> ps, String caminho) {
        List<String> linhas = new ArrayList<>();
        linhas.add(CABECALHO);
        Set<Integer> ids = new HashSet<>();
        for (Processo p : ps) {
            validar(p.getId(), p.getInstrucoesTotais(), p.getPrioridade(), p.getChegada());
            if (!ids.add(p.getId())) throw new IllegalArgumentException("ID duplicado: " + p.getId());
            linhas.add(p.getId() + "," + p.getInstrucoesTotais() + "," + p.getPrioridade() + "," + p.getChegada());
        }
        try {
            Files.write(Path.of(caminho), linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível salvar " + caminho, e);
        }
    }

    private static void validar(int id, int instrucoes, int prioridade, int chegada) {
        if (id <= 0 || instrucoes <= 0 || prioridade < 1 || prioridade > 5 || chegada < 0) {
            throw new IllegalArgumentException("ID/instruções devem ser positivos, prioridade 1..5 e chegada >= 0");
        }
    }

    /** Consulta sem consumir o cenário; devolve processos novos a cada chamada. */
    public List<Processo> chegadasNoCiclo(int ciclo) {
        List<Processo> chegadas = new ArrayList<>();
        for (Processo p : gerar()) {
            if (p.getChegada() == ciclo) chegadas.add(p);
        }
        return chegadas;
    }
}
