package simulador.gerador;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import simulador.cpu.CPU;
import simulador.escalonador.FCFS;
import simulador.modelo.Processo;

/** Verificações executáveis sem bibliotecas externas; falhas lançam AssertionError. */
public class TesteGeradorFCFS {
    private static void conferir(boolean condicao, String mensagem) {
        if (!condicao) throw new AssertionError(mensagem);
    }

    private static String dados(List<Processo> ps) {
        StringBuilder resultado = new StringBuilder();
        for (Processo p : ps) resultado.append(p.getId()).append(',')
                .append(p.getInstrucoesTotais()).append(',').append(p.getPrioridade())
                .append(',').append(p.getChegada()).append(';');
        return resultado.toString();
    }

    public static void main(String[] args) throws Exception {
        GeradorDeProcessos gerador = new GeradorDeProcessos(42, 10, 3);
        List<Processo> ps = gerador.gerar();
        conferir(ps.size() == 10, "Quantidade");
        conferir(dados(ps).equals(dados(gerador.gerar())), "Determinismo");
        for (int i = 0; i < ps.size(); i++) {
            Processo p = ps.get(i);
            conferir(p.getId() == i + 1 && p.getChegada() == i * 3, "ID/chegada");
            conferir(p.getInstrucoesTotais() >= 10 && p.getInstrucoesTotais() <= 50, "Instruções");
            conferir(p.getPrioridade() >= 1 && p.getPrioridade() <= 5, "Prioridade");
        }
        ps.get(0).executarInstrucao();
        conferir(gerador.gerar().get(0).getQuantidadeInstrucoes() == ps.get(0).getInstrucoesTotais(), "Objetos independentes");
        conferir(gerador.chegadasNoCiclo(3).get(0).getId() == 2, "Consulta de chegada");
        conferir(gerador.chegadasNoCiclo(2).isEmpty(), "Ciclo sem chegada");
        conferir(new GeradorDeProcessos(42, 3, 0).chegadasNoCiclo(0).size() == 3, "Chegadas simultâneas");
        conferir(new GeradorDeProcessos(42, 0, 3).gerar().isEmpty(), "Cenário vazio");
        Path temporario = Files.createTempFile("cenario-", ".csv");
        try {
            GeradorDeProcessos.salvarCsv(ps, temporario.toString());
            conferir(dados(ps).equals(dados(GeradorDeProcessos.lerCsv(temporario.toString()))), "CSV ida e volta");
            Files.writeString(temporario, "id,instrucoes,prioridade,chegada\n1,5,2,0\n1,3,1,1\n");
            boolean rejeitou = false;
            try { GeradorDeProcessos.lerCsv(temporario.toString()); }
            catch (IllegalArgumentException e) { rejeitou = true; }
            conferir(rejeitou, "Rejeitar CSV inválido");
        } finally { Files.deleteIfExists(temporario); }
        conferir(dados(gerador.gerar()).equals(dados(GeradorDeProcessos.lerCsv("cenarios/cenario_oficial.csv"))), "Cenário oficial");

        FCFS fila = new FCFS();
        conferir(fila.vazio() && fila.proximo() == null, "Fila vazia");
        fila.adicionar(ps.get(0));
        fila.adicionar(ps.get(1));
        fila.fila().clear();
        conferir(fila.proximo() == ps.get(0) && fila.proximo() == ps.get(1), "FIFO e cópia da fila");
        conferir(fila.quantum() == Integer.MAX_VALUE, "Não preemptivo");
        List<Processo> fixo = GeradorDeProcessos.lerCsv("cenarios/cenario_fixo.csv");
        new CPU(new FCFS(), fixo, 0).executar();
        int[] fins = {5, 8, 16, 18, 22};
        int[] esperas = {0, 4, 6, 13, 14};
        for (int i = 0; i < fixo.size(); i++) {
            conferir(fixo.get(i).getFim() == fins[i], "Fim/ordem FCFS");
            conferir(fixo.get(i).tempoEspera() == esperas[i], "Espera FCFS");
        }
        List<Processo> ocioso = List.of(new Processo(1, 2, 1, 3), new Processo(2, 1, 5, 8));
        new CPU(new FCFS(), ocioso, 0).executar();
        conferir(ocioso.get(0).getFim() == 5 && ocioso.get(1).getFim() == 9, "CPU ociosa");
        System.out.println("OK: Gerador, CSV e FCFS validados (ordem 1-2-3-4-5, fim 22, espera média 7,4).");
    }
}
